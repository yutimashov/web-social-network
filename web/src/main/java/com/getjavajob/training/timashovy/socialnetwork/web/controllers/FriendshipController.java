package com.getjavajob.training.timashovy.socialnetwork.web.controllers;

import com.getjavajob.training.timashovy.socialnetwork.domain.account.Account;
import com.getjavajob.training.timashovy.socialnetwork.service.interfaces.AccountService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.servlet.ModelAndView;

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
                                 @RequestParam("id") Long id) {
        model.addAttribute("friends", accountService.getFriends(id, 0, 40));
        return "friendship/friends";
    }

    @GetMapping("/all-friends")
    public ModelAndView showAllFriendsAjax(ModelAndView modelAndView,
                                           @RequestParam("id") Long id, @RequestParam("pageNumber") Integer pageNumber) {
        modelAndView.setViewName("friendship/ajaxFragment");
        modelAndView.addObject("friends", accountService.getFriends(id, pageNumber, 40));
        return modelAndView;
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
