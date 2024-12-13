package com.getjavajob.training.timashovy.socialnetwork.service.serviceimpl.group;

import com.getjavajob.training.timashovy.socialnetwork.dao.interfaces.group.GroupRepository;
import com.getjavajob.training.timashovy.socialnetwork.domain.account.Account;
import com.getjavajob.training.timashovy.socialnetwork.domain.group.Group;
import com.getjavajob.training.timashovy.socialnetwork.service.interfaces.GroupMembershipService;
import com.getjavajob.training.timashovy.socialnetwork.service.interfaces.GroupService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
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
        groupMembershipService.sendRequest(group, account);
        Group createdGroup = groupsDao.create(group, account);
        Long accountId = account.getId();
        groupMembershipService.makeMember(createdGroup.getId(), accountId);
        groupMembershipService.makeAdmin(createdGroup.getId(), accountId);
        return createdGroup;
    }

    @Override
    public List<Group> getAll() {
        return groupsDao.getAll();
    }

    @Override
    public boolean updateById(Long id, Group group) {
        return groupsDao.updateById(id, group);
    }

    @Override
    public boolean deleteById(Long id) {
        return groupsDao.deleteById(id);
    }

    @Override
    public Optional<Group> getById(Long groupId) {
        return groupsDao.getById(groupId);
    }

}
