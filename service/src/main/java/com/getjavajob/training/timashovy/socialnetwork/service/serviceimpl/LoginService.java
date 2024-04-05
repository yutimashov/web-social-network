package com.getjavajob.training.timashovy.socialnetwork.service.serviceimpl;

import com.getjavajob.training.timashovy.socialnetwork.common.account.Account;
import com.getjavajob.training.timashovy.socialnetwork.common.account.Password;
import com.getjavajob.training.timashovy.socialnetwork.service.interfaces.AccountService;
import com.getjavajob.training.timashovy.socialnetwork.service.interfaces.PasswordService;

import java.util.Optional;

import static com.getjavajob.training.timashovy.socialnetwork.service.serviceimpl.AccountServiceImpl.getAccountServiceInstance;
import static com.getjavajob.training.timashovy.socialnetwork.service.serviceimpl.PasswordServiceImpl.getPasswordServiceInstance;
import static com.getjavajob.training.timashovy.socialnetwork.service.util.CredentialHashUtil.hashCredential;
import static java.util.Objects.isNull;
import static java.util.Optional.empty;

public class LoginService {

    private static final LoginService loginService = new LoginService();
    private final AccountService accountService = getAccountServiceInstance();
    private final PasswordService passwordService = getPasswordServiceInstance();

    private LoginService() {
    }

    public static LoginService getLoginServiceInstance() {
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
        String verifyingSaltedPasswordValue = hashCredential(verifyingPassword, dbPassword.getSalt());
        return dbPasswordValue.equals(verifyingSaltedPasswordValue)
                ? accountService.getAccountById(dbPassword.getAccountId()) : empty();
    }

}
