package com.getjavajob.training.timashovy.socialnetwork.dao.daoimpl.account;

import com.getjavajob.training.timashovy.socialnetwork.common.account.Phone;
import com.getjavajob.training.timashovy.socialnetwork.dao.interfaces.account.PhoneDao;
import com.getjavajob.training.timashovy.socialnetwork.dao.util.DaoException;
import org.junit.jupiter.api.*;

import java.util.ArrayList;
import java.util.List;

import static com.getjavajob.training.timashovy.socialnetwork.common.account.PhoneType.PERSONAL;
import static com.getjavajob.training.timashovy.socialnetwork.common.account.PhoneType.WORKING;
import static com.getjavajob.training.timashovy.socialnetwork.dao.daoimpl.account.PhoneDaoImpl.createInstance;
import static com.getjavajob.training.timashovy.socialnetwork.dao.util.dbutils.dbconnection.ConnectionManager.getConnection;
import static com.getjavajob.training.timashovy.socialnetwork.util.TestScriptsLoader.executeScript;
import static java.util.Collections.emptyList;
import static org.junit.jupiter.api.Assertions.*;

class PhoneDaoImplTest {

    private static final String CREATE_TABLES_FILEPATH = "scripts/account/create.sql";
    private static final String LOAD_DATA_FILEPATH = "scripts/account/load.sql";
    private static final String CLEAR_TABLES_FILEPATH = "scripts/account/clear.sql";
    private static final String DROP_DB_FILEPATH = "scripts/account/drop.sql";
    private static final PhoneDao PHONE_DAO = createInstance();

    @BeforeEach
    public void fillTestTablesWith2Records() {
        executeScript(CLEAR_TABLES_FILEPATH);
        executeScript(LOAD_DATA_FILEPATH);
    }

    @BeforeAll
    public static void createTestTables() {
        executeScript(CREATE_TABLES_FILEPATH);
        executeScript(LOAD_DATA_FILEPATH);
    }

    @AfterAll
    public static void dropDataBaseAfterTestExecution() {
        executeScript(DROP_DB_FILEPATH);
    }

    @Nested
    @DisplayName("Long create(Phone phone)")
    class TestCreatePhone {

        @Test
        void shouldReturnPhoneIdWhenPhoneWasCreated() {
            assertEquals(3L, PHONE_DAO.create(getConnection(), new Phone(PERSONAL, "test", 1L)));
        }

        @Test
        void shouldThrowExceptionWhenCreationFailed() {
            Throwable exception = assertThrows(DaoException.class, () -> {
                PHONE_DAO.create(getConnection(), new Phone(PERSONAL, "test", -1L));
                throw new UnsupportedOperationException("Not supported");
            });
            assertEquals(DaoException.class, exception.getClass());
        }

    }

    @Nested
    @DisplayName("List<Phone> getAll(Long accountId)")
    class TestGetAllPhones {

        @Test
        void shouldReturnPhonesWhenAccountHasPhones() {
            List<Phone> expectedPhones = new ArrayList<>();
            expectedPhones.add(new Phone(1L, PERSONAL, "+375291112233", 1L));
            expectedPhones.add(new Phone(2L, WORKING, "+375291112233", 1L));
            assertIterableEquals(expectedPhones, PHONE_DAO.getAll(1L));
        }

        @Test
        void shouldReturnEmptyListWhenAccountHasNoPhones() {
            assertIterableEquals(emptyList(), PHONE_DAO.getAll(2L));
        }

    }

    @Nested
    @DisplayName("boolean update(Long phoneId, String newPhoneNumber)")
    class TestUpdatePhone {

        @Test
        void shouldReturnTrueWhenUpdateWithSuccess() {
            assertTrue(PHONE_DAO.update(1L, "test"));
        }

    }

}
