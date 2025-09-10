package com.getjavajob.messageservice.dao;

import com.getjavajob.training.timashovy.socialnetwork.domain.account.Account;
import com.getjavajob.training.timashovy.socialnetwork.domain.message.PersonalMessage;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class PersonalMessageRepositoryImpl implements PersonalMessageRepository {

    private final PersonalMessageRepositorySpringData personalMessageRepositorySpringData;

    @PersistenceContext
    private EntityManager entityManager;

    public PersonalMessageRepositoryImpl(PersonalMessageRepositorySpringData personalMessageRepositorySpringData) {
        this.personalMessageRepositorySpringData = personalMessageRepositorySpringData;
    }

    @Override
    public PersonalMessage save(PersonalMessage personalMessage) {
        personalMessageRepositorySpringData.save(personalMessage);
        return personalMessage;
    }

    @Override
    public Optional<PersonalMessage> getById(Long id) {
        return personalMessageRepositorySpringData.findById(id);
    }

    @Override
    public void delete(Long id) {
        if (personalMessageRepositorySpringData.existsById(id)) {
            personalMessageRepositorySpringData.deleteById(id);
        }
    }

    @Override
    public List<PersonalMessage> getAllPersonalMessagesWithAccount(Long authorId, Long receiverId) {
        return personalMessageRepositorySpringData.findAllPersonalMessagesBetweenAccounts(authorId, receiverId);
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

}
