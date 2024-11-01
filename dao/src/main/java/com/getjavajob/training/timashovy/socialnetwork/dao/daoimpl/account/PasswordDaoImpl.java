package com.getjavajob.training.timashovy.socialnetwork.dao.daoimpl.account;

import com.getjavajob.training.timashovy.socialnetwork.dao.interfaces.account.PasswordDao;
import com.getjavajob.training.timashovy.socialnetwork.domain.password.Password;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;
import java.util.Optional;

/**
 * Singleton class responsible for working with {@link com.getjavajob.training.timashovy.socialnetwork.dao.util.dbutils.TableNames#ACCOUNTS_TABLE accounts table} table in DB.
 * It provides safe multithreading approach for creating singleton object using synchronization mechanism.
 */
public class PasswordDaoImpl implements PasswordDao {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Long create(Password password) {
        entityManager.persist(password);
        entityManager.flush();
        return password.getId();
    }

    @Override
    public Optional<Password> getById(Long id) {
        try {
            Password existingPassword = entityManager.find(Password.class, id);
            return Optional.ofNullable(existingPassword);
        } catch (PersistenceException e) {
            return Optional.empty();
        }
    }

    @Override
    public Optional<Password> findByEmail(String email) {
        try {
            Password password = entityManager.createQuery(
                            "select p from Password p where p.account.email = :email", Password.class)
                    .setParameter("email", email)
                    .getSingleResult();
            return Optional.ofNullable(password);
        } catch (NoResultException e) {
            return Optional.empty();
        }
    }

}
