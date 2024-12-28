package com.getjavajob.training.timashovy.socialnetwork.dao.interfaces.repositories.friendship;

public interface FriendshipCheckerRepository {

    boolean checkFriendshipRecordExistence(Long requesterId, Long accepterId);

    boolean checkUsersAreFriends(Long requesterId, Long accepterId);

}
