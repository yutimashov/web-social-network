package com.getjavajob.training.timashovy.socialnetwork.dao.interfaces.group;

import com.getjavajob.training.timashovy.socialnetwork.dao.interfaces.Repository;
import com.getjavajob.training.timashovy.socialnetwork.domain.group.Group;

import java.util.List;

public interface GroupRepository extends Repository<Long, Group> {

    List<Group> getAll();

}
