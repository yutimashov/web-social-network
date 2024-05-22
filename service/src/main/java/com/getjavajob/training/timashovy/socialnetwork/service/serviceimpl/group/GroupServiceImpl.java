package com.getjavajob.training.timashovy.socialnetwork.service.serviceimpl.group;

import com.getjavajob.training.timashovy.socialnetwork.common.Group;
import com.getjavajob.training.timashovy.socialnetwork.dao.interfaces.BaseDao;
import com.getjavajob.training.timashovy.socialnetwork.service.interfaces.GroupService;

import java.util.List;
import java.util.Optional;

public class GroupServiceImpl implements GroupService {

    private final BaseDao<Group> groupDaoInstance;

    private GroupServiceImpl(BaseDao<Group> groupDaoInstance) {
        this.groupDaoInstance = groupDaoInstance;
    }

    public static GroupService createInstance(BaseDao<Group> groupDaoInstance) {
        return new GroupServiceImpl(groupDaoInstance);
    }

    @Override
    public Long create(Group group) {
        return groupDaoInstance.create(group);
    }

    @Override
    public List<Group> getAll() {
        return groupDaoInstance.getAll();
    }

    @Override
    public Optional<Group> getById(Long groupId) {
        return groupDaoInstance.getById(groupId);
    }

}
