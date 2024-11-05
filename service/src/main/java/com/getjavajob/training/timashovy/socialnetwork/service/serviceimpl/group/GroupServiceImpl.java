package com.getjavajob.training.timashovy.socialnetwork.service.serviceimpl.group;

import com.getjavajob.training.timashovy.socialnetwork.dao.interfaces.BaseDao;
import com.getjavajob.training.timashovy.socialnetwork.domain.account.Account;
import com.getjavajob.training.timashovy.socialnetwork.domain.group.Group;
import com.getjavajob.training.timashovy.socialnetwork.service.interfaces.GroupMembershipService;
import com.getjavajob.training.timashovy.socialnetwork.service.interfaces.GroupService;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

public class GroupServiceImpl implements GroupService {

    private final BaseDao<Group> groupDao;
    private final GroupMembershipService groupMembershipService;

    public GroupServiceImpl(BaseDao<Group> groupDao, GroupMembershipService groupMembershipService) {
        this.groupDao = groupDao;
        this.groupMembershipService = groupMembershipService;
    }

    @Transactional
    @Override
    public Long create(Group group, Account account) {
        Long groupId = groupDao.create(group);
        groupMembershipService.sendRequest(group, account);
        Long accountId = account.getId();
        groupMembershipService.makeMember(groupId, accountId);
        groupMembershipService.makeAdmin(groupId, accountId);
        return groupId;
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
