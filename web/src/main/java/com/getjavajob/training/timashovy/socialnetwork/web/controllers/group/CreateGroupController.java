package com.getjavajob.training.timashovy.socialnetwork.web.controllers.group;

import com.getjavajob.training.timashovy.socialnetwork.common.account.Account;
import com.getjavajob.training.timashovy.socialnetwork.service.interfaces.GroupMembershipService;
import com.getjavajob.training.timashovy.socialnetwork.service.interfaces.GroupService;
import com.getjavajob.training.timashovy.socialnetwork.web.dto.GroupDto;
import com.getjavajob.training.timashovy.socialnetwork.web.mappers.GroupMapper;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@Controller
@RequestMapping("/group")
public class CreateGroupController {

    private final GroupService groupService;
    private final GroupMembershipService groupMembershipService;

    public CreateGroupController(GroupService groupService, GroupMembershipService groupMembershipService) {
        this.groupService = groupService;
        this.groupMembershipService = groupMembershipService;
    }

    @GetMapping("/create")
    protected String doGet() {
        return "group/create";
    }

    @PostMapping("/create")
    protected String doPost(@ModelAttribute GroupDto groupDto,
                            @SessionAttribute("account") Account account) throws IOException {
        Long accountId = account.getId();
        Long groupId = groupService.create(new GroupMapper().toGroup(groupDto, accountId));
        groupMembershipService.sendRequest(groupId, accountId);
        groupMembershipService.makeMember(groupId, accountId);
        groupMembershipService.makeAdmin(groupId, accountId);
        return "redirect:/group/all";
    }

}
