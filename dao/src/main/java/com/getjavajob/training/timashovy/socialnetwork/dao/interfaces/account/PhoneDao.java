package com.getjavajob.training.timashovy.socialnetwork.dao.interfaces.account;

import com.getjavajob.training.timashovy.socialnetwork.common.account.Phone;

import java.util.List;

public interface PhoneDao {

    Long create(Phone phone);

    boolean delete();

    boolean update(Long phoneId, String newPhoneNumber);

    List<Phone> getAll(Long accountId);

}
