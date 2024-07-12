package com.getjavajob.training.timashovy.socialnetwork.dao.daoimpl.friendship;

import com.getjavajob.training.timashovy.socialnetwork.dao.interfaces.friendship.FriendshipDao;
import com.getjavajob.training.timashovy.socialnetwork.dao.util.exceptions.DaoException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static com.getjavajob.training.timashovy.socialnetwork.dao.util.dbutils.TableNames.FRIENDSHIP_TABLE;
import static com.getjavajob.training.timashovy.socialnetwork.dao.util.dbutils.dbconnection.ConnectionManager.getConnection;
import static com.getjavajob.training.timashovy.socialnetwork.dao.util.dbutils.fieldsnames.FriendshipTableFields.*;

/**
 * Singleton class responsible for working with `account_data.friendship` table in DB.
 * It provides safe multithreading approach for creating singleton object using synchronization mechanism.
 */
public class FriendshipDaoImpl implements FriendshipDao {

    private static final String ACCEPT_REQUEST = "UPDATE " + FRIENDSHIP_TABLE + " SET " + FRIENDSHIP_STATUS + " = "
            + "TRUE WHERE " + FRIENDSHIP_ACCEPTER_ID + " = ? AND " + FRIENDSHIP_REQUESTER_ID + " = ?;";
    private static final String GET_FRIENDS = "SELECT " + FRIENDSHIP_ACCOUNT_ID_1 + " FROM " + FRIENDSHIP_TABLE
            + " WHERE " + FRIENDSHIP_ACCOUNT_ID_2 + " = ? AND " + FRIENDSHIP_STATUS + " = TRUE UNION SELECT "
            + FRIENDSHIP_ACCOUNT_ID_2 + " FROM " + FRIENDSHIP_TABLE + " WHERE " + FRIENDSHIP_ACCOUNT_ID_1 + " = ? AND "
            + FRIENDSHIP_STATUS + " = TRUE;";
    private static final String DELETE_FRIEND = "DELETE FROM " + FRIENDSHIP_TABLE + " WHERE " + FRIENDSHIP_ACCOUNT_ID_1
            + " = ? AND " + FRIENDSHIP_ACCOUNT_ID_2 + " = ?;";
    private static final String SEND_REQUEST = "INSERT INTO " + FRIENDSHIP_TABLE + " (" + FRIENDSHIP_ACCOUNT_ID_1
            + ", " + FRIENDSHIP_ACCOUNT_ID_2 + ", " + FRIENDSHIP_REQUESTER_ID + ", " + FRIENDSHIP_ACCEPTER_ID + ") "
            + "VALUES(?, ?, ?, ?);";
    private static final String GET_INCOMING_REQUESTS = "SELECT " + FRIENDSHIP_REQUESTER_ID + " FROM "
            + FRIENDSHIP_TABLE + " WHERE " + FRIENDSHIP_STATUS + " = FALSE AND " + FRIENDSHIP_ACCEPTER_ID + " = ?;";
    private static final String GET_OUTGOING_REQUESTS = "SELECT " + FRIENDSHIP_ACCEPTER_ID + " FROM "
            + FRIENDSHIP_TABLE + " WHERE " + FRIENDSHIP_STATUS + " = FALSE AND " + FRIENDSHIP_REQUESTER_ID + " = ?;";

    /**
     * Add a new record to `friend_data.friendship` table
     * with default status of friendship (false)
     * Note: there is a constraint on db level:
     * account cannot send friend request to themselves
     *
     * @param requesterId id of account, who send request
     * @param accepterId  id of account, who gets request
     * @return status of friend request delivery
     */
    @Override
    public boolean sendRequest(Long requesterId, Long accepterId) {
        try (Connection connection = getConnection();
             PreparedStatement friendshipRequest = connection.prepareStatement(SEND_REQUEST)) {
            if (requesterId < accepterId) {
                friendshipRequest.setLong(1, requesterId);
                friendshipRequest.setLong(2, accepterId);
                friendshipRequest.setLong(3, requesterId);
                friendshipRequest.setLong(4, accepterId);
            } else {
                friendshipRequest.setLong(1, accepterId);
                friendshipRequest.setLong(2, requesterId);
                friendshipRequest.setLong(3, requesterId);
                friendshipRequest.setLong(4, accepterId);
            }
            return friendshipRequest.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new DaoException("dao: sendFriendshipRequest method failed: " + e.getMessage());
        }
    }

    @Override
    public boolean acceptRequest(Long requesterId, Long accepterId) {
        try (Connection connection = getConnection();
             PreparedStatement acceptFriendRequest = connection.prepareStatement(ACCEPT_REQUEST)) {
            acceptFriendRequest.setLong(1, accepterId);
            acceptFriendRequest.setLong(2, requesterId);
            return acceptFriendRequest.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new DaoException("dao: acceptFriendRequest method failed: " + e.getMessage());
        }
    }

    /**
     * Return ArrayList with ids of account's friends
     *
     * @param accountId id of account we want to get friends
     * @return list of account friends' ids
     */
    @Override
    public List<Long> getFriendsIds(Long accountId) {
        try (Connection connection = getConnection();
             PreparedStatement getFriends = connection.prepareStatement(GET_FRIENDS)) {
            List<Long> friends = new ArrayList<>();
            getFriends.setLong(1, accountId);
            getFriends.setLong(2, accountId);
            ResultSet friendsResult = getFriends.executeQuery();
            while (friendsResult.next()) {
                friends.add(friendsResult.getLong(1));
            }
            return friends;
        } catch (SQLException e) {
            throw new DaoException("dao: getFriendsIds method failed: " + e.getMessage());
        }
    }

    @Override
    public List<Long> getIncomingRequests(Long accountId) {
        try (Connection connection = getConnection();
             PreparedStatement incomingFriendRequests = connection.prepareStatement(GET_INCOMING_REQUESTS)) {
            return getRequests(accountId, incomingFriendRequests);
        } catch (SQLException e) {
            throw new DaoException("dao: getIncomingFriendRequests method failed: " + e.getMessage());
        }
    }

    private List<Long> getRequests(Long accountId, PreparedStatement preparedStatement) throws SQLException {
        List<Long> friendRequests = new ArrayList<>();
        preparedStatement.setLong(1, accountId);
        ResultSet friendRequestsResult = preparedStatement.executeQuery();
        while (friendRequestsResult.next()) {
            friendRequests.add(friendRequestsResult.getLong(1));
        }
        return friendRequests;
    }

    @Override
    public List<Long> getOutgoingRequests(Long accountId) {
        try (Connection connection = getConnection();
             PreparedStatement outgoingFriendRequests = connection.prepareStatement(GET_OUTGOING_REQUESTS)) {
            return getRequests(accountId, outgoingFriendRequests);
        } catch (SQLException e) {
            throw new DaoException("dao: getOutgoingFriendRequests method failed: " + e.getMessage());
        }
    }

    /**
     * Remove a corresponding record from `friend_data.friendship` table
     *
     * @param accountId        id of account who will delete friend
     * @param deletingFriendId id of friend account who will be deleted
     * @return status of friend deletion
     */
    @Override
    public boolean deleteFriend(Long accountId, Long deletingFriendId) {
        try (Connection connection = getConnection();
             PreparedStatement deleteFriend = connection.prepareStatement(DELETE_FRIEND)) {
            if (accountId < deletingFriendId) {
                deleteFriend.setLong(1, accountId);
                deleteFriend.setLong(2, deletingFriendId);
            } else {
                deleteFriend.setLong(1, deletingFriendId);
                deleteFriend.setLong(2, accountId);
            }
            return deleteFriend.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new DaoException("dao: delete friend method failed: " + e.getMessage());
        }
    }

}
