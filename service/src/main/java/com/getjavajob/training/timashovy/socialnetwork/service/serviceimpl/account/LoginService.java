package com.getjavajob.training.timashovy.socialnetwork.service.serviceimpl.account;

import com.getjavajob.training.timashovy.socialnetwork.common.account.Account;
import com.getjavajob.training.timashovy.socialnetwork.common.account.Password;
import com.getjavajob.training.timashovy.socialnetwork.service.interfaces.AccountService;
import com.getjavajob.training.timashovy.socialnetwork.service.interfaces.PasswordService;

import java.util.Optional;

import static com.getjavajob.training.timashovy.socialnetwork.service.util.PasswordUtil.hashCredentialData;
import static java.util.Objects.isNull;
import static java.util.Optional.empty;

public class LoginService {

    private static final LoginService loginService = new LoginService();
    private final AccountService accountService = AccountServiceImpl.getInstance();
    private final PasswordService passwordService = PasswordServiceImpl.getInstance();

    private LoginService() {
    }

    public static LoginService getInstance() {
        return loginService;
    }

    public Optional<Account> getLoggedInAccount(String verifyingEmail, String verifyingPassword) {
        if (isNull(verifyingEmail) || isNull(verifyingPassword)) {
            return empty();
        }
        Password dbPassword = passwordService.findPasswordByEmail(verifyingEmail);
        if (isNull(dbPassword)) {
            return empty();
        }
        String dbPasswordValue = dbPassword.getPassword();
        String verifyingSaltedPasswordValue = hashCredentialData(verifyingPassword, dbPassword.getSalt());
        return dbPasswordValue.equals(verifyingSaltedPasswordValue)
                ? accountService.getAccountById(dbPassword.getAccountId()) : empty();
    }

}
