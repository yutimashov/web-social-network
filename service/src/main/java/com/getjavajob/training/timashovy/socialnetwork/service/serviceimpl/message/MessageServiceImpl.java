package com.getjavajob.training.timashovy.socialnetwork.service.serviceimpl.message;

import com.getjavajob.training.timashovy.socialnetwork.dao.daoimpl.message.GroupMessageDaoImpl;
import com.getjavajob.training.timashovy.socialnetwork.dao.daoimpl.message.PersonalMessageDaoImpl;
import com.getjavajob.training.timashovy.socialnetwork.dao.daoimpl.message.PersonalWallMessageDaoImpl;
import com.getjavajob.training.timashovy.socialnetwork.dao.interfaces.BaseDao;
import com.getjavajob.training.timashovy.socialnetwork.domain.account.Account;
import com.getjavajob.training.timashovy.socialnetwork.domain.group.Group;
import com.getjavajob.training.timashovy.socialnetwork.domain.message.GroupMessage;
import com.getjavajob.training.timashovy.socialnetwork.domain.message.Message;
import com.getjavajob.training.timashovy.socialnetwork.domain.message.PersonalMessage;
import com.getjavajob.training.timashovy.socialnetwork.domain.message.PersonalWallMessage;
import com.getjavajob.training.timashovy.socialnetwork.service.interfaces.AccountService;
import com.getjavajob.training.timashovy.socialnetwork.service.interfaces.MessageService;
import org.slf4j.Logger;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.slf4j.LoggerFactory.getLogger;

public class MessageServiceImpl implements MessageService {

    private final GroupMessageDaoImpl groupMessageDao;
    private final PersonalWallMessageDaoImpl accountWallMessageDao;
    private final PersonalMessageDaoImpl personalMessageDao;
    private final AccountService accountService;
    private final BaseDao<Group> groupDao;
    private static final Logger logger = getLogger(MessageService.class);

    public MessageServiceImpl(GroupMessageDaoImpl groupMessageDao, PersonalWallMessageDaoImpl accountWallMessageDao,
                              PersonalMessageDaoImpl personalMessageDao, AccountService accountService,
                              BaseDao<Group> groupDao) {
        this.groupMessageDao = groupMessageDao;
        this.accountWallMessageDao = accountWallMessageDao;
        this.personalMessageDao = personalMessageDao;
        this.accountService = accountService;
        this.groupDao = groupDao;
    }

    @Transactional
    @Override
    public void createGroupMessage(GroupMessage groupMessage, Long groupId) {
        groupMessage.setGroup(groupDao.getById(groupId).get());
        groupMessageDao.create(groupMessage);
    }

    @Transactional
    @Override
    public void createPersonalWallMessage(PersonalWallMessage personalWallMessage) {
        logger.info("creating personal wall message in the wall of account with id = {}", personalWallMessage.getId());
        accountWallMessageDao.create(personalWallMessage);
    }

    @Transactional
    @Override
    public void createPersonalMessage(PersonalMessage personalMessage) {
        personalMessageDao.create(personalMessage);
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
    public List<GroupMessage> getAllGroupMessages(Long groupId) {
        return groupMessageDao.getAll(groupId);
    }

    @Override
    public List<PersonalWallMessage> getAllAccountWallMessages(Long destinationId) {
        return accountWallMessageDao.getAll(destinationId);
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
