package com.getjavajob.training.timashovy.socialnetwork.web.controllers.group;

import com.getjavajob.training.timashovy.socialnetwork.service.interfaces.GroupMembershipService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping({"/group/decline-request", "/group/delete-member"})
public class DeleteGroupMemberServlet {

    private final GroupMembershipService groupMembershipService;

    public DeleteGroupMemberServlet(GroupMembershipService groupMembershipService) {
        this.groupMembershipService = groupMembershipService;
    }

    @GetMapping
    protected String deleteGroupMember(@RequestParam("groupId") long groupId,
                                       @RequestParam("accountId") long accountId) {
        groupMembershipService.deleteMember(groupId, accountId);
        return "redirect:/group?id=" + groupId;
    }

}
