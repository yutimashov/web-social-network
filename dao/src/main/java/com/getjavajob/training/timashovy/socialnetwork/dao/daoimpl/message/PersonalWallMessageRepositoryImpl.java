package com.getjavajob.training.timashovy.socialnetwork.dao.daoimpl.message;

import com.getjavajob.training.timashovy.socialnetwork.dao.interfaces.repositories.message.PersonalWallMessageRepository;
import com.getjavajob.training.timashovy.socialnetwork.domain.message.PersonalWallMessage;
import org.springframework.stereotype.Repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.PersistenceContext;
import java.util.List;
import java.util.Optional;

import static java.util.Objects.isNull;
import static java.util.Optional.empty;
import static java.util.Optional.ofNullable;

@Repository
public class PersonalWallMessageRepositoryImpl implements PersonalWallMessageRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public PersonalWallMessage save(PersonalWallMessage personalMessage) {
        entityManager.persist(personalMessage);
        return personalMessage;
    }

    @Override
    public Optional<PersonalWallMessage> getById(Long id) {
        try {
            PersonalWallMessage existingMessage = entityManager.createQuery(
                            "select pm from PersonalWallMessage pm where pm.id = :messageId",
                            PersonalWallMessage.class)
                    .setParameter("messageId", id)
                    .getSingleResult();
            return ofNullable(existingMessage);
        } catch (NoResultException e) {
            return empty();
        }
    }

    @Override
    public List<PersonalWallMessage> getAll(Long accountId) {
        return entityManager.createQuery(
                        "select pm from PersonalWallMessage pm where pm.accountReceiverId = :accountId "
                                + "order by pm.creationDate desc",
                        PersonalWallMessage.class
                )
                .setParameter("accountId", accountId)
                .getResultList();
    }

    @Override
    public void delete(Long id) {
        PersonalWallMessage existingPersonalWallMessage = entityManager.find(PersonalWallMessage.class, id);
        if (!isNull(existingPersonalWallMessage)) {
            entityManager.remove(existingPersonalWallMessage);
        }
    }

}
