package com.getjavajob.training.timashovy.socialnetwork.service.serviceimpl;

import com.getjavajob.training.timashovy.socialnetwork.dao.daoimpl.group.GroupAvatarDaoImpl;
import com.getjavajob.training.timashovy.socialnetwork.service.interfaces.ImageService;

import java.io.InputStream;

public class GroupAvatarServiceImpl implements ImageService {

    private static final GroupAvatarServiceImpl GROUP_AVATAR_SERVICE = new GroupAvatarServiceImpl();
    private final GroupAvatarDaoImpl avatarDao = GroupAvatarDaoImpl.getInstance();

    private GroupAvatarServiceImpl() {
    }

    public static GroupAvatarServiceImpl getInstance() {
        return GROUP_AVATAR_SERVICE;
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
