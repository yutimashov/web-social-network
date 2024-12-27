package com.getjavajob.training.timashovy.socialnetwork.dao.daoimpl.account;

import com.getjavajob.training.timashovy.socialnetwork.dao.interfaces.account.PasswordRepository;
import com.getjavajob.training.timashovy.socialnetwork.dao.interfaces.account.PasswordRepositorySpringData;
import com.getjavajob.training.timashovy.socialnetwork.domain.password.Password;
import org.slf4j.Logger;
import org.springframework.stereotype.Repository;

import java.util.Optional;

import static org.slf4j.LoggerFactory.getLogger;

/**
 * Singleton class responsible for working with `account_data.passwords` table.
 * It provides safe multithreading approach for creating singleton object using synchronization mechanism.
 */
@Repository
public class PasswordRepositoryImpl implements PasswordRepository {

    private static final Logger logger = getLogger(PasswordRepositoryImpl.class);

    private final PasswordRepositorySpringData passwordRepositorySpringData;

    public PasswordRepositoryImpl(PasswordRepositorySpringData passwordRepositorySpringData) {
        this.passwordRepositorySpringData = passwordRepositorySpringData;
    }

    @Override
    public Password save(Password password) {
        passwordRepositorySpringData.save(password);
        return password;
    }

    @Override
    public Optional<Password> getById(Long id) {
        return passwordRepositorySpringData.findById(id);
    }

    @Override
    public Optional<Password> findByEmail(String email) {
        return passwordRepositorySpringData.findByAccountEmail(email);
    }

    @Override
    public void delete(Long id) {
        if (passwordRepositorySpringData.existsById(id)) {
            passwordRepositorySpringData.deleteById(id);
        }
    }

}
