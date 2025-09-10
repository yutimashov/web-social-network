package com.getjavajob.accountservice.service;

import com.getjavajob.accountservice.dao.AccountRepository;
import com.getjavajob.accountservice.exception.ServiceException;
import com.getjavajob.accountservice.web.feignclient.PasswordClient;
import com.getjavajob.accountservice.web.feignclient.PhoneClient;
import com.getjavajob.training.timashovy.socialnetwork.domain.account.Account;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static com.getjavajob.training.timashovy.socialnetwork.domain.account.AccountRole.ADMIN;
import static java.util.Objects.isNull;

@Service
public class AccountServiceImpl implements AccountService {

    private static final Logger logger = LoggerFactory.getLogger(AccountServiceImpl.class);
    private final AccountRepository accountDao;
    private final PhoneClient phoneClient;

    public AccountServiceImpl(AccountRepository accountDao, PhoneClient phoneClient) {
        this.accountDao = accountDao;
        this.phoneClient = phoneClient;
    }

    @Transactional
    @Override
    public void makeAdmin(Long accountId) {
        validateAccountId(accountId);
        if (accountDao.getById(accountId).isPresent()) {
            accountDao.changeRole(accountId, ADMIN);
            logger.info("Account={} becomes admin", accountId);
        }
    }

    @Transactional
    @Override
    public Account create(Account account, String personalPhones, String workingPhones) {
        Account createdAccount = accountDao.save(account);
        if (!personalPhones.isEmpty()) {
            phoneClient.createPersonalPhones(account, personalPhones);
        }
        if (!workingPhones.isEmpty()) {
            phoneClient.createWorkingPhones(account, workingPhones);
        }
        return createdAccount;
    }

    @Transactional
    @Override
    public void update(Long accountId, Account updatedAccount) {
        Account newAccount = accountDao.getById(accountId).isPresent() ? accountDao.getById(accountId).get() : null;
        if (isNull(newAccount)) {
            throw new ServiceException("updating non-existing account");
        }
        accountDao.updateById(updatedAccount, accountId);
    }

    /**
     * According to database constraints, accountId cannot be less or equal to zero, and it cannot be null.
     *
     * @param accountId id of account passed as argument
     */
    private void validateAccountId(Long accountId) {
        if (isNull(accountId) || accountId <= 0) {
            throw new IllegalArgumentException("Account id should be positive number greater than 0");
        }
    }

    private <T> void validateAccountFieldNotNull(T fieldName) {
        if (isNull(fieldName)) {
            throw new IllegalArgumentException("updating field should not be null");
        }
    }

    @Transactional
    @Override
    public void delete(Long accountId) {
        validateAccountId(accountId);
        accountDao.delete(accountId);
    }

    @Override
    public Optional<Account> getById(Long accountId) {
        return accountDao.getById(accountId);
    }

    @Override
    public List<Account> getAccounts(Long accountId, Long lastId, int limit) {
        return accountDao.getAccounts(accountId, lastId, limit);
    }

    @Override
    public List<Account> getAccountWithBirthdayToday(int month, int day) {
        return accountDao.getAccountWithBirthdayToday(month, day);
    }

    @Override
    public Optional<Account> findByEmail(String email) {
        return accountDao.findByEmail(email);
    }

    @Override
    public void updateById(Account account, Long accountId) {
        accountDao.updateById(account, accountId);
    }

}
