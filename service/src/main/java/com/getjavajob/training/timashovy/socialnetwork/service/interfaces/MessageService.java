package com.getjavajob.training.timashovy.socialnetwork.service.interfaces;

import com.getjavajob.training.timashovy.socialnetwork.common.message.Message;

import java.util.List;

public interface MessageService {

    Long create(Message message);

    boolean deleteById(Long id);

    Message getById(Long id);

    List<Message> getAll(Long groupId);

}
