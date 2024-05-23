package com.getjavajob.training.timashovy.socialnetwork.service.interfaces;

import com.getjavajob.training.timashovy.socialnetwork.common.account.Account;

import java.util.Optional;

public interface LoginService {

    Optional<Account> getLoggedInAccount(String email, String password);

}
