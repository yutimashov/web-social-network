package com.getjavajob.training.timashovy.socialnetwork.service.serviceimpl.account;

import com.getjavajob.training.timashovy.socialnetwork.common.account.Password;
import com.getjavajob.training.timashovy.socialnetwork.dao.daoimpl.account.PasswordDaoImpl;
import com.getjavajob.training.timashovy.socialnetwork.dao.interfaces.account.PasswordDao;
import com.getjavajob.training.timashovy.socialnetwork.service.interfaces.PasswordService;

import java.util.Optional;

import static com.getjavajob.training.timashovy.socialnetwork.service.util.PasswordUtil.generateSalt;
import static com.getjavajob.training.timashovy.socialnetwork.service.util.PasswordUtil.hashCredentialData;

public class PasswordServiceImpl implements PasswordService {

    private final PasswordDao passwordDao;

    private PasswordServiceImpl(PasswordDao passwordDao) {
        this.passwordDao = passwordDao;
    }

    private static class SingletonHolder {

        private static final PasswordServiceImpl INSTANCE;

        static {
            PasswordDao passwordDao = PasswordDaoImpl.getInstance();
            INSTANCE = new PasswordServiceImpl(passwordDao);
        }

    }

    public static PasswordServiceImpl getInstance() {
        return SingletonHolder.INSTANCE;
    }

    @Override
    public Long create(Long accountId, String rawPassword) {
        String salt = generateSalt();
        return passwordDao.create(accountId, new Password(accountId, hashCredentialData(rawPassword, salt), salt));
    }

    @Override
    public Optional<Password> get(Long accountId) {
        return passwordDao.getById(accountId);
    }

    @Override
    public Password findPasswordByEmail(String email) {
        return passwordDao.findByEmail(email);
    }

}
