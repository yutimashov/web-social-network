package com.getjavajob.groupservice.web;

import com.getjavajob.groupservice.service.group.GroupService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/group")
public class GroupController {

    private static final Logger logger = LoggerFactory.getLogger(GroupController.class);

    private final GroupService groupService;

    public GroupController(GroupService groupService) {
        this.groupService = groupService;
    }

/*    @GetMapping("/group")
    public ResponseEntity<Optional<Group>> group(@RequestParam Long id) {
        return ResponseEntity
                .status(OK)
                .body(groupService.getById(id));
    }*/

    @GetMapping("/all")
    public String allGroups(Model model) {
        logger.info("Get all groups");
        model.addAttribute("groups", groupService.getAll());
        logger.info("All groups: {}", groupService.getAll());
        return "group/groups";
    }

}
