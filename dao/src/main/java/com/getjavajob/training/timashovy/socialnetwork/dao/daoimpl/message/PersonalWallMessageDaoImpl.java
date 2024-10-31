package com.getjavajob.training.timashovy.socialnetwork.dao.daoimpl.message;

import com.getjavajob.training.timashovy.socialnetwork.dao.interfaces.BaseDao;
import com.getjavajob.training.timashovy.socialnetwork.domain.account.Account;
import com.getjavajob.training.timashovy.socialnetwork.domain.message.PersonalWallMessage;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Optional;

import static com.getjavajob.training.timashovy.socialnetwork.dao.util.dbutils.TableNames.PERSONAL_WALL_MESSAGE_TABLE;
import static com.getjavajob.training.timashovy.socialnetwork.dao.util.dbutils.fieldsnames.PersonalWallMessagesTableFields.PERSONAL_WALL_MESSAGE_AUTHOR_ID;
import static com.getjavajob.training.timashovy.socialnetwork.dao.util.dbutils.fieldsnames.PersonalWallMessagesTableFields.PERSONAL_WALL_MESSAGE_CREATION_DATE;
import static com.getjavajob.training.timashovy.socialnetwork.dao.util.dbutils.fieldsnames.PersonalWallMessagesTableFields.PERSONAL_WALL_MESSAGE_ID;
import static com.getjavajob.training.timashovy.socialnetwork.dao.util.dbutils.fieldsnames.PersonalWallMessagesTableFields.PERSONAL_WALL_MESSAGE_IMAGE;
import static com.getjavajob.training.timashovy.socialnetwork.dao.util.dbutils.fieldsnames.PersonalWallMessagesTableFields.PERSONAL_WALL_MESSAGE_RECEIVER_ID;
import static com.getjavajob.training.timashovy.socialnetwork.dao.util.dbutils.fieldsnames.PersonalWallMessagesTableFields.PERSONAL_WALL_MESSAGE_TEXT;

/**
 * Singleton class responsible for working with {@link com.getjavajob.training.timashovy.socialnetwork.dao.util.dbutils.TableNames#PERSONAL_WALL_MESSAGE_TABLE personal wall messages table}.
 * It provides safe multithreading approach for creating singleton object using synchronization mechanism.
 */
public class PersonalWallMessageDaoImpl implements BaseDao<PersonalWallMessage> {

    @PersistenceContext
    private EntityManager entityManager;


    private static final String GET_BY_ID = "SELECT " + PERSONAL_WALL_MESSAGE_ID + ", "
            + PERSONAL_WALL_MESSAGE_AUTHOR_ID + ", " + PERSONAL_WALL_MESSAGE_CREATION_DATE + ", "
            + PERSONAL_WALL_MESSAGE_TEXT + ", " + PERSONAL_WALL_MESSAGE_IMAGE + ", " + PERSONAL_WALL_MESSAGE_RECEIVER_ID
            + " FROM " + PERSONAL_WALL_MESSAGE_TABLE + " WHERE " + PERSONAL_WALL_MESSAGE_ID + " = ?;";

    @Override
    public Long create(PersonalWallMessage personalMessage) {
        entityManager.persist(personalMessage);
        entityManager.flush();
        return personalMessage.getId();
    }

    @Override
    public Optional<PersonalWallMessage> get(PersonalWallMessage personalWallMessage) {
        try {
            PersonalWallMessage existingMessage = entityManager.createQuery(
                            "select pm from PersonalWallMessage pm where pm.id = :messageId",
                            PersonalWallMessage.class)
                    .setParameter("messageId", personalWallMessage.getId())
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

    public List<PersonalWallMessage> getAll(Account account) {
        return entityManager.createQuery(
                        "select pm from PersonalWallMessage pm where pm.accountReceiverId = :accountId "
                                + "order by pm.creationDate desc",
                        PersonalWallMessage.class
                )
                .setParameter("accountId", account.getId())
                .getResultList();
    }

    @Override
    public boolean updateById(Long id, PersonalWallMessage personalWallMessage) {
        return false;
    }

    @Override
    public boolean delete(PersonalWallMessage personalWallMessage) {
        return false;
    }

}
