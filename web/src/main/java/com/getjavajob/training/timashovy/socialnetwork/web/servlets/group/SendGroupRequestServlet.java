package com.getjavajob.training.timashovy.socialnetwork.web.servlets.group;

import com.getjavajob.training.timashovy.socialnetwork.common.account.Account;
import com.getjavajob.training.timashovy.socialnetwork.service.interfaces.GroupMembershipService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttribute;

import javax.servlet.http.HttpServlet;

@Controller
@RequestMapping("/group/send-request")
public class SendGroupRequestServlet extends HttpServlet {

    private final GroupMembershipService groupMembershipService;

    public SendGroupRequestServlet(GroupMembershipService groupMembershipService) {
        this.groupMembershipService = groupMembershipService;
    }

    @GetMapping
    public String doGet(@RequestParam("id") long id, @SessionAttribute("account") Account account) {
        Long accountId = account.getId();
        groupMembershipService.sendRequest(id, accountId);
        return "redirect:/account?id=" + accountId;
    }

}
