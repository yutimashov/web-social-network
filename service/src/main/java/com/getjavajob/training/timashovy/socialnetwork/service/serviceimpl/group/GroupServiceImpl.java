package com.getjavajob.training.timashovy.socialnetwork.service.serviceimpl.group;

import com.getjavajob.training.timashovy.socialnetwork.common.Group;
import com.getjavajob.training.timashovy.socialnetwork.dao.interfaces.BaseDao;
import com.getjavajob.training.timashovy.socialnetwork.service.interfaces.GroupService;

import java.util.List;
import java.util.Optional;

import static com.getjavajob.training.timashovy.socialnetwork.dao.util.dbutils.dbconnection.ConnectionManager.getConnection;

public class GroupServiceImpl implements GroupService {

    private final BaseDao<Group> groupDaoInstance;

    public GroupServiceImpl(BaseDao<Group> groupDaoInstance) {
        this.groupDaoInstance = groupDaoInstance;
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
