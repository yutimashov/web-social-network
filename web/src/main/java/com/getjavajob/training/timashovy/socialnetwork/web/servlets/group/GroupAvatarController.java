package com.getjavajob.training.timashovy.socialnetwork.web.servlets.group;

import com.getjavajob.training.timashovy.socialnetwork.common.Group;
import com.getjavajob.training.timashovy.socialnetwork.service.interfaces.GroupService;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.InputStreamSource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServlet;
import java.io.InputStream;
import java.util.Optional;

import static org.springframework.http.HttpStatus.NOT_FOUND;
import static org.springframework.http.HttpStatus.OK;
import static org.springframework.http.MediaType.IMAGE_JPEG;
import static org.springframework.http.ResponseEntity.status;

@Controller
@RequestMapping("/group/avatar")
public class GroupAvatarServlet extends HttpServlet {

    private final GroupService groupService;

    public GroupAvatarServlet(GroupService groupService) {
        this.groupService = groupService;
    }

    @GetMapping
    protected ResponseEntity<InputStreamSource> getGroupAvatar(@RequestParam("id") long id) {
        Optional<Group> groupOptional = groupService.getById(id);
        if (groupOptional.isPresent()) {
            InputStream inputStreamImage = groupOptional.get().getAvatar();
            if (inputStreamImage != null) {
                HttpHeaders headers = new HttpHeaders();
                headers.setContentType(IMAGE_JPEG);
                return new ResponseEntity<>(new InputStreamResource(inputStreamImage), headers, OK);
            }
        }
        return status(NOT_FOUND).build();
    }

}
