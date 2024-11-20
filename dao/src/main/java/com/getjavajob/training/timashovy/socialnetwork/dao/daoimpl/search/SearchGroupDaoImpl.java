package com.getjavajob.training.timashovy.socialnetwork.dao.daoimpl.search;

import com.getjavajob.training.timashovy.socialnetwork.dao.interfaces.search.SearchDao;
import com.getjavajob.training.timashovy.socialnetwork.domain.group.Group;

import javax.persistence.EntityManager;
import javax.persistence.NonUniqueResultException;
import javax.persistence.PersistenceContext;
import java.util.List;

/**
 * Singleton class responsible for working with {@link com.getjavajob.training.timashovy.socialnetwork.dao.util.dbutils.TableNames#GROUPS_TABLE groups table}.
 * It provides safe multithreading approach for creating singleton object using synchronization mechanism.
 */
public class SearchGroupDaoImpl implements SearchDao<Group> {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<Group> findResults(String searchQuery, int currentPage, int recordsPerPage) {
        return entityManager.createQuery(
                        "select g from Group g where lower(g.name) like lower(:searchQuery)", Group.class
                )
                .setParameter("searchQuery", "%" + searchQuery + "%")
                .setFirstResult(currentPage * recordsPerPage - recordsPerPage)
                .setMaxResults(recordsPerPage)
                .getResultList();
    }

    @Override
    public Long findResultsAmount(String searchQuery) {
        try {
            return entityManager.createQuery(
                            "select count(*) from Group g where lower(g.name) ilike lower(:searchQuery)",
                            Long.class)
                    .setParameter("searchQuery", "%" + searchQuery + "%")
                    .getSingleResult();
        } catch (NonUniqueResultException e) {
            return -1L;
        }
    }

}
