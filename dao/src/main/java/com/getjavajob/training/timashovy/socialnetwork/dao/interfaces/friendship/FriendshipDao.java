package com.getjavajob.training.timashovy.socialnetwork.dao.interfaces.friendship;

import java.util.List;

public interface FriendshipDao {

    boolean sendFriendshipRequest(Long requesterId, Long accepterId);

    boolean acceptFriendRequest(Long requesterId, Long accepterId);

    boolean deleteFriend(Long accountId, Long friendId);

    List<Long> getFriendsIds(Long accountId);

    List<Long> getIncomingFriendRequests(Long accountId);

    List<Long> getOutgoingFriendRequests(Long accountId);

}
