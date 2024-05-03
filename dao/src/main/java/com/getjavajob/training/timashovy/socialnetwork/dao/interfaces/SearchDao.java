package com.getjavajob.training.timashovy.socialnetwork.dao.interfaces;

import java.util.List;

public interface SearchDao<T> {

    List<T> search(String searchQuery);

}
