package com.getjavajob.training.timashovy.socialnetwork.web.servlets.friendship;

import com.getjavajob.training.timashovy.socialnetwork.common.account.Account;
import com.getjavajob.training.timashovy.socialnetwork.service.interfaces.AccountService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttribute;

@Controller
@RequestMapping("/friends/delete")
public class DeleteFriendController {

    private final AccountService accountService;

    public DeleteFriendController(AccountService accountService) {
        this.accountService = accountService;
    }

    @GetMapping
    protected String doGet(@SessionAttribute("account") Account account, @RequestParam("id") long id) {
        Long accountId = account.getId();
        accountService.deleteFriend(accountId, id);
        return "redirect:/account?id=" + accountId;
    }

}
