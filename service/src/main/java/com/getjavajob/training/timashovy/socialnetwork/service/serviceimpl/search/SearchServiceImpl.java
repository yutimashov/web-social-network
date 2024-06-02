package com.getjavajob.training.timashovy.socialnetwork.service.serviceimpl.search;

import com.getjavajob.training.timashovy.socialnetwork.common.Group;
import com.getjavajob.training.timashovy.socialnetwork.common.account.Account;
import com.getjavajob.training.timashovy.socialnetwork.dao.interfaces.SearchDao;
import com.getjavajob.training.timashovy.socialnetwork.service.interfaces.SearchService;

import java.util.List;

public class SearchServiceImpl implements SearchService {

    private final SearchDao<Account> searchAccountDao;
    private final SearchDao<Group> searchGroupDao;
    private static volatile SearchService instance;

    private SearchServiceImpl(SearchDao<Account> searchAccountDao, SearchDao<Group> searchGroupDao) {
        this.searchAccountDao = searchAccountDao;
        this.searchGroupDao = searchGroupDao;
    }

    public static SearchService getInstance(SearchDao<Account> searchAccountDao, SearchDao<Group> searchGroupDao) {
        if (instance == null) {
            synchronized (SearchServiceImpl.class) {
                if (instance == null) {
                    instance = new SearchServiceImpl(searchAccountDao, searchGroupDao);
                }
            }
        }
        return instance;
    }

    @Override
    public List<Account> findAccounts(String searchQuery, int currentPage, int numOfRecords) {
        return searchAccountDao.searchAccounts(searchQuery, currentPage, numOfRecords);
    }

    @Override
    public int findAccountResultsAmount(String searchQuery) {
        return searchAccountDao.findResultsAmount(searchQuery);
    }

    @Override
    public int findGroupResultsAmount(String searchQuery) {
        return searchGroupDao.findResultsAmount(searchQuery);
    }

    @Override
    public List<Group> findGroups(String searchQuery, int currentPage, int numOfRecords) {
        return searchGroupDao.searchAccounts(searchQuery, currentPage, numOfRecords);
    }

}
