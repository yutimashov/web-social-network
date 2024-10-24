package com.getjavajob.training.timashovy.socialnetwork.service.serviceimpl.search;

import com.getjavajob.training.timashovy.socialnetwork.domain.group.Group;
import com.getjavajob.training.timashovy.socialnetwork.domain.account.Account;
import com.getjavajob.training.timashovy.socialnetwork.dao.interfaces.SearchDao;
import com.getjavajob.training.timashovy.socialnetwork.service.interfaces.SearchService;

import java.util.List;

public class SearchServiceImpl implements SearchService {

    private final SearchDao<Account> searchAccountDao;
    private final SearchDao<Group> searchGroupDao;

    public SearchServiceImpl(SearchDao<Account> searchAccountDao, SearchDao<Group> searchGroupDao) {
        this.searchAccountDao = searchAccountDao;
        this.searchGroupDao = searchGroupDao;
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
