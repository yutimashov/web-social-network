package com.getjavajob.training.timashovy.socialnetwork.web.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.getjavajob.training.timashovy.socialnetwork.domain.account.Account;
import com.getjavajob.training.timashovy.socialnetwork.domain.account.Phone;
import com.getjavajob.training.timashovy.socialnetwork.service.interfaces.AccountService;
import com.getjavajob.training.timashovy.socialnetwork.service.interfaces.AdminService;
import com.getjavajob.training.timashovy.socialnetwork.service.interfaces.MessageService;
import com.getjavajob.training.timashovy.socialnetwork.service.interfaces.PhoneService;
import com.getjavajob.training.timashovy.socialnetwork.web.dto.AccountDto;
import com.getjavajob.training.timashovy.socialnetwork.web.mappers.AccountMapper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Objects;

import static com.getjavajob.training.timashovy.socialnetwork.domain.account.PhoneType.PERSONAL;
import static com.getjavajob.training.timashovy.socialnetwork.domain.account.PhoneType.WORKING;
import static com.getjavajob.training.timashovy.socialnetwork.web.util.StatusTypes.DELETE_ACCOUNT_SUCCESS;

@Controller
@RequestMapping("/account")
public class AccountController {

    private final AccountService accountService;
    private final MessageService messageService;
    private final PhoneService phoneService;
    private final AdminService adminService;

    public AccountController(AccountService accountService, MessageService messageService, PhoneService phoneService,
                             AdminService adminService) {
        this.accountService = accountService;
        this.messageService = messageService;
        this.phoneService = phoneService;
        this.adminService = adminService;
    }

    @GetMapping
    public String account(@RequestParam("id") long accountId, Model model) {
        if (accountService.getById(accountId).isPresent()) {
            model.addAttribute("account", accountService.getById(accountId).get());
            model.addAttribute("wallPosts", messageService.getAllAccountWallMessages(accountId));
            model.addAttribute("accountService", accountService);
        }
        return "account/account";
    }

    @GetMapping("/all")
    public String allAccounts(Model model) {
        model.addAttribute("accounts", accountService.getAll());
        return "account/all";
    }

    @GetMapping("/delete")
    public String deleteAccount(@RequestParam("id") long id, @SessionAttribute("account") Account account) {
        Long accountIdToDelete = id;
        accountService.delete(accountIdToDelete);
        if (!Objects.equals(account.getId(), accountIdToDelete)) {
            return "redirect:/account/all";
        } else {
            return "redirect:/login" + DELETE_ACCOUNT_SUCCESS;
        }
    }

    @GetMapping("/make-admin")
    public String makeAdmin(@RequestParam("id") long id) {
        adminService.makeAdmin(id);
        return "redirect:/account?id=" + id;
    }

    @GetMapping("/edit")
    public String editAccount(Model model, @RequestParam("id") long accountId) {
        if (accountService.getById(accountId).isPresent()) {
            model.addAttribute("account", accountService.getById(accountId).get());
            model.addAttribute("avatarInputStream", accountService.getById(accountId).get().getAvatar());
            model.addAttribute("personalPhones", phoneService.getPersonalPhoneNumbers(accountId));
            model.addAttribute("workingPhones", phoneService.getWorkPhoneNumbers(accountId));
            return "account/edit";
        } else {
            return "error/404";
        }
    }

    @PostMapping("/edit")
    public String processAccountEditing(@ModelAttribute AccountDto accountDto, @RequestParam("id") Long accountId,
                                        HttpServletRequest req) throws IOException {
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
            phoneService.deleteById(deletedNode.get(i).asLong());
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
            phoneService.create(new Phone(PERSONAL, phoneNode.get("number").asText(), accountId));
        }
        for (JsonNode phoneNode : workingAddedNode) {
            phoneService.create(new Phone(WORKING, phoneNode.get("number").asText(), accountId));
        }
    }

    private JsonNode getRootNode(HttpServletRequest req) {
        String phonesJSON = req.getParameter("phoneData");
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            return objectMapper.readTree(phonesJSON);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

}
