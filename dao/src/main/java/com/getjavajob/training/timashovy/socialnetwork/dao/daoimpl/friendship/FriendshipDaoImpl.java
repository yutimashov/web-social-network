package com.getjavajob.training.timashovy.socialnetwork.dao.daoimpl.friendship;

import com.getjavajob.training.timashovy.socialnetwork.dao.exception.DaoException;
import com.getjavajob.training.timashovy.socialnetwork.dao.interfaces.friendship.FriendshipDao;
import com.getjavajob.training.timashovy.socialnetwork.domain.account.Account;
import com.getjavajob.training.timashovy.socialnetwork.domain.friendship.Friendship;
import org.slf4j.Logger;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;
import java.util.List;

import static java.util.Objects.isNull;
import static org.slf4j.LoggerFactory.getLogger;

/**
 * Singleton class responsible for working with `account_data.friendship` table in DB.
 * It provides safe multithreading approach for creating singleton object using synchronization mechanism.
 */
@Repository
public class FriendshipDaoImpl implements FriendshipDao {

    private static final Logger logger = getLogger(FriendshipDaoImpl.class);

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public void sendRequest(Account requester, Account accepter) {
        try {
            entityManager.persist(new Friendship(
                    getFirstAccount(requester, accepter).getId(),
                    getSecondAccount(requester, accepter).getId(),
                    requester,
                    accepter,
                    false
            ));
        } catch (PersistenceException e) {
            logger.error("Error sending friend request from id={} to id={}", requester.getId(), accepter.getId());
            throw new DaoException("Cannot send friend request to persistent storage", e);
        }
    }

    private Account getFirstAccount(Account requester, Account accepter) {
        return requester.getId() < accepter.getId() ? requester : accepter;
    }

    private Account getSecondAccount(Account requester, Account accepter) {
        return getFirstAccount(requester, accepter).equals(requester) ? accepter : requester;
    }

    @Override
    public boolean acceptRequest(Long requesterId, Long accepterId) {
        try {
            return entityManager.createQuery(
                            "update Friendship f set f.friendshipStatus = true where f.requester.id = :requester "
                                    + "and f.receiver.id = :accepter"
                    )
                    .setParameter("requester", requesterId)
                    .setParameter("accepter", accepterId)
                    .executeUpdate() > 0;
        } catch (PersistenceException e) {
            logger.error("Error accepting friend request from id={} to id={}", requesterId, accepterId);
            throw new DaoException("Cannot accept friend request in persistent storage", e);
        }
    }

    @Override
    public List<Long> getFriendsIds(Long accountId) {
        try {
            return entityManager.createQuery(
                            "select case when f.initiatorAccountId = :accountId "
                                    + "then f.friendAccountId else f.initiatorAccountId end from Friendship f "
                                    + "where (f.initiatorAccountId = :accountId or f.friendAccountId = :accountId) "
                                    + "and f.friendshipStatus = true",
                            Long.class
                    )
                    .setParameter("accountId", accountId)
                    .getResultList();
        } catch (PersistenceException e) {
            logger.error("Error getting friend ids id={}", accountId);
            throw new DaoException("Cannot get friend ids from persistent storage", e);
        }
    }

    @Override
    public List<Long> getIncomingRequests(Long accountId) {
        try {
            return entityManager.createQuery(
                            "select f.requester.id from Friendship f where f.friendshipStatus = false "
                                    + "and f.receiver.id = :accountId",
                            Long.class
                    )
                    .setParameter("accountId", accountId)
                    .getResultList();
        } catch (PersistenceException e) {
            logger.error("Error getting incoming requests for account id={}", accountId);
            throw new DaoException("Cannot get incoming friend requests from persistent storage", e);
        }
    }

    @Override
    public List<Long> getOutgoingRequests(Long accountId) {
        try {
            return entityManager.createQuery(
                            "select f.receiver.id from Friendship f where f.friendshipStatus = false "
                                    + "and f.requester.id = :accountId",
                            Long.class
                    )
                    .setParameter("accountId", accountId)
                    .getResultList();
        } catch (PersistenceException e) {
            logger.error("Error getting outgoing requests for account id={}", accountId);
            throw new DaoException("Cannot get outgoing friend requests from persistent storage", e);
        }
    }

    @Override
    public void deleteFriend(Long accountId, Long deletingFriendId) {
        try {
            Friendship.FriendshipId friendshipId = accountId < deletingFriendId
                    ? new Friendship.FriendshipId(accountId, deletingFriendId)
                    : new Friendship.FriendshipId(deletingFriendId, accountId);
            Friendship friendship = entityManager.find(Friendship.class, friendshipId);
            if (!isNull(friendship)) {
                entityManager.remove(friendship);
            }
        } catch (PersistenceException e) {
            logger.error("Error deleting friend from id={}, to be deleted id={}", accountId, deletingFriendId);
            throw new DaoException("Cannot get delete friendship from persistent storage", e);
        }
    }

}
