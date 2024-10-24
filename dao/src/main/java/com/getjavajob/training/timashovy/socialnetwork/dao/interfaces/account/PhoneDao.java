package com.getjavajob.training.timashovy.socialnetwork.dao.interfaces.account;

import com.getjavajob.training.timashovy.socialnetwork.domain.account.Phone;

import java.util.List;

public interface PhoneDao {

    Long create(Phone phone);

    boolean update(Long phoneId, String newPhoneNumber);

    List<Phone> getAll(Long accountId);

    void deleteById(Long id);

}
