package com.getjavajob.training.timashovy.socialnetwork.web.servlets.friendship;

import com.getjavajob.training.timashovy.socialnetwork.common.account.Account;
import com.getjavajob.training.timashovy.socialnetwork.service.interfaces.AccountService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttribute;

@Controller
@RequestMapping("/friends/accept-request")
public class AcceptFriendshipRequestController {

    private final AccountService accountService;

    public AcceptFriendshipRequestController(AccountService accountService) {
        this.accountService = accountService;
    }

    @GetMapping
    protected String doGet(@SessionAttribute("account") Account account, @RequestParam("id") long requesterAccountId) {
        Long accepterAccountId = account.getId();
        accountService.addFriend(requesterAccountId, accepterAccountId);
        return "redirect:/friends?id=" + accepterAccountId;
    }

}
