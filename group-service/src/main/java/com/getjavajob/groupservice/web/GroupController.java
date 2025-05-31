package com.getjavajob.groupservice.web;

import com.getjavajob.groupservice.service.group.GroupService;
import com.getjavajob.groupservice.service.membership.GroupMembershipService;
import com.getjavajob.groupservice.web.feignclients.MessageClient;
import com.getjavajob.training.timashovy.socialnetwork.domain.account.Account;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttribute;

@Controller
@RequestMapping("/group")
public class GroupController {

    private static final Logger logger = LoggerFactory.getLogger(GroupController.class);

    private final GroupService groupService;
    private final MessageClient messageClient;
    private final GroupMembershipService groupMembershipService;

    public GroupController(GroupService groupService, MessageClient messageClient,
                           GroupMembershipService groupMembershipService) {
        this.groupService = groupService;
        this.messageClient = messageClient;
        this.groupMembershipService = groupMembershipService;
    }

    @GetMapping
    public String group(@RequestParam("id") Long groupId,
                        @SessionAttribute("account") Account account,
                        Model model) {
        if (groupService.getById(groupId).isPresent()) {
            model.addAttribute("group", groupService.getById(groupId).get());
            Long accountId = account.getId();
            model.addAttribute("isAdmin", groupMembershipService.isAdmin(groupId, accountId));
            model.addAttribute("isSubscriber", groupMembershipService.isSubscriber(groupId, accountId));
            model.addAttribute("isMember", groupMembershipService.isMember(groupId, accountId));
            model.addAttribute("groupPosts", messageClient.groupMessages(groupId).getBody());
            //model.addAttribute("accountService", accountService);
            return "group/group";
        } else {
            return "error/404";
        }
    }

    @GetMapping("/all")
    public String allGroups(Model model) {
        logger.info("Get all groups");
        model.addAttribute("groups", groupService.getAll());
        logger.info("All groups: {}", groupService.getAll());
        return "group/groups";
    }

}
