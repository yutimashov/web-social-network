package com.getjavajob.accountservice.web.controller;

import com.getjavajob.accountservice.service.AccountService;
import com.getjavajob.accountservice.web.AccountDto;
import com.getjavajob.accountservice.web.AccountMapper;
import com.getjavajob.training.timashovy.socialnetwork.domain.account.Account;
import jakarta.servlet.http.HttpSession;
import org.slf4j.Logger;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Optional;

import static com.getjavajob.accountservice.web.UrlStatusParameter.REG_SUCCESS;
import static org.slf4j.LoggerFactory.getLogger;

@Controller
public class AccountController {

    private static final Logger logger = getLogger(AccountController.class);

    private final AccountService accountService;

    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @GetMapping("/update-by-id")
    public void updateById(@RequestParam Account account, @RequestParam Long accountId) {
        accountService.updateById(account, accountId);
    }

    @GetMapping("/find-by-email")
    public ResponseEntity<Optional<Account>> findByEmail(String email) {
        return ResponseEntity.ok(accountService.findByEmail(email));
    }

    @PostMapping("/register")
    public String processAccountRegistration(@ModelAttribute AccountDto accountDto,
                                             @RequestParam("password") String password,
                                             @RequestParam("personalPhones") String personalPhones,
                                             @RequestParam("workingPhones") String workingPhones) {
        Account account = accountService.create(new AccountMapper().toAccount(accountDto), password, personalPhones,
                workingPhones);
        // logger.info("New account={} has been registered", account.getId());
        return "redirect:/login" + REG_SUCCESS.getValue();
    }

    @GetMapping("/account")
    public String account(@RequestParam("id") Long accountId,
                          Model model,
                          HttpSession session) {
        Optional<Account> currentAccount = accountService.getById(accountId);
        if (currentAccount.isPresent()) {
/*            if (accountService.checkFriendshipRecordExistence(currentAccount.get().getId(), accountId)) {
                model.addAttribute("alreadySentFriendRequest", true);
            }*/
            logger.info("Get page of account: {}", currentAccount.get().getId());
            /*SecurityContext context = SecurityContextHolder.getContext();
            if (context.getAuthentication() != null) {
                logger.info("SecurityContext contains Authentication: {}", context.getAuthentication().getName());
            } else {
                logger.warn("SecurityContext does NOT contain an Authentication");
            }*/
            model.addAttribute("account", currentAccount.get());
/*            model.addAttribute("wallPosts", messageService.getAllAccountWallMessages(accountId));
            model.addAttribute("accountService", accountService);
            model.addAttribute("personalPhones", phoneService.getPhoneNumbers(accountId, PERSONAL));
            model.addAttribute("workingPhones", phoneService.getPhoneNumbers(accountId, WORKING));*/
            return "account/account";
        } else {
            return "error/404";
        }
    }

}
