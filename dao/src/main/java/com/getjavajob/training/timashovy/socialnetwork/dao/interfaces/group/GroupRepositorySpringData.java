package com.getjavajob.training.timashovy.socialnetwork.dao.interfaces.group;

import com.getjavajob.training.timashovy.socialnetwork.domain.group.Group;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GroupRepositorySpringData extends JpaRepository<Group, Long> {

}
