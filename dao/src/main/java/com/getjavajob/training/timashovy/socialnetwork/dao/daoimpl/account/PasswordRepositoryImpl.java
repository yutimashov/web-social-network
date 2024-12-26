package com.getjavajob.training.timashovy.socialnetwork.dao.daoimpl.account;

import com.getjavajob.training.timashovy.socialnetwork.dao.exception.DaoException;
import com.getjavajob.training.timashovy.socialnetwork.dao.interfaces.account.PasswordRepository;
import com.getjavajob.training.timashovy.socialnetwork.domain.password.Password;
import org.slf4j.Logger;
import org.springframework.stereotype.Repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.PersistenceException;
import java.util.Optional;

import static java.util.Objects.isNull;
import static java.util.Optional.empty;
import static java.util.Optional.ofNullable;
import static org.slf4j.LoggerFactory.getLogger;

/**
 * Singleton class responsible for working with `account_data.passwords` table.
 * It provides safe multithreading approach for creating singleton object using synchronization mechanism.
 */
@Repository
public class PasswordRepositoryImpl implements PasswordRepository {

    private static final Logger logger = getLogger(PasswordRepositoryImpl.class);

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Password save(Password password) {
        try {
            entityManager.persist(password);
            return password;
        } catch (PersistenceException e) {
            logger.error("Error persisting password for accountId={}", password.getId());
            throw new DaoException("Cannot save password to persistent storage", e);
        }
    }

    @Override
    public Optional<Password> getById(Long id) {
        try {
            return ofNullable(entityManager.find(Password.class, id));
        } catch (PersistenceException e) {
            logger.error("Error getting password by id: id={}", id);
            throw new DaoException("Cannot get password by provided id", e);
        }
    }

    @Override
    public Optional<Password> findByEmail(String email) {
        try {
            return ofNullable(entityManager.createQuery(
                            "select p from Password p join p.account a where a.email = :email",
                            Password.class)
                    .setParameter("email", email)
                    .getSingleResult()
            );
        } catch (NoResultException e) {
            return empty();
        } catch (PersistenceException e) {
            logger.error("Error getting password by email: email={}", email);
            throw new DaoException("Cannot get password by provided email", e);
        }
    }

    @Override
    public void delete(Long id) {
        try {
            Password existingPassword = entityManager.find(Password.class, id);
            if (!isNull(existingPassword)) {
                entityManager.remove(existingPassword);
            }
        } catch (PersistenceException e) {
            logger.error("Error deleting password with id={}", id);
            throw new DaoException("Cannot delete password by provided id", e);
        }
    }

}
