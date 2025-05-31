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

    @GetMapping("/register")
    public String handleRegisterPage() {
        return "auth/register";
    }

/*    @PostMapping("/register")
    public String processAccountRegistration(@ModelAttribute AccountDto accountDto,
                                             @RequestParam("password") String password,
                                             @RequestParam("personalPhones") String personalPhones,
                                             @RequestParam("workingPhones") String workingPhones) {
        Account account = accountService.create(new AccountMapper().toAccount(accountDto), password, personalPhones,
                workingPhones);
        return "redirect:/login" + REG_SUCCESS.getValue();
    }*/

}
