package com.getjavajob.training.timashovy.socialnetwork.service.interfaces;

import com.getjavajob.training.timashovy.socialnetwork.common.account.Account;
import com.getjavajob.training.timashovy.socialnetwork.common.account.Password;

public interface PasswordService {

    Long create(Password password);

    boolean verify(Password password);

    boolean update(Password password);

    Password get(Account account);

    Password findPasswordByEmail(String email);

}
