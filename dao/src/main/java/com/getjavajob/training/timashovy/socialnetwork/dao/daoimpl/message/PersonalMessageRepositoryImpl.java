package com.getjavajob.training.timashovy.socialnetwork.dao.daoimpl.message;

import com.getjavajob.training.timashovy.socialnetwork.dao.interfaces.message.PersonalMessageRepository;
import com.getjavajob.training.timashovy.socialnetwork.domain.account.Account;
import com.getjavajob.training.timashovy.socialnetwork.domain.message.PersonalMessage;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Optional;

import static java.util.Objects.isNull;

@Repository
public class PersonalMessageRepositoryImpl implements PersonalMessageRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public PersonalMessage save(PersonalMessage personalMessage) {
        entityManager.persist(personalMessage);
        return personalMessage;
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
    public void delete(Long id) {
        PersonalMessage existingPersonalMessage = entityManager.find(PersonalMessage.class, id);
        if (!isNull(existingPersonalMessage)) {
            entityManager.remove(existingPersonalMessage);
        }
    }

    @Override
    public List<Account> getAllAccounts(Long accountId) {
        return entityManager.createQuery(
                        "select distinct a from Account a " +
                                "join PersonalMessage pm on (pm.accountAuthorId = a.id or pm.destinationId = a.id) " +
                                "where pm.accountAuthorId = :accountId or pm.destinationId = :accountId",
                        Account.class
                )
                .setParameter("accountId", accountId)
                .getResultList();
    }

    @Override
    public List<PersonalMessage> getAllPersonalMessagesWithAccount(Long authorId, Long receiverId) {
        return entityManager.createQuery(
                        "select pm from PersonalMessage pm where " +
                                "(pm.accountAuthorId = :authorId and pm.destinationId = :receiverId) " +
                                "or (pm.accountAuthorId = :receiverId and pm.destinationId = :authorId)",
                        PersonalMessage.class
                )
                .setParameter("authorId", authorId)
                .setParameter("receiverId", receiverId)
                .getResultList();
    }

}
