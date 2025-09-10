package com.getjavajob.messageservice.web.controller;

import com.getjavajob.messageservice.service.MessageService;
import com.getjavajob.messageservice.web.dto.MessageDto;
import com.getjavajob.messageservice.web.dto.MessageMapper;
import com.getjavajob.training.timashovy.socialnetwork.domain.account.Account;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@Controller
@RequestMapping("/message")
public class MessageController {

    private static final Logger logger = LoggerFactory.getLogger(MessageController.class);
    private final MessageService messageService;

    public MessageController(MessageService messageService) {
        this.messageService = messageService;
    }

    @PostMapping("/account/wall/create")
    public void createAccountWallMessage(@ModelAttribute MessageDto messageDto,
                                         @RequestParam("accountReceiverId") Long accountReceiverId,
                                         @SessionAttribute("account") Account account,
                                         HttpServletRequest request,
                                         HttpServletResponse response) throws IOException {
        messageService.createPersonalWallMessage(new MessageMapper().toPersonalWallMessage(messageDto, account.getId(),
                accountReceiverId));
        response.sendRedirect(generateRedirectURL(request, accountReceiverId));
    }

    private String generateRedirectURL(HttpServletRequest request, Long accountId) {
        String scheme = request.getScheme();
        String serverName = request.getHeader("X-Forwarded-Host");
        if (serverName == null || serverName.isEmpty()) {
            serverName = request.getServerName();
        }
        return scheme + "://" + serverName + "/account?id=" + accountId;
    }

    @GetMapping("/account/messages")
    public String personalMessages(@RequestParam("id") Long id,
                                   Model model) {
        logger.info("Account id={} get personam messages", id);
        model.addAttribute("accounts", messageService.getAllAccountsWithPersonalMessages(id));
        return "account/messages";
    }

    @PostMapping("/account/messages/create")
    public String createPersonalMessage(@ModelAttribute MessageDto messageDto,
                                        @RequestParam("destinationId") long destinationId,
                                        @SessionAttribute("account") Account account) {
        messageService.createPersonalMessage(new MessageMapper().toPersonalMessage(messageDto, account.getId(),
                destinationId));
        return "redirect:/account/messages/dialog?id=" + destinationId;
    }

    @GetMapping("/account/messages/dialog")
    public String messageDialog(@RequestParam("id") long id,
                                @SessionAttribute("account") Account account,
                                Model model) {
        model.addAttribute("accountReceiverId", id);
        model.addAttribute("accountSender", account);
        model.addAttribute("messages", messageService.getAllPersonalMessagesWithAccount(account.getId(), id));
        return "account/dialog";
    }

/*    @PostMapping("/group/message/create")
    public String createGroupMessage(@ModelAttribute MessageDto messageDto,
                                     @RequestParam("groupId") long groupId,
                                     @SessionAttribute("account") Account account) {
        messageService.createGroupMessage(new MessageMapper().toGroupMessage(messageDto, account.getId()), groupId);
        return "redirect:/group?id=" + groupId;
    }*/

/*
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
    }*/

}
