package com.getjavajob.training.timashovy.socialnetwork.dao.interfaces.search;

import java.util.List;

public interface SearchDao<T> {

    List<T> findResults(String searchQuery, int currentPage, int numOfRecords);
    Long findResultsAmount(String searchQuery);

}
