package com.getjavajob.dataprocessingservice.account.web.feignclient;

import com.getjavajob.training.timashovy.socialnetwork.domain.account.Account;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Optional;

@FeignClient(name = "ACCOUNT-SERVICE", path = "/api/account")
public interface
AccountClient {

    @GetMapping
    ResponseEntity<Optional<Account>> accountById(@RequestParam Long id);

    @GetMapping("/update-by-id")
    void updateById(@RequestParam Account account, @RequestParam Long accountId);

}
