package com.getjavajob.accountservice.web.feignclient;

import com.getjavajob.training.timashovy.socialnetwork.domain.account.Account;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient("PHONE-SERVICE")
public interface PhoneClient {

    @PostMapping
    void createPersonalPhones(@RequestParam Account account, @RequestParam String phoneNumbers);

    @PostMapping
    void createWorkingPhones(@RequestParam Account account, @RequestParam String phoneNumbers);

}
