package com.getjavajob.training.timashovy.socialnetwork.service.serviceimpl.message;

import com.getjavajob.training.timashovy.socialnetwork.common.account.Account;
import com.getjavajob.training.timashovy.socialnetwork.common.message.Message;
import com.getjavajob.training.timashovy.socialnetwork.dao.daoimpl.message.PersonalMessageDaoImpl;
import com.getjavajob.training.timashovy.socialnetwork.dao.interfaces.MessageDao;
import com.getjavajob.training.timashovy.socialnetwork.service.interfaces.AccountService;
import com.getjavajob.training.timashovy.socialnetwork.service.interfaces.MessageService;

import java.util.ArrayList;
import java.util.List;

public class MessageServiceImpl implements MessageService {

    private final MessageDao groupMessageDao;
    private final MessageDao accountWallMessageDao;
    private final PersonalMessageDaoImpl personalMessageDao;
    private final AccountService accountService;

    public MessageServiceImpl(MessageDao groupMessageDao, MessageDao accountWallMessageDao,
                              PersonalMessageDaoImpl personalMessageDao, AccountService accountService) {
        this.groupMessageDao = groupMessageDao;
        this.accountWallMessageDao = accountWallMessageDao;
        this.personalMessageDao = personalMessageDao;
        this.accountService = accountService;
    }

    @Override
    public Long createGroupMessage(Message message) {
        return groupMessageDao.create(message);
    }

    @Override
    public Long createPersonalWallMessage(Message message) {
        return accountWallMessageDao.create(message);
    }

    @Override
    public Long createPersonalMessage(Message message) {
        return personalMessageDao.create(message);
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
    public List<Message> getAllGroupMessages(Long groupId) {
        return groupMessageDao.getAll(groupId);
    }

    @Override
    public List<Message> getAllAccountWallMessages(Long destinationId) {
        return accountWallMessageDao.getAll(destinationId);
    }

    @Override
    public List<Account> getAllAccountsWithPersonalMessages(Long accountId) {
        List<Long> personalMessageAccountsIds = personalMessageDao.getAllAccountsIds(accountId);
        List<Account> accounts = new ArrayList<>();
        for (Long personalMessageAccountsId : personalMessageAccountsIds) {
            if (accountService.getById(personalMessageAccountsId).isPresent()) {
                accounts.add(accountService.getById(personalMessageAccountsId).get());
            }
        }
        return accounts;
    }

    @Override
    public List<Message> getAllPersonalMessagesWithAccount(Long authorId, Long receiverId) {
        return personalMessageDao.getAllPersonalMessagesWithAccount(authorId, receiverId);
    }

}
