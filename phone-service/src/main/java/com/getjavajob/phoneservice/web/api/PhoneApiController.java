package com.getjavajob.phoneservice.web.api;

import com.getjavajob.phoneservice.service.PhoneService;
import com.getjavajob.training.timashovy.socialnetwork.domain.account.Account;
import com.getjavajob.training.timashovy.socialnetwork.domain.phone.Phone;
import com.getjavajob.training.timashovy.socialnetwork.domain.phone.PhoneType;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/api/phone")
public class PhoneApiController {

    private final PhoneService phoneService;

    public PhoneApiController(PhoneService phoneService) {
        this.phoneService = phoneService;
    }

    @DeleteMapping("/delete")
    public void delete(Long phoneId) {
        phoneService.delete(phoneId);
    }

    @GetMapping("/numbers")
    public ResponseEntity<List<Phone>> getNumbers(Long accountId, PhoneType phoneType) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(phoneService.getPhoneNumbers(accountId, phoneType));
    }

    @PostMapping("/update")
    public void update(Long phoneId, String newPhoneNumber) {
        phoneService.update(phoneId, newPhoneNumber);
    }

    @PostMapping("/create")
    public ResponseEntity<Phone> create(@RequestBody Phone phone) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(phoneService.create(phone));
    }

    @PostMapping("/create/personal")
    public void createPersonalPhones(@RequestBody Account account, @RequestBody String phoneNumbers) {
        phoneService.createPersonalPhones(account, phoneNumbers);
    }

    @PostMapping("/create/working")
    public void createWorkingPhones(@RequestBody Account account, @RequestBody String phoneNumbers) {
        phoneService.createWorkingPhones(account, phoneNumbers);
    }

}
