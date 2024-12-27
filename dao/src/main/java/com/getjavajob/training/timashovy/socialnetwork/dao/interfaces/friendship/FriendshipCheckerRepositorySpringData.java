package com.getjavajob.training.timashovy.socialnetwork.dao.interfaces.friendship;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface FriendshipCheckerRepositorySpringData {

    @Query("select count(f) > 0 from Friendship f where f.initiatorAccountId = :requesterId and f.friendAccountId = :accepterId")
    boolean existsByInitiatorAndFriend(@Param("requesterId") Long requesterId, @Param("accepterId") Long accepterId);

    @Query("select count(f) > 0 from Friendship f where f.initiatorAccountId = :requesterId and f.friendAccountId = :accepterId and f.friendshipStatus = true")
    boolean existsFriendshipByInitiatorAndFriendAndStatus(@Param("requesterId") Long requesterId,
                                                          @Param("accepterId") Long accepterId);

}
