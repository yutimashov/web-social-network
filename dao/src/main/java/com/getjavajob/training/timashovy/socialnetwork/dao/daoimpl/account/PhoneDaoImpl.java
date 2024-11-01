package com.getjavajob.training.timashovy.socialnetwork.dao.daoimpl.account;

import com.getjavajob.training.timashovy.socialnetwork.dao.interfaces.account.PhoneDao;
import com.getjavajob.training.timashovy.socialnetwork.domain.phone.Phone;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;
import java.util.List;

import static java.util.Objects.isNull;

/**
 * Singleton class responsible for working with `account_data.phones` table in DB.
 * It provides safe multithreading approach for creating singleton object using synchronization mechanism.
 */
public class PhoneDaoImpl implements PhoneDao {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Long create(Phone phone) {
        entityManager.persist(phone);
        entityManager.flush();
        return phone.getId();
    }

    @Override
    public List<Phone> getAll(Long accountId) {
        return entityManager.createQuery("select p from Phone p where p.account.id = :accountId", Phone.class)
                .setParameter("accountId", accountId)
                .getResultList();
    }

    @Override
    public boolean updateNumber(Long phoneId, String newNumber) {
        EntityTransaction transaction = entityManager.getTransaction();
        try {
            transaction.begin();
            Phone existingPhone = entityManager.find(Phone.class, phoneId);
            if (isNull(existingPhone)) {
                return false;
            }
            existingPhone.setNumber(newNumber);
            return true;
        } catch (PersistenceException e) {
            if (transaction.isActive()) {
                transaction.rollback();
            }
            return false;
        }
    }

    @Override
    public void delete(Long phoneId) {
        EntityTransaction transaction = entityManager.getTransaction();
        try {
            transaction.begin();
            Phone existingPhone = entityManager.find(Phone.class, phoneId);
            if (!isNull(existingPhone)) {
                entityManager.remove(existingPhone);
                transaction.commit();
            } else {
                transaction.rollback();
            }
        } catch (PersistenceException e) {
            if (transaction.isActive()) {
                transaction.rollback();
            }
        }
    }

}
