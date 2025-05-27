package com.getjavajob.securityservice.service.password;

import com.getjavajob.securityservice.dao.password.PasswordRepository;
import com.getjavajob.training.timashovy.socialnetwork.domain.account.Account;
import com.getjavajob.training.timashovy.socialnetwork.domain.password.Password;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class PasswordServiceImpl implements PasswordService {

    private static final Logger logger = LoggerFactory.getLogger(PasswordServiceImpl.class);

    private final PasswordRepository passwordRepository;

    private final PasswordEncoder passwordEncoder;

    @Autowired
    public PasswordServiceImpl(PasswordRepository passwordRepository, @Lazy PasswordEncoder passwordEncoder) {
        this.passwordRepository = passwordRepository;
        this.passwordEncoder = passwordEncoder;
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

}
