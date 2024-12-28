package com.getjavajob.training.timashovy.socialnetwork.dao.daoimpl.friendship;

import com.getjavajob.training.timashovy.socialnetwork.dao.interfaces.repositories.friendship.FriendshipCheckerRepository;
import com.getjavajob.training.timashovy.socialnetwork.dao.interfaces.springdatarepositories.FriendshipCheckerRepositorySpringData;
import org.slf4j.Logger;
import org.springframework.stereotype.Repository;

import static org.slf4j.LoggerFactory.getLogger;

/**
 * Singleton class responsible for working with `account_data.friendship` table in DB.
 * It provides safe multithreading approach for creating singleton object using synchronization mechanism.
 */
@Repository
public class FriendshipCheckerRepositoryImpl implements FriendshipCheckerRepository {

    private static final Logger logger = getLogger(FriendshipCheckerRepositoryImpl.class);
    private final FriendshipCheckerRepositorySpringData friendshipCheckerRepositorySpringData;

    public FriendshipCheckerRepositoryImpl(FriendshipCheckerRepositorySpringData friendshipCheckerRepositorySpringData) {
        this.friendshipCheckerRepositorySpringData = friendshipCheckerRepositorySpringData;
    }

    @Override
    public boolean checkFriendshipRecordExistence(Long requesterId, Long accepterId) {
        return friendshipCheckerRepositorySpringData.existsByInitiatorAndFriend(getFirstId(requesterId, accepterId),
                getSecondId(requesterId, accepterId));
    }

    private Long getFirstId(Long requesterId, Long accepterId) {
        return requesterId < accepterId ? requesterId : accepterId;
    }

    private Long getSecondId(Long requesterId, Long accepterId) {
        return requesterId < accepterId ? accepterId : requesterId;
    }

    @Override
    public boolean checkUsersAreFriends(Long requesterId, Long accepterId) {
        return friendshipCheckerRepositorySpringData.existsFriendshipByInitiatorAndFriendAndStatus(getFirstId(requesterId,
                accepterId), getSecondId(requesterId, accepterId));
    }

}
