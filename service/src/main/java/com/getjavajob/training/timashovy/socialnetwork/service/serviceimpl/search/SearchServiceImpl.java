package com.getjavajob.training.timashovy.socialnetwork.service.serviceimpl.search;

import com.getjavajob.training.timashovy.socialnetwork.common.Group;
import com.getjavajob.training.timashovy.socialnetwork.common.account.Account;
import com.getjavajob.training.timashovy.socialnetwork.dao.daoimpl.search.SearchAccountDaoImpl;
import com.getjavajob.training.timashovy.socialnetwork.dao.daoimpl.search.SearchGroupDaoImpl;
import com.getjavajob.training.timashovy.socialnetwork.dao.interfaces.SearchDao;
import com.getjavajob.training.timashovy.socialnetwork.service.interfaces.SearchService;

import java.util.List;

public class SearchServiceImpl implements SearchService {

    private static final SearchServiceImpl SEARCH_SERVICE = new SearchServiceImpl();
    private final SearchDao<Account> searchAccountDao = SearchAccountDaoImpl.getInstance();
    private final SearchDao<Group> searchGroupDao = SearchGroupDaoImpl.getInstance();

    private SearchServiceImpl() {
    }

    public static SearchServiceImpl getInstance() {
        return SEARCH_SERVICE;
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
