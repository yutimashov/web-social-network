package com.getjavajob.training.timashovy.socialnetwork.dao.daoimpl.friendship;

import com.getjavajob.training.timashovy.socialnetwork.dao.interfaces.friendship.FriendshipDao;
import com.getjavajob.training.timashovy.socialnetwork.util.ConnectionManagerTestUtils;
import org.junit.jupiter.api.*;

import java.util.ArrayList;
import java.util.List;

import static com.getjavajob.training.timashovy.socialnetwork.util.ConnectionManagerTestUtils.clearConnectionManagerMocks;
import static com.getjavajob.training.timashovy.socialnetwork.util.TestScriptsLoader.executeScript;
import static org.junit.jupiter.api.Assertions.*;

class FriendshipDaoImplTest {

    private static final String CREATE_TEST_TABLES_FILEPATH = "scripts/friendship/create.sql";
    private static final String LOAD_TEST_TABLES_FILEPATH = "scripts/friendship/load.sql";
    private static final String CLEAR_TEST_TABLES_FILEPATH = "scripts/friendship/clear.sql";
    private static final String DROP_TEST_DB_FILEPATH = "scripts/friendship/drop.sql";
    private static final FriendshipDao FRIENDSHIP_DAO = new FriendshipDaoImpl();

    @BeforeAll
    public static void createTestTables() {
        executeScript(CREATE_TEST_TABLES_FILEPATH);
    }

    @BeforeEach
    void setTestConnection() {
        ConnectionManagerTestUtils.mockConnectionManager();
    }

    @BeforeEach
    public void loadTestTablesWithData() {
        executeScript(LOAD_TEST_TABLES_FILEPATH);
    }

    @AfterEach
    void closeTestConnection() {
        clearConnectionManagerMocks();
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
