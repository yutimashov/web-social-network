package com.getjavajob.training.timashovy.socialnetwork.dao.interfaces.repositories.search;

import java.util.List;

public interface SearchRepository<T> {

    List<T> findResults(String searchQuery, int currentPage, int numOfRecords);
    Long findResultsAmount(String searchQuery);

}
