package com.getjavajob.friendshipservice.web.controller;

import com.getjavajob.friendshipservice.service.FriendshipService;
import com.getjavajob.training.timashovy.socialnetwork.domain.account.Account;
import org.slf4j.Logger;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttribute;

import java.util.Collections;
import java.util.List;

import static org.slf4j.LoggerFactory.getLogger;
import static org.springframework.http.HttpStatus.OK;

@Controller
public class FriendshipController {

    private static final Logger logger = getLogger(FriendshipController.class);
    private final FriendshipService friendshipService;

    public FriendshipController(FriendshipService friendshipService) {
        this.friendshipService = friendshipService;
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
    @GetMapping("/friends/accept-request")
    public String acceptRequest(@SessionAttribute Account account,
                                @RequestParam("id") Long requesterAccountId) {
        friendshipService.addFriend(requesterAccountId, account.getId());
        return "redirect:/friends?id=" + account.getId();
    }

    /**
     * Process friend deleting
     *
     * @param account who deletes a friend
     * @param id      of a friend who will be deleted
     * @return jsp page of account who deleted friend
     */
    @GetMapping("/friends/delete")
    public String deleteFriend(@SessionAttribute("account") Account account,
                               @RequestParam("id") Long id) {
        Long accountId = account.getId();
        friendshipService.deleteFriend(accountId, id);
        return "redirect:/friends?id=" + account.getId();
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
    @GetMapping("/friends/send-request")
    public String sendRequest(@SessionAttribute("account") Account account,
                              @RequestParam("id") Long id) {
        friendshipService.addFriend(account.getId(), id);
        return "redirect:/friends/requests/outgoing";
    }

    @GetMapping("/friends/requests/incoming")
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

    @GetMapping("/friends/requests/outgoing")
    public String showOutgoingRequests(@SessionAttribute("account") Account account,
                                       Model model,
                                       @RequestParam(required = false, defaultValue = "0") Long lastId,
                                       @RequestParam(defaultValue = "100") int limit,
                                       @RequestParam(required = false, defaultValue = "false") boolean isAjax) {
        logger.info("Account id={} lists outgoing friendship requests", account.getId());
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
