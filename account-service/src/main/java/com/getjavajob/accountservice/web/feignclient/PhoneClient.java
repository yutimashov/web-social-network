package com.getjavajob.accountservice.web.feignclient;

import com.getjavajob.training.timashovy.socialnetwork.domain.account.Account;
import com.getjavajob.training.timashovy.socialnetwork.domain.phone.Phone;
import com.getjavajob.training.timashovy.socialnetwork.domain.phone.PhoneType;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(name = "PHONE-SERVICE", path = "/api/phone")
public interface PhoneClient {

    @PostMapping("/create/personal")
    void createPersonalPhones(@RequestParam Account account, @RequestParam String phoneNumbers);

    @PostMapping("/create/working")
    void createWorkingPhones(@RequestParam Account account, @RequestParam String phoneNumbers);

    @DeleteMapping("/delete")
    void delete(@RequestParam Long phoneId);

    @GetMapping("/numbers")
    ResponseEntity<List<String>> getPhoneNumbers(@RequestParam Long accountId, @RequestParam PhoneType phoneType);

    @PostMapping("/update")
    void update(@RequestParam Long phoneId, @RequestParam String newPhoneNumber);

    @PostMapping("/create")
    ResponseEntity<Phone> create(@RequestBody Phone phone);

}
