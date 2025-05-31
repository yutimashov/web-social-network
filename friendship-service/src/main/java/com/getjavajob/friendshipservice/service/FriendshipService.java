package com.getjavajob.friendshipservice.service;

import com.getjavajob.training.timashovy.socialnetwork.domain.account.Account;

import java.util.List;

public interface FriendshipService {

    void sendRequest(Account requester, Account receiver);

    boolean acceptRequest(Long requesterId, Long accepterId);

    void deleteFriend(Long accountId, Long deletingFriendId);

    List<Account> getFollowerAccounts(Long accountId, Long lastId, int pageSize);

    List<Account> getFollowingAccounts(Long accountId, Long lastId, int pageSize);

    List<Account> getFriends(Long accountId, Long lastId, int pageSize);

    List<Long> getFriendsIds(Long accountId);

}
