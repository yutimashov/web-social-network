package com.getjavajob.training.timashovy.socialnetwork.web.servlets.group;

import com.getjavajob.training.timashovy.socialnetwork.common.account.Account;
import com.getjavajob.training.timashovy.socialnetwork.service.interfaces.AccountService;
import com.getjavajob.training.timashovy.socialnetwork.service.interfaces.GroupMembershipService;
import com.getjavajob.training.timashovy.socialnetwork.service.interfaces.GroupService;
import com.getjavajob.training.timashovy.socialnetwork.service.interfaces.MessageService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttribute;

@Controller
@RequestMapping("/group")
public class GroupInfoController {

    private final GroupMembershipService groupMembershipService;
    private final GroupService groupService;
    private final MessageService messageService;
    private final AccountService accountService;

    public GroupInfoController(GroupMembershipService groupMembershipService, GroupService groupService,
                               MessageService messageService, AccountService accountService) {
        this.groupMembershipService = groupMembershipService;
        this.groupService = groupService;
        this.messageService = messageService;
        this.accountService = accountService;
    }

    @GetMapping
    protected String getGroup(@RequestParam("id") long id, @SessionAttribute("account") Account account,
                              Model model) {
        if (groupService.getById(id).isPresent()) {
            model.addAttribute("group", groupService.getById(id).get());
            model.addAttribute("avatarInputStream", groupService.getById(id).get().getAvatar());
            Long accountId = account.getId();
            model.addAttribute("isAdmin", groupMembershipService.isAdmin(id, accountId));
            model.addAttribute("isSubscriber", groupMembershipService.isSubscriber(id, accountId));
            model.addAttribute("isMember", groupMembershipService.isMember(id, accountId));
            model.addAttribute("groupPosts", messageService.getAllGroupMessages(id));
            model.addAttribute("accountService", accountService);
        }
        return "group/group";
    }

}
