package com.getjavajob.training.timashovy.socialnetwork.dao.daoimpl.friendship;

import com.getjavajob.training.timashovy.socialnetwork.dao.interfaces.friendship.FriendshipDao;
import com.getjavajob.training.timashovy.socialnetwork.dao.util.DaoException;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static com.getjavajob.training.timashovy.socialnetwork.dao.util.dbutils.TableNames.FRIENDSHIP_TABLE;
import static com.getjavajob.training.timashovy.socialnetwork.dao.util.dbutils.dbconnection.ConnectionManager.getPreparedStatement;

public class FriendshipDaoImpl implements FriendshipDao {

    private static final FriendshipDaoImpl FRIENDSHIP_DAO_INSTANCE = new FriendshipDaoImpl();
    private static final String ACCEPT_REQUEST = "UPDATE " + FRIENDSHIP_TABLE + " SET status = TRUE " +
            "WHERE accepter_id = ? AND requester_id = ?;";
    private static final String GET_FRIENDS = "SELECT id_1 FROM " + FRIENDSHIP_TABLE + " WHERE id_2 = ? "
            + "AND status = TRUE UNION SELECT id_2 FROM " + FRIENDSHIP_TABLE + " WHERE id_1 = ? AND status = TRUE;";
    private static final String DELETE_FRIEND = "DELETE FROM " + FRIENDSHIP_TABLE + " WHERE id_1 = ? AND id_2 = ?;";
    private static final String SEND_REQUEST = "INSERT INTO " + FRIENDSHIP_TABLE + " (id_1, id_2, requester_id, " +
            "accepter_id) VALUES(?, ?, ?, ?);";
    private static final String GET_INCOMING_REQUESTS = "SELECT requester_id FROM " + FRIENDSHIP_TABLE
            + " WHERE status = FALSE AND accepter_id = ?;";
    private static final String GET_OUTGOING_REQUESTS = "SELECT accepter_id FROM " + FRIENDSHIP_TABLE
            + " WHERE status = FALSE AND requester_id = ?;";

    private FriendshipDaoImpl() {
    }

    public static FriendshipDaoImpl createInstance() {
        return FRIENDSHIP_DAO_INSTANCE;
    }

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
        try (PreparedStatement friendshipRequest = getPreparedStatement(SEND_REQUEST)) {
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
        try (PreparedStatement acceptFriendRequest = getPreparedStatement(ACCEPT_REQUEST)) {
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
        try (PreparedStatement getFriends = getPreparedStatement(GET_FRIENDS)) {
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
        try (PreparedStatement incomingFriendRequests = getPreparedStatement(GET_INCOMING_REQUESTS)) {
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
        try (PreparedStatement outgoingFriendRequests = getPreparedStatement(GET_OUTGOING_REQUESTS)) {
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
        try (PreparedStatement deleteFriend = getPreparedStatement(DELETE_FRIEND)) {
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
