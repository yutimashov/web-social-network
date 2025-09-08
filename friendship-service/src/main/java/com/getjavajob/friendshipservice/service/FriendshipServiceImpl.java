package com.getjavajob.friendshipservice.service;

import com.getjavajob.friendshipservice.dao.checker.FriendshipCheckerRepository;
import com.getjavajob.friendshipservice.dao.friendship.FriendshipRepository;
import com.getjavajob.training.timashovy.socialnetwork.domain.account.Account;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class FriendshipServiceImpl implements FriendshipService {

    private final FriendshipRepository friendshipRepository;
    private final FriendshipCheckerRepository friendshipCheckerRepository;

    public FriendshipServiceImpl(FriendshipRepository friendshipRepository,
                                 FriendshipCheckerRepository friendshipCheckerRepository) {
        this.friendshipRepository = friendshipRepository;
        this.friendshipCheckerRepository = friendshipCheckerRepository;
    }

    @Override
    public void sendRequest(Long requesterId, Long accepterId) {
        friendshipRepository.sendRequest(requesterId, accepterId);
    }

    @Override
    public boolean acceptRequest(Long requesterId, Long accepterId) {
        return friendshipRepository.acceptRequest(requesterId, accepterId);
    }

    @Transactional
    @Override
    public void deleteFriend(Long accountId, Long deletingFriendId) {
        friendshipRepository.deleteFriend(accountId, deletingFriendId);
    }

    @Override
    public List<Account> getFollowerAccounts(Long accountId, Long lastId, int pageSize) {
        return friendshipRepository.getFollowerAccounts(accountId, lastId, pageSize);
    }

    @Override
    public List<Account> getFollowingAccounts(Long accountId, Long lastId, int pageSize) {
        return friendshipRepository.getFollowingAccounts(accountId, lastId, pageSize);
    }

    @Override
    public List<Account> getFriends(Long accountId, Long lastId, int pageSize) {
        return friendshipRepository.getFriends(accountId, lastId, pageSize);
    }

    @Override
    public List<Long> getFriendsIds(Long accountId) {
        return friendshipRepository.getFriendsIds(accountId);
    }

    /**
     * If there is no record in friendship table, it means that no friendship connection exist.
     * Make a record - requester becomes follower of accepter.
     * If record is already exist, check if users are already friends.
     * If they are - return false.
     * If they are not friends, but friendship record exists, check requester.
     * If requester is the same as it is in table, it means that requester tries to add friend one more time.
     * If requester is accepter, it means that requester confirms friendship request already existed in the table.
     *
     * @param requesterId account id, who has initiated friendship request
     * @param accepterId  account id, who is addresses of friendship request
     */
    @Transactional
    @Override
    public void addFriend(Long requesterId, Long accepterId) {
        if (requesterId.equals(accepterId)) {
            throw new IllegalArgumentException("Account cannot send friend request to themselves");
        }
        if (!friendshipCheckerRepository.checkFriendshipRecordExistence(requesterId, accepterId)) {
            friendshipRepository.sendRequest(requesterId, accepterId);
            return;
        }
        if (friendshipCheckerRepository.checkUsersAreFriends(requesterId, accepterId)) {
            return;
        }
        friendshipRepository.acceptRequest(requesterId, accepterId);
    }

}
