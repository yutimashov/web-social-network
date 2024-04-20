package com.getjavajob.training.timashovy.socialnetwork.dao.interfaces.friendship;

import java.util.List;

public interface FriendshipDao {

    boolean sendRequest(Long requesterId, Long accepterId);

    boolean acceptRequest(Long requesterId, Long accepterId);

    boolean deleteFriend(Long accountId, Long friendId);

    List<Long> getFriendsIds(Long accountId);

    List<Long> getIncomingRequests(Long accountId);

    List<Long> getOutgoingRequests(Long accountId);

}
