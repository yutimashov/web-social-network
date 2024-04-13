package com.getjavajob.training.timashovy.socialnetwork.service.interfaces;

import com.getjavajob.training.timashovy.socialnetwork.common.Group;

import java.util.List;

public interface GroupService {

    Long createGroup(Group group);
    List<Group> listGroups();

}
