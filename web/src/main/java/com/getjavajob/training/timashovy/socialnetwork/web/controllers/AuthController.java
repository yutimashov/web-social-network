package com.getjavajob.training.timashovy.socialnetwork.web.controllers;

import com.getjavajob.training.timashovy.socialnetwork.domain.account.Account;
import com.getjavajob.training.timashovy.socialnetwork.service.interfaces.AccountService;
import com.getjavajob.training.timashovy.socialnetwork.web.dto.AccountDto;
import com.getjavajob.training.timashovy.socialnetwork.web.mappers.AccountMapper;
import org.slf4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import static com.getjavajob.training.timashovy.socialnetwork.web.util.UrlStatusParameter.REG_SUCCESS;
import static org.slf4j.LoggerFactory.getLogger;

@Controller
public class AuthController {

    private final AccountService accountService;
    private static final Logger logger = getLogger(AuthController.class);

    public AuthController(AccountService accountService) {
        this.accountService = accountService;
    }

    @GetMapping("/login")
    public String handleLoginPage() {
        logger.info("Got request to render login page");
        return "auth/login";
    }

    @GetMapping("/register")
    public String handleRegisterPage() {
        logger.info("Got request to render register page");
        return "auth/register";
    }

    @PostMapping("/register")
    public String processAccountRegistration(@ModelAttribute AccountDto accountDto,
                                             @RequestParam("password") String password,
                                             @RequestParam("personalPhones") String personalPhones,
                                             @RequestParam("workingPhones") String workingPhones) {
        Account account = accountService.create(new AccountMapper().toAccount(accountDto), password, personalPhones,
                workingPhones);
        logger.info("New account={} has been registered", account.getId());
        return "redirect:/login" + REG_SUCCESS.getValue();
    }

}
