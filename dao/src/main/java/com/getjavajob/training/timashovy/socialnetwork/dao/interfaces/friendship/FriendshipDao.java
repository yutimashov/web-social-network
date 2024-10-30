package com.getjavajob.training.timashovy.socialnetwork.dao.interfaces.friendship;

import com.getjavajob.training.timashovy.socialnetwork.domain.account.Account;

import java.util.List;

public interface FriendshipDao {

    void sendRequest(Account requester, Account accepter);

    boolean acceptRequest(Account requester, Account accepter);

    boolean deleteFriend(Long accountId, Long friendId);

    List<Long> getFriendsIds(Long accountId);

    List<Long> getIncomingRequests(Long accountId);

    List<Long> getOutgoingRequests(Long accountId);

}
