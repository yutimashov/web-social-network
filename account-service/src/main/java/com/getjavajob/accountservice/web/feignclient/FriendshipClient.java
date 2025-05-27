package com.getjavajob.accountservice.web.feignclient;

import com.getjavajob.training.timashovy.socialnetwork.domain.account.Account;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient("FRIENDSHIP-SERVICE")
public interface FriendshipClient {

    @GetMapping("/check-existance")
    boolean checkExistence(@RequestParam Long requesterId, @RequestParam Long accepterId);

    @GetMapping("/check-friendship")
    boolean checkFriendship(@RequestParam Long requesterId, @RequestParam Long accepterId);

    @GetMapping("/send-request")
    void sendRequest(@RequestParam Account requester, @RequestParam Account receiver);

    @GetMapping("/accept-request")
    void acceptRequest(@RequestParam Long requesterId, @RequestParam Long accepterId);

    @DeleteMapping("/delete")
    void deleteFriend(@RequestParam Long accountId, @RequestParam Long deletingFriendId);

    @GetMapping("/friends")
    ResponseEntity<List<Account>> getFriends(@RequestParam Long accountId, @RequestParam Long lastId, @RequestParam int pageSize);

    @GetMapping("/friends-ids")
    ResponseEntity<List<Long>> getFriendsIds(@RequestParam Long accountId);

    @GetMapping("/followers")
    ResponseEntity<List<Account>> getFollowers(@RequestParam Long accountId, @RequestParam Long lastId, @RequestParam int pageSize);

    @GetMapping("/followings")
    ResponseEntity<List<Account>> getFollowings(@RequestParam Long accountId, @RequestParam Long lastId, @RequestParam int pageSize);

}
