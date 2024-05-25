package com.getjavajob.training.timashovy.socialnetwork.dao.interfaces.account;

import com.getjavajob.training.timashovy.socialnetwork.common.account.Password;

import java.sql.Connection;
import java.util.Optional;

public interface PasswordDao {

    Long create(Connection connection, Password password);

    Optional<Password> getById(Long accountId);

    Optional<Password> findByEmail(String email);

}
