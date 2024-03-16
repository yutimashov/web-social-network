package com.getjavajob.training.timashovy.socialnetwork.service.serviceimpl;

import com.getjavajob.training.timashovy.socialnetwork.common.account.Account;
import com.getjavajob.training.timashovy.socialnetwork.common.account.Password;
import com.getjavajob.training.timashovy.socialnetwork.dao.daoimpl.LoginDaoImpl;

import static com.getjavajob.training.timashovy.socialnetwork.dao.daoimpl.LoginDaoImpl.getLoginDaoImpl;
import static com.getjavajob.training.timashovy.socialnetwork.service.serviceimpl.AccountServiceImpl.getAccountServiceInstance;
import static com.getjavajob.training.timashovy.socialnetwork.service.util.CredentialHashUtil.hashCredential;
import static java.util.Objects.isNull;

public class LoginService {

    private static final LoginService loginService = new LoginService();
    private final LoginDaoImpl loginDao = getLoginDaoImpl();
    private final AccountServiceImpl accountService = getAccountServiceInstance();

    private LoginService() {
    }

    public static LoginService getLoginServiceInstance() {
        return loginService;
    }

    //TODO: avoid null returning. Think about using `Optional`
    public Account checkLogin(String email, String password) {
        Password dbPassword = loginDao.findPasswordByEmail(email);
        if (isNull(email) || isNull(password) || isNull(dbPassword)) {
            return null;
        }
        String passwordSalt = dbPassword.getSalt();
        String userEnteredPasswordValue = hashCredential(password, passwordSalt);
        return dbPassword.getPassword().equals(userEnteredPasswordValue)
                ? accountService.getAccountById(dbPassword.getAccountId())
                : null;
    }

}
