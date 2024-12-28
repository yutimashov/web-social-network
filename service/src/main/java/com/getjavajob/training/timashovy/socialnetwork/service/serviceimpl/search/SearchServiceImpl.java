package com.getjavajob.training.timashovy.socialnetwork.service.serviceimpl.search;

import com.getjavajob.training.timashovy.socialnetwork.domain.group.Group;
import com.getjavajob.training.timashovy.socialnetwork.domain.account.Account;
import com.getjavajob.training.timashovy.socialnetwork.dao.interfaces.repositories.search.SearchRepository;
import com.getjavajob.training.timashovy.socialnetwork.service.interfaces.SearchService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SearchServiceImpl implements SearchService {

    private final SearchRepository<Account> searchAccountDao;
    private final SearchRepository<Group> searchGroupDao;

    public SearchServiceImpl(SearchRepository<Account> searchAccountDao, SearchRepository<Group> searchGroupDao) {
        this.searchAccountDao = searchAccountDao;
        this.searchGroupDao = searchGroupDao;
    }

    @Override
    public List<Account> findAccounts(String searchQuery, int currentPage, int numOfRecords) {
        return searchAccountDao.findResults(searchQuery, currentPage, numOfRecords);
    }

    @Override
    public Long findAccountResultsAmount(String searchQuery) {
        return searchAccountDao.findResultsAmount(searchQuery);
    }

    @Override
    public Long findGroupResultsAmount(String searchQuery) {
        return searchGroupDao.findResultsAmount(searchQuery);
    }

    @Override
    public List<Group> findGroups(String searchQuery, int currentPage, int numOfRecords) {
        return searchGroupDao.findResults(searchQuery, currentPage, numOfRecords);
    }

}
