package com.getjavajob.training.timashovy.socialnetwork.dao.interfaces.account;

import com.getjavajob.training.timashovy.socialnetwork.common.account.Password;

import java.util.Optional;

public interface PasswordDao {

    Long create(Long accountId, Password password);

    boolean update(Password password);

    Optional<Password> getById(Long accountId);

}
