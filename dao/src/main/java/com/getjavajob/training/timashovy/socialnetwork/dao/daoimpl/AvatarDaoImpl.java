package com.getjavajob.training.timashovy.socialnetwork.dao.daoimpl;

import com.getjavajob.training.timashovy.socialnetwork.common.Account;
import com.getjavajob.training.timashovy.socialnetwork.dao.interfaces.ImageDao;

import java.io.InputStream;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import static com.getjavajob.training.timashovy.socialnetwork.dao.util.dbconnection.ConnectionManager.getPreparedStatement;

public class AvatarDaoImpl implements ImageDao<Account> {

    private static final AvatarDaoImpl IMAGE_DAO_IMPL = new AvatarDaoImpl();

    private static final String UPLOAD_ACCOUNT_AVATAR = "INSERT INTO account_data.account_avatars (account_id,"
            + " avatar_blob) VALUES (?, ?)";

    private AvatarDaoImpl() {
    }

    public static AvatarDaoImpl getAvatarDaoImpl() {
        return IMAGE_DAO_IMPL;
    }

    @Override
    public boolean upload(Account account, InputStream imageInputStream) {
        try (PreparedStatement preparedStatement = getPreparedStatement(UPLOAD_ACCOUNT_AVATAR)) {
            preparedStatement.setLong(1, account.getId());
            preparedStatement.setBinaryStream(2, imageInputStream);
            return preparedStatement.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean delete(Long imageId) {
        return false;
    }

    @Override
    public boolean update(Long imageId) {
        return false;
    }

}
