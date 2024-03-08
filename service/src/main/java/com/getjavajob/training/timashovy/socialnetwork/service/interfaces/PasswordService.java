package com.getjavajob.training.timashovy.socialnetwork.service.interfaces;

import com.getjavajob.training.timashovy.socialnetwork.common.Account;
import com.getjavajob.training.timashovy.socialnetwork.common.Password;

public interface PasswordService {

    boolean savePassword(Account account, String password);
    boolean checkPassword(String enteredPassword);
    boolean changePassword(Password password);

}
