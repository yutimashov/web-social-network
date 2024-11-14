package com.getjavajob.training.timashovy.socialnetwork.dao.daoimpl.account;

import com.getjavajob.training.timashovy.socialnetwork.dao.interfaces.account.PasswordDao;
import com.getjavajob.training.timashovy.socialnetwork.domain.password.Password;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import java.util.Optional;

import static java.util.Objects.isNull;
import static java.util.Optional.empty;
import static java.util.Optional.ofNullable;

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
        return password.getId();
    }

    @Override
    public Optional<Password> getById(Long id) {
        return ofNullable(entityManager.find(Password.class, id));
    }

    @Override
    public Optional<Password> findByEmail(String email) {
        try {
            return ofNullable(entityManager.createQuery(
                            "select p from Password p join p.account a where a.email = :email", Password.class)
                    .setParameter("email", email)
                    .getSingleResult());
        } catch (NoResultException e) {
            return empty();
        }
    }

    @Override
    public void deleteById(Long id) {
        Password existingPassword = entityManager.find(Password.class, id);
        if (!isNull(existingPassword)) {
            entityManager.remove(existingPassword);
        }
    }

}
