package com.getjavajob.groupservice.web.feignclients;

import com.getjavajob.training.timashovy.socialnetwork.domain.message.GroupMessage;
import com.getjavajob.training.timashovy.socialnetwork.domain.message.PersonalWallMessage;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient(name = "MESSAGE-SERVICE", path = "/api/message")
public interface MessageClient {

    @GetMapping("/wall/account")
    ResponseEntity<List<PersonalWallMessage>> getAccountWallMessages(@RequestParam("id") Long accountId);

    @GetMapping("/wall/group")
    ResponseEntity<List<GroupMessage>> groupMessages(@RequestParam Long id);

}
