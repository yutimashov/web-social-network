package com.getjavajob.training.timashovy.socialnetwork.dao.daoimpl.message;

import com.getjavajob.training.timashovy.socialnetwork.common.message.Message;
import com.getjavajob.training.timashovy.socialnetwork.dao.interfaces.BaseDao;
import org.junit.jupiter.api.*;

import java.io.ByteArrayInputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.getjavajob.training.timashovy.socialnetwork.common.message.MessageType.ACCOUNT_PERSONAL;
import static com.getjavajob.training.timashovy.socialnetwork.dao.daoimpl.message.MessageDaoImpl.getInstance;
import static com.getjavajob.training.timashovy.socialnetwork.util.TestScriptsLoader.executeScript;
import static java.time.LocalDate.of;
import static org.junit.jupiter.api.Assertions.*;

class MessageDaoImplTest {

    private static final String CREATE_TEST_TABLES_FILEPATH = "scripts/message/create.sql";
    private static final String LOAD_DATA_INTO_TEST_TABLES_FILEPATH = "scripts/message/load.sql";
    private static final String EMPTY_TEST_TABLES_FILEPATH = "scripts/message/clear.sql";
    private static final String DROP_TEST_DB_FILEPATH = "scripts/message/drop.sql";

    private static final BaseDao<Message> MESSAGE_DAO = getInstance();
    private static final Message TEST_MESSAGE = new Message(1L, "test", ACCOUNT_PERSONAL);

    @BeforeAll
    static void createTestTables() {
        executeScript(CREATE_TEST_TABLES_FILEPATH);
    }

    @BeforeEach
    public void fillTestTablesWith2Records() {
        executeScript(LOAD_DATA_INTO_TEST_TABLES_FILEPATH);
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
