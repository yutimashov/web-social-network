package com.getjavajob.training.timashovy.socialnetwork.service.interfaces;

import com.getjavajob.training.timashovy.socialnetwork.domain.account.Account;
import com.getjavajob.training.timashovy.socialnetwork.domain.message.GroupMessage;
import com.getjavajob.training.timashovy.socialnetwork.domain.message.Message;
import com.getjavajob.training.timashovy.socialnetwork.domain.message.PersonalMessage;
import com.getjavajob.training.timashovy.socialnetwork.domain.message.PersonalWallMessage;

import java.util.List;

public interface MessageService {

    Long createGroupMessage(GroupMessage groupMessage);

    Long createPersonalWallMessage(PersonalWallMessage personalWallMessage);

    Long createPersonalMessage(PersonalMessage personalMessage);

    boolean deleteById(Long id);

    Message getGroupMessageById(Long id);

    Message getAccountWallMessageById(Long accountId);

    Message getPersonalMessageById(Long messageId);

    List<GroupMessage> getAllGroupMessages(Long groupId);

    List<PersonalWallMessage> getAllAccountWallMessages(Long destinationId);

    List<Account> getAllAccountsWithPersonalMessages(Long accountId);

    List<PersonalMessage> getAllPersonalMessagesWithAccount(Long authorId, Long receiverId);

}
