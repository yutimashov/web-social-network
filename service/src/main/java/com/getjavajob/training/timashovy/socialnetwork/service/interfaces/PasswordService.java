package com.getjavajob.training.timashovy.socialnetwork.service.interfaces;

import com.getjavajob.training.timashovy.socialnetwork.common.account.Account;
import com.getjavajob.training.timashovy.socialnetwork.common.account.Password;

public interface PasswordService {

    boolean savePassword(Account account, String password);
    boolean checkPassword(Account account, String enteredPassword);
    boolean changePassword(Password password);

}
