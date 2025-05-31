package com.getjavajob.messageservice.web;

import com.getjavajob.messageservice.service.MessageService;
import com.getjavajob.training.timashovy.socialnetwork.domain.message.GroupMessage;
import com.getjavajob.training.timashovy.socialnetwork.domain.message.PersonalWallMessage;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping("/api/message")
public class ApiMessageController {

    private final MessageService messageService;

    public ApiMessageController(MessageService messageService) {
        this.messageService = messageService;
    }

    @GetMapping("/wall/account")
    public ResponseEntity<List<PersonalWallMessage>> wallMessages(@RequestParam("id") Long accountId) {
        return ResponseEntity
                .status(OK)
                .body(messageService.getAllAccountWallMessages(accountId));
    }

    @GetMapping("/wall/group")
    public ResponseEntity<List<GroupMessage>> groupMessages(@RequestParam Long id) {
        return ResponseEntity
                .status(OK)
                .body(messageService.getMessagesByGroupId(id));
    }

}
