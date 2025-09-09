package com.getjavajob.securityservice.web.feignclient;

import com.getjavajob.training.timashovy.socialnetwork.domain.account.Account;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

@FeignClient(name = "ACCOUNT-SERVICE", path = "/api/account")
public interface AccountServiceClient {

    @PostMapping(value = "/create", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    ResponseEntity<Account> createAccount(@RequestParam("firstName") String firstName,
                                          @RequestParam("lastName") String lastName,
                                          @RequestParam(value = "middleName", required = false) String middleName,
                                          @RequestParam("email") String email,
                                          @RequestParam(value = "icq", required = false) String icq,
                                          @RequestParam(value = "skype", required = false) String skype,
                                          @RequestParam(value = "personalPhones", required = false) String personalPhones,
                                          @RequestParam(value = "workingPhones", required = false) String workingPhones,
                                          @RequestPart(value = "avatar", required = false) MultipartFile avatar);

}
