package com.getjavajob.training.timashovy.socialnetwork.dao.interfaces.friendship;

import com.getjavajob.training.timashovy.socialnetwork.domain.account.Account;

import java.util.List;

public interface FriendshipRepository {

    void sendRequest(Account requester, Account receiver);

    boolean acceptRequest(Long requesterId, Long accepterId);

    void deleteFriend(Long accountId, Long deletingFriendId);

    List<Long> getFriendsIds(Long accountId);

    List<Long> getIncomingRequests(Long accountId);

    List<Long> getOutgoingRequests(Long accountId);

}
