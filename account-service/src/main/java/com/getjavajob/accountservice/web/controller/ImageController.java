package com.getjavajob.accountservice.web.controller;

import com.getjavajob.accountservice.service.AccountService;
import com.getjavajob.training.timashovy.socialnetwork.domain.account.Account;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.InputStreamSource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.Optional;

import static org.springframework.http.HttpStatus.NOT_FOUND;
import static org.springframework.http.HttpStatus.OK;
import static org.springframework.http.MediaType.IMAGE_JPEG;
import static org.springframework.http.ResponseEntity.status;

@Controller
public class ImageController {

    private final AccountService accountService;
    //private final MessageService messageService;

    public ImageController(AccountService accountService) {
        this.accountService = accountService;
        //this.messageService = messageService;
    }

    @GetMapping("/account/avatar")
    public ResponseEntity<InputStreamSource> accountAvatar(@RequestParam("id") Long id) {
        Optional<Account> accountOptional = accountService.getById(id);
        if (accountOptional.isPresent()) {
            InputStream avatar = new ByteArrayInputStream(accountOptional.get().getAvatar());
            return createImageResponse(avatar);
        }
        return status(NOT_FOUND).build();
    }

    /*@GetMapping("/personal-message/image")
    public ResponseEntity<InputStreamSource> personalMessageImage(@RequestParam("id") Long id) {
        return createImageResponse(new ByteArrayInputStream(messageService.getPersonalMessageById(id).getPhoto()));
    }

    @GetMapping("/account-wall/image")
    public ResponseEntity<InputStreamSource> accountPostImage(@RequestParam("id") Long id) {
        return createImageResponse(new ByteArrayInputStream(messageService.getAccountWallMessageById(id).getPhoto()));
    }*/

    private ResponseEntity<InputStreamSource> createImageResponse(InputStream inputStream) {
        if (inputStream != null) {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(IMAGE_JPEG);
            return new ResponseEntity<>(new InputStreamResource(inputStream), headers, OK);
        }
        return status(NOT_FOUND).build();
    }

}
