package com.getjavajob.friendshipservice.web.api;

import com.getjavajob.friendshipservice.service.FriendshipCheckerService;
import com.getjavajob.friendshipservice.service.FriendshipService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping("/api/friendship")
public class FriendshipApiController {

    private final FriendshipCheckerService friendshipCheckerService;
    private final FriendshipService friendshipService;

    public FriendshipApiController(FriendshipCheckerService friendshipCheckerService,
                                   FriendshipService friendshipService) {
        this.friendshipCheckerService = friendshipCheckerService;
        this.friendshipService = friendshipService;
    }

    @GetMapping("/check-existance")
    public ResponseEntity<Boolean> checkExistence(Long requesterId, Long accepterId) {
        return ResponseEntity
                .status(OK)
                .body(friendshipCheckerService.checkFriendshipRecordExistence(requesterId, accepterId));
    }

    @GetMapping("/id")
    public ResponseEntity<List<Long>> getFriendId(@RequestParam Long accountId) {
        return ResponseEntity
                .status(OK)
                .body(friendshipService.getFriendsIds(accountId));
    }

}
