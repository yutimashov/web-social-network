package com.getjavajob.training.timashovy.socialnetwork.dao.interfaces.account;

import com.getjavajob.training.timashovy.socialnetwork.common.account.Password;
import com.getjavajob.training.timashovy.socialnetwork.dao.util.dbutils.dbconnection.ConnectionWrapper;

import java.util.Optional;

public interface PasswordDao {

    Long create(ConnectionWrapper connection, Password password);

    Optional<Password> getById(Long accountId);

    Optional<Password> findByEmail(String email);

}
