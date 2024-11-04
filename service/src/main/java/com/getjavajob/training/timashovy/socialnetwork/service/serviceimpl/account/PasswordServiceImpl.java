package com.getjavajob.training.timashovy.socialnetwork.service.serviceimpl.account;

import com.getjavajob.training.timashovy.socialnetwork.dao.interfaces.account.PasswordDao;
import com.getjavajob.training.timashovy.socialnetwork.domain.account.Account;
import com.getjavajob.training.timashovy.socialnetwork.domain.password.Password;
import com.getjavajob.training.timashovy.socialnetwork.service.interfaces.PasswordService;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static com.getjavajob.training.timashovy.socialnetwork.service.util.PasswordUtil.generateSalt;
import static com.getjavajob.training.timashovy.socialnetwork.service.util.PasswordUtil.hashCredentialData;

public class PasswordServiceImpl implements PasswordService {

    private final PasswordDao passwordDao;

    public PasswordServiceImpl(PasswordDao passwordDao) {
        this.passwordDao = passwordDao;
    }

    @Transactional
    @Override
    public Long create(Account account, String rawPassword) {
        String salt = generateSalt();
        return passwordDao.create(new Password(account, hashCredentialData(rawPassword, salt), salt));
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
