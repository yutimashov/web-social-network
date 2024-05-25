package com.getjavajob.training.timashovy.socialnetwork.service.interfaces;

import com.getjavajob.training.timashovy.socialnetwork.common.account.Phone;
import com.getjavajob.training.timashovy.socialnetwork.common.account.PhoneType;

import java.util.List;

public interface PhoneService {

    List<Phone> createPersonalPhones(Long accountId, String phoneNumbers);

    List<Phone> createWorkingPhones(Long accountId, String phoneNumbers);

    List<Phone> getPersonalPhoneNumbers(Long accountId);

    List<Phone> getWorkPhoneNumbers(Long accountId);

    void updateById(Long phoneId, String newPhoneNumber);

}
