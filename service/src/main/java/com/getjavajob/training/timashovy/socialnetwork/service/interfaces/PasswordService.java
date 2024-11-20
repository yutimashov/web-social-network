package com.getjavajob.training.timashovy.socialnetwork.service.interfaces;

import com.getjavajob.training.timashovy.socialnetwork.domain.account.Account;
import com.getjavajob.training.timashovy.socialnetwork.domain.password.Password;

import java.util.Optional;

public interface PasswordService {

    Password create(Account account, String rawPassword);

    Optional<Password> get(Long accountId);

    Optional<Password> findPasswordByEmail(String email);

}
