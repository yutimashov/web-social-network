package com.getjavajob.training.timashovy.socialnetwork.service.serviceimpl.account;

import com.getjavajob.training.timashovy.socialnetwork.dao.interfaces.account.PasswordRepository;
import com.getjavajob.training.timashovy.socialnetwork.domain.account.Account;
import com.getjavajob.training.timashovy.socialnetwork.domain.password.Password;
import com.getjavajob.training.timashovy.socialnetwork.service.interfaces.PasswordService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static com.getjavajob.training.timashovy.socialnetwork.service.util.PasswordUtil.generateSalt;
import static com.getjavajob.training.timashovy.socialnetwork.service.util.PasswordUtil.hashCredentialData;

@Service
public class PasswordServiceImpl implements PasswordService {

    private final PasswordRepository passwordRepository;

    public PasswordServiceImpl(PasswordRepository passwordRepository) {
        this.passwordRepository = passwordRepository;
    }

    @Transactional
    @Override
    public Password create(Account account, String rawPassword) {
        String salt = generateSalt();
        Password password = new Password(hashCredentialData(rawPassword, salt), salt);
        password.setAccount(account);
        return passwordRepository.save(password);
    }

    @Override
    public Optional<Password> get(Long accountId) {
        return passwordRepository.getById(accountId);
    }

    @Override
    public Optional<Password> findPasswordByEmail(String email) {
        return passwordRepository.findByEmail(email);
    }

}
