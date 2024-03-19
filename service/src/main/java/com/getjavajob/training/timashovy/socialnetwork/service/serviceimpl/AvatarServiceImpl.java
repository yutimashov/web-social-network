package com.getjavajob.training.timashovy.socialnetwork.service.serviceimpl;

import com.getjavajob.training.timashovy.socialnetwork.dao.daoimpl.account.AvatarDaoImpl;
import com.getjavajob.training.timashovy.socialnetwork.service.interfaces.ImageService;

import java.io.InputStream;

import static com.getjavajob.training.timashovy.socialnetwork.dao.daoimpl.account.AvatarDaoImpl.getAvatarDaoImpl;

public class AvatarServiceImpl implements ImageService {

    private static final AvatarServiceImpl AVATAR_SERVICE = new AvatarServiceImpl();
    private final AvatarDaoImpl avatarDao = getAvatarDaoImpl();

    private AvatarServiceImpl() {
    }

    public static AvatarServiceImpl getAvatarServiceInstance() {
        return AVATAR_SERVICE;
    }

    @Override
    public boolean upload(Long accountId, InputStream avatarInputStream) {
        return avatarDao.upload(accountId, avatarInputStream);
    }

    @Override
    public boolean delete(Long id) {
        return false;
    }

    @Override
    public InputStream get(Long accountId) {
        return avatarDao.get(accountId);
    }

    @Override
    public boolean update(Long accountId, InputStream imageInputStream) {
        return avatarDao.update(accountId, imageInputStream);
    }

}
