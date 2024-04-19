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

    private static final String CREATE_TABLES_FILEPATH = "scripts/message/create.sql";
    private static final String LOAD_DATA_FILEPATH = "scripts/message/load.sql";
    private static final String CLEAR_TABLES_FILEPATH = "scripts/message/clear.sql";
    private static final String DROP_DB_FILEPATH = "scripts/message/drop.sql";
    private static final BaseDao<MessageImage> MESSAGE_IMAGE_DAO = getInstance();
    private static final MessageImage TEST_MESSAGE_IMAGE = new MessageImage(1L,
            new ByteArrayInputStream("test".getBytes()), 1L);

    @BeforeAll
    static void createTestTables() {
        executeScript(CREATE_TABLES_FILEPATH);
    }

    @BeforeEach
    public void fillTestTablesWith2Records() {
        executeScript(CLEAR_TABLES_FILEPATH);
        executeScript(LOAD_DATA_FILEPATH);
    }

    @AfterAll
    public static void dropDataBaseAfterTestExecution() {
        executeScript(DROP_DB_FILEPATH);
    }

    @Nested
    @DisplayName("Long create()")
    class TestCreate {

        @Test
        void shouldReturn2LWhenCreateSecondMessageImageInTable() {
            assertEquals(2L, MESSAGE_IMAGE_DAO.create(TEST_MESSAGE_IMAGE));
        }

    }

    @Nested
    @DisplayName("void getById()")
    class TestGetById {

        @Test
        void shouldReturnEmptyOptionalWhenMessageImageNotExists() {
            executeScript(CLEAR_TABLES_FILEPATH);
            assertEquals(Optional.empty(), MESSAGE_IMAGE_DAO.getById(1L));
        }

        @Test
        void shouldReturn1LWhenTryToGetExistingOnlyOneInTableMessageImage() {
            MessageImage messageImage = null;
            if (MESSAGE_IMAGE_DAO.getById(1L).isPresent()) {
                messageImage = MESSAGE_IMAGE_DAO.getById(1L).get();
            }
            assertEquals(Optional.of(TEST_MESSAGE_IMAGE).get(), messageImage);
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