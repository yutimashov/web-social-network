package com.getjavajob.training.timashovy.socialnetwork.web.controllers.admin;

import com.getjavajob.training.timashovy.socialnetwork.common.account.Account;
import com.getjavajob.training.timashovy.socialnetwork.service.interfaces.AccountService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;

import static com.getjavajob.training.timashovy.socialnetwork.web.util.StatusTypes.DELETE_ACCOUNT_SUCCESS;

@RequestMapping("/account/delete")
@Controller
public class DeleteAccountController {

    private final AccountService accountService;

    public DeleteAccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @GetMapping
    protected String doGet(@RequestParam("id") long id, @SessionAttribute("account") Account account) {
        Long accountIdToDelete = id;
        accountService.delete(accountIdToDelete);
        if (!Objects.equals(account.getId(), accountIdToDelete)) {
            return "redirect:/account/all";
        } else {
            return "redirect:/login" + DELETE_ACCOUNT_SUCCESS;
        }
    }

}
