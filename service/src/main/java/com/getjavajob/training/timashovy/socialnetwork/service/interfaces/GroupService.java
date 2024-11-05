package com.getjavajob.training.timashovy.socialnetwork.service.interfaces;

import com.getjavajob.training.timashovy.socialnetwork.domain.account.Account;
import com.getjavajob.training.timashovy.socialnetwork.domain.group.Group;

import java.util.List;
import java.util.Optional;

public interface GroupService {

    Long create(Group group, Account account);

    List<Group> getAll();

    Optional<Group> getById(Long groupId);

}
