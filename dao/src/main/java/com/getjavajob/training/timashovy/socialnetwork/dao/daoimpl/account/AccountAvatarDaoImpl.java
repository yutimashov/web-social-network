package com.getjavajob.training.timashovy.socialnetwork.dao.daoimpl.account;

import com.getjavajob.training.timashovy.socialnetwork.dao.interfaces.ImageDao;
import com.getjavajob.training.timashovy.socialnetwork.dao.util.DaoException;

import java.io.InputStream;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import static com.getjavajob.training.timashovy.socialnetwork.dao.util.dbutils.TableNames.ACCOUNT_AVATARS_TABLE;
import static com.getjavajob.training.timashovy.socialnetwork.dao.util.dbutils.dbconnection.ConnectionManager.getPreparedStatement;

public class AccountAvatarDaoImpl implements ImageDao {

    private static final AccountAvatarDaoImpl IMAGE_DAO_IMPL = new AccountAvatarDaoImpl();
    private static final String UPLOAD_ACCOUNT_AVATAR = "INSERT INTO " + ACCOUNT_AVATARS_TABLE + " (account_id,"
            + " avatar_blob) VALUES (?, ?)";
    private static final String GET_ACCOUNT_AVATAR = "SELECT avatar_blob FROM " + ACCOUNT_AVATARS_TABLE + " WHERE "
            + "account_id = ?;";
    private static final String UPDATE_ACCOUNT_AVATAR = "UPDATE " + ACCOUNT_AVATARS_TABLE + " SET avatar_blob = ? "
            + "WHERE account_id = ?;";

    private AccountAvatarDaoImpl() {
    }

    public static AccountAvatarDaoImpl getInstance() {
        return IMAGE_DAO_IMPL;
    }

    @Override
    public boolean upload(Long accountId, InputStream imageInputStream) {
        try (PreparedStatement preparedStatement = getPreparedStatement(UPLOAD_ACCOUNT_AVATAR)) {
            preparedStatement.setLong(1, accountId);
            preparedStatement.setBinaryStream(2, imageInputStream);
            return preparedStatement.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public InputStream get(Long accountId) {
        try (PreparedStatement getAvatarStatement = getPreparedStatement(GET_ACCOUNT_AVATAR)) {
            getAvatarStatement.setLong(1, accountId);
            ResultSet avatarRecord = getAvatarStatement.executeQuery();
            if (avatarRecord.next()) {
                return avatarRecord.getBinaryStream("avatar_blob");
            } else {
                //TODO: avoid return null, use `Optional` instead
                return null;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean delete(Long imageId) {
        return false;
    }

    @Override
    public boolean update(Long accountId, InputStream imageInputStream) {
        try (PreparedStatement updateByIdStatement = getPreparedStatement(UPDATE_ACCOUNT_AVATAR)) {
            updateByIdStatement.setBinaryStream(1, imageInputStream);
            updateByIdStatement.setLong(2, accountId);
            return updateByIdStatement.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new DaoException("dao: update account avatar method failed: ", e);
        }
    }

}
