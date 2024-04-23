package com.getjavajob.training.timashovy.socialnetwork.service.interfaces;

import com.getjavajob.training.timashovy.socialnetwork.common.message.Message;

public interface MessageService {

    Long create(Message message);

    boolean deleteById(Long id);

    Message getById(Long id);

}
