package com.getjavajob.training.timashovy.socialnetwork.dao.daoimpl;

import com.getjavajob.training.timashovy.socialnetwork.dao.interfaces.FriendshipDao;
import com.getjavajob.training.timashovy.socialnetwork.dao.util.exceptions.DaoException;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static com.getjavajob.training.timashovy.socialnetwork.dao.util.dbconnection.ConnectionManager.getPreparedStatement;

public class FriendshipDaoImpl implements FriendshipDao {

    private static final FriendshipDaoImpl FRIENDSHIP_DAO_INSTANCE = new FriendshipDaoImpl();
    private static final String ACCEPT_FRIEND_REQUEST = "UPDATE friend_data.friendship SET status = TRUE "
            + "WHERE accepter_id = ? AND requester_id = ?;";
    private static final String GET_FRIENDS = "SELECT id_1 FROM friend_data.friendship WHERE id_2 = ? "
            + "AND status = TRUE UNION SELECT id_2 FROM friend_data.friendship WHERE id_1 = ? AND status = TRUE;";
    private static final String DELETE_FRIEND = "DELETE FROM friend_data.friendship WHERE id_1 = ? AND id_2 = ?;";
    private static final String SEND_FRIEND_REQUEST = "INSERT INTO friend_data.friendship (id_1, id_2, requester_id, " +
            "accepter_id) VALUES(?, ?, ?, ?);";
    private static final String GET_INCOMING_FRIEND_REQUESTS = "SELECT requester_id FROM friend_data.friendship " +
            "WHERE status = FALSE AND accepter_id = ?;";

    private FriendshipDaoImpl() {
    }

    public static FriendshipDaoImpl getFriendshipDaoInstance() {
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
    public boolean sendFriendshipRequest(Long requesterId, Long accepterId) {
        try (PreparedStatement friendshipRequest = getPreparedStatement(SEND_FRIEND_REQUEST)) {
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
    public boolean acceptFriendRequest(Long requesterId, Long accepterId) {
        try (PreparedStatement acceptFriendRequest = getPreparedStatement(ACCEPT_FRIEND_REQUEST)) {
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
    public List<Long> getIncomingFriendRequests(Long accountId) {
        try (PreparedStatement getFriendRequests = getPreparedStatement(GET_INCOMING_FRIEND_REQUESTS)) {
            List<Long> friendRequests = new ArrayList<>();
            getFriendRequests.setLong(1, accountId);
            ResultSet friendRequestsResult = getFriendRequests.executeQuery();
            while (friendRequestsResult.next()) {
                friendRequests.add(friendRequestsResult.getLong(1));
            }
            return friendRequests;
        } catch (SQLException e) {
            throw new DaoException("dao: getFriendRequests method failed: " + e.getMessage());
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
