package com.getjavajob.accountservice.web.api;

import com.getjavajob.accountservice.service.AccountService;
import com.getjavajob.training.timashovy.socialnetwork.domain.account.Account;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Optional;

import static com.getjavajob.training.timashovy.socialnetwork.domain.account.AccountRole.REGULAR;
import static java.util.Objects.isNull;
import static org.springframework.http.HttpStatus.OK;

@Controller
@RequestMapping("/api/account")
public class AccountApiController {

    private final AccountService accountService;

    public AccountApiController(AccountService accountService) {
        this.accountService = accountService;
    }

    @GetMapping
    public ResponseEntity<Optional<Account>> accountById(@RequestParam Long id) {
        return ResponseEntity
                .status(OK)
                .body(accountService.getById(id));
    }

    @GetMapping("/update-by-id")
    public void updateById(@RequestParam Account account, @RequestParam Long accountId) {
        accountService.updateById(account, accountId);
    }

    @PostMapping("/create")
    public ResponseEntity<Account> createAccount(@RequestParam("firstName") String firstName,
                                                 @RequestParam("lastName") String lastName,
                                                 @RequestParam("email") String email,
                                                 @RequestParam(value = "middleName", required = false) String middleName,
                                                 @RequestParam(value = "icq", required = false) String icq,
                                                 @RequestParam(value = "skype", required = false) String skype,
                                                 @RequestPart(value = "avatar", required = false) MultipartFile avatar,
                                                 @RequestParam(value = "personalPhones", required = false) String personalPhones,
                                                 @RequestParam(value = "workingPhones", required = false) String workingPhones)
            throws IOException {
        Account account = accountService.create(new Account.Builder()
                .firstName(firstName)
                .lastName(lastName)
                .middleName(middleName)
                .email(email)
                .skype(skype)
                .avatar(!isNull(avatar) && avatar.getSize() > 0 ? avatar.getBytes() : null)
                .role(REGULAR)
                .icq(icq)
                .build(), personalPhones, workingPhones);
        return new ResponseEntity<>(account, HttpStatus.OK);
    }

}
