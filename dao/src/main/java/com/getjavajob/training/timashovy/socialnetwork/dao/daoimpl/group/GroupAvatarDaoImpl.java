package com.getjavajob.training.timashovy.socialnetwork.dao.daoimpl.group;

import com.getjavajob.training.timashovy.socialnetwork.dao.interfaces.ImageDao;
import com.getjavajob.training.timashovy.socialnetwork.dao.util.DaoException;

import java.io.InputStream;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import static com.getjavajob.training.timashovy.socialnetwork.dao.util.dbutils.dbconnection.ConnectionManager.getPreparedStatement;

public final class GroupAvatarDaoImpl implements ImageDao {

    private static final GroupAvatarDaoImpl GROUP_AVATAR_DAO = new GroupAvatarDaoImpl();
    private static final String UPLOAD_GROUP_AVATAR = "INSERT INTO group_data.group_avatars (group_id,"
            + " avatar_blob) VALUES (?, ?)";
    private static final String GET_GROUP_AVATAR = "SELECT avatar_blob FROM group_data.group_avatars WHERE "
            + "group_id = ?;";
    private static final String UPDATE_GROUP_AVATAR = "UPDATE group_data.group_avatars SET avatar_blob = ? "
            + "WHERE group_id = ?;";

    private GroupAvatarDaoImpl() {
    }

    public static GroupAvatarDaoImpl getInstance() {
        return GROUP_AVATAR_DAO;
    }

    @Override
    public boolean upload(Long id, InputStream imageInputStream) {
        try (PreparedStatement preparedStatement = getPreparedStatement(UPLOAD_GROUP_AVATAR)) {
            preparedStatement.setLong(1, id);
            preparedStatement.setBinaryStream(2, imageInputStream);
            return preparedStatement.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean delete(Long id) {
        return false;
    }

    @Override
    public InputStream get(Long id) {
        try (PreparedStatement getAvatarStatement = getPreparedStatement(GET_GROUP_AVATAR)) {
            getAvatarStatement.setLong(1, id);
            ResultSet avatarRecord = getAvatarStatement.executeQuery();
            if (avatarRecord.next()) {
                return avatarRecord.getBinaryStream("avatar_blob");
            } else {
                return null;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean update(Long id, InputStream imageInputStream) {
        try (PreparedStatement updateByIdStatement = getPreparedStatement(UPDATE_GROUP_AVATAR)) {
            updateByIdStatement.setBinaryStream(1, imageInputStream);
            updateByIdStatement.setLong(2, id);
            return updateByIdStatement.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new DaoException("dao: update group avatar method failed: ", e);
        }
    }

}
