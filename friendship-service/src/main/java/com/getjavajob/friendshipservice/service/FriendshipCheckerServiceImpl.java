package com.getjavajob.friendshipservice.service;

import com.getjavajob.friendshipservice.dao.checker.FriendshipCheckerRepository;
import org.springframework.stereotype.Service;

@Service
public class FriendshipCheckerServiceImpl implements FriendshipCheckerService {

    private final FriendshipCheckerRepository friendshipCheckerRepository;

    public FriendshipCheckerServiceImpl(FriendshipCheckerRepository friendshipCheckerRepository) {
        this.friendshipCheckerRepository = friendshipCheckerRepository;
    }

    @Override
    public boolean checkFriendshipRecordExistence(Long requesterId, Long accepterId) {
        return friendshipCheckerRepository.checkFriendshipRecordExistence(requesterId, accepterId);
    }

    @Override
    public boolean checkUsersAreFriends(Long requesterId, Long accepterId) {
        return friendshipCheckerRepository.checkUsersAreFriends(requesterId, accepterId);
    }

}
