package com.getjavajob.searchservice.dao.group;

import com.getjavajob.training.timashovy.socialnetwork.domain.group.Group;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Class provides functionality for searching groups.
 */
@Repository
public class GroupSearchRepositoryImpl implements GroupSearchRepository {

    private final GroupSearchRepositorySpringData groupSearchRepositorySpringData;

    public GroupSearchRepositoryImpl(GroupSearchRepositorySpringData groupSearchRepositorySpringData) {
        this.groupSearchRepositorySpringData = groupSearchRepositorySpringData;
    }

    @Override
    public List<Group> findGroupsSearchResult(String searchQuery, String lastGroupName, int limit) {
        return groupSearchRepositorySpringData.findGroupsSearchResult(searchQuery, lastGroupName, limit);
    }

}
