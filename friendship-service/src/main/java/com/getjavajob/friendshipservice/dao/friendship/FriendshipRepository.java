package com.getjavajob.friendshipservice.dao.friendship;


import com.getjavajob.training.timashovy.socialnetwork.domain.account.Account;

import java.util.List;

public interface FriendshipRepository {

    void sendRequest(Long requesterId, Long accepterId);

    boolean acceptRequest(Long requesterId, Long accepterId);

    void deleteFriend(Long accountId, Long deletingFriendId);

    List<Account> getFollowerAccounts(Long accountId, Long lastId, int pageSize);

    List<Account> getFollowingAccounts(Long accountId, Long lastId, int pageSize);

    List<Account> getFriends(Long accountId, Long lastId, int pageSize);

    List<Long> getFriendsIds(Long accountId);

}
