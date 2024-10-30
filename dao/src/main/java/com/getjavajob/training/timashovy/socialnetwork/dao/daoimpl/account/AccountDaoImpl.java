package com.getjavajob.training.timashovy.socialnetwork.dao.daoimpl.account;

import com.getjavajob.training.timashovy.socialnetwork.dao.interfaces.BaseDao;
import com.getjavajob.training.timashovy.socialnetwork.domain.account.Account;

import javax.persistence.*;
import java.util.List;
import java.util.Optional;

import static java.util.Objects.isNull;

/**
 * Singleton class responsible for working with {@link com.getjavajob.training.timashovy.socialnetwork.dao.util.dbutils.TableNames#ACCOUNTS_TABLE accounts table}.
 * It provides functionality for working with data inside above-mentioned table.
 */
public class AccountDaoImpl implements BaseDao<Account> {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Long create(Account account) {
        entityManager.persist(account);
        entityManager.flush();
        return account.getId();
    }

    @Override
    public boolean updateById(Long id, Account account) {
        EntityTransaction transaction = entityManager.getTransaction();
        try {
            transaction.begin();
            Account existingAccount = entityManager.find(Account.class, id);
            if (isNull(existingAccount)) {
                return false;
            }
            existingAccount.setFirstName(account.getFirstName());
            existingAccount.setLastName(account.getLastName());
            existingAccount.setMiddleName(account.getMiddleName());
            existingAccount.setBirthDate(account.getBirthDate());
            existingAccount.setPersonalAddress(account.getPersonalAddress());
            existingAccount.setWorkAddress(account.getWorkAddress());
            existingAccount.setEmail(account.getEmail());
            existingAccount.setIcq(account.getIcq());
            existingAccount.setSkype(account.getSkype());
            existingAccount.setAdditionalInfo(account.getAdditionalInfo());
            existingAccount.setRole(account.getRole());
            existingAccount.setAvatar(account.getAvatar());
            existingAccount.setPhones(account.getPhones());
            existingAccount.setPassword(account.getPassword());
            return true;
        } catch (PersistenceException e) {
            if (transaction.isActive()) {
                transaction.rollback();
            }
            return false;
        }
    }

    @Override
    public boolean deleteById(Long id) {
        EntityTransaction transaction = entityManager.getTransaction();
        try {
            transaction.begin();
            Account account = entityManager.find(Account.class, id);
            if (!isNull(account)) {
                entityManager.remove(account);
                transaction.commit();
                return true;
            } else {
                transaction.rollback();
                return false;
            }
        } catch (PersistenceException e) {
            if (transaction.isActive()) {
                transaction.rollback();
            }
            return false;
        }
    }

    @Override
    public Optional<Account> getById(Long accountId) {
        try {
            Account account = entityManager.createQuery(
                            "select a from Account a where a.id = :accountId", Account.class)
                    .setParameter("accountId", accountId)
                    .getSingleResult();
            return Optional.ofNullable(account);
        } catch (NoResultException e) {
            return Optional.empty();
        }
    }

    @Override
    public List<Account> getAll() {
        return entityManager.createQuery("select a from Account a", Account.class).getResultList();
    }

}
