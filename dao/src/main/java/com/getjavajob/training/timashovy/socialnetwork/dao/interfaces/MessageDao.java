package com.getjavajob.training.timashovy.socialnetwork.dao.interfaces;

import com.getjavajob.training.timashovy.socialnetwork.common.message.Message;

import java.util.List;
import java.util.Optional;

public interface MessageDao {

    Long create(Message message);

    Optional<Message> getById(Long id);

    List<Message> getAll(Long destinationId);

    boolean updateById(Long id, Message message);

    boolean deleteById(Long id);

}
