package com.getjavajob.training.timashovy.socialnetwork.dao.daoimpl.friendship;

import com.getjavajob.training.timashovy.socialnetwork.dao.interfaces.friendship.FriendshipCheckerDao;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.context.jdbc.Sql.ExecutionPhase.AFTER_TEST_METHOD;
import static org.springframework.test.context.jdbc.Sql.ExecutionPhase.BEFORE_TEST_METHOD;

@ExtendWith(SpringExtension.class)
@ContextConfiguration("classpath:test-config.xml")
@Sql(
        scripts = {
                "classpath:scripts/friendship/create.sql",
                "classpath:scripts/friendship/load.sql"
        },
        executionPhase = BEFORE_TEST_METHOD
)
@Sql(
        scripts = {
                "classpath:scripts/friendship/clear.sql",
                "classpath:scripts/friendship/drop.sql"
        },
        executionPhase = AFTER_TEST_METHOD
)
class FriendshipCheckerDaoImplTest {

    @Autowired
    private FriendshipCheckerDao FRIENDSHIP_CHECKER_DAO;

    @Nested
    @DisplayName("boolean checkFriendshipRecordExistence(Long requesterId, Long accepterId)")
    class TestCheckFriendshipRecordExistence {

        @Test
        void shouldReturnTrueWhenRecordExistsAndRequestedIdLessThanAccepterId() {
            assertTrue(FRIENDSHIP_CHECKER_DAO.checkFriendshipRecordExistence(1L, 2L));
        }

        @Test
        void shouldReturnTrueWhenRecordExistsAndRequestedIdGreaterThanAccepterId() {
            assertTrue(FRIENDSHIP_CHECKER_DAO.checkFriendshipRecordExistence(2L, 1L));
        }

        @Test
        void shouldReturnFalseWhenRecordIsNotExisted() {
            assertFalse(FRIENDSHIP_CHECKER_DAO.checkFriendshipRecordExistence(-1L, 2L));
        }

    }

    @Nested
    @DisplayName("boolean checkUsersAreFriends(Long requesterId, Long accepterId)")
    class TestCheckUsersAreFriends {

        @Test
        void shouldReturnTrueWhenUsersAreFriendsAndRequestedIdLessThanAccepterId() {
            assertTrue(FRIENDSHIP_CHECKER_DAO.checkUsersAreFriends(1L, 2L));
        }

        @Test
        void shouldReturnTrueWhenUsersAreFriendsAndRequestedIdGreaterThanAccepterId() {
            assertTrue(FRIENDSHIP_CHECKER_DAO.checkUsersAreFriends(2L, 1L));
        }

        @Test
        void shouldReturnFalseWhenUsersAreNotFriends() {
            assertFalse(FRIENDSHIP_CHECKER_DAO.checkUsersAreFriends(-1L, 1L));
        }

    }

}
