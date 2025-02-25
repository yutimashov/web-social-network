package com.getjavajob.training.timashovy.socialnetwork.web.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.getjavajob.training.timashovy.socialnetwork.domain.account.Account;
import com.getjavajob.training.timashovy.socialnetwork.domain.phone.Phone;
import com.getjavajob.training.timashovy.socialnetwork.service.interfaces.AccountService;
import com.getjavajob.training.timashovy.socialnetwork.service.interfaces.AdminService;
import com.getjavajob.training.timashovy.socialnetwork.service.interfaces.MessageService;
import com.getjavajob.training.timashovy.socialnetwork.service.interfaces.PhoneService;
import com.getjavajob.training.timashovy.socialnetwork.service.interfaces.XmlDataHandler;
import com.getjavajob.training.timashovy.socialnetwork.service.util.exceptions.ServiceException;
import com.getjavajob.training.timashovy.socialnetwork.web.dto.AccountDto;
import com.getjavajob.training.timashovy.socialnetwork.web.mappers.AccountMapper;
import com.getjavajob.training.timashovy.socialnetwork.web.util.exceptions.WebException;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static com.getjavajob.training.timashovy.socialnetwork.domain.phone.PhoneType.PERSONAL;
import static com.getjavajob.training.timashovy.socialnetwork.domain.phone.PhoneType.WORKING;
import static com.getjavajob.training.timashovy.socialnetwork.web.util.UrlStatusParameter.DELETE_ACCOUNT_SUCCESS_STATUS;
import static org.slf4j.LoggerFactory.getLogger;
import static org.springframework.http.HttpHeaders.CONTENT_DISPOSITION;
import static org.springframework.http.HttpHeaders.CONTENT_TYPE;
import static org.springframework.http.HttpStatus.NOT_FOUND;
import static org.springframework.http.HttpStatus.OK;

@Controller
@RequestMapping("/account")
public class AccountController {

    private final AccountService accountService;
    private final MessageService messageService;
    private final PhoneService phoneService;
    private final AdminService adminService;
    private final XmlDataHandler xmlDataHandler;
    private final int accountPageSize = 40;
    private final int accountPageInitialNumber = 0;
    private static final Logger logger = getLogger(AccountController.class);

    public AccountController(AccountService accountService, MessageService messageService, PhoneService phoneService,
                             AdminService adminService, XmlDataHandler xmlDataHandler) {
        this.accountService = accountService;
        this.messageService = messageService;
        this.phoneService = phoneService;
        this.adminService = adminService;
        this.xmlDataHandler = xmlDataHandler;
    }

    @GetMapping
    public String account(@RequestParam("id") Long accountId,
                          @SessionAttribute Account account,
                          Model model) {
        Optional<Account> maybeAccount = Objects.equals(accountId, account.getId()) ? Optional.of(account)
                : accountService.getById(accountId);
        if (maybeAccount.isPresent()) {
            if (accountService.checkFriendshipRecordExistence(maybeAccount.get().getId(), accountId)) {
                model.addAttribute("alreadySentFriendRequest", true);
            }
            model.addAttribute("account", maybeAccount.get());
            model.addAttribute("wallPosts", messageService.getAllAccountWallMessages(accountId));
            model.addAttribute("accountService", accountService);
            model.addAttribute("personalPhones", phoneService.getPhoneNumbers(accountId, PERSONAL));
            model.addAttribute("workingPhones", phoneService.getPhoneNumbers(accountId, WORKING));
            return "account/account";
        } else {
            return "error/404";
        }
    }

    @GetMapping("/all")
    public String allAccounts(Model model,
                              @SessionAttribute Account account,
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

    @GetMapping("/delete")
    public String deleteAccount(@RequestParam("id") Long id,
                                @SessionAttribute Account account) {
        accountService.delete(id);
        logger.info("Account={} is deleted successfully", id);
        if (!Objects.equals(account.getId(), id)) {
            return "redirect:/account/all";
        } else {
            return "redirect:/login" + DELETE_ACCOUNT_SUCCESS_STATUS.getValue();
        }
    }

    @GetMapping("/make-admin")
    public String makeAdmin(@RequestParam("id") Long id) {
        adminService.makeAdmin(id);
        logger.info("Account={} becomes admin", id);
        return "redirect:/account?id=" + id;
    }

    @GetMapping("/edit")
    public String edit(Model model,
                       @RequestParam("id") Long accountId) {
        Optional<Account> maybeAccount = accountService.getById(accountId);
        if (maybeAccount.isPresent()) {
            model.addAttribute("account", maybeAccount.get());
            model.addAttribute("avatarInputStream", maybeAccount.get().getAvatar());
            logger.info("trying get phones for account id: {}", accountId);
            logger.info("personal phones: {}", phoneService.getPhoneNumbers(accountId, PERSONAL));
            logger.info("working phones: {}", phoneService.getPhoneNumbers(accountId, WORKING));
            model.addAttribute("personalPhones", phoneService.getPhoneNumbers(accountId, PERSONAL));
            model.addAttribute("workingPhones", phoneService.getPhoneNumbers(accountId, WORKING));
            return "account/edit";
        } else {
            return "error/404";
        }
    }

    @PostMapping("/xml-update")
    public String updateXml(@RequestParam("file") MultipartFile file,
                            @RequestParam("id") Long accountId) {
        try {
            xmlDataHandler.updateAccount(file.getInputStream(), accountId);
        } catch (IOException e) {
            logger.error("IO error while updating account with id={}", accountId, e);
            throw new WebException("Failed to process the file. Please try again later.");
        } catch (ServiceException e) {
            logger.error("Service error for account with id={}", accountId, e);
            throw new WebException("An error occurred while processing the account data. Please try again later.");
        }
        return "redirect:/account/edit?id=" + accountId;
    }

    @GetMapping("/xml-download")
    public ResponseEntity<byte[]> downloadAccountXml(@RequestParam("id") Long accountId) {
        Optional<Account> maybeAccount = accountService.getById(accountId);
        if (maybeAccount.isPresent()) {
            try {
                byte[] accountData = xmlDataHandler.loadAccountData(maybeAccount.get());
                HttpHeaders headers = new HttpHeaders();
                headers.add(CONTENT_DISPOSITION, "attachment; filename=account.xml");
                headers.add(CONTENT_TYPE, "application/xml");
                return new ResponseEntity<>(accountData, headers, OK);
            } catch (ServiceException e) {
                throw new WebException("An error occurred while processing account data to xml file. "
                        + "Please try again later.");
            }
        } else {
            return new ResponseEntity<>(NOT_FOUND);
        }
    }

    @PostMapping("/edit")
    public String update(@ModelAttribute AccountDto accountDto,
                         @RequestParam("id") Long accountId,
                         HttpServletRequest req) {
        accountService.update(accountId, new AccountMapper().toAccount(accountDto));
        addPhones(req, accountId);
        updatePhones(req);
        deletePhones(req);
        return "redirect:/account?id=" + accountId;
    }

    private void deletePhones(HttpServletRequest req) {
        JsonNode rootNode = getRootNode(req);
        JsonNode deletedNode = rootNode.get("deletedPhonesIds");
        for (int i = 0; i < deletedNode.size(); i++) {
            phoneService.delete(deletedNode.get(i).asLong());
        }
    }

    private void updatePhones(HttpServletRequest req) {
        JsonNode rootNode = getRootNode(req);
        JsonNode updatedNode = rootNode.get("updated");
        for (JsonNode phoneNode : updatedNode) {
            phoneService.update(phoneNode.get("id").asLong(), phoneNode.get("number").asText());
        }
    }

    private void addPhones(HttpServletRequest req, Long accountId) {
        JsonNode rootNode = getRootNode(req);
        JsonNode addedNode = rootNode.get("added");
        JsonNode personalAddedNode = addedNode.get("personal");
        JsonNode workingAddedNode = addedNode.get("working");
        for (JsonNode phoneNode : personalAddedNode) {
            phoneService.create(new Phone(PERSONAL, phoneNode.get("number").asText(),
                    accountService.getById(accountId).get()));
        }
        for (JsonNode phoneNode : workingAddedNode) {
            phoneService.create(new Phone(WORKING, phoneNode.get("number").asText(),
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
