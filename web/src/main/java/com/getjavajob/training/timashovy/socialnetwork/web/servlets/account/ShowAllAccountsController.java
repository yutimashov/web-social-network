package com.getjavajob.training.timashovy.socialnetwork.web.servlets.account;

import com.getjavajob.training.timashovy.socialnetwork.service.interfaces.AccountService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.ServletException;
import java.io.IOException;

@Controller
@RequestMapping("/account/all")
public class ShowAllAccountsController {

    private final AccountService accountService;

    public ShowAllAccountsController(AccountService accountService) {
        this.accountService = accountService;
    }

    @GetMapping
    protected String doGet(Model model) throws IOException, ServletException {
        model.addAttribute("accounts", accountService.getAll());
        return "account/all";
    }

}
