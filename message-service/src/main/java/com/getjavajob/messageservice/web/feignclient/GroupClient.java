package com.getjavajob.messageservice.web.feignclient;

import com.getjavajob.training.timashovy.socialnetwork.domain.group.Group;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Optional;

@FeignClient("GROUP-SERVICE")
public interface GroupClient {

    @GetMapping("/group")
    ResponseEntity<Optional<Group>> group(@RequestParam Long id);

}
