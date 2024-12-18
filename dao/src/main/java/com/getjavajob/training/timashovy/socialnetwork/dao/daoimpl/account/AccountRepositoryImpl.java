package com.getjavajob.training.timashovy.socialnetwork.dao.daoimpl.account;

import com.getjavajob.training.timashovy.socialnetwork.dao.exception.DaoException;
import com.getjavajob.training.timashovy.socialnetwork.dao.interfaces.account.AccountRepository;
import com.getjavajob.training.timashovy.socialnetwork.domain.account.Account;
import com.getjavajob.training.timashovy.socialnetwork.domain.account.AccountRole;
import com.getjavajob.training.timashovy.socialnetwork.domain.phone.Phone;
import org.slf4j.Logger;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;
import java.util.List;
import java.util.Optional;

import static java.util.Objects.isNull;
import static java.util.Optional.ofNullable;
import static org.slf4j.LoggerFactory.getLogger;

/**
 * Provides functionality for working with data inside `account_data.accounts` table.
 */
@Repository
public class AccountRepositoryImpl implements AccountRepository {

    private static final Logger logger = getLogger(AccountRepositoryImpl.class);

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Account save(Account account) {
        try {
            entityManager.persist(account);
            return account;
        } catch (PersistenceException e) {
            logger.error("Error persisting account={}", account.getId());
            throw new DaoException("Cannot save account to persistent storage", e);
        }
    }

    @Override
    public void updateById(Account account, Long accountId) {
        try {
            Account updatedAccount = entityManager.find(Account.class, accountId);
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
            entityManager.merge(updatedAccount);
        } catch (PersistenceException e) {
            logger.error("Error persisting account={}", accountId);
            throw new DaoException("Cannot save account to persistent storage", e);
        }
    }

    @Override
    public Optional<Account> findByEmail(String email) {
        try {
            return ofNullable(entityManager.createQuery(
                            "select a from Account a where a.email = :email",
                            Account.class)
                    .setParameter("email", email)
                    .getSingleResult()
            );
        } catch (PersistenceException e) {
            logger.error("Error getting account by email: email={}", email);
            throw new DaoException("Cannot get account by provided email", e);
        }
    }

    @Override
    public void changeRole(Long id, AccountRole role) {
        try {
            Account existingAccount = entityManager.find(Account.class, id);
            existingAccount.setRole(role);
        } catch (PersistenceException e) {
            logger.error("Error changing account role: account={}", role);
            throw new DaoException("Cannot change account role", e);
        }
    }

    @Override
    public void delete(Long id) {
        try {
            Account deletingAccount = entityManager.find(Account.class, id);
            if (!isNull(deletingAccount)) {
                entityManager.remove(deletingAccount);
            }
        } catch (PersistenceException e) {
            logger.error("Error deleting account={}", id);
            throw new DaoException("Cannot delete account by provided id", e);
        }
    }

    @Override
    public Optional<Account> getById(Long id) {
        try {
            return ofNullable(entityManager.find(Account.class, id));
        } catch (PersistenceException e) {
            logger.error("Error getting account by id: account={}", id);
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
