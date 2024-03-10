package com.getjavajob.training.timashovy.socialnetwork.service.serviceimpl;

import com.getjavajob.training.timashovy.socialnetwork.common.account.Password;

import static com.getjavajob.training.timashovy.socialnetwork.dao.daoimpl.LoginDaoImpl.getLoginDaoImpl;
import static com.getjavajob.training.timashovy.socialnetwork.service.serviceimpl.PasswordServiceImpl.getPasswordServiceInstance;
import static java.util.Objects.isNull;

public class LoginService {

    private static final LoginService loginService = new LoginService();

    private LoginService() {
    }

    public static LoginService getLoginServiceInstance() {
        return loginService;
    }

    public boolean login(String email, String password) {
        if (isNull(email) || isNull(password)) {
            throw new RuntimeException();
        }
        Password dbPassword = getLoginDaoImpl().findPasswordByEmail(email);
        String userEnteredPassword = getPasswordServiceInstance().createHashedPassword(password, dbPassword.getSalt());
        return dbPassword.getPassword().equals(userEnteredPassword);
    }

}
