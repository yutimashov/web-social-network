package com.getjavajob.training.timashovy.socialnetwork.service.interfaces;

import com.getjavajob.training.timashovy.socialnetwork.domain.account.Account;
import com.getjavajob.training.timashovy.socialnetwork.domain.phone.Phone;

import java.util.List;

public interface PhoneService {

    void createPersonalPhones(Account account, String phoneNumbers);

    void createWorkingPhones(Account account, String phoneNumbers);

    List<Phone> getPersonalPhoneNumbers(Long accountId);

    List<Phone> getWorkPhoneNumbers(Long accountId);

    void delete(Long phoneId);

    boolean update(Long phoneId, String newPhoneNumber);

    Long create(Phone phone);

}
