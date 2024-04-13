package com.getjavajob.training.timashovy.socialnetwork.service.interfaces;

import com.getjavajob.training.timashovy.socialnetwork.common.Group;

import java.util.List;
import java.util.Optional;

public interface GroupService {

    Long createGroup(Group group);
    List<Group> listGroups();
    boolean addUser(Long groupId, Long accountId);
    Optional<Group> getById(Long groupId);

}
