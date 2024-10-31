package com.getjavajob.training.timashovy.socialnetwork.dao.daoimpl.search;

import com.getjavajob.training.timashovy.socialnetwork.dao.interfaces.SearchDao;
import com.getjavajob.training.timashovy.socialnetwork.domain.account.Account;

import javax.persistence.EntityManager;
import javax.persistence.NonUniqueResultException;
import javax.persistence.PersistenceContext;
import java.util.List;

/**
 * Singleton class responsible for working with {@link com.getjavajob.training.timashovy.socialnetwork.dao.util.dbutils.TableNames#ACCOUNTS_TABLE accounts table}.
 * It provides safe multithreading approach for creating singleton object using synchronization mechanism.
 */
public class SearchAccountDaoImpl implements SearchDao<Account> {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<Account> findResults(String searchQuery, int currentPage, int recordsPerPage) {
        return entityManager.createQuery("SELECT a FROM Account a WHERE LOWER(a.firstName) "
                        + "LIKE LOWER(:searchQuery) OR LOWER(a.lastName) LIKE LOWER(:searchQuery)", Account.class)
                .setParameter("searchQuery", "%" + searchQuery + "%")
                .setFirstResult(currentPage * recordsPerPage - recordsPerPage)
                .setMaxResults(recordsPerPage)
                .getResultList();
    }

    @Override
    public int findResultsAmount(String searchQuery) {
        try {
            return entityManager.createQuery(
                            "select count(*) from Account a where a.firstName like lower(:searchQuery) "
                                    + "or lower(a.lastName) like lower(:sesrchQuery)",
                            Integer.class)
                    .setParameter("searchQuery", searchQuery)
                    .getSingleResult();
        } catch (NonUniqueResultException e) {
            return -1;
        }
    }

}
