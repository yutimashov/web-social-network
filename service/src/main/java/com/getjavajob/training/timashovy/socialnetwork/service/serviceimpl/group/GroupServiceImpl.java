package com.getjavajob.training.timashovy.socialnetwork.service.serviceimpl.group;

import com.getjavajob.training.timashovy.socialnetwork.common.Group;
import com.getjavajob.training.timashovy.socialnetwork.dao.interfaces.BaseDao;
import com.getjavajob.training.timashovy.socialnetwork.service.interfaces.GroupService;

import java.util.List;
import java.util.Optional;

import static com.getjavajob.training.timashovy.socialnetwork.dao.util.dbutils.dbconnection.ConnectionManager.getConnection;

public class GroupServiceImpl implements GroupService {

    private final BaseDao<Group> groupDaoInstance;
    private static volatile GroupService instance;

    private GroupServiceImpl(BaseDao<Group> groupDaoInstance) {
        this.groupDaoInstance = groupDaoInstance;
    }

    public static GroupService getInstance(BaseDao<Group> groupDaoInstance) {
        if (instance == null) {
            synchronized (GroupServiceImpl.class) {
                if (instance == null) {
                    instance = new GroupServiceImpl(groupDaoInstance);
                }
            }
        }
        return instance;
    }

    @Override
    public Long create(Group group) {
        return groupDaoInstance.create(getConnection(), group);
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
