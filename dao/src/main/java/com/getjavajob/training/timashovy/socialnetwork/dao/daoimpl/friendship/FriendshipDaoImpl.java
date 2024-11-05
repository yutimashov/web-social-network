package com.getjavajob.training.timashovy.socialnetwork.dao.daoimpl.friendship;

import com.getjavajob.training.timashovy.socialnetwork.dao.interfaces.friendship.FriendshipDao;
import com.getjavajob.training.timashovy.socialnetwork.domain.account.Account;
import com.getjavajob.training.timashovy.socialnetwork.domain.friendship.Friendship;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

import static java.util.Objects.isNull;

/**
 * Singleton class responsible for working with `account_data.friendship` table in DB.
 * It provides safe multithreading approach for creating singleton object using synchronization mechanism.
 */
public class FriendshipDaoImpl implements FriendshipDao {

    @PersistenceContext
    private EntityManager entityManager;

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
    public boolean acceptRequest(Long requesterId, Long accepterId) {
        return entityManager.createQuery(
                        "update Friendship f set f.friendshipStatus = true where f.requester.id = :requester "
                                + "and f.receiver.id = :accepter"
                )
                .setParameter("requester", requesterId)
                .setParameter("accepter", accepterId)
                .executeUpdate() > 0;
    }

    @Override
    public List<Long> getFriendsIds(Long accountId) {
        return entityManager.createQuery(
                        "select case when f.firstFriendAccountId = :accountId "
                                + "then f.secondFriendAccountId else f.firstFriendAccountId end from Friendship f "
                                + "where (f.firstFriendAccountId = :accountId or f.secondFriendAccountId = :accountId) "
                                + "and f.friendshipStatus = true",
                        Long.class
                )
                .setParameter("accountId", accountId)
                .getResultList();
    }

    @Override
    public List<Long> getIncomingRequests(Long accountId) {
        return entityManager.createQuery(
                        "select f.requester.id from Friendship f where f.friendshipStatus = false "
                                + "and f.receiver.id = :accountId",
                        Long.class
                )
                .setParameter("accountId", accountId)
                .getResultList();
    }

    @Override
    public List<Long> getOutgoingRequests(Long accountId) {
        return entityManager.createQuery(
                        "select f.receiver.id from Friendship f where f.friendshipStatus = false "
                                + "and f.requester.id = :accountId",
                        Long.class
                )
                .setParameter("accountId", accountId)
                .getResultList();
    }

    @Override
    public boolean deleteFriend(Long accountId, Long deletingFriendId) {
        Friendship.FriendshipId friendshipId;
        if (accountId < deletingFriendId) {
            friendshipId = new Friendship.FriendshipId(accountId, deletingFriendId);
        } else {
            friendshipId = new Friendship.FriendshipId(deletingFriendId, accountId);
        }
        Friendship friendship = entityManager.find(Friendship.class, friendshipId);
        if (!isNull(friendship)) {
            entityManager.remove(friendship);
            return true;
        } else {
            return false;
        }
    }

}
