package com.getjavajob.training.timashovy.socialnetwork.web.servlets.group;

import com.getjavajob.training.timashovy.socialnetwork.service.interfaces.GroupService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/group/all")
public class ListAllGroupsController {

    private final GroupService groupService;

    public ListAllGroupsController(GroupService groupService) {
        this.groupService = groupService;
    }

    @GetMapping
    public String doGet(Model model) {
        model.addAttribute("groups", groupService.getAll());
        return "group/groups";
    }

}
