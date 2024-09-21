package com.getjavajob.training.timashovy.socialnetwork.web.servlets.auth;

import com.getjavajob.training.timashovy.socialnetwork.service.interfaces.AccountService;
import com.getjavajob.training.timashovy.socialnetwork.web.dto.AccountDto;
import com.getjavajob.training.timashovy.socialnetwork.web.mappers.AccountMapper;
import com.getjavajob.training.timashovy.socialnetwork.web.mappers.AccountRegistrationDataMapper;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

import static com.getjavajob.training.timashovy.socialnetwork.web.util.StatusTypes.REG_SUCCESS;

@Controller
@RequestMapping("/register")
public class RegisterAccountController {

    private final AccountService accountService;

    public RegisterAccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @GetMapping
    protected String getRegisterPage() {
        return "auth/register";
    }

    @PostMapping
    protected String processAccountRegistration(@ModelAttribute AccountDto accountDto,
                                                @RequestParam("password") String password,
                                                @RequestParam("personalPhones") String personalPhones,
                                                @RequestParam("workingPhones") String workingPhones) throws IOException {
        accountService.create(
                new AccountRegistrationDataMapper().toAccountRegistrationData(
                        new AccountMapper().toAccount(accountDto), password, personalPhones, workingPhones
                )
        );
        return "redirect:/login" + REG_SUCCESS;
    }

}
