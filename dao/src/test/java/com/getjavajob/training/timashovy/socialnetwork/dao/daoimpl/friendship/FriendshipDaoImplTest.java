package com.getjavajob.training.timashovy.socialnetwork.dao.daoimpl.friendship;

import com.getjavajob.training.timashovy.socialnetwork.dao.interfaces.friendship.FriendshipDao;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.context.jdbc.Sql.ExecutionPhase.AFTER_TEST_METHOD;
import static org.springframework.test.context.jdbc.Sql.ExecutionPhase.BEFORE_TEST_METHOD;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(locations = {"classpath:test-beans-dao.xml"})
@Sql(scripts = "classpath:scripts/friendship/create.sql", executionPhase = BEFORE_TEST_METHOD)
@Sql(scripts = "classpath:scripts/friendship/load.sql", executionPhase = BEFORE_TEST_METHOD)
@Sql(scripts = "classpath:scripts/friendship/clear.sql", executionPhase = AFTER_TEST_METHOD)
@Sql(scripts = "classpath:scripts/friendship/drop.sql", executionPhase = AFTER_TEST_METHOD)
class FriendshipDaoImplTest {

    @Autowired
    private FriendshipDao FRIENDSHIP_DAO;

    @Nested
    @DisplayName("sendFriendRequest(Long requesterId, Long accepterId)")
    class TestSendFriendRequest {

        @Test
        void shouldReturnTrueWhenTwoAccountsExist() {
            assertTrue(FRIENDSHIP_DAO.sendRequest(1L, 4L));
        }

    }

    @Nested
    @DisplayName("acceptFriendRequest(Long requesterId, Long accepterId)")
    class TestAcceptFriendRequest {

        @Test
        void shouldReturnFalseWhenRecordExistsWithStatusFalse() {
            assertFalse(FRIENDSHIP_DAO.acceptRequest(4L, 3L));
        }

        @Test
        void shouldReturnTrueWhenRecordExistsWithStatusFalseButInWrongOrder() {
            assertTrue(FRIENDSHIP_DAO.acceptRequest(3L, 4L));
        }

    }

    @Nested
    @DisplayName("getFriends(Long accountId)")
    class TestGetFriends {

        @Test
        void shouldReturnListWithOneRecordWhenOnlyOneFriendExistsWithStatusFalse() {
            List<Long> friendsIds = new ArrayList<>();
            friendsIds.add(2L);
            assertEquals(friendsIds, FRIENDSHIP_DAO.getFriendsIds(1L));
        }

        @Test
        void shouldReturnEmptyListWhenOneFriendExistsWithStatusFalse() {
            assertEquals(new ArrayList<>(), FRIENDSHIP_DAO.getFriendsIds(3L));
        }

        @Test
        void shouldReturnEmptyListWhenAccountHasNoFriends() {
            assertEquals(new ArrayList<>(), FRIENDSHIP_DAO.getFriendsIds(-1L));
        }

    }

    @Nested
    @DisplayName("deleteFriend(Long accountId, Long deletingFriendId)")
    class TestDeleteFriend {

        @Test
        void shouldReturnTrueWhenRecordWithFriendCanBeDeleted() {
            assertTrue(FRIENDSHIP_DAO.deleteFriend(1L, 2L));
        }

        @Test
        void shouldReturnFalseWhenRecordWithFriendIsNotExisted() {
            assertFalse(FRIENDSHIP_DAO.deleteFriend(-1L, -2L));
        }

    }

}
