package com.getjavajob.training.timashovy.socialnetwork.service.interfaces;

import com.getjavajob.training.timashovy.socialnetwork.domain.account.Phone;

import java.util.List;

public interface PhoneService {

    List<Phone> createPersonalPhones(Long accountId, String phoneNumbers);

    List<Phone> createWorkingPhones(Long accountId, String phoneNumbers);

    List<Phone> getPersonalPhoneNumbers(Long accountId);

    List<Phone> getWorkPhoneNumbers(Long accountId);

    void deleteById(Long phoneId);

    boolean update(Long phoneId, String newPhoneNumber);

    Long create(Phone phone);

}
