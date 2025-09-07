package com.getjavajob.phoneservice.dao;

import com.getjavajob.training.timashovy.socialnetwork.domain.phone.Phone;
import com.getjavajob.training.timashovy.socialnetwork.domain.phone.PhoneType;

import java.util.List;

public interface PhoneRepository extends Repository<Long, Phone> {

    void updateNumber(Long phoneId, String newNumber);

    List<Phone> getPhoneNumbers(Long accountId, PhoneType phoneType);

}
