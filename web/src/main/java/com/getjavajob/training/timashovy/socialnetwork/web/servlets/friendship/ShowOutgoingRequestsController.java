package com.getjavajob.training.timashovy.socialnetwork.web.servlets.friendship;

import com.getjavajob.training.timashovy.socialnetwork.common.account.Account;
import com.getjavajob.training.timashovy.socialnetwork.service.interfaces.AccountService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttribute;

@Controller
@RequestMapping("/friends/requests/outgoing")
public class ShowOutgoingRequestsController {

    private final AccountService accountService;

    public ShowOutgoingRequestsController(AccountService accountService) {
        this.accountService = accountService;
    }

    @GetMapping
    protected String doGet(@SessionAttribute("account") Account account, Model model) {
        model.addAttribute("outgoingFriendRequests", accountService.getOutgoingFriendRequests(account.getId()));
        return "friendship/requests/outgoing";
    }

}
