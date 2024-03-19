package com.getjavajob.training.timashovy.socialnetwork.service.serviceimpl;

import com.getjavajob.training.timashovy.socialnetwork.common.account.Account;
import com.getjavajob.training.timashovy.socialnetwork.common.account.Password;
import com.getjavajob.training.timashovy.socialnetwork.dao.daoimpl.account.PasswordDaoImpl;
import com.getjavajob.training.timashovy.socialnetwork.service.interfaces.PasswordService;

import static com.getjavajob.training.timashovy.socialnetwork.dao.daoimpl.account.PasswordDaoImpl.getPasswordDaoInstance;

public class PasswordServiceImpl implements PasswordService {

    private static final PasswordServiceImpl passwordServiceImpl = new PasswordServiceImpl();
    private final PasswordDaoImpl passwordDao = getPasswordDaoInstance();

    private PasswordServiceImpl() {
    }

    public static PasswordServiceImpl getPasswordServiceInstance() {
        return passwordServiceImpl;
    }

    @Override
    public Long create(Long accountId, String rawPassword) {
        return passwordDao.create(accountId, rawPassword);
    }

    @Override
    public boolean verify(Password password) {
        return false;
    }

    @Override
    public boolean update(Password password) {
        return false;
    }

    @Override
    public Password get(Account account) {
        return passwordDao.get(account);
    }

    @Override
    public Password findPasswordByEmail(String email) {
        return passwordDao.findByEmail(email);
    }

}
