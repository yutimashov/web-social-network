package com.getjavajob.accountservice.web.api;

import com.getjavajob.accountservice.service.AccountService;
import com.getjavajob.training.timashovy.socialnetwork.domain.account.Account;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Optional;

import static org.springframework.http.HttpStatus.OK;

@Controller
@RequestMapping("/api/account")
public class AccountApiController {

    private final AccountService accountService;

    public AccountApiController(AccountService accountService) {
        this.accountService = accountService;
    }

    @GetMapping
    public ResponseEntity<Optional<Account>> accountById(@RequestParam Long id) {
        return ResponseEntity
                .status(OK)
                .body(accountService.getById(id));
    }

    @GetMapping("/update-by-id")
    public void updateById(@RequestParam Account account, @RequestParam Long accountId) {
        accountService.updateById(account, accountId);
    }

}
