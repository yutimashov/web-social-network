package com.getjavajob.training.timashovy.socialnetwork.web.controllers;

import com.getjavajob.training.timashovy.socialnetwork.common.account.Account;
import com.getjavajob.training.timashovy.socialnetwork.service.interfaces.AccountService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttribute;

@Controller
@RequestMapping("/friends")
public class FriendshipController {

    private final AccountService accountService;

    public FriendshipController(AccountService accountService) {
        this.accountService = accountService;
    }

    @GetMapping("/accept-request")
    public String acceptRequest(@SessionAttribute("account") Account account,
                                @RequestParam("id") long requesterAccountId) {
        Long accepterAccountId = account.getId();
        accountService.addFriend(requesterAccountId, accepterAccountId);
        return "redirect:/friends?id=" + accepterAccountId;
    }

    @GetMapping("/delete")
    public String delete(@SessionAttribute("account") Account account, @RequestParam("id") long id) {
        Long accountId = account.getId();
        accountService.deleteFriend(accountId, id);
        return "redirect:/account?id=" + accountId;
    }

    @GetMapping("/send-request")
    protected String sendRequest(@SessionAttribute("account") Account account, @RequestParam("id") long id) {
        Long requesterAccountId = account.getId();
        accountService.addFriend(requesterAccountId, id);
        return "redirect:/account?id=" + requesterAccountId;
    }

    @GetMapping()
    public String showAllFriends(Model model, @RequestParam("id") long id) {
        model.addAttribute("friends", accountService.getFriends(id));
        return "friendship/friends";
    }

    @GetMapping("/requests")
    protected String showAllRequests() {
        return "friendship/requests/requests";
    }

    @GetMapping("/requests/incoming")
    public String showIncomingRequests(@SessionAttribute("account") Account account, Model model) {
        model.addAttribute("friendRequests", accountService.getIncomingFriendRequests(account.getId()));
        return "friendship/requests/incoming";
    }

    @GetMapping("/requests/outgoing")
    public String showOutgoingRequests(@SessionAttribute("account") Account account, Model model) {
        model.addAttribute("outgoingFriendRequests", accountService.getOutgoingFriendRequests(account.getId()));
        return "friendship/requests/outgoing";
    }

}
