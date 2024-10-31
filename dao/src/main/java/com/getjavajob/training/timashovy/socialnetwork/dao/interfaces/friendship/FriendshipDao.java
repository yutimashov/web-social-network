package com.getjavajob.training.timashovy.socialnetwork.dao.interfaces.friendship;

import com.getjavajob.training.timashovy.socialnetwork.domain.account.Account;

import java.util.List;

public interface FriendshipDao {

    void sendRequest(Account requester, Account accepter);

    boolean acceptRequest(Account requester, Account accepter);

    boolean deleteFriend(Account friendshipOwner, Account friendToRemove);

    List<Long> getFriendsIds(Account account);

    List<Long> getIncomingRequests(Long accountId);

    List<Long> getOutgoingRequests(Long accountId);

}
