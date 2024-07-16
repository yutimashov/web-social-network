package com.getjavajob.training.timashovy.socialnetwork.dao.daoimpl.account;

import com.getjavajob.training.timashovy.socialnetwork.common.account.Password;
import com.getjavajob.training.timashovy.socialnetwork.dao.interfaces.account.PasswordDao;
import com.getjavajob.training.timashovy.socialnetwork.dao.util.exceptions.DaoException;
import com.getjavajob.training.timashovy.socialnetwork.util.ConnectionManagerTestUtils;
import org.junit.jupiter.api.*;

import java.util.Optional;

import static com.getjavajob.training.timashovy.socialnetwork.util.ConnectionManagerTestUtils.clearConnectionManagerMocks;
import static com.getjavajob.training.timashovy.socialnetwork.util.TestScriptsLoader.executeScript;
import static java.util.Optional.empty;
import static org.junit.jupiter.api.Assertions.*;

class PasswordDaoImplTest {

    private static final String CREATE_TABLES_FILEPATH = "scripts/account/create.sql";
    private static final String LOAD_DATA_FILEPATH = "scripts/account/load.sql";
    private static final String DROP_DB_FILEPATH = "scripts/account/drop.sql";
    private static final PasswordDao PASSWORD_DAO = new PasswordDaoImpl();
    private static final Password TEST_PASSWORD = new Password(1L, "test", "test");

    @BeforeAll
    public static void createTestTables() {
        executeScript(CREATE_TABLES_FILEPATH);
        executeScript(LOAD_DATA_FILEPATH);
    }

    @BeforeEach
    void setTestConnection() {
        ConnectionManagerTestUtils.mockConnectionManager();
    }

    @AfterAll
    public static void dropDataBaseAfterTestExecution() {
        executeScript(DROP_DB_FILEPATH);
    }

    @AfterEach
    void closeTestConnection() {
        clearConnectionManagerMocks();
    }

    @Nested
    @DisplayName("Long create(Long accountId, Password password)")
    class TestCreatePassword {

        @Test
        void shouldReturnPasswordIdWhenPasswordIsCreated() {
            assertEquals(2L, PASSWORD_DAO.create(new Password(1L, "test", "test")));
        }

        @Test
        void shouldThrowExceptionWhenAccountIdDoesNotExist() {
            Long nonExistingAccountId = -1L;
            Throwable exception = assertThrows(DaoException.class, () -> {
                PASSWORD_DAO.create(new Password(nonExistingAccountId, "test", "test"));
                throw new UnsupportedOperationException("Not supported");
            });
            assertEquals(DaoException.class, exception.getClass());
        }

    }

    @Nested
    @DisplayName("Optional<Password> getById(Long accountId)")
    class TestGetPasswordById {

        @Test
        void shouldReturnOptionalWithPasswordWhenPasswordExists() {
            Optional<Password> optionalPassword = PASSWORD_DAO.getById(1L);
            assertTrue(optionalPassword.isPresent());
            assertEquals(TEST_PASSWORD, optionalPassword.get());
        }

        @Test
        void shouldReturnEmptyOptionalWhenAccountIdDoesNotExist() {
            Long nonExistingAccountId = -1L;
            assertEquals(empty(), PASSWORD_DAO.getById(nonExistingAccountId));
        }

    }

    @Nested
    @DisplayName("Password findByEmail(String email)")
    class TestGetPasswordByAccountEmail {

        @Test
        void shouldReturnOptionalWithPasswordWhenPasswordExists() {
            Optional<Password> optionalPassword = PASSWORD_DAO.findByEmail("test");
            assertTrue(optionalPassword.isPresent());
            assertEquals(TEST_PASSWORD, optionalPassword.get());
        }

        @Test
        void shouldReturnEmptyOptionalWhenAccountEmailDoesNotExist() {
            String nonExistingEmail = "not@exist.com";
            assertEquals(empty(), PASSWORD_DAO.findByEmail(nonExistingEmail));
        }

    }

}
