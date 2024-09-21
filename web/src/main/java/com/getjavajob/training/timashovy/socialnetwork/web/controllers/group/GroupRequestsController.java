package com.getjavajob.training.timashovy.socialnetwork.web.controllers.group;

import com.getjavajob.training.timashovy.socialnetwork.service.interfaces.GroupMembershipService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/group/requests")
public class GroupRequestsController {

    private final GroupMembershipService groupMembershipService;

    public GroupRequestsController(GroupMembershipService groupMembershipService) {
        this.groupMembershipService = groupMembershipService;
    }

    @GetMapping
    public String doGet(@RequestParam("id") long groupId, Model model) {
        model.addAttribute("groupRequests", groupMembershipService.getIncomingRequests(groupId));
        model.addAttribute("groupId", groupId);
        return "group/requests";
    }

}
