package com.getjavajob.training.timashovy.socialnetwork.service.serviceimpl.group;

import com.getjavajob.training.timashovy.socialnetwork.dao.interfaces.group.GroupRepository;
import com.getjavajob.training.timashovy.socialnetwork.domain.account.Account;
import com.getjavajob.training.timashovy.socialnetwork.domain.group.Group;
import com.getjavajob.training.timashovy.socialnetwork.service.interfaces.GroupMembershipService;
import com.getjavajob.training.timashovy.socialnetwork.service.interfaces.GroupService;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

public class GroupServiceImpl implements GroupService {

    private final GroupRepository groupDao;
    private final GroupMembershipService groupMembershipService;

    public GroupServiceImpl(GroupRepository groupDao, GroupMembershipService groupMembershipService) {
        this.groupDao = groupDao;
        this.groupMembershipService = groupMembershipService;
    }

    @Transactional
    @Override
    public Group create(Group group, Account account) {
        groupMembershipService.sendRequest(group, account);
        Group createdGroup = groupDao.save(group);
        Long accountId = account.getId();
        groupMembershipService.makeMember(createdGroup.getId(), accountId);
        groupMembershipService.makeAdmin(createdGroup.getId(), accountId);
        return createdGroup;
    }

    @Override
    public List<Group> getAll() {
        return groupDao.getAll();
    }

    @Override
    public Optional<Group> getById(Long groupId) {
        return groupDao.getById(groupId);
    }

}
