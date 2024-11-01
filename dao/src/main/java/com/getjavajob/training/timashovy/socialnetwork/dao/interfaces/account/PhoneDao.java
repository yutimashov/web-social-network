package com.getjavajob.training.timashovy.socialnetwork.dao.interfaces.account;

import com.getjavajob.training.timashovy.socialnetwork.domain.phone.Phone;

import java.util.List;

public interface PhoneDao {

    Long create(Phone phone);

    boolean updateNumber(Long phoneId, String newNumber);

    List<Phone> getAll(Long accountId);

    void delete(Long phoneId);

}
