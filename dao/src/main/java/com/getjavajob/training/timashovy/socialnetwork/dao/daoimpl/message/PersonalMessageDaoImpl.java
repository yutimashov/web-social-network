package com.getjavajob.training.timashovy.socialnetwork.dao.daoimpl.message;

import com.getjavajob.training.timashovy.socialnetwork.dao.interfaces.BaseDao;
import com.getjavajob.training.timashovy.socialnetwork.domain.account.Account;
import com.getjavajob.training.timashovy.socialnetwork.domain.message.PersonalMessage;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;
import java.util.List;
import java.util.Optional;

import static java.util.Objects.isNull;

public class PersonalMessageDaoImpl implements BaseDao<PersonalMessage> {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Long create(PersonalMessage personalMessage) {
        entityManager.persist(personalMessage);
        entityManager.flush();
        return personalMessage.getId();
    }

    @Override
    public Optional<PersonalMessage> getById(Long id) {
        try {
            PersonalMessage existingMessage = entityManager.createQuery(
                            "select pm from PersonalMessage pm where pm.id = :messageId", PersonalMessage.class)
                    .setParameter("messageId", id)
                    .getSingleResult();
            return Optional.ofNullable(existingMessage);
        } catch (NoResultException e) {
            return Optional.empty();
        }
    }

    @Override
    public List<PersonalMessage> getAll() {
        //TODO
        return null;
    }

    @Override
    public boolean updateById(Long id, PersonalMessage personalMessage) {
        EntityTransaction transaction = entityManager.getTransaction();
        try {
            transaction.begin();
            PersonalMessage existingPersonalMessage = entityManager.find(PersonalMessage.class, id);
            if (isNull(existingPersonalMessage)) {
                return false;
            }
            existingPersonalMessage.setDestinationId(personalMessage.getDestinationId());
            existingPersonalMessage.setText(personalMessage.getText());
            existingPersonalMessage.setPhoto(personalMessage.getPhoto());
            existingPersonalMessage.setAccountAuthorId(personalMessage.getAccountAuthorId());
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
            PersonalMessage existingPersonalMessage = entityManager.find(PersonalMessage.class, id);
            if (!isNull(existingPersonalMessage)) {
                entityManager.remove(existingPersonalMessage);
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

    public List<Account> getAllAccounts(Long accountId) {
        return entityManager.createQuery(
                        "select distinct pm.accountAuthorId from PersonalMessage pm "
                                + "where pm.destinationId = :accountId "
                                + "union select distinct pm.destinationId from PersonalMessage pm "
                                + "where pm.accountAuthorId = :accountId",
                        Account.class
                )
                .getResultList();
    }

    public List<PersonalMessage> getAllPersonalMessagesWithAccount(Long authorId, Long receiverId) {
        return entityManager.createQuery(
                        "select pm from PersonalMessage pm where pm.accountAuthorId = :authorId "
                                + "and pm.destinationId = :receiverId "
                                + "union select pm from PersonalMessage pm where pm.accountAuthorId = :receiverId "
                                + "and pm.destinationId = :authorId",
                        PersonalMessage.class
                )
                .setParameter("authorId", authorId)
                .setParameter("receiverId", receiverId)
                .getResultList();
    }

}
