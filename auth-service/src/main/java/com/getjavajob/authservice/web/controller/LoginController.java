package com.getjavajob.authservice.web.controller;

import com.getjavajob.authservice.service.password.PasswordService;
import com.getjavajob.authservice.web.feignclient.AccountServiceClient;
import com.getjavajob.training.timashovy.socialnetwork.domain.account.Account;
import org.slf4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

import static com.getjavajob.authservice.web.util.UrlStatusParameter.REGISTRATION_ACCOUNT_ERROR;
import static com.getjavajob.authservice.web.util.UrlStatusParameter.REG_SUCCESS;
import static org.slf4j.LoggerFactory.getLogger;

@Controller
public class LoginController {

    private static final Logger logger = getLogger(LoginController.class);
    private final AccountServiceClient accountServiceClient;
    private final PasswordService passwordService;

    public LoginController(AccountServiceClient accountServiceClient, PasswordService passwordService) {
        this.accountServiceClient = accountServiceClient;
        this.passwordService = passwordService;
    }

    @GetMapping("/login")
    public String handleLoginPage() {
        return "auth/login";
    }

    @GetMapping("/register")
    public String handleRegisterPage() {
        return "auth/register";
    }

    @PostMapping("/register")
    public String processAccountRegistration(@RequestParam("firstName") String firstName,
                                             @RequestParam("lastName") String lastName,
                                             @RequestParam("middleName") String middleName,
                                             @RequestParam("email") String email,
                                             @RequestParam("icq") String icq,
                                             @RequestParam("skype") String skype,
                                             @RequestParam(value = "personalPhoneNumber", required = false) String personalPhones,
                                             @RequestParam(value = "workPhoneNumber", required = false) String workingPhones,
                                             @RequestParam("password") String password,
                                             @RequestPart(value = "avatar", required = false) MultipartFile avatar) {
        try {
            Account savedAccount = accountServiceClient.createAccount(
                    firstName,
                    lastName,
                    middleName,
                    email,
                    icq,
                    skype,
                    personalPhones,
                    workingPhones,
                    avatar
            ).getBody();
            logger.info("Account={}", savedAccount);
            passwordService.create(savedAccount, password);
            return "redirect:/login" + REG_SUCCESS.getValue();
        } catch (Exception e) {
            logger.error("Registration failed", e);
            return "redirect:/register" + REGISTRATION_ACCOUNT_ERROR.getValue();
        }
    }

}
