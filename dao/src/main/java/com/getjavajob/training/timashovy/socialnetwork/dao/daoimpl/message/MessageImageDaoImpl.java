package com.getjavajob.training.timashovy.socialnetwork.dao.daoimpl.message;

import com.getjavajob.training.timashovy.socialnetwork.common.message.MessageImage;
import com.getjavajob.training.timashovy.socialnetwork.dao.interfaces.BaseDao;

import java.util.List;
import java.util.Optional;

public class MessageImageDaoImpl implements BaseDao<MessageImage> {

    private static final MessageImageDaoImpl MESSAGE_IMAGE_DAO = new MessageImageDaoImpl();

    public static MessageImageDaoImpl getInstance() {
        return MESSAGE_IMAGE_DAO;
    }

    private MessageImageDaoImpl() {
    }

    @Override
    public Long create(MessageImage messageImage) {
        return null;
    }

    @Override
    public Optional<MessageImage> getById(Long id) {
        return Optional.empty();
    }

    @Override
    public List<MessageImage> getAll() {
        return null;
    }

    @Override
    public boolean updateById(Long id, MessageImage messageImage) {
        return false;
    }

    @Override
    public boolean deleteById(Long id) {
        return false;
    }

}
