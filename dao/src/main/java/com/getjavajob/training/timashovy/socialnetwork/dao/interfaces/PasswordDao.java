package com.getjavajob.training.timashovy.socialnetwork.dao.interfaces;

import com.getjavajob.training.timashovy.socialnetwork.common.Account;
import com.getjavajob.training.timashovy.socialnetwork.common.Password;

public interface PasswordDao {

    boolean savePassword(Account account, Password password);
    boolean updatePassword(Account account, Password password);
    boolean checkPassword(Account account, Password password);

}
