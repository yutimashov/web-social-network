package com.getjavajob.training.timashovy.socialnetwork.service.serviceimpl.group;

import com.getjavajob.training.timashovy.socialnetwork.dao.daoimpl.group.GroupAvatarDaoImpl;
import com.getjavajob.training.timashovy.socialnetwork.service.interfaces.ImageService;

import java.io.InputStream;

public class GroupAvatarServiceImpl implements ImageService {

    private final GroupAvatarDaoImpl avatarDao;

    private GroupAvatarServiceImpl(GroupAvatarDaoImpl avatarDao) {
        this.avatarDao = avatarDao;
    }

    public static GroupAvatarServiceImpl createInstance(GroupAvatarDaoImpl avatarDao) {
        return new GroupAvatarServiceImpl(avatarDao);
    }

    @Override
    public boolean create(Long id, InputStream imageInputStream) {
        return avatarDao.upload(id, imageInputStream);
    }

    @Override
    public boolean delete(Long id) {
        return false;
    }

    @Override
    public InputStream get(Long id) {
        return avatarDao.get(id);
    }

    @Override
    public boolean update(Long id, InputStream imageInputStream) {
        return avatarDao.update(id, imageInputStream);
    }

}
