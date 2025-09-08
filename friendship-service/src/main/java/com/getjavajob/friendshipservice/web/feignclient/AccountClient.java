package com.getjavajob.friendshipservice.web.feignclient;

import com.getjavajob.training.timashovy.socialnetwork.domain.account.Account;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "ACCOUNT-SERVICE", path = "/api/account")
public interface AccountClient {

    @GetMapping("/get")
    ResponseEntity<Account> getAccount(@RequestParam Long accountId);

}
