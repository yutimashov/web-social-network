package com.getjavajob.training.timashovy.socialnetwork.dao.interfaces.account;

import com.getjavajob.training.timashovy.socialnetwork.domain.account.Account;
import com.getjavajob.training.timashovy.socialnetwork.domain.phone.Phone;

import java.util.List;

public interface PhoneDao {

    Long create(Phone phone);

    boolean updateNumber(Phone phone, String newNumber);

    List<Phone> getAll(Account account);

    void delete(Phone phone);

}
