package com.getjavajob.training.timashovy.socialnetwork.service.serviceimpl.message;

import com.getjavajob.training.timashovy.socialnetwork.common.message.MessageImage;
import com.getjavajob.training.timashovy.socialnetwork.dao.daoimpl.message.MessageImageDaoImpl;
import com.getjavajob.training.timashovy.socialnetwork.service.interfaces.MessageImageService;

public class MessageImageServiceImpl implements MessageImageService {

    private final MessageImageDaoImpl imageDao = MessageImageDaoImpl.getInstance();

    private static final MessageImageServiceImpl MESSAGE_IMAGE_SERVICE = new MessageImageServiceImpl();

    private MessageImageServiceImpl() {
    }

    public static MessageImageServiceImpl getInstance() {
        return MESSAGE_IMAGE_SERVICE;
    }

    @Override
    public Long create(MessageImage messageImage) {
        return imageDao.create(messageImage);
    }

}
