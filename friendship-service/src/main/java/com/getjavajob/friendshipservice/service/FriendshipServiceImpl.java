package com.getjavajob.friendshipservice.service;

import com.getjavajob.friendshipservice.dao.friendship.FriendshipRepository;
import com.getjavajob.training.timashovy.socialnetwork.domain.account.Account;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FriendshipServiceImpl implements FriendshipService {

    private final FriendshipRepository friendshipRepository;

    public FriendshipServiceImpl(FriendshipRepository friendshipRepository) {
        this.friendshipRepository = friendshipRepository;
    }

    @Override
    public void sendRequest(Account requester, Account receiver) {
        friendshipRepository.sendRequest(requester, receiver);
    }

    @Override
    public boolean acceptRequest(Long requesterId, Long accepterId) {
        return friendshipRepository.acceptRequest(requesterId, accepterId);
    }

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

}
