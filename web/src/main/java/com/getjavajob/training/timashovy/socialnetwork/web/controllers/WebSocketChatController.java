package com.getjavajob.training.timashovy.socialnetwork.web.controllers;

import com.getjavajob.training.timashovy.socialnetwork.domain.message.PersonalMessage;
import com.getjavajob.training.timashovy.socialnetwork.service.interfaces.AccountService;
import com.getjavajob.training.timashovy.socialnetwork.service.interfaces.MessageService;
import org.slf4j.Logger;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

import static org.slf4j.LoggerFactory.getLogger;

@Controller
public class WebSocketChatController {

    private final SimpMessageSendingOperations messagingTemplate;
    private final MessageService messageService;
    private final AccountService accountService;
    private static final Logger logger = getLogger(WebSocketChatController.class);

    public WebSocketChatController(SimpMessagingTemplate messagingTemplate, MessageService messageService,
                                   AccountService accountService) {
        this.messagingTemplate = messagingTemplate;
        this.messageService = messageService;
        this.accountService = accountService;
    }

    @MessageMapping("/message")
    public void sendMessage(@Payload PersonalMessage message) {
        messageService.createPersonalMessage(message);
        accountService.getById(message.getDestinationId()).ifPresent(account -> {
            messagingTemplate.convertAndSendToUser(
                    account.getEmail(),
                    "/queue/messages",
                    message
            );
        });
    }

}
