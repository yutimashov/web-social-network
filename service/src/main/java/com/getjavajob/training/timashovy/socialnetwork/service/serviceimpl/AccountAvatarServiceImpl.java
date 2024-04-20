package com.getjavajob.training.timashovy.socialnetwork.service.serviceimpl;

import com.getjavajob.training.timashovy.socialnetwork.dao.daoimpl.account.AccountAvatarDaoImpl;
import com.getjavajob.training.timashovy.socialnetwork.service.interfaces.ImageService;

import java.io.InputStream;

public class AccountAvatarServiceImpl implements ImageService {

    private static final AccountAvatarServiceImpl AVATAR_SERVICE = new AccountAvatarServiceImpl();
    private final AccountAvatarDaoImpl avatarDao = AccountAvatarDaoImpl.getInstance();

    private AccountAvatarServiceImpl() {
    }

    public static AccountAvatarServiceImpl getInstance() {
        return AVATAR_SERVICE;
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
    public boolean update(Long accountId, InputStream imageInputStream) {
        return avatarDao.update(accountId, imageInputStream);
    }

}
