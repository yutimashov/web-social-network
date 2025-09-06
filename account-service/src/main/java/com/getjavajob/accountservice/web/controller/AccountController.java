package com.getjavajob.accountservice.web.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.getjavajob.accountservice.exception.WebException;
import com.getjavajob.accountservice.service.AccountService;
import com.getjavajob.accountservice.web.dto.AccountDto;
import com.getjavajob.accountservice.web.dto.AccountMapper;
import com.getjavajob.accountservice.web.feignclient.FriendshipClient;
import com.getjavajob.accountservice.web.feignclient.MessageClient;
import com.getjavajob.accountservice.web.feignclient.PhoneClient;
import com.getjavajob.training.timashovy.socialnetwork.domain.account.Account;
import com.getjavajob.training.timashovy.socialnetwork.domain.phone.Phone;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static com.getjavajob.accountservice.web.util.UrlStatusParameter.DELETE_ACCOUNT_SUCCESS_STATUS;
import static com.getjavajob.training.timashovy.socialnetwork.domain.phone.PhoneType.PERSONAL;
import static com.getjavajob.training.timashovy.socialnetwork.domain.phone.PhoneType.WORKING;
import static org.slf4j.LoggerFactory.getLogger;

/**
 * All functionality for managing accounts using requests.
 */
@Controller
@RequestMapping("/account")
public class AccountController {

    private static final Logger logger = getLogger(AccountController.class);

    private final AccountService accountService;
    private final MessageClient messageClient;
    private final PhoneClient phoneClient;
    private final FriendshipClient friendshipClient;

    public AccountController(AccountService accountService, MessageClient messageClient, PhoneClient phoneClient,
                             FriendshipClient friendshipClient) {
        this.accountService = accountService;
        this.messageClient = messageClient;
        this.phoneClient = phoneClient;
        this.friendshipClient = friendshipClient;
    }

    /**
     * Account page
     *
     * @param accountId id of requested account
     * @param model     model
     * @param account   session account
     * @return view of account or 404 page
     */
    @GetMapping
    public String account(@RequestParam("id") Long accountId,
                          Model model,
                          @SessionAttribute("account") Account account) {
        Optional<Account> currentAccount = accountService.getById(accountId);
        if (currentAccount.isPresent()) {
            logger.info("Get page of account: id={}", currentAccount.get().getId());
            if (friendshipClient.checkExistence(currentAccount.get().getId(), accountId).getBody()) {
                model.addAttribute("alreadySentFriendRequest", true);
            }
            model.addAttribute("account", currentAccount.get());
            model.addAttribute("wallPosts", messageClient.getAccountWallMessages(accountId).getBody());
            model.addAttribute("accountService", accountService);
            model.addAttribute("personalPhones", phoneClient.getPhoneNumbers(accountId, PERSONAL)
                    .getBody());
            model.addAttribute("workingPhones", phoneClient.getPhoneNumbers(accountId, WORKING)
                    .getBody());
            return "account/account";
        } else {
            return "error/404";
        }
    }

    /**
     * All accounts
     *
     * @param model   page model
     * @param account session account
     * @param lastId  account's last id on the current page
     * @param limit   accounts per page limit
     * @param isAjax  type of query
     * @return page with all accounts or 404 page
     */
    @GetMapping("/all")
    public String allAccounts(Model model,
                              @SessionAttribute("account") Account account,
                              @RequestParam(required = false, defaultValue = "0") Long lastId,
                              @RequestParam(defaultValue = "100") int limit,
                              @RequestParam(required = false, defaultValue = "false") boolean isAjax) {
        List<Account> accountsBatch = accountService.getAccounts(account.getId(), lastId, limit);
        if (!accountsBatch.isEmpty()) {
            Long newLastId = accountsBatch.stream()
                    .map(Account::getId)
                    .max(Long::compareTo)
                    .orElse(lastId);
            model.addAttribute("accounts", accountsBatch);
            model.addAttribute("lastId", newLastId);
            model.addAttribute("limit", limit);
        } else {
            model.addAttribute("accounts", Collections.emptyList());
            model.addAttribute("hasMore", false);
        }
        return !isAjax ? "account/all" : "account/ajaxFragment";
    }

    /**
     * Delete account.
     * Appropriate service method has role check limitations.
     *
     * @param id      deleting account id
     * @param account session account
     * @return redirect to all accounts page or login (if account deleted themselves)
     */
    @DeleteMapping("/delete")
    public void deleteAccount(@RequestParam("id") Long id,
                              @SessionAttribute Account account,
                              HttpServletRequest request,
                              HttpServletResponse response) throws IOException {
        accountService.delete(id);
        logger.info("Account={} is deleted successfully", id);
        if (!Objects.equals(account.getId(), id)) {
            response.sendRedirect(generateRedirectBaseURL(request, "/account/all"));
        } else {
            response.sendRedirect(generateRedirectBaseURL(request, "/login" + DELETE_ACCOUNT_SUCCESS_STATUS
                    .getValue()));
        }
    }

    /**
     * Make application admin.
     *
     * @param id new admin account id
     */
    @GetMapping("/make-admin")
    public void makeAdmin(@RequestParam("id") Long id,
                          HttpServletRequest request,
                          HttpServletResponse response) throws IOException {
        accountService.makeAdmin(id);
        response.sendRedirect(generateRedirectBaseURL(request, "/account?id=" + id));
    }

    /**
     * Editing account page.
     *
     * @param model     account editing page model
     * @param accountId editing account id
     * @return account editing page or 404 page
     */
    @GetMapping("/edit")
    public String edit(Model model,
                       @RequestParam("id") Long accountId) {
        Optional<Account> maybeAccount = accountService.getById(accountId);
        if (maybeAccount.isPresent()) {
            model.addAttribute("account", maybeAccount.get());
            model.addAttribute("avatarInputStream", maybeAccount.get().getAvatar());
            model.addAttribute("personalPhones", phoneClient.getPhoneNumbers(accountId, PERSONAL));
            model.addAttribute("workingPhones", phoneClient.getPhoneNumbers(accountId, WORKING));
            return "account/edit";
        } else {
            return "error/404";
        }
    }

    @PostMapping("/edit")
    public void update(@ModelAttribute AccountDto accountDto,
                       @RequestParam("id") Long accountId,
                       HttpServletRequest req,
                       HttpServletResponse response) throws IOException {
        accountService.update(accountId, new AccountMapper().toAccount(accountDto));
        addPhones(req, accountId);
        updatePhones(req);
        deletePhones(req);
        response.sendRedirect(generateRedirectBaseURL(req, "/account?id=" + accountId));
    }

/*    private String generateRedirectBaseURL(HttpServletRequest request, Long accountId) {
        String scheme = request.getScheme();
        String serverName = request.getHeader("X-Forwarded-Host");
        if (serverName == null || serverName.isEmpty()) {
            serverName = request.getServerName();
        }
        return scheme + "://" + serverName + "/account?id=" + accountId;
    }*/

    private String generateRedirectBaseURL(HttpServletRequest request, String path) {
        String scheme = request.getScheme() + "://";
        String serverName = request.getHeader("X-Forwarded-Host");
        if (serverName == null || serverName.isEmpty()) {
            serverName = request.getServerName();
        }
        return scheme + serverName + path;
    }

    private void deletePhones(HttpServletRequest req) {
        JsonNode rootNode = getRootNode(req);
        JsonNode deletedNode = rootNode.get("deletedPhonesIds");
        for (int i = 0; i < deletedNode.size(); i++) {
            phoneClient.delete(deletedNode.get(i).asLong());
        }
    }

    private void updatePhones(HttpServletRequest req) {
        JsonNode rootNode = getRootNode(req);
        JsonNode updatedNode = rootNode.get("updated");
        for (JsonNode phoneNode : updatedNode) {
            phoneClient.update(phoneNode.get("id").asLong(), phoneNode.get("number").asText());
        }
    }

    private void addPhones(HttpServletRequest req, Long accountId) {
        JsonNode rootNode = getRootNode(req);
        JsonNode addedNode = rootNode.get("added");
        JsonNode personalAddedNode = addedNode.get("personal");
        JsonNode workingAddedNode = addedNode.get("working");
        for (JsonNode phoneNode : personalAddedNode) {
            phoneClient.create(new Phone(PERSONAL, phoneNode.get("number").asText(),
                    accountService.getById(accountId).get()));
        }
        for (JsonNode phoneNode : workingAddedNode) {
            phoneClient.create(new Phone(WORKING, phoneNode.get("number").asText(),
                    accountService.getById(accountId).get()));
        }
    }

    private JsonNode getRootNode(HttpServletRequest req) {
        String phonesJSON = req.getParameter("phoneData");
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            return objectMapper.readTree(phonesJSON);
        } catch (JsonProcessingException e) {
            throw new WebException("Problems with processing operations with phone numbers.");
        }
    }

}
