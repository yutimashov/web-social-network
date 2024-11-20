package com.getjavajob.training.timashovy.socialnetwork.dao.daoimpl.message;

import com.getjavajob.training.timashovy.socialnetwork.dao.interfaces.message.GroupMessageRepository;
import com.getjavajob.training.timashovy.socialnetwork.domain.message.GroupMessage;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Optional;

import static java.util.Objects.isNull;
import static java.util.Optional.empty;
import static java.util.Optional.ofNullable;

public class GroupMessageDaoImpl implements GroupMessageRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public GroupMessage save(GroupMessage message) {
        entityManager.persist(message);
        return message;
    }

    @Override
    public Optional<GroupMessage> getById(Long groupId) {
        try {
            GroupMessage existingMessage = entityManager.createQuery(
                            "select gm from GroupMessage gm where gm.id = :groupId", GroupMessage.class)
                    .setParameter("groupId", groupId)
                    .getSingleResult();
            return ofNullable(existingMessage);
        } catch (NoResultException e) {
            return empty();
        }
    }

    @Override
    public void delete(Long id) {
        GroupMessage existingGroupMessage = entityManager.find(GroupMessage.class, id);
        if (!isNull(existingGroupMessage)) {
            entityManager.remove(existingGroupMessage);
        }
    }

    @Override
    public List<GroupMessage> getMessagesByGroupId(Long groupId) {
        return entityManager.createQuery(
                        "select gm from GroupMessage gm where gm.group.id = :groupId", GroupMessage.class)
                .setParameter("groupId", groupId)
                .getResultList();
    }

}
