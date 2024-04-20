package com.getjavajob.training.timashovy.socialnetwork.service.serviceimpl;

import com.getjavajob.training.timashovy.socialnetwork.dao.daoimpl.group.GroupAvatarDaoImpl;
import com.getjavajob.training.timashovy.socialnetwork.service.interfaces.ImageService;

import java.io.InputStream;

import static com.getjavajob.training.timashovy.socialnetwork.dao.daoimpl.group.GroupAvatarDaoImpl.getInstance;

public class GroupAvatarServiceImpl implements ImageService {

    private static final GroupAvatarServiceImpl GROUP_AVATAR_SERVICE = new GroupAvatarServiceImpl();
    private final GroupAvatarDaoImpl avatarDao = getInstance();

    private GroupAvatarServiceImpl() {
    }

    public static GroupAvatarServiceImpl getGroupAvatarServiceInstance() {
        return GROUP_AVATAR_SERVICE;
    }

    @Override
    public boolean create(Long id, InputStream avatarInputStream) {
        return avatarDao.upload(id, avatarInputStream);
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
