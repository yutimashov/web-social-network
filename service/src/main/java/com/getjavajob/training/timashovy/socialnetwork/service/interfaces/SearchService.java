package com.getjavajob.training.timashovy.socialnetwork.service.interfaces;

import com.getjavajob.training.timashovy.socialnetwork.domain.group.Group;
import com.getjavajob.training.timashovy.socialnetwork.domain.account.Account;

import java.util.List;

public interface SearchService {

    List<Account> findAccounts(String searchQuery, int currentPage, int numOfRecords);

    Long findAccountResultsAmount(String searchQuery);

    Long findGroupResultsAmount(String searchQuery);

    List<Group> findGroups(String searchQuery, int currentPage, int numOfRecords);

}
