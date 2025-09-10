package com.getjavajob.groupservice.web.controller;

import com.getjavajob.groupservice.service.group.GroupService;
import com.getjavajob.training.timashovy.socialnetwork.domain.group.Group;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.InputStreamSource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.Optional;

import static org.springframework.http.HttpStatus.NOT_FOUND;
import static org.springframework.http.HttpStatus.OK;
import static org.springframework.http.MediaType.IMAGE_JPEG;
import static org.springframework.http.ResponseEntity.status;

@Controller
public class ImageController {

    private final GroupService groupService;

    public ImageController(GroupService groupService) {
        this.groupService = groupService;
    }

    @GetMapping("/group/avatar")
    public ResponseEntity<InputStreamSource> accountAvatar(@RequestParam("id") Long id) {
        Optional<Group> groupOptional = groupService.getById(id);
        if (groupOptional.isPresent()) {
            InputStream avatar = new ByteArrayInputStream(groupOptional.get().getAvatar());
            return createImageResponse(avatar);
        }
        return status(NOT_FOUND).build();
    }

    private ResponseEntity<InputStreamSource> createImageResponse(InputStream inputStream) {
        if (inputStream != null) {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(IMAGE_JPEG);
            return new ResponseEntity<>(new InputStreamResource(inputStream), headers, OK);
        }
        return status(NOT_FOUND).build();
    }

}
