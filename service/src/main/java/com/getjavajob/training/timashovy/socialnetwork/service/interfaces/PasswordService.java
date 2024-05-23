package com.getjavajob.training.timashovy.socialnetwork.service.interfaces;

import com.getjavajob.training.timashovy.socialnetwork.common.account.Password;

import java.util.Optional;

public interface PasswordService {

    Long create(Long accountId, String rawPassword);

    Optional<Password> get(Long accountId);

    Optional<Password> findPasswordByEmail(String email);

}
