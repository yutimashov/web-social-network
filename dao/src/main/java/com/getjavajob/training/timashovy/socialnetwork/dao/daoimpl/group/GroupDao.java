package com.getjavajob.training.timashovy.socialnetwork.dao.daoimpl.group;

import com.getjavajob.training.timashovy.socialnetwork.dao.interfaces.group.GroupRepository;
import com.getjavajob.training.timashovy.socialnetwork.domain.group.Group;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Optional;

import static java.util.Objects.isNull;
import static java.util.Optional.empty;
import static java.util.Optional.ofNullable;

public class GroupDao implements GroupRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Group save(Group group) {
        entityManager.persist(group);
        return group;
    }

    @Override
    public void delete(Long id) {
        Group group = entityManager.find(Group.class, id);
        if (!isNull(group)) {
            entityManager.remove(group);
        }
    }

    @Override
    public Optional<Group> getById(Long groupId) {
        try {
            Group existingGroup = entityManager.find(Group.class, groupId);
            return ofNullable(existingGroup);
        } catch (NoResultException e) {
            return empty();
        }
    }

    @Override
    public List<Group> getAll() {
        return entityManager.createQuery("select g from Group g", Group.class).getResultList();
    }

}
