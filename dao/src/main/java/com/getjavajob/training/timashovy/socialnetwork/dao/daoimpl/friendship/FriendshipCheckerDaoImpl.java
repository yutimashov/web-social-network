package com.getjavajob.training.timashovy.socialnetwork.dao.daoimpl.friendship;

import com.getjavajob.training.timashovy.socialnetwork.dao.interfaces.friendship.FriendshipCheckerDao;

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
    public boolean checkFriendshipRecordExistence(Long requesterId, Long accepterId) {
        return entityManager.createQuery(
                        "select 1 from Friendship f where f.initiatorAccountId = :requesterId "
                                + "and f.friendAccountId = :accepterId"
                )
                .setParameter("requesterId", getFirstId(requesterId, accepterId))
                .setParameter("accepterId", getSecondId(requesterId, accepterId))
                .getResultList()
                .size() > 0;
    }

    private Long getFirstId(Long requesterId, Long accepterId) {
        return requesterId < accepterId ? requesterId : accepterId;
    }

    private Long getSecondId(Long requesterId, Long accepterId) {
        return requesterId < accepterId ? accepterId : requesterId;
    }

    @Override
    public boolean checkUsersAreFriends(Long requesterId, Long accepterId) {
        return entityManager.createQuery(
                        "select 1 from Friendship f where f.initiatorAccountId = :requesterId "
                                + "and f.friendAccountId = :accepterId and f.friendshipStatus = true"
                )
                .setParameter("requesterId", getFirstId(requesterId, accepterId))
                .setParameter("accepterId", getSecondId(requesterId, accepterId))
                .getResultList()
                .size() > 0;
    }

}
