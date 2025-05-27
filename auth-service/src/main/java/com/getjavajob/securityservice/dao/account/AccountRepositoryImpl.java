package com.getjavajob.securityservice.dao.account;

import com.getjavajob.training.timashovy.socialnetwork.domain.account.Account;
import org.slf4j.Logger;
import org.springframework.stereotype.Repository;

import java.util.Optional;

import static org.slf4j.LoggerFactory.getLogger;

/**
 * Provides functionality for working with data inside `account_data.accounts` table.
 */
@Repository
public class AccountRepositoryImpl implements AccountRepository {

    private static final Logger logger = getLogger(AccountRepositoryImpl.class);

    private final AccountRepositorySpringData accountRepositorySpringData;

    public AccountRepositoryImpl(AccountRepositorySpringData accountRepositorySpringData) {
        this.accountRepositorySpringData = accountRepositorySpringData;
    }

    @Override
    public Optional<Account> findByEmail(String email) {
        return accountRepositorySpringData.findByEmail(email);
    }

}
