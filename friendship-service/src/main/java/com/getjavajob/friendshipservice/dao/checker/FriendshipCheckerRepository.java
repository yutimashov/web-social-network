package com.getjavajob.friendshipservice.dao.checker;

public interface FriendshipCheckerRepository {

    boolean checkFriendshipRecordExistence(Long requesterId, Long accepterId);

    boolean checkUsersAreFriends(Long requesterId, Long accepterId);

}
