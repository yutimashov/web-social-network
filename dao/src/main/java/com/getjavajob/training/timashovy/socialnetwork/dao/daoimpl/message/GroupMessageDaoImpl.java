package com.getjavajob.training.timashovy.socialnetwork.dao.daoimpl.message;

import com.getjavajob.training.timashovy.socialnetwork.dao.interfaces.BaseDao;
import com.getjavajob.training.timashovy.socialnetwork.domain.message.GroupMessage;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;
import java.util.List;
import java.util.Optional;

import static java.util.Objects.isNull;

public class GroupMessageDaoImpl implements BaseDao<GroupMessage> {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Long create(GroupMessage message) {
        entityManager.persist(message);
        entityManager.flush();
        return message.getId();
    }

    @Override
    public Optional<GroupMessage> get(GroupMessage groupMessage) {
        try {
            GroupMessage existingMessage = entityManager.createQuery(
                            "select gm from GroupMessage gm where gm.id = :accountId", GroupMessage.class)
                    .setParameter("accountId", groupMessage.getId())
                    .getSingleResult();
            return Optional.ofNullable(existingMessage);
        } catch (NoResultException e) {
            return Optional.empty();
        }
    }

    @Override
    public List<GroupMessage> getAll() {
        return entityManager.createQuery("select gm from GroupMessage gm", GroupMessage.class).getResultList();
    }

    @Override
    public boolean updateById(Long id, GroupMessage groupMessage) {
        EntityTransaction transaction = entityManager.getTransaction();
        try {
            transaction.begin();
            GroupMessage existingGroupMessage = entityManager.find(GroupMessage.class, id);
            if (isNull(existingGroupMessage)) {
                return false;
            }
            existingGroupMessage.setGroup(groupMessage.getGroup());
            existingGroupMessage.setText(groupMessage.getText());
            existingGroupMessage.setPhoto(groupMessage.getPhoto());
            existingGroupMessage.setAccountAuthorId(groupMessage.getAccountAuthorId());
            return true;
        } catch (PersistenceException e) {
            if (transaction.isActive()) {
                transaction.rollback();
            }
            return false;
        }
    }

    @Override
    public boolean delete(GroupMessage groupMessage) {
        EntityTransaction transaction = entityManager.getTransaction();
        try {
            transaction.begin();
            GroupMessage existingGroupMessage = entityManager.find(GroupMessage.class, groupMessage.getId());
            if (!isNull(existingGroupMessage)) {
                entityManager.remove(existingGroupMessage);
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

}
