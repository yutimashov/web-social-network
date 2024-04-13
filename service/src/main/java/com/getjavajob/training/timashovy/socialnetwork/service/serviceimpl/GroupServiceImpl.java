package com.getjavajob.training.timashovy.socialnetwork.service.serviceimpl;

import com.getjavajob.training.timashovy.socialnetwork.common.Group;
import com.getjavajob.training.timashovy.socialnetwork.dao.interfaces.AccountGroupDao;
import com.getjavajob.training.timashovy.socialnetwork.service.interfaces.GroupService;

import java.util.List;
import java.util.Optional;

import static com.getjavajob.training.timashovy.socialnetwork.dao.daoimpl.GroupDaoImpl.getGroupDaoInstance;

public class GroupServiceImpl implements GroupService {

    private static final GroupServiceImpl GROUP_SERVICE_INSTANCE = new GroupServiceImpl();

    private final AccountGroupDao<Group> groupDaoInstance = getGroupDaoInstance();

    private GroupServiceImpl() {
    }

    public static GroupServiceImpl getGroupServiceImplInstance() {
        return GROUP_SERVICE_INSTANCE;
    }

    @Override
    public Long createGroup(Group group) {
        return groupDaoInstance.create(group);
    }

    @Override
    public List<Group> listGroups() {
        return groupDaoInstance.getAll();
    }

    @Override
    public boolean sendGroupMemberRequest(Long groupId, Long accountId) {
        return groupDaoInstance.sendGroupMemberRequest(groupId, accountId);
    }

    @Override
    public boolean makeUserGroupAdmin(Long groupId, Long accountId) {
        return groupDaoInstance.makeUserGroupAdmin(groupId, accountId);
    }

    @Override
    public boolean makeAccountGroupMember(Long groupId, Long accountId) {
        return groupDaoInstance.makeAccountGroupMember(groupId, accountId);
    }

    @Override
    public Optional<Group> getById(Long groupId) {
        return groupDaoInstance.getById(groupId);
    }

}
