package com.getjavajob.training.timashovy.socialnetwork.service.interfaces;

import com.getjavajob.training.timashovy.socialnetwork.common.Group;
import com.getjavajob.training.timashovy.socialnetwork.common.account.Account;

import java.util.List;
import java.util.Optional;

public interface GroupService {

    Long create(Group group);

    List<Group> getAll();

    Optional<Group> getById(Long groupId);


}
