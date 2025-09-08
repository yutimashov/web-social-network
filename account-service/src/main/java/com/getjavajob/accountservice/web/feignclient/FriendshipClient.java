package com.getjavajob.accountservice.web.feignclient;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient("FRIENDSHIP-SERVICE")
public interface FriendshipClient {

    @GetMapping("/api/friendship/check-existance")
    ResponseEntity<Boolean> checkFriendshipExistence(@RequestParam Long requesterId, @RequestParam Long accepterId);

}
