package com.getjavajob.searchservice.service.group;

import com.getjavajob.training.timashovy.socialnetwork.domain.group.Group;

import java.util.List;

public interface GroupSearchService {

    List<Group> findAccounts(String searchQuery, String lastName, int limit);

}
