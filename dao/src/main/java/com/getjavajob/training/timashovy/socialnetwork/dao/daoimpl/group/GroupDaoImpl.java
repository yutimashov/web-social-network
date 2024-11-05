package com.getjavajob.training.timashovy.socialnetwork.dao.daoimpl.group;

import com.getjavajob.training.timashovy.socialnetwork.dao.interfaces.BaseDao;
import com.getjavajob.training.timashovy.socialnetwork.domain.group.Group;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;
import java.util.List;
import java.util.Optional;

import static java.util.Objects.isNull;

public class GroupDaoImpl implements BaseDao<Group> {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Long create(Group group) {
        entityManager.persist(group);
        entityManager.flush();
        return group.getId();
    }

    @Override
    public boolean updateById(Long id, Group group) {
        EntityTransaction transaction = entityManager.getTransaction();
        try {
            transaction.begin();
            Group existingGroup = entityManager.find(Group.class, id);
            if (isNull(existingGroup)) {
                return false;
            }
            existingGroup.setAvatar(group.getAvatar());
            existingGroup.setDescription(group.getDescription());
            existingGroup.setName(group.getName());
            existingGroup.setAccountOwner(group.getAccountOwner());
            existingGroup.setMessages(group.getMessages());
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
            Group group = entityManager.find(Group.class, id);
            if (!isNull(group)) {
                entityManager.remove(group);
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

    @Override
    public Optional<Group> getById(Long groupId) {
        try {
            Group group = entityManager.createQuery(
                            "select g from Group g where g.id = :groupId", Group.class)
                    .setParameter("groupId", groupId)
                    .getSingleResult();
            return Optional.ofNullable(group);
        } catch (NoResultException e) {
            return Optional.empty();
        }
    }

    @Override
    public List<Group> getAll() {
        return entityManager.createQuery("select g from Group g", Group.class).getResultList();
    }

}
