package com.getjavajob.training.timashovy.socialnetwork.service.serviceimpl.account;

import com.getjavajob.training.timashovy.socialnetwork.dao.interfaces.account.PasswordRepository;
import com.getjavajob.training.timashovy.socialnetwork.domain.account.Account;
import com.getjavajob.training.timashovy.socialnetwork.domain.password.Password;
import com.getjavajob.training.timashovy.socialnetwork.service.interfaces.PasswordService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class PasswordServiceImpl implements PasswordService {

    private static final Logger logger = LoggerFactory.getLogger(PasswordServiceImpl.class);

    private final PasswordRepository passwordRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    public PasswordServiceImpl(PasswordRepository passwordRepository) {
        this.passwordRepository = passwordRepository;
    }

    @Transactional
    @Override
    public Password create(Account account, String rawPassword) {
        Password password = new Password(passwordEncoder.encode(rawPassword));
        password.setAccount(account);
        logger.info("new password from rawPassword={} created with value={} with encoder={}", rawPassword,
                password.getPasswordValue(), passwordEncoder);
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
