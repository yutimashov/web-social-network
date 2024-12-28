package com.getjavajob.training.timashovy.socialnetwork.dao.interfaces.repositories.group;

import com.getjavajob.training.timashovy.socialnetwork.domain.account.Account;
import com.getjavajob.training.timashovy.socialnetwork.domain.group.Group;

import java.util.List;
import java.util.Optional;

public interface GroupRepository {

    Group create(Group group, Account account);

    Optional<Group> getById(Long id);

    List<Group> getAll();

    boolean updateById(Long id, Group group);

    boolean deleteById(Long id);


}
