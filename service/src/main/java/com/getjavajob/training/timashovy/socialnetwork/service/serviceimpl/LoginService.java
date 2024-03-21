package com.getjavajob.training.timashovy.socialnetwork.service.serviceimpl;

import com.getjavajob.training.timashovy.socialnetwork.common.account.Account;
import com.getjavajob.training.timashovy.socialnetwork.common.account.Password;
import com.getjavajob.training.timashovy.socialnetwork.service.interfaces.AccountService;
import com.getjavajob.training.timashovy.socialnetwork.service.interfaces.PasswordService;

import static com.getjavajob.training.timashovy.socialnetwork.service.serviceimpl.AccountServiceImpl.getAccountServiceInstance;
import static com.getjavajob.training.timashovy.socialnetwork.service.serviceimpl.PasswordServiceImpl.getPasswordServiceInstance;
import static com.getjavajob.training.timashovy.socialnetwork.service.util.CredentialHashUtil.hashCredential;
import static java.util.Objects.isNull;

public class LoginService {

    private static final LoginService loginService = new LoginService();
    private final AccountService accountService = getAccountServiceInstance();
    private final PasswordService passwordService = getPasswordServiceInstance();

    private LoginService() {
    }

    public static LoginService getLoginServiceInstance() {
        return loginService;
    }

    public Account verifyRawLoginCredentials(String email, String rawPassword) {
        Password dbPassword = passwordService.findPasswordByEmail(email);
        String passwordValue = dbPassword.getPassword();
        String salt = dbPassword.getSalt();
        if (checkDataForNull(email, rawPassword, dbPassword)) {
            String hashedPasswordValue = hashCredential(rawPassword, salt);
            return passwordValue.equals(hashedPasswordValue) ? accountService.getAccountById(dbPassword.getAccountId())
                    : null;
        }
        return null;
    }

    public Account verifyLoginCredentials(String email, String password) {
        Password dbPassword = passwordService.findPasswordByEmail(email);
        if (dbPassword == null) {
            return null;
        }
        String passwordValue = dbPassword.getPassword();
        if (passwordValue.equals(password)) {
            return accountService.getAccountById(dbPassword.getAccountId());
        }
        return null;
    }

    private boolean checkDataForNull(Object... data) {
        for (Object dataValue : data) {
            if (isNull(dataValue)) {
                return false;
            }
        }
        return true;
    }

}
