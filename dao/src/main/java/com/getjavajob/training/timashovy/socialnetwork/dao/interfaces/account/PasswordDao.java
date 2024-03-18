package com.getjavajob.training.timashovy.socialnetwork.dao.interfaces.account;

import com.getjavajob.training.timashovy.socialnetwork.common.account.Account;
import com.getjavajob.training.timashovy.socialnetwork.common.account.Password;

public interface PasswordDao {

    Long create(Password password);

    boolean update(Password password);

    boolean verify(Account account, Password password);

    Password get(Account account);

}
