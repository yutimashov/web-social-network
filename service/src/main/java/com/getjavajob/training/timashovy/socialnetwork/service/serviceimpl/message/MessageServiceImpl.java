package com.getjavajob.training.timashovy.socialnetwork.service.serviceimpl.message;

import com.getjavajob.training.timashovy.socialnetwork.common.message.Message;
import com.getjavajob.training.timashovy.socialnetwork.dao.daoimpl.message.GroupMessageDaoImpl;
import com.getjavajob.training.timashovy.socialnetwork.dao.interfaces.MessageDao;
import com.getjavajob.training.timashovy.socialnetwork.service.interfaces.MessageService;

import java.util.List;

public class MessageServiceImpl implements MessageService {

    private final MessageDao messageDao = GroupMessageDaoImpl.getInstance();

    private static final MessageServiceImpl MESSAGE_SERVICE = new MessageServiceImpl();

    private MessageServiceImpl() {
    }

    public static MessageServiceImpl getInstance() {
        return MESSAGE_SERVICE;
    }

    @Override
    public Long create(Message message) {
        return messageDao.create(message);
    }

    @Override
    public boolean deleteById(Long id) {
        return false;
    }

    @Override
    public Message getById(Long id) {
        if (messageDao.getById(id).isPresent()) {
            return messageDao.getById(id).get();
        }
        return null;
    }

    @Override
    public List<Message> getAll(Long groupId) {
        return messageDao.getAll(groupId);
    }

}
