package com.getjavajob.training.timashovy.socialnetwork.dao.daoimpl;

import com.getjavajob.training.timashovy.socialnetwork.dao.interfaces.FriendshipChecker;
import org.junit.jupiter.api.*;

import static com.getjavajob.training.timashovy.socialnetwork.dao.daoimpl.FriendshipCheckerImpl.getFriendshipCheckerInstance;
import static com.getjavajob.training.timashovy.socialnetwork.util.TestScriptsLoader.executeScript;
import static org.junit.jupiter.api.Assertions.*;

class FriendshipCheckerImplTest {

    private static final String CREATE_TEST_TABLES_FILEPATH = "scripts/create_test_db.sql";
    private static final String LOAD_TEST_TABLES_FILEPATH = "scripts/load_test_data.sql";
    private static final String CLEAR_TEST_TABLES_FILEPATH = "scripts/clear_test_db.sql";
    private static final String DROP_TEST_DB_FILEPATH = "scripts/drop_test_db.sql";
    private static final FriendshipChecker FRIENDSHIP_CHECKER_INSTANCE = getFriendshipCheckerInstance();

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
    @DisplayName("checkFriendshipRecordExistence(Long requesterId, Long accepterId)")
    class TestCheckFriendshipRecordExistence {

        @Test
        void whenRecordExistAndRequestedIdIsLessThanAccepterId() {
            assertTrue(FRIENDSHIP_CHECKER_INSTANCE.checkFriendshipRecordExistence(1L, 2L));
        }

        @Test
        void whenRecordExistAndRequestedIdIsGreaterThanAccepterId() {
            assertTrue(FRIENDSHIP_CHECKER_INSTANCE.checkFriendshipRecordExistence(2L, 1L));
        }

        @Test
        void whenRecordExistDoesNotExist() {
            assertFalse(FRIENDSHIP_CHECKER_INSTANCE.checkFriendshipRecordExistence(-1L, 2L));
        }

    }

    @Nested
    @DisplayName("checkUsersAreFriends(Long requesterId, Long accepterId)")
    class TestCheckUsersAreFriends {

        @Test
        void whenUsersAreFriendsAndRequestedIdIsLessThanAccepterId() {
            assertTrue(FRIENDSHIP_CHECKER_INSTANCE.checkUsersAreFriends(1L, 2L));
        }

        @Test
        void whenUsersAreFriendsAndRequestedIdIsGreaterThanAccepterId() {
            assertTrue(FRIENDSHIP_CHECKER_INSTANCE.checkUsersAreFriends(2L, 1L));
        }

        @Test
        void whenUsersAreNotFriends() {
            assertFalse(FRIENDSHIP_CHECKER_INSTANCE.checkUsersAreFriends(-1L, 1L));
        }

    }

    @Nested
    @DisplayName("boolean checkFriendRequestAlreadyExist(Long requesterId, Long accepterId)")
    class TestFriendRequestAlreadyExist {

        @Test
        void whenFriendRequestAlreadyExistRequesterIdLessThanAccepterId() {
            assertTrue(FRIENDSHIP_CHECKER_INSTANCE.checkFriendRequestAlreadyExist(3L, 4L));
        }

        @Test
        void whenFriendRequestAlreadyExistRequesterIdGreaterThanAccepterId() {
            assertTrue(FRIENDSHIP_CHECKER_INSTANCE.checkFriendRequestAlreadyExist(4L, 3L));
        }

        @Test
        void whenFriendRequestIsNotExisted() {
            assertFalse(FRIENDSHIP_CHECKER_INSTANCE.checkFriendRequestAlreadyExist(-1L, 1L));
        }

    }

}
