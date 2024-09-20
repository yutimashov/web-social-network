package com.getjavajob.training.timashovy.socialnetwork.web.servlets.message;

import com.getjavajob.training.timashovy.socialnetwork.service.interfaces.MessageService;
import com.getjavajob.training.timashovy.socialnetwork.service.serviceimpl.message.MessageServiceImpl;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.InputStreamSource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServlet;
import java.io.InputStream;

import static org.springframework.http.HttpStatus.NOT_FOUND;
import static org.springframework.http.HttpStatus.OK;
import static org.springframework.http.MediaType.IMAGE_JPEG;
import static org.springframework.http.ResponseEntity.status;

@Controller
public class MessageImageController extends HttpServlet {

    private final MessageService messageService;
    private final MessageServiceImpl messageServiceImpl;

    public MessageImageController(MessageService messageService, MessageServiceImpl messageServiceImpl) {
        this.messageService = messageService;
        this.messageServiceImpl = messageServiceImpl;
    }

    @GetMapping("/group-message/image")
    public ResponseEntity<InputStreamSource> getGroupMessageImage(@RequestParam("id") long id) {
        InputStream inputStreamImage = messageService.getGroupMessageById(id).getPhoto();
        if (inputStreamImage != null) {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(IMAGE_JPEG);
            return new ResponseEntity<>(new InputStreamResource(inputStreamImage), headers, OK);
        }
        return status(NOT_FOUND).build();
    }

    @GetMapping("/personal-message/image")
    public ResponseEntity<InputStreamSource> getPersonalMessageImage(@RequestParam("id") long id) {
        InputStream inputStreamImage = messageService.getPersonalMessageById(id).getPhoto();
        if (inputStreamImage != null) {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(IMAGE_JPEG);
            return new ResponseEntity<>(new InputStreamResource(inputStreamImage), headers, OK);
        }
        return status(NOT_FOUND).build();
    }

    @GetMapping("/account-wall/image")
    public ResponseEntity<InputStreamSource> getAccountWallImage(@RequestParam("id") long id) {
        InputStream inputStreamImage = messageServiceImpl.getAccountWallMessageById(id).getPhoto();
        if (inputStreamImage != null) {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(IMAGE_JPEG);
            return new ResponseEntity<>(new InputStreamResource(inputStreamImage), headers, OK);
        }
        return status(NOT_FOUND).build();
    }

}
