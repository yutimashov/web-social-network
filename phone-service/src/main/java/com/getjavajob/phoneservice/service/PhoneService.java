package com.getjavajob.phoneservice.service;

import com.getjavajob.training.timashovy.socialnetwork.domain.account.Account;
import com.getjavajob.training.timashovy.socialnetwork.domain.phone.Phone;
import com.getjavajob.training.timashovy.socialnetwork.domain.phone.PhoneType;

import java.util.List;

public interface PhoneService {

    void createPersonalPhones(Account account, String phoneNumbers);

    void createWorkingPhones(Account account, String phoneNumbers);

    List<Phone> getPhoneNumbers(Long accountId, PhoneType phoneType);

    void delete(Long phoneId);

    void update(Long phoneId, String newPhoneNumber);

    Phone create(Phone phone);

}
