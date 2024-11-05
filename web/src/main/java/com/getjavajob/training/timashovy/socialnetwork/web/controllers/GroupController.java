package com.getjavajob.training.timashovy.socialnetwork.web.controllers;

import com.getjavajob.training.timashovy.socialnetwork.domain.account.Account;
import com.getjavajob.training.timashovy.socialnetwork.service.interfaces.AccountService;
import com.getjavajob.training.timashovy.socialnetwork.service.interfaces.GroupMembershipService;
import com.getjavajob.training.timashovy.socialnetwork.service.interfaces.GroupService;
import com.getjavajob.training.timashovy.socialnetwork.service.interfaces.MessageService;
import com.getjavajob.training.timashovy.socialnetwork.web.dto.GroupDto;
import com.getjavajob.training.timashovy.socialnetwork.web.mappers.GroupMapper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttribute;

import javax.servlet.http.HttpServlet;

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
    public String group(@RequestParam("id") Long groupId,
                        @SessionAttribute("account") Account account,
                        Model model) {
        if (groupService.getById(groupId).isPresent()) {
            model.addAttribute("group", groupService.getById(groupId).get());
            Long accountId = account.getId();
            model.addAttribute("isAdmin", groupMembershipService.isAdmin(groupId, accountId));
            model.addAttribute("isSubscriber", groupMembershipService.isSubscriber(groupId, accountId));
            model.addAttribute("isMember", groupMembershipService.isMember(groupId, accountId));
            model.addAttribute("groupPosts", messageService.getAllGroupMessages(groupId));
            model.addAttribute("accountService", accountService);
            return "group/group";
        } else {
            return "error/404";
        }
    }

    @GetMapping("/accept-request")
    public String acceptRequest(@RequestParam("groupId") Long groupId,
                                @RequestParam("accountId") Long accountId) {
        groupMembershipService.makeMember(groupId, accountId);
        return "redirect:/group?id=" + groupId;
    }

    @GetMapping("/create")
    public String create() {
        return "group/create";
    }

    @PostMapping("/create")
    public String processGroupCreation(@ModelAttribute GroupDto groupDto,
                                       @SessionAttribute("account") Account account) {
        groupService.create(new GroupMapper().toGroup(groupDto, account), account);
        return "redirect:/group/all";
    }

    @GetMapping({"/decline-request", "/delete-member"})
    public String deleteGroupMember(@RequestParam("groupId") Long groupId,
                                    @RequestParam("accountId") Long accountId) {
        groupMembershipService.deleteMember(groupId, accountId);
        return "redirect:/group?id=" + groupId;
    }

    @GetMapping("/members")
    public String groupMembers(@RequestParam("id") Long groupId,
                               Model model) {
        if (groupService.getById(groupId).isPresent()) {
            model.addAttribute("group", groupService.getById(groupId).get());
            model.addAttribute("groupMembers", groupMembershipService.getRegularMembers(groupId));
            model.addAttribute("groupAdmins", groupMembershipService.getAdmins(groupId));
        }
        return "group/members";
    }

    @GetMapping("/requests")
    public String requests(@RequestParam("id") Long groupId,
                           Model model) {
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
    public String makeAdmin(@RequestParam("groupId") Long groupId,
                            @RequestParam("accountId") Long accountId) {
        groupMembershipService.makeAdmin(groupId, accountId);
        return "redirect:/group?id=" + groupId;
    }

    @GetMapping("/send-request")
    public String sendRequest(@RequestParam("id") Long groupId,
                              @SessionAttribute("account") Account account) {
        if (groupService.getById(groupId).isPresent()) {
            groupMembershipService.sendRequest(groupService.getById(groupId).get(), account);
            return "redirect:/account?id=" + account.getId();
        } else {
            return "error/404";
        }
    }

}
