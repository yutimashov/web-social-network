package com.getjavajob.friendshipservice.web.controller;

import com.getjavajob.friendshipservice.service.FriendshipCheckerService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import static org.springframework.http.HttpStatus.OK;

@Controller
public class FriendshipCheckerController {

    private final FriendshipCheckerService friendshipCheckerService;

    public FriendshipCheckerController(FriendshipCheckerService friendshipCheckerService) {
        this.friendshipCheckerService = friendshipCheckerService;
    }

    @GetMapping("/friendship/check-existance")
    public ResponseEntity<Boolean> checkExistence(Long requesterId, Long accepterId) {
        return ResponseEntity
                .status(OK)
                .body(friendshipCheckerService.checkFriendshipRecordExistence(requesterId, accepterId));
    }

    @GetMapping("/friendship/check-friendship")
    public boolean checkFriendship(Long requesterId, Long accepterId) {
        return friendshipCheckerService.checkUsersAreFriends(requesterId, accepterId);
    }

}
