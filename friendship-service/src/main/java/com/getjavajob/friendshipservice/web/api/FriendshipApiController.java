package com.getjavajob.friendshipservice.web.api;

import com.getjavajob.friendshipservice.service.FriendshipCheckerService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import static org.springframework.http.HttpStatus.OK;

@Controller
@RequestMapping("/api/friendship")
public class FriendshipApiController {

    private final FriendshipCheckerService friendshipCheckerService;

    public FriendshipApiController(FriendshipCheckerService friendshipCheckerService) {
        this.friendshipCheckerService = friendshipCheckerService;
    }

    @GetMapping("/check-existance")
    @ResponseBody
    public ResponseEntity<Boolean> checkExistence(Long requesterId, Long accepterId) {
        return ResponseEntity
                .status(OK)
                .body(friendshipCheckerService.checkFriendshipRecordExistence(requesterId, accepterId));
    }

}
