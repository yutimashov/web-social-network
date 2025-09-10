package com.getjavajob.messageservice.dao;

import com.getjavajob.training.timashovy.socialnetwork.domain.message.GroupMessage;

import java.util.List;

public interface GroupMessageRepository extends Repository<Long, GroupMessage> {

    List<GroupMessage> getMessagesByGroupId(Long groupId);

}
