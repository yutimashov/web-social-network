package com.getjavajob.training.timashovy.socialnetwork.service.serviceimpl;

import com.getjavajob.training.timashovy.socialnetwork.dao.daoimpl.account.AccountAvatarDaoImpl;
import com.getjavajob.training.timashovy.socialnetwork.service.interfaces.ImageService;

import java.io.InputStream;

import static com.getjavajob.training.timashovy.socialnetwork.dao.daoimpl.account.AccountAvatarDaoImpl.getAvatarDaoImpl;

public class AccountAvatarServiceImpl implements ImageService {

    private static final AccountAvatarServiceImpl AVATAR_SERVICE = new AccountAvatarServiceImpl();
    private final AccountAvatarDaoImpl avatarDao = getAvatarDaoImpl();

    private AccountAvatarServiceImpl() {
    }

    public static AccountAvatarServiceImpl getAccountAvatarServiceInstance() {
        return AVATAR_SERVICE;
    }

    @Override
    public boolean upload(Long id, InputStream avatarInputStream) {
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
