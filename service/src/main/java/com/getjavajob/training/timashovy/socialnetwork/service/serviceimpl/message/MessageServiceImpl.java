package com.getjavajob.training.timashovy.socialnetwork.service.serviceimpl.message;

import com.getjavajob.training.timashovy.socialnetwork.dao.interfaces.repositories.group.GroupRepository;
import com.getjavajob.training.timashovy.socialnetwork.dao.interfaces.repositories.message.GroupMessageRepository;
import com.getjavajob.training.timashovy.socialnetwork.dao.interfaces.repositories.message.PersonalMessageRepository;
import com.getjavajob.training.timashovy.socialnetwork.dao.interfaces.repositories.message.PersonalWallMessageRepository;
import com.getjavajob.training.timashovy.socialnetwork.domain.account.Account;
import com.getjavajob.training.timashovy.socialnetwork.domain.message.GroupMessage;
import com.getjavajob.training.timashovy.socialnetwork.domain.message.Message;
import com.getjavajob.training.timashovy.socialnetwork.domain.message.PersonalMessage;
import com.getjavajob.training.timashovy.socialnetwork.domain.message.PersonalWallMessage;
import com.getjavajob.training.timashovy.socialnetwork.service.interfaces.MessageService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class MessageServiceImpl implements MessageService {

    private final GroupMessageRepository groupMessageDao;
    private final PersonalWallMessageRepository accountWallMessageDao;
    private final PersonalMessageRepository personalMessageDao;
    private final GroupRepository groupRepository;

    public MessageServiceImpl(GroupMessageRepository groupMessageDao, PersonalWallMessageRepository accountWallMessageDao,
                              PersonalMessageRepository personalMessageDao,
                              GroupRepository groupRepository) {
        this.groupMessageDao = groupMessageDao;
        this.accountWallMessageDao = accountWallMessageDao;
        this.personalMessageDao = personalMessageDao;
        this.groupRepository = groupRepository;
    }

    @Transactional
    @Override
    public void createGroupMessage(GroupMessage groupMessage, Long groupId) {
        groupMessage.setGroup(groupRepository.getById(groupId).get());
        groupMessageDao.save(groupMessage);
    }

    @Transactional
    @Override
    public void createPersonalWallMessage(PersonalWallMessage personalWallMessage) {
        accountWallMessageDao.save(personalWallMessage);
    }

    @Transactional
    @Override
    public void createPersonalMessage(PersonalMessage personalMessage) {
        personalMessageDao.save(personalMessage);
    }

    @Override
    public boolean deleteById(Long id) {
        return false;
    }

    @Override
    public Message getGroupMessageById(Long id) {
        if (groupMessageDao.getById(id).isPresent()) {
            return groupMessageDao.getById(id).get();
        }
        return null;
    }

    @Override
    public Message getAccountWallMessageById(Long accountId) {
        if (accountWallMessageDao.getById(accountId).isPresent()) {
            return accountWallMessageDao.getById(accountId).get();
        }
        return null;
    }

    @Override
    public Message getPersonalMessageById(Long messageId) {
        if (personalMessageDao.getById(messageId).isPresent()) {
            return personalMessageDao.getById(messageId).get();
        }
        return null;
    }

    @Override
    public List<GroupMessage> getMessagesByGroupId(Long groupId) {
        return groupMessageDao.getMessagesByGroupId(groupId);
    }

    @Override
    public List<PersonalWallMessage> getAllAccountWallMessages(Long destinationId) {
        return accountWallMessageDao.getAll(destinationId);
    }

    @Override
    public List<PersonalWallMessage> getNewsFeed(Long destinationId) {
        return accountWallMessageDao.getNewsFeed(destinationId);
    }

    @Override
    public List<Account> getAllAccountsWithPersonalMessages(Long accountId) {
        return personalMessageDao.getAllAccounts(accountId);
    }

    @Override
    public List<PersonalMessage> getAllPersonalMessagesWithAccount(Long authorId, Long receiverId) {
        return personalMessageDao.getAllPersonalMessagesWithAccount(authorId, receiverId);
    }

}
