package com.getjavajob.training.timashovy.socialnetwork.service.serviceimpl.account;

import com.getjavajob.training.timashovy.socialnetwork.common.account.Account;
import com.getjavajob.training.timashovy.socialnetwork.common.account.Password;
import com.getjavajob.training.timashovy.socialnetwork.service.interfaces.AccountService;
import com.getjavajob.training.timashovy.socialnetwork.service.interfaces.LoginService;
import com.getjavajob.training.timashovy.socialnetwork.service.interfaces.PasswordService;

import java.util.Optional;

import static com.getjavajob.training.timashovy.socialnetwork.service.util.PasswordUtil.hashCredentialData;
import static java.util.Objects.isNull;
import static java.util.Optional.empty;

public class LoginServiceImpl implements LoginService {

    private final AccountService accountService;
    private final PasswordService passwordService;

    private LoginServiceImpl(AccountService accountService, PasswordService passwordService) {
        this.accountService = accountService;
        this.passwordService = passwordService;
    }

    public static LoginServiceImpl createInstance(AccountService accountService, PasswordService passwordService) {
        return new LoginServiceImpl(accountService, passwordService);
    }

    @Override
    public Optional<Account> getLoggedInAccount(String email, String password) {
        if (isNull(email) || isNull(password) || !passwordService.findPasswordByEmail(email).isPresent()) {
            return empty();
        }
        Password dbPassword = passwordService.findPasswordByEmail(email).get();
        String dbPasswordValue = dbPassword.getPassword();
        String verifyingSaltedPasswordValue = hashCredentialData(password, dbPassword.getSalt());
        return dbPasswordValue.equals(verifyingSaltedPasswordValue)
                ? accountService.getById(dbPassword.getAccountId()) : empty();
    }

}
