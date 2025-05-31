package com.getjavajob.securityservice.web.controller;

import com.getjavajob.securityservice.service.password.PasswordService;
import com.getjavajob.training.timashovy.socialnetwork.domain.account.Account;
import com.getjavajob.training.timashovy.socialnetwork.domain.password.Password;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Controller
public class PasswordController {

    private final PasswordService passwordService;

    public PasswordController(PasswordService passwordService) {
        this.passwordService = passwordService;
    }

    @PostMapping("password/create")
    public ResponseEntity<Password> create(@RequestBody Account account, @RequestBody String rawPassword) {
        Password password = passwordService.create(account, rawPassword);
        return ResponseEntity.status(HttpStatus.OK)
                .body(password);
    }

}
