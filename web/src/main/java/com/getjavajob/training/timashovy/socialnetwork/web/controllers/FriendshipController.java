package com.getjavajob.training.timashovy.socialnetwork.web.controllers;

import com.getjavajob.training.timashovy.socialnetwork.domain.account.Account;
import com.getjavajob.training.timashovy.socialnetwork.service.interfaces.AccountService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttribute;

import java.util.Collections;
import java.util.List;

@Controller
@RequestMapping("/friends")
public class FriendshipController {

    private final AccountService accountService;

    public FriendshipController(AccountService accountService) {
        this.accountService = accountService;
    }

    @GetMapping("/accept-request")
    public String acceptRequest(@SessionAttribute Account account,
                                @RequestParam("id") Long requesterAccountId) {
        accountService.addFriend(requesterAccountId, account.getId());
        return "redirect:/friends?id=" + account.getId();
    }

    @GetMapping("/delete")
    public String delete(@SessionAttribute("account") Account account,
                         @RequestParam("id") Long id) {
        Long accountId = account.getId();
        accountService.deleteFriend(accountId, id);
        return "redirect:/account?id=" + accountId;
    }

    @GetMapping("/send-request")
    public String sendRequest(@SessionAttribute("account") Account account,
                              @RequestParam("id") Long id) {
        Long requesterAccountId = account.getId();
        accountService.addFriend(requesterAccountId, id);
        return "redirect:/account?id=" + requesterAccountId;
    }

    @GetMapping()
    public String showAllFriends(Model model,
                                 @RequestParam("id") Long id,
                                 @RequestParam(required = false, defaultValue = "0") Long lastId,
                                 @RequestParam(defaultValue = "100") int limit) {
        List<Account> friendsBatch = accountService.getFriends(id, lastId, limit);
        if (!friendsBatch.isEmpty()) {
            Long newLastId = friendsBatch.stream()
                    .map(Account::getId)
                    .max(Long::compareTo)
                    .orElse(lastId);
            model.addAttribute("friends", friendsBatch);
            model.addAttribute("lastId", newLastId);
            model.addAttribute("limit", limit);
            model.addAttribute("accountId", id);
        } else {
            model.addAttribute("friends", Collections.emptyList());
            model.addAttribute("hasMore", false);
        }
        return "friendship/friends";
    }

    @GetMapping("/requests/incoming")
    public String showIncomingRequests(@SessionAttribute("account") Account account,
                                       Model model) {
        model.addAttribute("friendRequests", accountService.getIncomingFriendRequests(account.getId()));
        return "friendship/requests/incoming";
    }

    @GetMapping("/requests/outgoing")
    public String showOutgoingRequests(@SessionAttribute("account") Account account,
                                       Model model) {
        model.addAttribute("outgoingFriendRequests", accountService
                .getOutgoingFriendRequests(account.getId()));
        return "friendship/requests/outgoing";
    }

}
