package com.getjavajob.training.timashovy.socialnetwork.dao.daoimpl.account;

import com.getjavajob.training.timashovy.socialnetwork.dao.exception.DaoException;
import com.getjavajob.training.timashovy.socialnetwork.dao.interfaces.account.AccountRepository;
import com.getjavajob.training.timashovy.socialnetwork.domain.account.Account;
import com.getjavajob.training.timashovy.socialnetwork.domain.account.AccountRole;
import org.slf4j.Logger;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;
import java.util.List;
import java.util.Optional;

import static java.util.Objects.isNull;
import static java.util.Optional.ofNullable;
import static org.slf4j.LoggerFactory.getLogger;

/**
 * Singleton class responsible for working with `account_data.accounts` table.
 * It provides functionality for working with data inside above-mentioned table.
 */
public class AccountDao implements AccountRepository {

    private static final Logger logger = getLogger(AccountDao.class);

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Account save(Account account) {
        try {
            entityManager.persist(account);
            return account;
        } catch (PersistenceException e) {
            logger.error("Error persisting account firstName={}, lastName={}", account.getFirstName(),
                    account.getLastName());
            throw new DaoException("Cannot save account to persistent storage", e);
        }
    }

    @Override
    public void changeRole(Long id, AccountRole role) {
        try {
            Account existingAccount = entityManager.find(Account.class, id);
            existingAccount.setRole(role);
        } catch (PersistenceException e) {
            logger.error("Error changing account role: id={}, role={}", id, role);
            throw new DaoException("Cannot change account role", e);
        }
    }

    @Transactional
    @Override
    public void delete(Long id) {
        try {
            Account deletingAccount = entityManager.find(Account.class, id);
            if (!isNull(deletingAccount)) {
                entityManager.remove(deletingAccount);
            }
        } catch (PersistenceException e) {
            logger.error("Error deleting account with id={}", id);
            throw new DaoException("Cannot delete account by provided id", e);
        }
    }

    @Override
    public Optional<Account> getById(Long id) {
        try {
            return ofNullable(entityManager.find(Account.class, id));
        } catch (PersistenceException e) {
            logger.error("Error getting account by id: id={}", id);
            throw new DaoException("Cannot get account by provided id", e);
        }
    }

    @Override
    public List<Account> getAll() {
        try {
            return entityManager.createQuery("select a from Account a", Account.class).getResultList();
        } catch (PersistenceException e) {
            logger.error("Error getting all accounts");
            throw new DaoException("Cannot get accounts", e);
        }
    }

}
