package com.getjavajob.training.timashovy.socialnetwork.service.serviceimpl.account;

import com.getjavajob.training.timashovy.socialnetwork.common.account.Password;
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

    public static PasswordService createInstance(PasswordDao passwordDao) {
        return new PasswordServiceImpl(passwordDao);
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
    public Optional<Password> findPasswordByEmail(String email) {
        return passwordDao.findByEmail(email);
    }

}
