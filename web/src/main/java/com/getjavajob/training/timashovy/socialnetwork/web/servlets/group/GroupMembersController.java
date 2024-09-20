package com.getjavajob.training.timashovy.socialnetwork.web.servlets.group;

import com.getjavajob.training.timashovy.socialnetwork.service.interfaces.GroupMembershipService;
import com.getjavajob.training.timashovy.socialnetwork.service.interfaces.GroupService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/group/members")
public class GroupMembersController {

    private final GroupService groupService;
    private final GroupMembershipService groupMembershipService;

    public GroupMembersController(GroupService groupService, GroupMembershipService groupMembershipService) {
        this.groupService = groupService;
        this.groupMembershipService = groupMembershipService;
    }

    @GetMapping
    public String doGet(@RequestParam("id") long groupId, Model model) {
        if (groupService.getById(groupId).isPresent()) {
            model.addAttribute("group", groupService.getById(groupId).get());
            model.addAttribute("groupMembers", groupMembershipService.getRegularMembers(groupId));
            model.addAttribute("groupAdmins", groupMembershipService.getAdmins(groupId));
        }
        return "group/members";
    }

}
