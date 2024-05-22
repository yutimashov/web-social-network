package com.getjavajob.training.timashovy.socialnetwork.dao.daoimpl.friendship;

import com.getjavajob.training.timashovy.socialnetwork.dao.interfaces.friendship.FriendshipChecker;
import org.junit.jupiter.api.*;

import static com.getjavajob.training.timashovy.socialnetwork.dao.daoimpl.friendship.FriendshipCheckerDaoImpl.createInstance;
import static com.getjavajob.training.timashovy.socialnetwork.util.TestScriptsLoader.executeScript;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class FriendshipCheckerDaoImplTest {

    private static final String CREATE_TEST_TABLES_FILEPATH = "scripts/friendship/create_test_db.sql";
    private static final String LOAD_TEST_TABLES_FILEPATH = "scripts/friendship/load_test_data.sql";
    private static final String CLEAR_TEST_TABLES_FILEPATH = "scripts/friendship/clear_test_db.sql";
    private static final String DROP_TEST_DB_FILEPATH = "scripts/friendship/drop_test_db.sql";
    private static final FriendshipChecker FRIENDSHIP_CHECKER_DAO = createInstance();

    @BeforeAll
    public static void createTestTables() {
        executeScript(CREATE_TEST_TABLES_FILEPATH);
    }

    @BeforeEach
    public void loadTestTablesWithData() {
        executeScript(LOAD_TEST_TABLES_FILEPATH);
    }

    @AfterEach
    public void clearTestTables() {
        executeScript(CLEAR_TEST_TABLES_FILEPATH);
    }

    @AfterAll
    public static void dropDataBaseAfterTestExecution() {
        executeScript(DROP_TEST_DB_FILEPATH);
    }

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
