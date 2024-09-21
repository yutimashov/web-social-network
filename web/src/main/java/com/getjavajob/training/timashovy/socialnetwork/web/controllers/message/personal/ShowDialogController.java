package com.getjavajob.training.timashovy.socialnetwork.web.controllers.message.personal;

import com.getjavajob.training.timashovy.socialnetwork.common.account.Account;
import com.getjavajob.training.timashovy.socialnetwork.service.interfaces.AccountService;
import com.getjavajob.training.timashovy.socialnetwork.service.serviceimpl.message.MessageServiceImpl;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttribute;

@Controller
@RequestMapping("/account/messages/dialog")
public class ShowDialogController {

    private final AccountService accountService;
    private final MessageServiceImpl messageService;

    public ShowDialogController(AccountService accountService, MessageServiceImpl messageService) {
        this.accountService = accountService;
        this.messageService = messageService;
    }

    @GetMapping
    public String showMessageDialog(@RequestParam("id") long id, @SessionAttribute("account") Account account,
                                    Model model) {
        if (accountService.getById(id).isPresent()) {
            model.addAttribute("account", accountService.getById(id));
        }
        model.addAttribute("accountService", accountService);
        model.addAttribute("messages", messageService.getAllPersonalMessagesWithAccount(account.getId(), id));
        return "account/dialog";
    }

}
