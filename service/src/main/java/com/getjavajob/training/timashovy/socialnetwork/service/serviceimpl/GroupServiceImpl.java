package com.getjavajob.training.timashovy.socialnetwork.service.serviceimpl;

import com.getjavajob.training.timashovy.socialnetwork.common.Group;
import com.getjavajob.training.timashovy.socialnetwork.dao.interfaces.AccountGroupDao;
import com.getjavajob.training.timashovy.socialnetwork.service.interfaces.GroupService;

import java.util.List;

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

}
