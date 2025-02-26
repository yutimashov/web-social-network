package com.getjavajob.training.timashovy.socialnetwork.web.controllers;

import com.getjavajob.training.timashovy.socialnetwork.domain.account.Account;
import com.getjavajob.training.timashovy.socialnetwork.service.interfaces.AccountService;
import com.getjavajob.training.timashovy.socialnetwork.service.interfaces.MessageService;
import com.getjavajob.training.timashovy.socialnetwork.service.rabbitmq.AccountWallPostNotificationService;
import com.getjavajob.training.timashovy.socialnetwork.web.dto.MessageDto;
import com.getjavajob.training.timashovy.socialnetwork.web.mappers.MessageMapper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping
public class MessageController {

    private final AccountService accountService;
    private final MessageService messageService;
    private final AccountWallPostNotificationService accountWallPostNotificationService;

    public MessageController(AccountService accountService, MessageService messageService,
                             AccountWallPostNotificationService accountWallPostNotificationService) {
        this.accountService = accountService;
        this.messageService = messageService;
        this.accountWallPostNotificationService = accountWallPostNotificationService;
    }

    @GetMapping("/account/messages/dialog")
    public String messageDialog(@RequestParam("id") long id,
                                @SessionAttribute("account") Account account,
                                Model model) {
        if (accountService.getById(id).isPresent()) {
            model.addAttribute("accountReceiver", accountService.getById(id).get());
        }
        model.addAttribute("accountSender", account);
        model.addAttribute("messages", messageService.getAllPersonalMessagesWithAccount(account.getId(), id));
        return "account/dialog";
    }

    @GetMapping("/account/messages")
    public String personalMessages(@RequestParam("id") long id,
                                   Model model) {
        model.addAttribute("accounts", messageService.getAllAccountsWithPersonalMessages(id));
        return "account/messages";
    }

    @PostMapping("/group/message/create")
    public String createGroupMessage(@ModelAttribute MessageDto messageDto,
                                     @RequestParam("groupId") long groupId,
                                     @SessionAttribute("account") Account account) {
        messageService.createGroupMessage(new MessageMapper().toGroupMessage(messageDto, account.getId()), groupId);
        return "redirect:/group?id=" + groupId;
    }

    @PostMapping("/account-wall/message/create")
    public String createAccountWallMessage(@ModelAttribute MessageDto messageDto,
                                           @RequestParam("accountReceiverId") long accountReceiverId,
                                           @SessionAttribute("account") Account account) {
        messageService.createPersonalWallMessage(new MessageMapper().toPersonalWallMessage(messageDto, account.getId(),
                accountReceiverId));
        accountWallPostNotificationService.notifyFriends(accountService.getById(accountReceiverId).get());
        return "redirect:/account?id=" + accountReceiverId;
    }

    @PostMapping("/account/messages/create")
    public String createPersonalMessage(@ModelAttribute MessageDto messageDto,
                                        @RequestParam("destinationId") long destinationId,
                                        @SessionAttribute("account") Account account) {
        messageService.createPersonalMessage(new MessageMapper().toPersonalMessage(messageDto, account.getId(),
                destinationId));
        return "redirect:/account/messages/dialog?id=" + destinationId;
    }

}
