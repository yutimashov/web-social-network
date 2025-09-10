package com.getjavajob.friendshipservice.service;

public interface FriendshipCheckerService {

    boolean checkFriendshipRecordExistence(Long requesterId, Long accepterId);

    boolean checkUsersAreFriends(Long requesterId, Long accepterId);

}
