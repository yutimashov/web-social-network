package com.getjavajob.training.timashovy.socialnetwork.dao.daoimpl.message;

import com.getjavajob.training.timashovy.socialnetwork.common.message.MessageImage;
import com.getjavajob.training.timashovy.socialnetwork.dao.interfaces.BaseDao;
import org.junit.jupiter.api.*;

import java.io.ByteArrayInputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.getjavajob.training.timashovy.socialnetwork.dao.daoimpl.message.MessageImageDaoImpl.getInstance;
import static com.getjavajob.training.timashovy.socialnetwork.util.TestScriptsLoader.executeScript;
import static org.junit.jupiter.api.Assertions.*;

class MessageImageDaoImplTest {

    private static final String CREATE_TEST_TABLES_FILEPATH = "scripts/message/create.sql";
    private static final String LOAD_DATA_INTO_TEST_TABLES_FILEPATH = "scripts/message/load.sql";
    private static final String EMPTY_TEST_TABLES_FILEPATH = "scripts/message/clear.sql";
    private static final String DROP_TEST_DB_FILEPATH = "scripts/message/drop.sql";
    private static final BaseDao<MessageImage> MESSAGE_IMAGE_DAO = getInstance();
    private static final MessageImage TEST_MESSAGE_IMAGE = new MessageImage(1L,
            new ByteArrayInputStream("test".getBytes()));

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
        void shouldReturn1LWhenCreateInEmptyTable() {
            emptyTestTables();
            assertEquals(1L, MESSAGE_IMAGE_DAO.create(TEST_MESSAGE_IMAGE));
        }

    }

    @Nested
    @DisplayName("void getById()")
    class TestGetById {

        @Test
        void shouldReturnEmptyOptionalWhenMessageImageNotExists() {
            emptyTestTables();
            assertEquals(Optional.empty(), MESSAGE_IMAGE_DAO.getById(1L));
        }

        @Test
        void shouldReturn1LWhenTryToGetExistingOnlyOneInTableMessageImage() {
            assertEquals(Optional.of(TEST_MESSAGE_IMAGE), MESSAGE_IMAGE_DAO.getById(1L));
        }

    }

    @Nested
    @DisplayName("void getAll()")
    class TestGetAll {

        @Test
        void shouldReturnEmptyListWhenTableIsEmpty() {
            emptyTestTables();
            assertEquals(new ArrayList<MessageImage>(), MESSAGE_IMAGE_DAO.getAll());
        }

        @Test
        void shouldReturnListWithTwoMessageImagesWhen2MessageImagesInTable() {
            List<MessageImage> messages = new ArrayList<>();
            messages.add(TEST_MESSAGE_IMAGE);
            assertEquals(messages, MESSAGE_IMAGE_DAO.getAll());
        }

    }

    @Nested
    @DisplayName("boolean updateById(Long id, MessageImage messageImage")
    class TestUpdateById {

        @Test
        void shouldReturnFalseWhenMessageImageNotExists() {
            assertFalse(MESSAGE_IMAGE_DAO.updateById(-1L, TEST_MESSAGE_IMAGE));
        }

        @Test
        void shouldReturnTrueWhenMessageImageExists() {
            assertTrue(MESSAGE_IMAGE_DAO.updateById(1L, TEST_MESSAGE_IMAGE));
        }

    }

    @Nested
    @DisplayName("boolean deleteById(Long id)")
    class TestDeleteById {

        @Test
        void shouldReturnFalseWhenMessageNotExists() {
            assertFalse(MESSAGE_IMAGE_DAO.deleteById(-1L));
        }

        @Test
        void shouldReturnTrueWhenMessageExists() {
            assertTrue(MESSAGE_IMAGE_DAO.deleteById(1L));
        }

    }

}