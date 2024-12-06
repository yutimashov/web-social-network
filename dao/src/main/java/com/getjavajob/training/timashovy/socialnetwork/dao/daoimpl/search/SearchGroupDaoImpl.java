package com.getjavajob.training.timashovy.socialnetwork.dao.daoimpl.search;

import com.getjavajob.training.timashovy.socialnetwork.dao.interfaces.search.SearchDao;
import com.getjavajob.training.timashovy.socialnetwork.domain.group.Group;

import javax.persistence.EntityManager;
import javax.persistence.NonUniqueResultException;
import javax.persistence.PersistenceContext;
import java.util.List;

/**
 * Class provides functionality for searching groups.
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
                            "select count(g.id) from Group g where lower(g.name) like lower(:searchQuery)",
                            Long.class)
                    .setParameter("searchQuery", "%" + searchQuery + "%")
                    .getSingleResult();
        } catch (NonUniqueResultException e) {
            return -1L;
        }
    }

}
