package com.getjavajob.friendshipservice.web.controller;

import com.getjavajob.friendshipservice.service.FriendshipService;
import com.getjavajob.training.timashovy.socialnetwork.domain.account.Account;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

import static org.springframework.http.HttpStatus.OK;

@Controller
public class FriendshipController {

    private final FriendshipService friendshipService;

    public FriendshipController(FriendshipService friendshipService) {
        this.friendshipService = friendshipService;
    }

    @GetMapping("/accept-request")
    public void acceptRequest(Long requesterId, Long accepterId) {
        friendshipService.acceptRequest(requesterId, accepterId);
    }

    @DeleteMapping("/delete")
    public void deleteFriend(Long accountId, Long deletingFriendId) {
        friendshipService.deleteFriend(accountId, deletingFriendId);
    }

    @GetMapping("/friends")
    public ResponseEntity<List<Account>> getFriends(Long accountId, Long lastId, int pageSize) {
        return ResponseEntity.status(OK)
                .body(friendshipService.getFriends(accountId, lastId, pageSize));
    }

    @GetMapping("/api/friendship/id")
    @ResponseBody
    public ResponseEntity<List<Long>> getFriendsIds(@RequestParam Long accountId) {
        return ResponseEntity
                .status(OK)
                .body(friendshipService.getFriendsIds(accountId));
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

    @GetMapping("/send-request")
    public void sendRequest(Account requester, Account receiver) {
        friendshipService.sendRequest(requester, receiver);
    }

}
