package com.getjavajob.groupservice.web.controller;

import com.getjavajob.groupservice.service.group.GroupService;
import com.getjavajob.groupservice.service.membership.GroupMembershipService;
import com.getjavajob.groupservice.web.dto.GroupDto;
import com.getjavajob.groupservice.web.dto.GroupMapper;
import com.getjavajob.groupservice.web.feignclients.MessageClient;
import com.getjavajob.training.timashovy.socialnetwork.domain.account.Account;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

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

    /**
     * Get group page
     *
     * @param groupId id of the group
     * @param account session account
     * @return jsp page of the group
     */
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

    /**
     * List all existing groups.
     *
     * @return jsp page with listed groups
     */
    @GetMapping("/all")
    public String allGroups(Model model) {
        model.addAttribute("groups", groupService.getAll());
        return "group/groups";
    }

    /**
     * Get page for group creation.
     *
     * @return jsp page with creating group elements
     */
    @GetMapping("/create")
    public String create() {
        return "group/create";
    }

    /**
     * Create new group.
     *
     * @param groupDto object with information related to new group
     * @param account  session account
     * @return page of all existing groups with a new added one
     */
    @PostMapping("/create")
    public String processGroupCreation(@ModelAttribute GroupDto groupDto,
                                       @SessionAttribute("account") Account account) {
        groupService.create(new GroupMapper().toGroup(groupDto, account), account);
        return "redirect:/group/all";
    }

    /**
     * Accept group member request.
     *
     * @param groupId   group to join
     * @param accountId id of account to join
     * @return jsp page of the group with added member
     */
    @GetMapping("/accept-request")
    public String acceptRequest(@RequestParam("groupId") Long groupId,
                                @RequestParam("accountId") Long accountId) {
        groupMembershipService.makeMember(groupId, accountId);
        return "redirect:/group?id=" + groupId;
    }

    /**
     * Decline group member request.
     *
     * @param groupId   group to join
     * @param accountId id of account to join
     * @return jsp page of the group with added member
     */
    @GetMapping({"/decline-request", "/delete-member"})
    public String deleteGroupMember(@RequestParam("groupId") Long groupId,
                                    @RequestParam("accountId") Long accountId) {
        groupMembershipService.deleteMember(groupId, accountId);
        return "redirect:/group?id=" + groupId;
    }

    /**
     * List all group members.
     *
     * @param groupId id of a group
     * @return jsp page with group members
     */
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

    /**
     * List account requests for joining group.
     *
     * @param groupId id of a group
     * @return account requests for group joining
     */
    @GetMapping("/requests")
    public String requests(@RequestParam("id") Long groupId,
                           Model model) {
        model.addAttribute("groupRequests", groupMembershipService.getIncomingRequests(groupId));
        model.addAttribute("groupId", groupId);
        return "group/requests";
    }

    /**
     * Make a regular group member admin.
     *
     * @param groupId   id of a group
     * @param accountId id of account, who will be a new admin
     * @return jsp page of a group
     */
    @GetMapping("/make-admin")
    public String makeAdmin(@RequestParam("groupId") Long groupId,
                            @RequestParam("accountId") Long accountId) {
        groupMembershipService.makeAdmin(groupId, accountId);
        return "redirect:/group?id=" + groupId;
    }

    /**
     * Send request for joining group.
     *
     * @param groupId id of a group
     * @param account session account
     * @return jsp page of account or 404 if group does not exist
     */
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
