package com.getjavajob.training.timashovy.socialnetwork.dao.daoimpl.friendship;

import com.getjavajob.training.timashovy.socialnetwork.dao.interfaces.friendship.FriendshipCheckerDao;
import com.getjavajob.training.timashovy.socialnetwork.domain.account.Account;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 * Singleton class responsible for working with `account_data.friendship` table in DB.
 * It provides safe multithreading approach for creating singleton object using synchronization mechanism.
 */
public class FriendshipCheckerDaoImpl implements FriendshipCheckerDao {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public boolean checkFriendshipRecordExistence(Account requester, Account accepter) {
        return entityManager.createQuery(
                        "select 1 from Friendship f where f.firstFriendAccountId = :requesterId "
                                + "and f.secondFriendAccountId = :accepterId"
                )
                .setParameter("requesterId", getFirstId(requester, accepter))
                .setParameter("accepterId", getSecondId(requester, accepter))
                .getResultList()
                .isEmpty();
    }

    private Long getFirstId(Account requester, Account accepter) {
        return requester.getId() < accepter.getId() ? requester.getId() : accepter.getId();
    }

    private Long getSecondId(Account requester, Account accepter) {
        return requester.getId() < accepter.getId() ? accepter.getId() : requester.getId();
    }

    @Override
    public boolean checkUsersAreFriends(Account requester, Account accepter) {
        return entityManager.createQuery(
                        "select 1 from Friendship f where f.firstFriendAccountId = :requesterId "
                                + "and f.secondFriendAccountId = :accepterId and f.friendshipStatus = true"
                )
                .setParameter("requesterId", getFirstId(requester, accepter))
                .setParameter("accepterId", getSecondId(requester, accepter))
                .getResultList()
                .isEmpty();
    }

}
