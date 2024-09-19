package com.getjavajob.training.timashovy.socialnetwork.web.servlets.account;

import com.getjavajob.training.timashovy.socialnetwork.common.account.Account;
import com.getjavajob.training.timashovy.socialnetwork.service.interfaces.AccountService;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.InputStreamSource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.InputStream;
import java.util.Optional;

import static org.springframework.http.HttpStatus.NOT_FOUND;
import static org.springframework.http.HttpStatus.OK;
import static org.springframework.http.MediaType.IMAGE_JPEG;
import static org.springframework.http.ResponseEntity.status;

@Controller
@RequestMapping("/account/avatar")
public class AccountAvatarController {

    private final AccountService accountService;

    public AccountAvatarController(AccountService accountService) {
        this.accountService = accountService;
    }

    @GetMapping
    public ResponseEntity<InputStreamSource> getAccountAvatar(@RequestParam("id") long id) {
        Optional<Account> accountOptional = accountService.getById(id);
        if (accountOptional.isPresent()) {
            InputStream inputStreamImage = accountOptional.get().getAvatar();
            if (inputStreamImage != null) {
                HttpHeaders headers = new HttpHeaders();
                headers.setContentType(IMAGE_JPEG);
                return new ResponseEntity<>(new InputStreamResource(inputStreamImage), headers, OK);
            }
        }
        return status(NOT_FOUND).build();
    }

}
