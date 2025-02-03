package com.getjavajob.training.timashovy.socialnetwork.dao.interfaces.repositories.message;

import com.getjavajob.training.timashovy.socialnetwork.dao.interfaces.Repository;
import com.getjavajob.training.timashovy.socialnetwork.domain.account.Account;
import com.getjavajob.training.timashovy.socialnetwork.domain.message.PersonalMessage;

import java.util.List;

public interface PersonalMessageRepository extends Repository<Long, PersonalMessage> {

    List<Account> getAllAccounts(Long accountId);

    List<PersonalMessage> getAllPersonalMessagesWithAccount(Long authorId, Long receiverId);

}
