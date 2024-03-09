package com.getjavajob.training.timashovy.socialnetwork.service.serviceimpl;

import com.getjavajob.training.timashovy.socialnetwork.common.Account;

import java.io.InputStream;

import static com.getjavajob.training.timashovy.socialnetwork.dao.daoimpl.AvatarDaoImpl.getAvatarDaoImpl;

public class AvatarServiceImpl {

    private static final AvatarServiceImpl AVATAR_SERVICE = new AvatarServiceImpl();

    private AvatarServiceImpl() {
    }

    public static AvatarServiceImpl getAvatarService() {
        return AVATAR_SERVICE;
    }

    public boolean uploadAvatar(Account account, InputStream avatarInputStream) {
        return getAvatarDaoImpl().upload(account, avatarInputStream);
    }

}
