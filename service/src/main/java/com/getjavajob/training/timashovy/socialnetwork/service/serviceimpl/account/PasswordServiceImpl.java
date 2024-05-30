package com.getjavajob.training.timashovy.socialnetwork.service.serviceimpl.account;

import com.getjavajob.training.timashovy.socialnetwork.common.account.Password;
import com.getjavajob.training.timashovy.socialnetwork.dao.interfaces.account.PasswordDao;
import com.getjavajob.training.timashovy.socialnetwork.service.interfaces.PasswordService;

import java.util.Optional;

import static com.getjavajob.training.timashovy.socialnetwork.service.util.PasswordUtil.generateSalt;
import static com.getjavajob.training.timashovy.socialnetwork.service.util.PasswordUtil.hashCredentialData;

public class PasswordServiceImpl implements PasswordService {

    private final PasswordDao passwordDao;
    private static volatile PasswordService instance;

    private PasswordServiceImpl(PasswordDao passwordDao) {
        this.passwordDao = passwordDao;
    }

    public static PasswordService getInstance(PasswordDao passwordDao) {
        if (instance == null) {
            synchronized (PasswordServiceImpl.class) {
                if (instance == null) {
                    instance = new PasswordServiceImpl(passwordDao);
                }
            }
        }
        return instance;
    }

    @Override
    public Password create(Long accountId, String rawPassword) {
        String salt = generateSalt();
        return new Password(accountId, hashCredentialData(rawPassword, salt), salt);
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
