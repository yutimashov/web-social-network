package com.getjavajob.training.timashovy.socialnetwork.dao.interfaces.repositories.friendship;

import com.getjavajob.training.timashovy.socialnetwork.domain.account.Account;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface FriendshipRepository {

    void sendRequest(Account requester, Account receiver);

    boolean acceptRequest(Long requesterId, Long accepterId);

    void deleteFriend(Long accountId, Long deletingFriendId);

    List<Long> getIncomingRequests(Long accountId);

    List<Long> getOutgoingRequests(Long accountId);

    Page<Account> getFriends(Long accountId, Pageable page);

}
