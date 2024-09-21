package com.getjavajob.training.timashovy.socialnetwork.web.controllers.friendship;

import com.getjavajob.training.timashovy.socialnetwork.service.interfaces.AccountService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/friends")
public class ShowAllFriendsController {

    private final AccountService accountService;

    public ShowAllFriendsController(AccountService accountService) {
        this.accountService = accountService;
    }

    @GetMapping
    public String doGet(Model model, @RequestParam("id") long id) {
        model.addAttribute("friends", accountService.getFriends(id));
        return "friendship/friends";
    }

}
