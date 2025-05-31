package com.getjavajob.searchservice.service.group;

import com.getjavajob.searchservice.dao.group.GroupSearchRepository;
import com.getjavajob.training.timashovy.socialnetwork.domain.group.Group;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GroupSearchServiceImpl implements GroupSearchService {

    private final GroupSearchRepository groupSearchRepository;

    public GroupSearchServiceImpl(GroupSearchRepository groupSearchRepository) {
        this.groupSearchRepository = groupSearchRepository;
    }

    @Override
    public List<Group> findAccounts(String searchQuery, String lastName, int limit) {
        return groupSearchRepository.findGroupsSearchResult(searchQuery, lastName, limit);
    }

}
