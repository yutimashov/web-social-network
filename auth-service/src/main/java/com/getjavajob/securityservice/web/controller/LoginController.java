package com.getjavajob.securityservice.web.controller;

import org.slf4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import static org.slf4j.LoggerFactory.getLogger;

@Controller
public class LoginController {

    private static final Logger logger = getLogger(LoginController.class);

    @GetMapping("/login")
    public String handleLoginPage() {
        return "auth/login";
    }

}
