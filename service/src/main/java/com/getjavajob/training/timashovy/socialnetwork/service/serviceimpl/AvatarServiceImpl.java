package com.getjavajob.training.timashovy.socialnetwork.service.serviceimpl;

import com.getjavajob.training.timashovy.socialnetwork.dao.daoimpl.account.AvatarDaoImpl;

import java.io.InputStream;

import static com.getjavajob.training.timashovy.socialnetwork.dao.daoimpl.account.AvatarDaoImpl.getAvatarDaoImpl;

public class AvatarServiceImpl {

    private static final AvatarServiceImpl AVATAR_SERVICE = new AvatarServiceImpl();
    private final AvatarDaoImpl avatarDao = getAvatarDaoImpl();

    private AvatarServiceImpl() {
    }

    public static AvatarServiceImpl getAvatarServiceInstance() {
        return AVATAR_SERVICE;
    }

    public boolean uploadAvatar(Long accountId, InputStream avatarInputStream) {
        return avatarDao.upload(accountId, avatarInputStream);
    }

    public InputStream getAvatar(Long accountId) {
        return avatarDao.get(accountId);
    }

    public boolean updateAvatar(Long accountId, InputStream imageInputStream) {
        return avatarDao.update(accountId, imageInputStream);
    }

}
