package com.getjavajob.training.timashovy.socialnetwork.dao.daoimpl;

import com.getjavajob.training.timashovy.socialnetwork.dao.interfaces.FriendshipDao;
import org.junit.jupiter.api.*;

import java.util.ArrayList;
import java.util.List;

import static com.getjavajob.training.timashovy.socialnetwork.dao.daoimpl.FriendshipDaoImpl.getFriendshipDaoInstance;
import static com.getjavajob.training.timashovy.socialnetwork.util.TestScriptsLoader.executeScript;
import static org.junit.jupiter.api.Assertions.*;

class FriendshipDaoImplTest {

    private static final String CREATE_TEST_TABLES_FILEPATH = "scripts/create_test_db.sql";
    private static final String LOAD_TEST_TABLES_FILEPATH = "scripts/load_test_data.sql";
    private static final String CLEAR_TEST_TABLES_FILEPATH = "scripts/clear_test_db.sql";
    private static final String DROP_TEST_DB_FILEPATH = "scripts/drop_test_db.sql";
    private static final FriendshipDao FRIENDSHIP_DAO_INSTANCE = getFriendshipDaoInstance();

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
    @DisplayName("sendFriendRequest(Long requesterId, Long accepterId)")
    class TestSendFriendRequest {

        @Test
        void whenTwoAccountExists() {
            assertTrue(FRIENDSHIP_DAO_INSTANCE.sendFriendshipRequest(1L, 4L));
        }

    }

    @Nested
    @DisplayName("acceptFriendRequest(Long requesterId, Long accepterId)")
    class TestAcceptFriendRequest {

        @Test
        void whenRecordAlreadyExistsWithStatusFalse() {
            assertTrue(FRIENDSHIP_DAO_INSTANCE.acceptFriendRequest(4L, 3L));
        }

        @Test
        void whenRecordAlreadyExistsWithStatusFalseButInWrongOrder() {
            assertTrue(FRIENDSHIP_DAO_INSTANCE.acceptFriendRequest(3L, 4L));
        }

    }

    @Nested
    @DisplayName("getFriends(Long accountId)")
    class TestGetFriends {

        @Test
        void whenOnlyOneFriendExistedWithStatusTrue() {
            List<Long> friendsId = new ArrayList<>();
            friendsId.add(2L);
            assertEquals(friendsId, FRIENDSHIP_DAO_INSTANCE.getFriendsIds(1L));
        }

        @Test
        void whenOnlyOneFriendExistedWithStatusFalse() {
            assertEquals(new ArrayList<>(), FRIENDSHIP_DAO_INSTANCE.getFriendsIds(3L));
        }

        @Test
        void whenAccountHasNoFriends() {
            assertEquals(new ArrayList<>(), FRIENDSHIP_DAO_INSTANCE.getFriendsIds(-1L));
        }

    }

    @Nested
    @DisplayName("deleteFriend(Long accountId, Long deletingFriendId)")
    class TestDeleteFriend {

        @Test
        void whenRecordWithFriendsExists() {
            assertTrue(FRIENDSHIP_DAO_INSTANCE.deleteFriend(1L, 2L));
        }

        @Test
        void whenRecordWithFriendsIsNotExisted() {
            assertFalse(FRIENDSHIP_DAO_INSTANCE.deleteFriend(999L, 777L));
        }

    }

}
