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

    private final GroupRepository groupsDao;
    private final GroupMembershipService groupMembershipService;

    public GroupServiceImpl(GroupMembershipService groupMembershipService,
                            GroupRepository groupsDao) {
        this.groupMembershipService = groupMembershipService;
        this.groupsDao = groupsDao;
    }

    @Transactional
    @Override
    public Group create(Group group, Account account) {
        Group createdGroup = groupsDao.save(group);
        groupMembershipService.sendRequest(createdGroup, account);
        Long accountId = account.getId();
        groupMembershipService.makeMember(createdGroup.getId(), accountId);
        groupMembershipService.makeAdmin(createdGroup.getId(), accountId);
        return createdGroup;
    }

    @Override
    public List<Group> findAll() {
        return groupsDao.findAll();
    }

    @Override
    public Optional<Group> findById(Long groupId) {
        return groupsDao.findById(groupId);
    }

}
