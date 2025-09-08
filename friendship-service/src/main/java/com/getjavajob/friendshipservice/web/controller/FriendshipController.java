package com.getjavajob.friendshipservice.web.controller;

import com.getjavajob.friendshipservice.service.FriendshipService;
import com.getjavajob.friendshipservice.web.feignclient.AccountClient;
import com.getjavajob.training.timashovy.socialnetwork.domain.account.Account;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttribute;

import java.util.Collections;
import java.util.List;

import static org.springframework.http.HttpStatus.OK;

@Controller
public class FriendshipController {

    private final FriendshipService friendshipService;
    private final AccountClient accountClient;

    public FriendshipController(FriendshipService friendshipService, AccountClient accountClient) {
        this.friendshipService = friendshipService;
        this.accountClient = accountClient;
    }

    /**
     * List all account's friends.
     *
     * @param id     of account, whose friends are shown
     * @param lastId last friend id loaded from db
     * @param limit  max friends amount per page
     * @param isAjax type of request
     * @return jsp page with account's friends
     */
    @GetMapping("/friends")
    public String showAllFriends(Model model,
                                 @RequestParam("id") Long id,
                                 @RequestParam(required = false, defaultValue = "0") Long lastId,
                                 @RequestParam(defaultValue = "100") int limit,
                                 @RequestParam(required = false, defaultValue = "false") boolean isAjax) {
        List<Account> friendsBatch = friendshipService.getFriends(id, lastId, limit);
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
        return !isAjax ? "friendship/friends" : "friendship/ajaxFragment";
    }

    /**
     * Accept friendship request from another account.
     *
     * @param account            account who received request
     * @param requesterAccountId id of account who sent request
     */
    @GetMapping("/accept-request")
    public String acceptRequest(@SessionAttribute Account account,
                                @RequestParam("id") Long requesterAccountId) {
        Account requesterAccount = accountClient.getAccount(requesterAccountId).getBody();
        friendshipService.addFriend(requesterAccount, account);
        return "redirect:/friends?id=" + account.getId();
    }

    /**
     * Process friend deleting
     *
     * @param account who deletes a friend
     * @param id      of a friend who will be deleted
     * @return jsp page of account who deleted friend
     */
    @DeleteMapping("/delete")
    public String deleteFriend(@SessionAttribute("account") Account account,
                               @RequestParam("id") Long id) {
        Long accountId = account.getId();
        friendshipService.deleteFriend(accountId, id);
        return "redirect:/account?id=" + accountId;
    }


    @GetMapping("/followers")
    public ResponseEntity<List<Account>> getFollowers(Long accountId, Long lastId, int pageSize) {
        return ResponseEntity.status(OK)
                .body(friendshipService.getFollowerAccounts(accountId, lastId, pageSize));
    }

    @GetMapping("/followings")
    public ResponseEntity<List<Account>> getFollowings(Long accountId, Long lastId, int pageSize) {
        return ResponseEntity.status(OK)
                .body(friendshipService.getFollowingAccounts(accountId, lastId, pageSize));
    }

    /**
     * Send friendship request.
     *
     * @param account session account (sends request)
     * @param id      of account who receive request
     * @return jsp page of session account
     */
    @GetMapping("/send-request")
    public String sendRequest(@SessionAttribute("account") Account account,
                              @RequestParam("id") Long id) {
        Account receiverAccount = accountClient.getAccount(id).getBody();
        friendshipService.addFriend(account, receiverAccount);
        return "redirect:/account?id=" + receiverAccount.getId();
    }

    @GetMapping("/requests/incoming")
    public String showIncomingRequests(@SessionAttribute("account") Account account,
                                       Model model,
                                       @RequestParam(required = false, defaultValue = "0") Long lastId,
                                       @RequestParam(defaultValue = "100") int limit,
                                       @RequestParam(required = false, defaultValue = "false") boolean isAjax) {
        List<Account> friendsBatch = friendshipService.getFollowerAccounts(account.getId(), lastId, limit);
        if (!friendsBatch.isEmpty()) {
            Long newLastId = friendsBatch.stream()
                    .map(Account::getId)
                    .max(Long::compareTo)
                    .orElse(lastId);
            model.addAttribute("friendRequests", friendsBatch);
            model.addAttribute("lastId", newLastId);
            model.addAttribute("limit", limit);
            model.addAttribute("accountId", account.getId());
        } else {
            model.addAttribute("friendRequests", Collections.emptyList());
            model.addAttribute("hasMore", false);
        }
        return !isAjax ? "friendship/requests/incoming" : "friendship/requests/incoming-ajaxFragment";
    }

    @GetMapping("/requests/outgoing")
    public String showOutgoingRequests(@SessionAttribute("account") Account account,
                                       Model model,
                                       @RequestParam(required = false, defaultValue = "0") Long lastId,
                                       @RequestParam(defaultValue = "100") int limit,
                                       @RequestParam(required = false, defaultValue = "false") boolean isAjax) {
        List<Account> friendsBatch = friendshipService.getFollowingAccounts(account.getId(), lastId, limit);
        if (!friendsBatch.isEmpty()) {
            Long newLastId = friendsBatch.stream()
                    .map(Account::getId)
                    .max(Long::compareTo)
                    .orElse(lastId);
            model.addAttribute("outgoingFriendRequests", friendsBatch);
            model.addAttribute("lastId", newLastId);
            model.addAttribute("limit", limit);
            model.addAttribute("accountId", account.getId());
        } else {
            model.addAttribute("outgoingFriendRequests", Collections.emptyList());
            model.addAttribute("hasMore", false);
        }
        return !isAjax ? "friendship/requests/outgoing" : "friendship/requests/outgoing-ajaxFragment";
    }

}
