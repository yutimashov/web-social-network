package com.getjavajob.securityservice.service.password;

import com.getjavajob.training.timashovy.socialnetwork.domain.account.Account;
import com.getjavajob.training.timashovy.socialnetwork.domain.password.Password;

import java.util.Optional;

public interface PasswordService {

    Password create(Account account, String rawPassword);

    Optional<Password> get(Long accountId);

}
