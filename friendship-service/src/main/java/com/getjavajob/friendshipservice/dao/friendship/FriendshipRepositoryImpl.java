package com.getjavajob.friendshipservice.dao.friendship;

import com.getjavajob.training.timashovy.socialnetwork.domain.account.Account;
import com.getjavajob.training.timashovy.socialnetwork.domain.friendship.Friendship;
import org.slf4j.Logger;
import org.springframework.stereotype.Repository;

import java.util.List;

import static org.slf4j.LoggerFactory.getLogger;

/**
 * Singleton class responsible for working with `account_data.friendship` table in DB.
 * It provides safe multithreading approach for creating singleton object using synchronization mechanism.
 */
@Repository
public class FriendshipRepositoryImpl implements FriendshipRepository {

    private static final Logger logger = getLogger(FriendshipRepositoryImpl.class);

    private final FriendshipRepositorySpringData friendshipRepositorySpringData;

    public FriendshipRepositoryImpl(FriendshipRepositorySpringData friendshipRepositorySpringData) {
        this.friendshipRepositorySpringData = friendshipRepositorySpringData;
    }

    @Override
    public void sendRequest(Long requesterId, Long accepterId) {
        Friendship friendship = new Friendship();
        friendship.setInitiatorAccountId(getFirstAccountId(requesterId, accepterId));
        friendship.setFriendAccountId(getSecondAccountId(requesterId, accepterId));
        Account requester = new Account();
        requester.setId(requesterId);
        friendship.setRequester(requester);
        Account accepter = new Account();
        accepter.setId(accepterId);
        friendship.setReceiver(accepter);
        friendship.setFriendshipStatus(false);
        friendshipRepositorySpringData.save(friendship);
    }

    private Long getFirstAccountId(Long requesterId, Long accepterId) {
        return requesterId < accepterId ? requesterId : accepterId;
    }

    private Long getSecondAccountId(Long requesterId, Long accepterId) {
        return getFirstAccountId(requesterId, accepterId).equals(requesterId) ? accepterId : requesterId;
    }

    @Override
    public boolean acceptRequest(Long requesterId, Long accepterId) {
        return friendshipRepositorySpringData.acceptRequest(requesterId, accepterId) > 0;
    }

    @Override
    public List<Account> getFollowerAccounts(Long accountId, Long lastId, int pageSize) {
        return friendshipRepositorySpringData.findFollowerAccountsById(accountId, lastId, pageSize);
    }

    @Override
    public List<Account> getFollowingAccounts(Long accountId, Long lastId, int pageSize) {
        return friendshipRepositorySpringData.findFollowingAccountsById(accountId, lastId, pageSize);
    }

    @Override
    public void deleteFriend(Long accountId, Long deletingFriendId) {
        friendshipRepositorySpringData.deleteFriend(accountId, deletingFriendId);
    }

    @Override
    public List<Account> getFriends(Long accountId, Long lastId, int pageSize) {
        return friendshipRepositorySpringData.findFriendsByAccountId(accountId, lastId, pageSize);
    }

    @Override
    public List<Long> getFriendsIds(Long accountId) {
        return friendshipRepositorySpringData.getFriendsIds(accountId);
    }

}
