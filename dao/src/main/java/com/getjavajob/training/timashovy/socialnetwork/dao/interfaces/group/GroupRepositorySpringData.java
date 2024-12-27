package com.getjavajob.training.timashovy.socialnetwork.dao.interfaces.group;

import com.getjavajob.training.timashovy.socialnetwork.domain.group.Group;
import org.springframework.data.repository.CrudRepository;

public interface GroupRepositorySpringData extends CrudRepository<Group, Long> {
}
