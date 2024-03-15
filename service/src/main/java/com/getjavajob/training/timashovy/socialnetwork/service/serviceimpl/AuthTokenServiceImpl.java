package com.getjavajob.training.timashovy.socialnetwork.service.serviceimpl;

import com.getjavajob.training.timashovy.socialnetwork.common.account.AuthToken;
import com.getjavajob.training.timashovy.socialnetwork.dao.daoimpl.account.AuthTokenDaoImpl;
import com.getjavajob.training.timashovy.socialnetwork.service.interfaces.CredentialsProviderService;

import static com.getjavajob.training.timashovy.socialnetwork.dao.daoimpl.account.AuthTokenDaoImpl.getAuthTokenDaoInstance;

public class AuthTokenServiceImpl implements CredentialsProviderService<AuthToken> {

    private static final AuthTokenServiceImpl AUTH_TOKEN_SERVICE = new AuthTokenServiceImpl();
    private final AuthTokenDaoImpl authTokenDao = getAuthTokenDaoInstance();

    private AuthTokenServiceImpl() {
    }

    public static AuthTokenServiceImpl getAuthTokenServiceInstance() {
        return AUTH_TOKEN_SERVICE;
    }

    @Override
    public Long create(AuthToken authToken) {
        return authTokenDao.create(authToken);
    }

    @Override
    public boolean verify(AuthToken authToken) {
        return false;
    }

    @Override
    public boolean update(AuthToken authToken) {
        return false;
    }

}
