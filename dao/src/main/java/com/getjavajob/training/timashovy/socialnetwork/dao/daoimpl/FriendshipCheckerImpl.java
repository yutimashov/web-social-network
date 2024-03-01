package com.getjavajob.training.timashovy.socialnetwork.dao.daoimpl;

import com.getjavajob.training.timashovy.socialnetwork.dao.interfaces.FriendshipChecker;
import com.getjavajob.training.timashovy.socialnetwork.dao.util.exceptions.DaoException;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import static com.getjavajob.training.timashovy.socialnetwork.dao.util.dbconnection.ConnectionManager.getPreparedStatement;

public class FriendshipCheckerImpl implements FriendshipChecker {

    private static final FriendshipCheckerImpl FRIENDSHIP_CHECKER_INSTANCE = new FriendshipCheckerImpl();
    private static final String FRIENDSHIP_RECORD_EXISTENCE = "SELECT 1 FROM friend_data.friendship WHERE id_1 = ? "
            + "AND id_2 = ?;";
    private static final String ARE_USERS_FRIENDS = "SELECT 1 FROM friend_data.friendship WHERE id_1 = ? AND id_2 = ? "
            + "AND status = TRUE;";
    private static final String REQUESTERS_EQUALITY = "SELECT 1 FROM friend_data.friendship WHERE ? = requester_id "
            + "AND ? = accepter_id;";

    private FriendshipCheckerImpl() {
    }

    public static FriendshipCheckerImpl getFriendshipCheckerInstance() {
        return FRIENDSHIP_CHECKER_INSTANCE;
    }

    @Override
    public boolean checkFriendshipRecordExistence(Long requesterId, Long accepterId) {
        try (PreparedStatement friendshipExistence = getPreparedStatement(FRIENDSHIP_RECORD_EXISTENCE)) {
            return followConstraintReqIdIsLessThanAcceptId(requesterId, accepterId, friendshipExistence);
        } catch (SQLException e) {
            throw new DaoException("dao: friendship record existence method failed: " + e.getMessage());
        }
    }

    @Override
    public boolean checkUsersAreFriends(Long requesterId, Long accepterId) {
        try (PreparedStatement checkFriends = getPreparedStatement(ARE_USERS_FRIENDS)) {
            return followConstraintReqIdIsLessThanAcceptId(requesterId, accepterId, checkFriends);
        } catch (SQLException e) {
            throw new DaoException("dao: areUsersFriends method failed: " + e.getMessage());
        }
    }

    private boolean followConstraintReqIdIsLessThanAcceptId(Long requesterId, Long accepterId,
                                                            PreparedStatement checkFriends) throws SQLException {
        if (requesterId < accepterId) {
            checkFriends.setLong(1, requesterId);
            checkFriends.setLong(2, accepterId);
        } else {
            checkFriends.setLong(1, accepterId);
            checkFriends.setLong(2, requesterId);
        }
        ResultSet resultSet = checkFriends.executeQuery();
        return resultSet.next();
    }

    @Override
    public boolean checkFriendRequestAlreadyExist(Long requesterId, Long accepterId) {
        try (PreparedStatement checkFriendRequest = getPreparedStatement(REQUESTERS_EQUALITY)) {
            return followConstraintReqIdIsLessThanAcceptId(requesterId, accepterId, checkFriendRequest);
        } catch (SQLException e) {
            throw new DaoException("dao: checkFriendRequestAlreadyExist method failed: " + e.getMessage());
        }
    }

}
