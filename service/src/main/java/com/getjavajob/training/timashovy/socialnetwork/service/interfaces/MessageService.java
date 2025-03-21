package com.getjavajob.training.timashovy.socialnetwork.service.interfaces;

import com.getjavajob.training.timashovy.socialnetwork.domain.account.Account;
import com.getjavajob.training.timashovy.socialnetwork.domain.message.GroupMessage;
import com.getjavajob.training.timashovy.socialnetwork.domain.message.Message;
import com.getjavajob.training.timashovy.socialnetwork.domain.message.PersonalMessage;
import com.getjavajob.training.timashovy.socialnetwork.domain.message.PersonalWallMessage;

import java.util.List;

public interface MessageService {

    void createGroupMessage(GroupMessage groupMessage, Long groupId);

    void createPersonalWallMessage(PersonalWallMessage personalWallMessage);

    void createPersonalMessage(PersonalMessage personalMessage);

    boolean deleteById(Long id);

    Message getGroupMessageById(Long id);

    Message getAccountWallMessageById(Long accountId);

    Message getPersonalMessageById(Long messageId);

    List<GroupMessage> getMessagesByGroupId(Long groupId);

    List<PersonalWallMessage> getAllAccountWallMessages(Long destinationId);

    List<Account> getAllAccountsWithPersonalMessages(Long accountId);

    List<PersonalMessage> getAllPersonalMessagesWithAccount(Long authorId, Long receiverId);

    List<PersonalWallMessage> getNewsFeed(Long destinationId);

}
