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

    public Account verifyLoginCredentials(String email, String password) {
        Password dbPassword = passwordService.findPasswordByEmail(email);
        if (checkDataForNull(email, password, dbPassword)) {
            String enteredPasswordValue = hashCredential(password, dbPassword.getSalt());
            return dbPassword.getPassword().equals(enteredPasswordValue)
                    ? accountService.getAccountById(dbPassword.getAccountId())
                    : null;
        } else {
            return null;
        }
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
