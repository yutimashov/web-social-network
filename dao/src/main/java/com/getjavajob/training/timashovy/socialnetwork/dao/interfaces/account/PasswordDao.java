package com.getjavajob.training.timashovy.socialnetwork.dao.interfaces.account;

import com.getjavajob.training.timashovy.socialnetwork.domain.account.Password;

import java.util.Optional;

public interface PasswordDao {

    Long create(Password password);

    Optional<Password> getById(Long accountId);

    Optional<Password> findByEmail(String email);

}
