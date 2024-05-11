package com.getjavajob.training.timashovy.socialnetwork.service.interfaces;

import com.getjavajob.training.timashovy.socialnetwork.common.Group;
import com.getjavajob.training.timashovy.socialnetwork.common.account.Account;

import java.util.List;

public interface SearchService {

    List<Account> findAccounts(String searchQuery, int currentPage, int numOfRecords);

    int findAccountResultsAmount(String searchQuery);

    int findGroupResultsAmount(String searchQuery);

    List<Group> findGroups(String searchQuery, int currentPage, int numOfRecords);

}
