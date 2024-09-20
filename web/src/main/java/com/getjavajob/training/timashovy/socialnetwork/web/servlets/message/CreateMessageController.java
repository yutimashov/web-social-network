package com.getjavajob.training.timashovy.socialnetwork.web.servlets.message;

import com.getjavajob.training.timashovy.socialnetwork.common.account.Account;
import com.getjavajob.training.timashovy.socialnetwork.service.interfaces.MessageService;
import com.getjavajob.training.timashovy.socialnetwork.service.serviceimpl.message.MessageServiceImpl;
import com.getjavajob.training.timashovy.socialnetwork.web.dto.MessageDto;
import com.getjavajob.training.timashovy.socialnetwork.web.mappers.MessageMapper;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttribute;

import java.io.IOException;

@Controller
public class CreateMessageController {

    private final MessageService messageService;
    private final MessageServiceImpl messageServiceImpl;

    public CreateMessageController(MessageService messageService, MessageServiceImpl messageServiceImpl) {
        this.messageService = messageService;
        this.messageServiceImpl = messageServiceImpl;
    }

    @PostMapping("/group/message/create")
    public String createGroupMessage(@ModelAttribute MessageDto messageDto, @RequestParam("groupId") long groupId,
                                     @SessionAttribute("account") Account account) throws IOException {
        messageService.createGroupMessage(new MessageMapper().toMessage(messageDto, account.getId(), groupId));
        return "redirect:/group?id=" + groupId;
    }

    @PostMapping("/account-wall/message/create")
    public String createAccountWallMessage(@ModelAttribute MessageDto messageDto,
                                           @RequestParam("accountReceiverId") long accountReceiverId,
                                           @SessionAttribute("account") Account account) throws IOException {
        messageServiceImpl.createPersonalWallMessage(new MessageMapper().toMessage(messageDto, account.getId(),
                accountReceiverId));
        return "redirect:/account?id=" + accountReceiverId;
    }

}
