package com.getjavajob.training.timashovy.socialnetwork.dao.daoimpl.friendship;

import com.getjavajob.training.timashovy.socialnetwork.dao.interfaces.friendship.FriendshipRepository;
import com.getjavajob.training.timashovy.socialnetwork.dao.interfaces.friendship.FriendshipRepositorySpringData;
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
    public void sendRequest(Account requester, Account accepter) {
        friendshipRepositorySpringData.save(new Friendship(getFirstAccount(requester, accepter).getId(),
                getSecondAccount(requester, accepter).getId(), requester, accepter, false));
    }

    private Account getFirstAccount(Account requester, Account accepter) {
        return requester.getId() < accepter.getId() ? requester : accepter;
    }

    private Account getSecondAccount(Account requester, Account accepter) {
        return getFirstAccount(requester, accepter).equals(requester) ? accepter : requester;
    }

    @Override
    public boolean acceptRequest(Long requesterId, Long accepterId) {
        return friendshipRepositorySpringData.acceptRequest(requesterId, accepterId) > 0;
    }

    @Override
    public List<Long> getFriendsIds(Long accountId) {
        return friendshipRepositorySpringData.getFriendsIds(accountId);
    }

    @Override
    public List<Long> getIncomingRequests(Long accountId) {
        return friendshipRepositorySpringData.getIncomingRequests(accountId);
    }

    @Override
    public List<Long> getOutgoingRequests(Long accountId) {
        return friendshipRepositorySpringData.getOutgoingRequests(accountId);
    }

    @Override
    public void deleteFriend(Long accountId, Long deletingFriendId) {
        friendshipRepositorySpringData.deleteFriend(accountId, deletingFriendId);
    }

}
