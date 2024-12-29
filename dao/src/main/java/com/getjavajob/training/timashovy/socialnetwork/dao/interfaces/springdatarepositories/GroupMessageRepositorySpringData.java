package com.getjavajob.training.timashovy.socialnetwork.dao.interfaces.springdatarepositories;

import com.getjavajob.training.timashovy.socialnetwork.domain.message.GroupMessage;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface GroupMessageRepositorySpringData extends CrudRepository<GroupMessage, Long> {

    List<GroupMessage> findByGroupId(Long groupId);

}
