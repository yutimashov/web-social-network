package com.getjavajob.training.timashovy.socialnetwork.service.serviceimpl.account;

import com.getjavajob.training.timashovy.socialnetwork.common.account.Account;
import com.getjavajob.training.timashovy.socialnetwork.common.account.Password;
import com.getjavajob.training.timashovy.socialnetwork.service.interfaces.AccountService;
import com.getjavajob.training.timashovy.socialnetwork.service.interfaces.PasswordService;
import com.getjavajob.training.timashovy.socialnetwork.service.util.ServiceSingletonRegistry;

import java.util.Optional;

import static com.getjavajob.training.timashovy.socialnetwork.service.util.PasswordUtil.hashCredentialData;
import static com.getjavajob.training.timashovy.socialnetwork.service.util.ServiceSingletonsNames.LOGIN_SERVICE_SINGLETON;
import static java.util.Objects.isNull;
import static java.util.Optional.empty;

public class LoginService {

    private final AccountService accountService;
    private final PasswordService passwordService;

    private LoginService(AccountService accountService, PasswordService passwordService) {
        this.accountService = accountService;
        this.passwordService = passwordService;
    }

    public static LoginService getInstance() {
        return ServiceSingletonRegistry.getInstance().getSingleton(LOGIN_SERVICE_SINGLETON);
    }

    public static void registerSingleton(AccountService accountService, PasswordService passwordService) {
        ServiceSingletonRegistry.getInstance().registerSingleton(LOGIN_SERVICE_SINGLETON,
                new LoginService(accountService, passwordService));
    }

    public Optional<Account> getLoggedInAccount(String email, String password) {
        if (isNull(email) || isNull(password)) {
            return empty();
        }
        Password dbPassword = passwordService.findPasswordByEmail(email);
        if (isNull(dbPassword)) {
            return empty();
        }
        String dbPasswordValue = dbPassword.getPassword();
        String verifyingSaltedPasswordValue = hashCredentialData(password, dbPassword.getSalt());
        return dbPasswordValue.equals(verifyingSaltedPasswordValue)
                ? accountService.getById(dbPassword.getAccountId()) : empty();
    }

}
