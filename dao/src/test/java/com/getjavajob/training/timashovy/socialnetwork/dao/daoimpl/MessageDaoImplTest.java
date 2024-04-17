package com.getjavajob.training.timashovy.socialnetwork.dao.daoimpl;

import com.getjavajob.training.timashovy.socialnetwork.common.message.Message;
import com.getjavajob.training.timashovy.socialnetwork.dao.interfaces.BaseDao;
import org.junit.jupiter.api.*;

import static com.getjavajob.training.timashovy.socialnetwork.util.TestScriptsLoader.executeScript;

class MessageDaoImplTest {

    private static final String CREATE_TEST_TABLES_FILEPATH = "scripts/create_test_db.sql";
    private static final String LOAD_DATA_INTO_TEST_TABLES_FILEPATH = "scripts/load_test_data.sql";
    private static final String EMPTY_TEST_TABLES_FILEPATH = "scripts/clear_test_db.sql";
    private static final String DROP_TEST_DB_FILEPATH = "scripts/drop_test_db.sql";

    private static final BaseDao<Message> MESSAGE_DAO_INSTANCE = MessageDaoImpl.getInstance();

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

    @Test
    void create() {
    }

    @Test
    void getById() {
    }

    @Test
    void getAll() {
    }

    @Test
    void updateById() {
    }

    @Test
    void deleteById() {
    }

}