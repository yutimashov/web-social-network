package com.getjavajob.training.timashovy.socialnetwork.web.controllers;

import com.getjavajob.training.timashovy.socialnetwork.domain.account.Account;
import com.getjavajob.training.timashovy.socialnetwork.domain.message.PersonalWallMessage;
import com.getjavajob.training.timashovy.socialnetwork.service.interfaces.AccountService;
import com.getjavajob.training.timashovy.socialnetwork.service.interfaces.MessageService;
import com.getjavajob.training.timashovy.socialnetwork.web.dto.MessageDto;
import com.getjavajob.training.timashovy.socialnetwork.web.mappers.MessageMapper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static java.util.Collections.emptyList;

@Controller
@RequestMapping
public class MessageController {

    private final AccountService accountService;
    private final MessageService messageService;

    public MessageController(AccountService accountService, MessageService messageService) {
        this.accountService = accountService;
        this.messageService = messageService;
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

    @GetMapping("/newsfeed")
    public String newsFeed(@SessionAttribute Account account,
                           @RequestParam(required = false, defaultValue = "0") Long lastPostId,
                           @RequestParam(required = false, defaultValue = "0") Long cacheStartRange,
                           @RequestParam(defaultValue = "10") int pageSize,
                           @RequestParam(required = false, defaultValue = "false") boolean isAjax,
                           Model model) {
        List<PersonalWallMessage> newsFeedBatch = messageService.getNewsFeed(account.getId(), lastPostId,
                cacheStartRange, pageSize);
        if (!newsFeedBatch.isEmpty()) {
            Long newLastId = newsFeedBatch.stream()
                    .map(PersonalWallMessage::getId)
                    .max(Long::compareTo)
                    .orElse(lastPostId);
            model.addAttribute("newsfeed", newsFeedBatch);
            model.addAttribute("lastPostId", newLastId);
            model.addAttribute("cacheStartRange", cacheStartRange);
            model.addAttribute("limit", pageSize);
            model.addAttribute("accountService", accountService);
        } else {
            model.addAttribute("newsfeed", emptyList());
            model.addAttribute("hasMore", false);
        }
        return !isAjax ? "newsfeed/newsfeed" : "newsfeed/ajaxFragment";
    }

}
