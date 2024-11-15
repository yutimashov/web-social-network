package com.getjavajob.training.timashovy.socialnetwork.dao.daoimpl.account;

import com.getjavajob.training.timashovy.socialnetwork.dao.interfaces.account.PhoneDao;
import com.getjavajob.training.timashovy.socialnetwork.domain.phone.Phone;
import com.getjavajob.training.timashovy.socialnetwork.domain.phone.PhoneType;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
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
    public List<Phone> getPhones(Long accountId, PhoneType phoneType) {
        return entityManager.createQuery("select p from Phone p where p.account.id = :accountId "
                        + "and p.phoneType = :phoneType", Phone.class)
                .setParameter("accountId", accountId)
                .setParameter("phoneType", phoneType)
                .getResultList();
    }

    @Override
    public List<String> getPhoneNumbers(Long accountId, PhoneType phoneType) {
        return entityManager.createQuery("select p.number from Phone p where p.account.id = :accountId "
                        + "and p.phoneType = :phoneType", String.class)
                .setParameter("accountId", accountId)
                .setParameter("phoneType", phoneType)
                .getResultList();
    }

    @Override
    public boolean updateNumber(Long phoneId, String newNumber) {
        Phone existingPhone = entityManager.find(Phone.class, phoneId);
        if (isNull(existingPhone)) {
            return false;
        }
        existingPhone.setNumber(newNumber);
        return true;
    }

    @Override
    public void delete(Long phoneId) {
        Phone existingPhone = entityManager.find(Phone.class, phoneId);
        if (!isNull(existingPhone)) {
            entityManager.remove(existingPhone);
        }
    }

}
