package com.getjavajob.training.timashovy.socialnetwork.dao.daoimpl.friendship;

import com.getjavajob.training.timashovy.socialnetwork.dao.interfaces.friendship.FriendshipDao;
import com.getjavajob.training.timashovy.socialnetwork.domain.account.Account;
import com.getjavajob.training.timashovy.socialnetwork.domain.friendship.Friendship;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;
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
    public boolean acceptRequest(Account requester, Account accepter) {
        return entityManager.createQuery(
                        "update Friendship f set f.friendshipStatus = true where f.requester = :requester "
                                + "and f.receiver = :accepter"
                ).
                setParameter("requester", requester)
                .setParameter("accepter", accepter)
                .executeUpdate() > 0;
    }

    @Override
    public List<Long> getFriendsIds(Account account) {
        return entityManager.createQuery(
                        "select f.firstFriendAccountId from Friendship f "
                                + "where f.secondFriendAccountId = :accountId and f.friendshipStatus = true "
                                + "union "
                                + "select f.secondFriendAccountId from Friendship f "
                                + "where f.firstFriendAccountId = :accountId and f.friendshipStatus = true",
                        Long.class
                )
                .setParameter("accountId", account.getId())
                .getResultList();
    }

    @Override
    public List<Long> getIncomingRequests(Long accountId) {
        return entityManager.createQuery(
                        "select f.requester.id from Friendship f where f.friendshipStatus = false " +
                                "and f.receiver.id = :accountId",
                        Long.class
                )
                .setParameter("accountId", accountId)
                .getResultList();
    }

    @Override
    public List<Long> getOutgoingRequests(Long accountId) {
        return entityManager.createQuery(
                        "select f.receiver.id from Friendship f where f.friendshipStatus = false " +
                                "and f.requester.id = :accountId",
                        Long.class
                )
                .setParameter("accountId", accountId)
                .getResultList();
    }

    @Override
    public boolean deleteFriend(Account friendshipOwner, Account friendToRemove) {
        EntityTransaction transaction = entityManager.getTransaction();
        try {
            transaction.begin();
            Friendship.FriendshipId friendshipId;
            if (friendshipOwner.getId() < friendToRemove.getId()) {
                friendshipId = new Friendship.FriendshipId(friendshipOwner.getId(), friendToRemove.getId());
            } else {
                friendshipId = new Friendship.FriendshipId(friendToRemove.getId(), friendshipOwner.getId());
            }
            Friendship friendship = entityManager.find(Friendship.class, friendshipId);
            if (!isNull(friendship)) {
                entityManager.remove(friendship);
                transaction.commit();
                return true;
            } else {
                transaction.rollback();
                return false;
            }
        } catch (PersistenceException e) {
            if (transaction.isActive()) {
                transaction.rollback();
            }
            return false;
        }
    }

}
