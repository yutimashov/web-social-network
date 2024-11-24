package com.getjavajob.training.timashovy.socialnetwork.dao.daoimpl.group;

import com.getjavajob.training.timashovy.socialnetwork.dao.exception.DaoException;
import com.getjavajob.training.timashovy.socialnetwork.dao.interfaces.group.GroupRepository;
import com.getjavajob.training.timashovy.socialnetwork.domain.group.Group;
import org.slf4j.Logger;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;
import java.util.List;
import java.util.Optional;

import static java.util.Objects.isNull;
import static java.util.Optional.ofNullable;
import static org.slf4j.LoggerFactory.getLogger;

public class GroupRepositoryImpl implements GroupRepository {

    private static final Logger logger = getLogger(GroupRepositoryImpl.class);

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Group save(Group group) {
        try {
            entityManager.persist(group);
            return group;
        } catch (PersistenceException e) {
            logger.error("Error persisting group name={} from account={}", group.getName(),
                    group.getAccountOwner().getId());
            throw new DaoException("Cannot save group to persistent storage", e);
        }
    }

    @Override
    public void delete(Long id) {
        try {
            Group group = entityManager.find(Group.class, id);
            if (!isNull(group)) {
                entityManager.remove(group);
            }
        } catch (PersistenceException e) {
            logger.error("Error deleting group: id={}", id);
            throw new DaoException("Cannot delete group by provided id", e);
        }
    }

    @Override
    public Optional<Group> getById(Long id) {
        try {
            return ofNullable(entityManager.find(Group.class, id));
        } catch (PersistenceException e) {
            logger.error("Error getting group by id: id={}", id);
            throw new DaoException("Cannot get group by provided id", e);
        }
    }

    @Override
    public List<Group> getAll() {
        try {
            return entityManager.createQuery("select g from Group g", Group.class).getResultList();
        } catch (PersistenceException e) {
            logger.error("Error getting all groups");
            throw new DaoException("Cannot get all groups from persistent storage", e);
        }
    }

}
