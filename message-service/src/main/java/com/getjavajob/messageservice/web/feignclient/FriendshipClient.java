package com.getjavajob.messageservice.web.feignclient;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient("FRIENDSHIP-SERVICE")
public interface FriendshipClient {

    @GetMapping("/api/friendship/id")
    ResponseEntity<List<Long>> getFriendsIds(@RequestParam Long accountId);

}
