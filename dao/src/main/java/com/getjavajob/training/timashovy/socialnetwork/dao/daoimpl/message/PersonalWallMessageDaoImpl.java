package com.getjavajob.training.timashovy.socialnetwork.dao.daoimpl.message;

import com.getjavajob.training.timashovy.socialnetwork.dao.interfaces.BaseDao;
import com.getjavajob.training.timashovy.socialnetwork.domain.message.PersonalWallMessage;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Optional;

/**
 * Singleton class responsible for working with {@link com.getjavajob.training.timashovy.socialnetwork.dao.util.dbutils.TableNames#PERSONAL_WALL_MESSAGE_TABLE personal wall messages table}.
 * It provides safe multithreading approach for creating singleton object using synchronization mechanism.
 */
public class PersonalWallMessageDaoImpl implements BaseDao<PersonalWallMessage> {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Long create(PersonalWallMessage personalMessage) {
        entityManager.persist(personalMessage);
        entityManager.flush();
        return personalMessage.getId();
    }

    @Override
    public Optional<PersonalWallMessage> getById(Long id) {
        try {
            PersonalWallMessage existingMessage = entityManager.createQuery(
                            "select pm from PersonalWallMessage pm where pm.id = :messageId",
                            PersonalWallMessage.class)
                    .setParameter("messageId", id)
                    .getSingleResult();
            return Optional.ofNullable(existingMessage);
        } catch (NoResultException e) {
            return Optional.empty();
        }
    }

    @Override
    public List<PersonalWallMessage> getAll() {
        //TODO
        return null;
    }

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
    public boolean updateById(Long id, PersonalWallMessage personalWallMessage) {
        return false;
    }

    @Override
    public boolean deleteById(Long id) {
        return false;
    }

}
