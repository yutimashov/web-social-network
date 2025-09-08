package com.getjavajob.friendshipservice.web.api;

import com.getjavajob.friendshipservice.service.FriendshipService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

import static org.springframework.http.HttpStatus.OK;

@Controller
@RequestMapping("/api/friendship")
public class FriendshipApiController {

    private final FriendshipService friendshipService;

    public FriendshipApiController(FriendshipService friendshipService) {
        this.friendshipService = friendshipService;
    }

    @GetMapping("/id")
    @ResponseBody
    public ResponseEntity<List<Long>> getFriendsIds(@RequestParam Long accountId) {
        return ResponseEntity
                .status(OK)
                .body(friendshipService.getFriendsIds(accountId));
    }

}
