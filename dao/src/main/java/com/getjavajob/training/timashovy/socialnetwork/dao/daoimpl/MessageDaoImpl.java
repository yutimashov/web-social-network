package com.getjavajob.training.timashovy.socialnetwork.dao.daoimpl;

import com.getjavajob.training.timashovy.socialnetwork.common.message.Message;
import com.getjavajob.training.timashovy.socialnetwork.dao.interfaces.BaseDao;

import java.util.List;
import java.util.Optional;

public class MessageDaoImpl implements BaseDao<Message> {

    private static final MessageDaoImpl MESSAGE_DAO = new MessageDaoImpl();

    public static MessageDaoImpl getInstance() {
        return MESSAGE_DAO;
    }

    private MessageDaoImpl() {
    }

    @Override
    public Long create(Message message) {
        return null;
    }

    @Override
    public Optional<Message> getById(Long id) {
        return Optional.empty();
    }

    @Override
    public List<Message> getAll() {
        return null;
    }

    @Override
    public boolean updateById(Long id, Message message) {
        return false;
    }

    @Override
    public boolean deleteById(Long id) {
        return false;
    }

}
