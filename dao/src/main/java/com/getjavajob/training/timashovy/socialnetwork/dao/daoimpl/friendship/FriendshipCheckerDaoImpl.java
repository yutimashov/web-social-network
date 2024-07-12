package com.getjavajob.training.timashovy.socialnetwork.dao.daoimpl.friendship;

import com.getjavajob.training.timashovy.socialnetwork.dao.interfaces.friendship.FriendshipCheckerDao;
import com.getjavajob.training.timashovy.socialnetwork.dao.util.exceptions.DaoException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import static com.getjavajob.training.timashovy.socialnetwork.dao.util.dbutils.TableNames.FRIENDSHIP_TABLE;
import static com.getjavajob.training.timashovy.socialnetwork.dao.util.dbutils.dbconnection.ConnectionManager.getConnection;
import static com.getjavajob.training.timashovy.socialnetwork.dao.util.dbutils.fieldsnames.FriendshipTableFields.*;

/**
 * Singleton class responsible for working with `account_data.friendship` table in DB.
 * It provides safe multithreading approach for creating singleton object using synchronization mechanism.
 */
public class FriendshipCheckerDaoImpl implements FriendshipCheckerDao {

    private static final String FRIENDSHIP_RECORD_EXISTENCE = "SELECT 1 FROM " + FRIENDSHIP_TABLE + " WHERE "
            + FRIENDSHIP_ACCOUNT_ID_1 + " = ? AND " + FRIENDSHIP_ACCOUNT_ID_2 + " = ?;";
    private static final String ARE_USERS_FRIENDS = "SELECT 1 FROM " + FRIENDSHIP_TABLE + " WHERE "
            + FRIENDSHIP_ACCOUNT_ID_1 + " = ? AND " + FRIENDSHIP_ACCOUNT_ID_2 + " = ? AND " + FRIENDSHIP_STATUS
            + " = TRUE;";

    @Override
    public boolean checkFriendshipRecordExistence(Long requesterId, Long accepterId) {
        try (Connection connection = getConnection();
             PreparedStatement friendshipExistence = connection.prepareStatement(FRIENDSHIP_RECORD_EXISTENCE)) {
            return verifyOrderOfAccountIdsInQuery(requesterId, accepterId, friendshipExistence);
        } catch (SQLException e) {
            throw new DaoException("dao: friendship record existence method failed: " + e.getMessage());
        }
    }

    private boolean verifyOrderOfAccountIdsInQuery(Long requesterId, Long accepterId, PreparedStatement checkFriends)
            throws SQLException {
        if (requesterId < accepterId) {
            checkFriends.setLong(1, requesterId);
            checkFriends.setLong(2, accepterId);
        } else {
            checkFriends.setLong(1, accepterId);
            checkFriends.setLong(2, requesterId);
        }
        return checkFriends.executeQuery().next();
    }

    @Override
    public boolean checkUsersAreFriends(Long requesterId, Long accepterId) {
        try (Connection connection = getConnection();
             PreparedStatement checkFriends = connection.prepareStatement(ARE_USERS_FRIENDS)) {
            return verifyOrderOfAccountIdsInQuery(requesterId, accepterId, checkFriends);
        } catch (SQLException e) {
            throw new DaoException("dao: areUsersFriends method failed: " + e.getMessage());
        }
    }

}
