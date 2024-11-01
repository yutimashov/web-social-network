package com.getjavajob.training.timashovy.socialnetwork.service.serviceimpl.account;

import com.getjavajob.training.timashovy.socialnetwork.domain.account.Account;
import com.getjavajob.training.timashovy.socialnetwork.domain.password.Password;
import com.getjavajob.training.timashovy.socialnetwork.service.interfaces.AccountService;
import com.getjavajob.training.timashovy.socialnetwork.service.interfaces.LoginService;
import com.getjavajob.training.timashovy.socialnetwork.service.interfaces.PasswordService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Optional;

import static com.getjavajob.training.timashovy.socialnetwork.service.util.PasswordUtil.hashCredentialData;
import static java.util.Objects.isNull;
import static java.util.Optional.empty;

public class LoginServiceImpl implements LoginService {

    private final AccountService accountService;
    private final PasswordService passwordService;

    private final static Logger logger = LoggerFactory.getLogger(LoginServiceImpl.class);

    public LoginServiceImpl(AccountService accountService, PasswordService passwordService) {
        this.accountService = accountService;
        this.passwordService = passwordService;
    }

    @Override
    public Optional<Account> getLoggedInAccount(String email, String password) {
        logger.info("going to get loggedIn account with password = {} and email = {}", password, email);
        if (isNull(email) || isNull(password)) {
            return empty();
        }
        Optional<Password> accountPassword = passwordService.findPasswordByEmail(email);
        if (!accountPassword.isPresent()) {
            return empty();
        }
        Password dbPassword = accountPassword.get();
        return dbPassword.getPasswordValue().equals(hashCredentialData(password, dbPassword.getSalt()))
                ? accountService.getById(dbPassword.getId())
                : empty();
    }

}
