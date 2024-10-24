package com.getjavajob.training.timashovy.socialnetwork.web.controllers;

import com.getjavajob.training.timashovy.socialnetwork.domain.group.Group;
import com.getjavajob.training.timashovy.socialnetwork.domain.account.Account;
import com.getjavajob.training.timashovy.socialnetwork.service.interfaces.AccountService;
import com.getjavajob.training.timashovy.socialnetwork.service.interfaces.GroupService;
import com.getjavajob.training.timashovy.socialnetwork.service.interfaces.MessageService;
import com.getjavajob.training.timashovy.socialnetwork.service.serviceimpl.message.MessageServiceImpl;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.InputStreamSource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.InputStream;
import java.util.Optional;

import static org.springframework.http.HttpStatus.NOT_FOUND;
import static org.springframework.http.HttpStatus.OK;
import static org.springframework.http.MediaType.IMAGE_JPEG;
import static org.springframework.http.ResponseEntity.status;

@Controller
public class ImageController {

    private final AccountService accountService;
    private final GroupService groupService;
    private final MessageService messageService;
    private final MessageServiceImpl messageServiceImpl;

    public ImageController(AccountService accountService, GroupService groupService,
                           MessageService messageService, MessageServiceImpl messageServiceImpl) {
        this.accountService = accountService;
        this.groupService = groupService;
        this.messageService = messageService;
        this.messageServiceImpl = messageServiceImpl;
    }

    @GetMapping("/account/avatar")
    public ResponseEntity<InputStreamSource> accountAvatar(@RequestParam("id") long id) {
        Optional<Account> accountOptional = accountService.getById(id);
        if (accountOptional.isPresent()) {
            InputStream avatar = accountOptional.get().getAvatar();
            return createImageResponse(avatar);
        }
        return status(NOT_FOUND).build();
    }

    @GetMapping("/group/avatar")
    public ResponseEntity<InputStreamSource> groupAvatar(@RequestParam("id") long id) {
        Optional<Group> groupOptional = groupService.getById(id);
        if (groupOptional.isPresent()) {
            InputStream avatar = groupOptional.get().getAvatar();
            return createImageResponse(avatar);
        }
        return status(NOT_FOUND).build();
    }

    @GetMapping("/group-message/image")
    public ResponseEntity<InputStreamSource> groupPostImage(@RequestParam("id") long id) {
        return createImageResponse(messageService.getGroupMessageById(id).getPhoto());
    }

    @GetMapping("/personal-message/image")
    public ResponseEntity<InputStreamSource> personalMessageImage(@RequestParam("id") long id) {
        return createImageResponse(messageService.getPersonalMessageById(id).getPhoto());
    }

    @GetMapping("/account-wall/image")
    public ResponseEntity<InputStreamSource> accountPostImage(@RequestParam("id") long id) {
        return createImageResponse(messageServiceImpl.getAccountWallMessageById(id).getPhoto());
    }

    private ResponseEntity<InputStreamSource> createImageResponse(InputStream inputStream) {
        if (inputStream != null) {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(IMAGE_JPEG);
            return new ResponseEntity<>(new InputStreamResource(inputStream), headers, OK);
        }
        return status(NOT_FOUND).build();
    }

}
