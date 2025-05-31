package com.getjavajob.groupservice.dao.group;

import com.getjavajob.training.timashovy.socialnetwork.domain.group.Group;
import org.springframework.data.repository.CrudRepository;

public interface GroupRepositorySpringData extends CrudRepository<Group, Long> {
}
