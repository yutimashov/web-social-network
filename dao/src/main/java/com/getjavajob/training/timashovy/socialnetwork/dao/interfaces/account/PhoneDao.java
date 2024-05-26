package com.getjavajob.training.timashovy.socialnetwork.dao.interfaces.account;

import com.getjavajob.training.timashovy.socialnetwork.common.account.Phone;
import com.getjavajob.training.timashovy.socialnetwork.dao.util.dbutils.dbconnection.ConnectionWrapper;

import java.util.List;

public interface PhoneDao {

    Long create(ConnectionWrapper conn, Phone phone);

    boolean update(Long phoneId, String newPhoneNumber);

    List<Phone> getAll(Long accountId);

}
