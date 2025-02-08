package com.getjavajob.training.timashovy.socialnetwork.service.serviceimpl.search;

import com.getjavajob.training.timashovy.socialnetwork.dao.interfaces.repositories.search.GroupSearchRepository;
import com.getjavajob.training.timashovy.socialnetwork.domain.group.Group;
import com.getjavajob.training.timashovy.socialnetwork.service.interfaces.GroupSearchService;
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
