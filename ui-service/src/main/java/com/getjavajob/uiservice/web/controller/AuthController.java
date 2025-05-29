package com.getjavajob.uiservice.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AuthController {

    @GetMapping("/login")
    public String handleLoginPage() {
        return "auth/login";
    }

    @GetMapping("/register")
    public String handleRegisterPage() {
        return "auth/register";
    }

    @GetMapping("/accounts")
    public String handleAccounts() {
        return "account/all";
    }

}
