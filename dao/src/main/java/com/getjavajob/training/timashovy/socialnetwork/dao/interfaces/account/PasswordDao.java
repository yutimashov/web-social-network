package com.getjavajob.training.timashovy.socialnetwork.dao.interfaces.account;

import com.getjavajob.training.timashovy.socialnetwork.common.account.Account;
import com.getjavajob.training.timashovy.socialnetwork.common.account.Password;

public interface PasswordDao {

    boolean savePassword(Account account, Password password);
    boolean updatePassword(Account account, Password password);
    boolean checkPassword(Account account, Password password);

}
