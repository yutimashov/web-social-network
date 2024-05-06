package com.getjavajob.training.timashovy.socialnetwork.dao.interfaces;

import java.util.List;

public interface SearchDao<T> {

    List<T> searchAccounts(String searchQuery, int currentPage, int numOfRecords);
    int findResultsAmount(String searchQuery);

}
