package com.getjavajob.training.timashovy.socialnetwork.dao.daoimpl.friendship;

import com.getjavajob.training.timashovy.socialnetwork.dao.interfaces.springdatarepositories.friendship.FriendshipCheckerRepositorySpringData;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.openMocks;

class FriendshipCheckerDaoImplTest {

    @Mock
    private FriendshipCheckerRepositorySpringData friendshipCheckerRepositorySpringData;

    @InjectMocks
    private FriendshipCheckerRepositoryImpl friendshipCheckerDao;

    @BeforeEach
    void setup() {
        openMocks(this);
    }

    @Nested
    @DisplayName("boolean checkFriendshipRecordExistence(Long requesterId, Long accepterId)")
    class TestCheckFriendshipRecordExistence {

        @Test
        void shouldReturnTrueWhenRecordExistsAndRequestedIdLessThanAccepterId() {
            Long requesterId = 1L;
            Long accepterId = 2L;
            when(friendshipCheckerRepositorySpringData.existsByInitiatorAndFriend(requesterId, accepterId))
                    .thenReturn(true);
            assertTrue(friendshipCheckerDao.checkFriendshipRecordExistence(requesterId, accepterId));
        }

        @Test
        void shouldReturnFalseWhenRecordIsNotExisted() {
            Long requesterId = 1L;
            Long accepterId = 2L;
            when(friendshipCheckerRepositorySpringData.existsByInitiatorAndFriend(requesterId, accepterId))
                    .thenReturn(false);
            assertFalse(friendshipCheckerDao.checkFriendshipRecordExistence(requesterId, accepterId));
        }

    }

    @Nested
    @DisplayName("boolean checkUsersAreFriends(Long requesterId, Long accepterId)")
    class TestCheckUsersAreFriends {

        @Test
        void shouldReturnTrueWhenUsersAreFriends() {
            Long requesterId = 1L;
            Long accepterId = 2L;
            when(friendshipCheckerRepositorySpringData.existsFriendshipByInitiatorAndFriendAndStatus(requesterId,
                    accepterId)).thenReturn(true);
            assertTrue(friendshipCheckerDao.checkUsersAreFriends(requesterId, accepterId));
        }

        @Test
        void shouldReturnFalseWhenUsersAreNotFriends() {
            Long requesterId = 1L;
            Long accepterId = 2L;
            when(friendshipCheckerRepositorySpringData.existsFriendshipByInitiatorAndFriendAndStatus(requesterId,
                    accepterId)).thenReturn(false);
            assertFalse(friendshipCheckerDao.checkUsersAreFriends(requesterId, accepterId));
        }

    }

}
