package com.getjavajob.training.timashovy.socialnetwork.dao.daoimpl.message;

import com.getjavajob.training.timashovy.socialnetwork.common.message.Message;
import com.getjavajob.training.timashovy.socialnetwork.dao.interfaces.MessageDao;
import com.getjavajob.training.timashovy.socialnetwork.util.ConnectionManagerTestUtils;
import org.junit.jupiter.api.*;

import java.util.Optional;

import static com.getjavajob.training.timashovy.socialnetwork.util.ConnectionManagerTestUtils.clearConnectionManagerMocks;
import static com.getjavajob.training.timashovy.socialnetwork.util.TestScriptsLoader.executeScript;
import static java.time.LocalDate.of;
import static org.junit.jupiter.api.Assertions.*;

class GroupMessageDaoImplTest {

    private static final String CREATE_TEST_TABLES_FILEPATH = "scripts/message/create.sql";
    private static final String LOAD_DATA_INTO_TEST_TABLES_FILEPATH = "scripts/message/load.sql";
    private static final String EMPTY_TEST_TABLES_FILEPATH = "scripts/message/clear.sql";
    private static final String DROP_TEST_DB_FILEPATH = "scripts/message/drop.sql";

    private static final MessageDao MESSAGE_DAO = new GroupMessageDaoImpl();
    private static final Message TEST_MESSAGE = new Message.Builder()
            .id(1L)
            .destinationId(1L)
            .accountAuthorId(1L)
            .text("test")
            .creationDate(of(2020, 1, 1))
            .build();

    @BeforeAll
    static void createTestTables() {
        executeScript(CREATE_TEST_TABLES_FILEPATH);
    }

    @BeforeEach
    void setTestConnection() {
        ConnectionManagerTestUtils.mockConnectionManager();
    }

    @BeforeEach
    public void fillTestTablesWith2Records() {
        executeScript(LOAD_DATA_INTO_TEST_TABLES_FILEPATH);
    }

    @AfterEach
    void closeTestConnection() {
        clearConnectionManagerMocks();
    }

    @AfterEach
    public void emptyTestTables() {
        executeScript(EMPTY_TEST_TABLES_FILEPATH);
    }

    @AfterAll
    public static void dropDataBaseAfterTestExecution() {
        executeScript(DROP_TEST_DB_FILEPATH);
    }

    @Nested
    @DisplayName("Long create()")
    class TestCreate {

        @Test
        void shouldReturn1LWhenMessageCreatedInEmptyTable() {
            emptyTestTables();
            fillTestTablesWith2Records();
            assertEquals(2L, MESSAGE_DAO.create(TEST_MESSAGE));
        }

    }

    @Nested
    @DisplayName("void getById()")
    class TestGetById {

        @Test
        void shouldReturnEmptyOptionalWhenMessageNotExists() {
            emptyTestTables();
            assertEquals(Optional.empty(), MESSAGE_DAO.getById(1L));
        }

        @Test
        void shouldRerun1LWhenTryToGetExistingOnlyOneInTableMessage() {
            assertEquals(Optional.of(TEST_MESSAGE), MESSAGE_DAO.getById(1L));
        }

    }

    @Nested
    @DisplayName("boolean updateById(Long id, Message message")
    class TestUpdateById {

        @Test
        void shouldReturnFalseWhenMessageNotExists() {
            assertFalse(MESSAGE_DAO.updateById(-1L, TEST_MESSAGE));
        }

        @Test
        void shouldReturnTrueWhenAccountExists() {
            assertTrue(MESSAGE_DAO.updateById(1L, TEST_MESSAGE));
        }

    }

    @Nested
    @DisplayName("boolean deleteById(Long id)")
    class TestDeleteById {

        @Test
        void shouldReturnFalseWhenMessageNotExists() {
            assertFalse(MESSAGE_DAO.deleteById(-1L));
        }

        @Test
        void shouldReturnTrueWhenMessageExists() {
            assertTrue(MESSAGE_DAO.deleteById(1L));
        }

    }

}
