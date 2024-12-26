package com.getjavajob.training.timashovy.socialnetwork.dao.daoimpl.account;

import com.getjavajob.training.timashovy.socialnetwork.dao.exception.DaoException;
import com.getjavajob.training.timashovy.socialnetwork.dao.interfaces.account.PhoneRepository;
import com.getjavajob.training.timashovy.socialnetwork.domain.phone.Phone;
import com.getjavajob.training.timashovy.socialnetwork.domain.phone.PhoneType;
import org.slf4j.Logger;
import org.springframework.stereotype.Repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.PersistenceException;
import java.util.List;
import java.util.Optional;

import static java.util.Objects.isNull;
import static java.util.Optional.ofNullable;
import static org.slf4j.LoggerFactory.getLogger;

/**
 * Singleton class responsible for working with `account_data.phones` table in DB.
 * It provides safe multithreading approach for creating singleton object using synchronization mechanism.
 */
@Repository
public class PhoneRepositoryImpl implements PhoneRepository {

    private static final Logger logger = getLogger(PhoneRepositoryImpl.class);

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Phone save(Phone phone) {
        try {
            entityManager.persist(phone);
            return phone;
        } catch (PersistenceException e) {
            logger.error("Error persisting phone for account with id={}, phoneNumber={}", phone.getAccount().getId(),
                    phone.getNumber());
            throw new DaoException("Cannot save phone to persistent storage", e);
        }
    }

    @Override
    public Optional<Phone> getById(Long id) {
        try {
            return ofNullable(entityManager.find(Phone.class, id));
        } catch (PersistenceException e) {
            logger.error("Error getting phone by id: id={}", id);
            throw new DaoException("Cannot get phone by provided id", e);
        }
    }

    @Override
    public List<Phone> getPhones(Long accountId, PhoneType phoneType) {
        try {
            return entityManager.createQuery("select p from Phone p where p.account.id = :accountId "
                            + "and p.phoneType = :phoneType", Phone.class)
                    .setParameter("accountId", accountId)
                    .setParameter("phoneType", phoneType)
                    .getResultList();
        } catch (PersistenceException e) {
            logger.error("Error getting phones accountId={}, phoneType={}", accountId, phoneType);
            throw new DaoException("Cannot get phones", e);
        }
    }

    @Override
    public List<String> getPhoneNumbers(Long accountId, PhoneType phoneType) {
        try {
            return entityManager.createQuery("select p.number from Phone p where p.account.id = :accountId "
                            + "and p.phoneType = :phoneType", String.class)
                    .setParameter("accountId", accountId)
                    .setParameter("phoneType", phoneType)
                    .getResultList();
        } catch (PersistenceException e) {
            logger.error("Error getting phones values accountId={}, phoneType={}", accountId, phoneType);
            throw new DaoException("Cannot get phones values", e);
        }
    }

    @Override
    public void updateNumber(Long id, String newNumber) {
        try {
            Phone existingPhone = entityManager.find(Phone.class, id);
            if (!isNull(existingPhone)) {
                existingPhone.setNumber(newNumber);
            }
        } catch (PersistenceException e) {
            logger.error("Error updating phone id={}, newNumber={}", id, newNumber);
            throw new DaoException("Cannot get phones values", e);
        }
    }

    @Override
    public void delete(Long id) {
        try {
            Phone existingPhone = entityManager.find(Phone.class, id);
            if (!isNull(existingPhone)) {
                entityManager.remove(existingPhone);
            }
        } catch (PersistenceException e) {
            logger.error("Error deleting phone id={}", id);
            throw new DaoException("Cannot delete phone", e);
        }
    }

}
