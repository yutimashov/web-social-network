package com.getjavajob.messageservice.web.controller;

import com.getjavajob.messageservice.service.MessageService;
import com.getjavajob.training.timashovy.socialnetwork.domain.message.PersonalMessage;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

@Controller
public class WebSocketChatController {

    private final SimpMessageSendingOperations messagingTemplate;
    private final MessageService messageService;

    public WebSocketChatController(SimpMessagingTemplate messagingTemplate, MessageService messageService) {
        this.messagingTemplate = messagingTemplate;
        this.messageService = messageService;
    }

    @MessageMapping("/message")
    public void sendMessage(@Payload PersonalMessage message) {
        messageService.createPersonalMessage(message);
        messagingTemplate.convertAndSendToUser(
                message.getDestinationId().toString(),
                "/queue/messages",
                message
        );
    }

}
