package com.getjavajob.training.timashovy.socialnetwork.dao.interfaces;

public interface FriendshipChecker {

    boolean checkFriendshipRecordExistence(Long requesterId, Long accepterId);

    boolean checkUsersAreFriends(Long requesterId, Long accepterId);

    boolean checkFriendRequestAlreadyExist(Long requesterId, Long accepterId);

}
