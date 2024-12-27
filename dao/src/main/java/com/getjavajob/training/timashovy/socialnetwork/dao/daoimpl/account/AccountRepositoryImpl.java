package com.getjavajob.training.timashovy.socialnetwork.dao.daoimpl.account;

import com.getjavajob.training.timashovy.socialnetwork.dao.exception.DaoException;
import com.getjavajob.training.timashovy.socialnetwork.dao.interfaces.account.AccountRepository;
import com.getjavajob.training.timashovy.socialnetwork.dao.interfaces.account.AccountRepositorySpringData;
import com.getjavajob.training.timashovy.socialnetwork.domain.account.Account;
import com.getjavajob.training.timashovy.socialnetwork.domain.account.AccountRole;
import com.getjavajob.training.timashovy.socialnetwork.domain.phone.Phone;
import jakarta.persistence.PersistenceException;
import org.slf4j.Logger;
import org.springframework.stereotype.Repository;

import java.util.List;
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
    public Account save(Account account) {
        try {
            accountRepositorySpringData.save(account);
            return account;
        } catch (DaoException e) {
            logger.error("Error persisting account={}", account.getId(), e);
            throw new DaoException("Cannot save account to persistent storage", e);
        }
    }

    @Override
    public void updateById(Account account, Long accountId) {
        try {
            Optional<Account> maybeUpdatedAccount = accountRepositorySpringData.findById(accountId);
            if (maybeUpdatedAccount.isPresent()) {
                Account updatedAccount = maybeUpdatedAccount.get();
                if (account.getAvatar() != null) {
                    updatedAccount.setAvatar(account.getAvatar());
                }
                if (account.getFirstName() != null && !account.getFirstName().isEmpty()) {
                    updatedAccount.setFirstName(account.getFirstName());
                }
                if (account.getLastName() != null && !account.getLastName().isEmpty()) {
                    updatedAccount.setLastName(account.getLastName());
                }
                if (account.getMiddleName() != null && !account.getMiddleName().isEmpty()) {
                    updatedAccount.setMiddleName(account.getMiddleName());
                }
                if (account.getBirthDate() != null) {
                    updatedAccount.setBirthDate(account.getBirthDate());
                }
                if (account.getSkype() != null && !account.getSkype().isEmpty()) {
                    updatedAccount.setSkype(account.getSkype());
                }
                if (account.getIcq() != null && !account.getIcq().isEmpty()) {
                    updatedAccount.setIcq(account.getIcq());
                }
                if (account.getEmail() != null && !account.getEmail().isEmpty()) {
                    updatedAccount.setEmail(account.getEmail());
                }
                if (account.getPersonalAddress() != null && !account.getPersonalAddress().isEmpty()) {
                    updatedAccount.setPersonalAddress(account.getPersonalAddress());
                }
                if (account.getPhones() != null && !account.getPhones().isEmpty()) {
                    for (Phone phone : account.getPhones()) {
                        phone.setAccount(updatedAccount);
                    }
                    updatedAccount.getPhones().addAll(account.getPhones());
                }
                accountRepositorySpringData.save(updatedAccount);
            }
        } catch (PersistenceException e) {
            logger.error("Error persisting account={}", accountId);
            throw new DaoException("Cannot save account to persistent storage", e);
        }
    }

    @Override
    public Optional<Account> findByEmail(String email) {
        return accountRepositorySpringData.findByEmail(email);
    }

    @Override
    public void changeRole(Long id, AccountRole role) {
        Optional<Account> existingAccount = accountRepositorySpringData.findById(id);
        if (existingAccount.isPresent()) {
            Account account = existingAccount.get();
            account.setRole(role);
        }
    }

    @Override
    public void delete(Long id) {
        if (accountRepositorySpringData.existsById(id)) {
            accountRepositorySpringData.deleteById(id);
        }
    }

    @Override
    public Optional<Account> getById(Long id) {
        return accountRepositorySpringData.findById(id);
    }

    @Override
    public List<Account> getAll() {
        return (List<Account>) accountRepositorySpringData.findAll();
    }

}
