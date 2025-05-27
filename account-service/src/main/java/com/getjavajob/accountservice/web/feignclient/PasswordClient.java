package com.getjavajob.accountservice.web.feignclient;

import com.getjavajob.training.timashovy.socialnetwork.domain.account.Account;
import com.getjavajob.training.timashovy.socialnetwork.domain.password.Password;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@FeignClient("PASSWORD-SERVICE")
public interface PasswordClient {

    @GetMapping("password/{accountId}")
    ResponseEntity<Password> get(@PathVariable Long accountId);

    @PostMapping("password/create")
    ResponseEntity<Password> create(@RequestBody Account account, @RequestParam String rawPassword);

    @DeleteMapping("password/{id}")
    void delete(@PathVariable Long id);

}
