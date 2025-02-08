package com.getjavajob.training.timashovy.socialnetwork.dao.interfaces.repositories.search;

import com.getjavajob.training.timashovy.socialnetwork.domain.group.Group;

import java.util.List;

public interface GroupSearchRepository {

    List<Group> findGroupsSearchResult(String searchQuery, String lastGroupName, int limit);

}
