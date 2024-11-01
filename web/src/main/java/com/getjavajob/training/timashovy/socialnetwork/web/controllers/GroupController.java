package com.getjavajob.training.timashovy.socialnetwork.web.controllers;

import com.getjavajob.training.timashovy.socialnetwork.domain.account.Account;
import com.getjavajob.training.timashovy.socialnetwork.domain.group.Group;
import com.getjavajob.training.timashovy.socialnetwork.service.interfaces.AccountService;
import com.getjavajob.training.timashovy.socialnetwork.service.interfaces.GroupMembershipService;
import com.getjavajob.training.timashovy.socialnetwork.service.interfaces.GroupService;
import com.getjavajob.training.timashovy.socialnetwork.service.interfaces.MessageService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttribute;

import javax.servlet.http.HttpServlet;
import java.io.IOException;

@Controller
@RequestMapping("/group")
public class GroupController extends HttpServlet {

    private final GroupMembershipService groupMembershipService;
    private final GroupService groupService;
    private final MessageService messageService;
    private final AccountService accountService;

    public GroupController(GroupMembershipService groupMembershipService, GroupService groupService,
                           MessageService messageService, AccountService accountService) {
        this.groupMembershipService = groupMembershipService;
        this.groupService = groupService;
        this.messageService = messageService;
        this.accountService = accountService;
    }

    @GetMapping
    public String group(@RequestParam("id") long id, @SessionAttribute("account") Account account,
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

    @GetMapping("/accept-request")
    public String acceptRequest(@RequestParam("groupId") long groupId,
                                @RequestParam("accountId") long accountId) {
        groupMembershipService.makeMember(groupId, accountId);
        return "redirect:/group?id=" + groupId;
    }

    @GetMapping("/create")
    public String create() {
        return "group/create";
    }

    @PostMapping("/create")
    public String processGroupCreation(@ModelAttribute Group group,
                                       @SessionAttribute("account") Account account) throws IOException {
        Long accountId = account.getId();
        Long groupId = groupService.create(group);
        groupMembershipService.sendRequest(group, account);
        groupMembershipService.makeMember(groupId, accountId);
        groupMembershipService.makeAdmin(groupId, accountId);
        return "redirect:/group/all";
    }

    @GetMapping({"/decline-request", "/delete-member"})
    public String deleteGroupMember(@RequestParam("groupId") long groupId,
                                    @RequestParam("accountId") long accountId) {
        groupMembershipService.deleteMember(groupId, accountId);
        return "redirect:/group?id=" + groupId;
    }

    @GetMapping("/members")
    public String groupMembers(@RequestParam("id") long groupId, Model model) {
        if (groupService.getById(groupId).isPresent()) {
            model.addAttribute("group", groupService.getById(groupId).get());
            model.addAttribute("groupMembers", groupMembershipService.getRegularMembers(groupId));
            model.addAttribute("groupAdmins", groupMembershipService.getAdmins(groupId));
        }
        return "group/members";
    }

    @GetMapping("/requests")
    public String requests(@RequestParam("id") long groupId, Model model) {
        model.addAttribute("groupRequests", groupMembershipService.getIncomingRequests(groupId));
        model.addAttribute("groupId", groupId);
        return "group/requests";
    }

    @GetMapping("/all")
    public String allGroups(Model model) {
        model.addAttribute("groups", groupService.getAll());
        return "group/groups";
    }

    @GetMapping("/make-admin")
    public String makeAdmin(@RequestParam("groupId") long groupId, @RequestParam("accountId") long accountId) {
        groupMembershipService.makeAdmin(groupId, accountId);
        return "redirect:/group?id=" + groupId;
    }

    @GetMapping("/send-request")
    public String sendRequest(@RequestParam("id") long id, @SessionAttribute("account") Account account) {
        groupMembershipService.sendRequest(groupService.getById(id).get(), account);
        return "redirect:/account?id=" + account.getId();
    }

}
