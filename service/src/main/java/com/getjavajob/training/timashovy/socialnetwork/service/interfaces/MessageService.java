package com.getjavajob.training.timashovy.socialnetwork.service.interfaces;

import com.getjavajob.training.timashovy.socialnetwork.common.message.Message;

import java.util.List;

public interface MessageService {

    Long createGroupMessage(Message message);

    Long createPersonalWallMessage(Message message);

    Long createPersonalMessage(Message message);

    boolean deleteById(Long id);

    Message getGroupMessageById(Long id);

    Message getAccountWallMessageById(Long accountId);

    Message getPersonalMessageById(Long messageId);

    List<Message> getAllGroupMessages(Long groupId);

    List<Message> getAllAccountWallMessages(Long destinationId);

}
