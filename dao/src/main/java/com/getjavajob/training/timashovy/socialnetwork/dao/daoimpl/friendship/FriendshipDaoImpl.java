package com.getjavajob.training.timashovy.socialnetwork.dao.daoimpl.friendship;

import com.getjavajob.training.timashovy.socialnetwork.dao.interfaces.friendship.FriendshipDao;
import com.getjavajob.training.timashovy.socialnetwork.domain.account.Account;
import com.getjavajob.training.timashovy.socialnetwork.domain.friendship.Friendship;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

import static com.getjavajob.training.timashovy.socialnetwork.dao.util.dbutils.TableNames.FRIENDSHIP_TABLE;
import static com.getjavajob.training.timashovy.socialnetwork.dao.util.dbutils.fieldsnames.FriendshipTableFields.*;

/**
 * Singleton class responsible for working with `account_data.friendship` table in DB.
 * It provides safe multithreading approach for creating singleton object using synchronization mechanism.
 */
public class FriendshipDaoImpl implements FriendshipDao {

    @PersistenceContext
    private EntityManager entityManager;

    private static final String GET_FRIENDS_IDS = "SELECT " + FRIENDSHIP_ACCOUNT_ID_1 + " FROM " + FRIENDSHIP_TABLE
            + " WHERE " + FRIENDSHIP_ACCOUNT_ID_2 + " = ? AND " + FRIENDSHIP_STATUS + " = TRUE UNION SELECT "
            + FRIENDSHIP_ACCOUNT_ID_2 + " FROM " + FRIENDSHIP_TABLE + " WHERE " + FRIENDSHIP_ACCOUNT_ID_1 + " = ? AND "
            + FRIENDSHIP_STATUS + " = TRUE;";
    private static final String DELETE_FRIEND = "DELETE FROM " + FRIENDSHIP_TABLE + " WHERE " + FRIENDSHIP_ACCOUNT_ID_1
            + " = ? AND " + FRIENDSHIP_ACCOUNT_ID_2 + " = ?;";
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
    public void sendRequest(Account requester, Account accepter) {
        entityManager.persist(new Friendship(
                getFirstAccount(requester, accepter).getId(),
                getSecondAccount(requester, accepter).getId(),
                requester,
                accepter,
                false
        ));
    }

    private Account getFirstAccount(Account requester, Account accepter) {
        return requester.getId() < accepter.getId() ? requester : accepter;
    }

    private Account getSecondAccount(Account requester, Account accepter) {
        return getFirstAccount(requester, accepter).equals(requester) ? accepter : requester;
    }

    @Override
    public boolean acceptRequest(Account requester, Account accepter) {
        return entityManager.createQuery(
                        "update Friendship f set f.friendshipStatus = true where f.requester = :requester "
                                + "and f.receiver = :accepter"
                ).
                setParameter("requester", requester)
                .setParameter("accepter", accepter)
                .executeUpdate() > 0;
    }

    /**
     * Return ArrayList with ids of account's friends
     *
     * @param accountId id of account we want to get friends
     * @return list of account friends' ids
     */
    @Override
    public List<Long> getFriendsIds(Long accountId) {
        return jdbcTemplate.queryForList(GET_FRIENDS_IDS, Long.class, accountId, accountId);
    }

    @Override
    public List<Long> getIncomingRequests(Long accountId) {
        return jdbcTemplate.query(GET_INCOMING_REQUESTS, (rs, rowNum) -> rs.getLong(1), accountId);
    }

    @Override
    public List<Long> getOutgoingRequests(Long accountId) {
        return jdbcTemplate.query(GET_OUTGOING_REQUESTS, (rs, rowNum) -> rs.getLong(1), accountId);
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
        return jdbcTemplate.update(DELETE_FRIEND, getDeletingFriendIds(accountId, deletingFriendId)) > 0;
    }

    private Object[] getDeletingFriendIds(Long firstAccountId, Long secondAccountId) {
        return firstAccountId < secondAccountId ? new Long[]{firstAccountId, secondAccountId}
                : new Long[]{secondAccountId, firstAccountId};
    }

}
