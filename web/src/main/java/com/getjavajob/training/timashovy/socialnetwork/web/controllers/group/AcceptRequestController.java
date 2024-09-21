package com.getjavajob.training.timashovy.socialnetwork.web.controllers.group;

import com.getjavajob.training.timashovy.socialnetwork.service.interfaces.GroupMembershipService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServlet;

@Controller
@RequestMapping("/group/accept-request")
public class AcceptRequestController extends HttpServlet {

    private final GroupMembershipService groupMembershipService;

    public AcceptRequestController(GroupMembershipService groupMembershipService) {
        this.groupMembershipService = groupMembershipService;
    }

    @GetMapping
    protected String acceptRequest(@RequestParam("groupId") long groupId,
                                   @RequestParam("accountId") long accountId) {
        groupMembershipService.makeMember(groupId, accountId);
        return "redirect:/group?id=" + groupId;
    }

}
