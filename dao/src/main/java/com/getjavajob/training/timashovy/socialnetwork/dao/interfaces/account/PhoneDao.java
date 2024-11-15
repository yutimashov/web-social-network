package com.getjavajob.training.timashovy.socialnetwork.dao.interfaces.account;

import com.getjavajob.training.timashovy.socialnetwork.domain.phone.Phone;
import com.getjavajob.training.timashovy.socialnetwork.domain.phone.PhoneType;

import java.util.List;

public interface PhoneDao {

    Long create(Phone phone);

    boolean updateNumber(Long phoneId, String newNumber);

    List<Phone> getPhones(Long accountId, PhoneType phoneType);

    List<String> getPhoneNumbers(Long accountId, PhoneType phoneType);

    void delete(Long phoneId);

}
