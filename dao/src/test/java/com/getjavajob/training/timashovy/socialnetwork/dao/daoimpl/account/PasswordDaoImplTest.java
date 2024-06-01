package com.getjavajob.training.timashovy.socialnetwork.dao.daoimpl.account;

import com.getjavajob.training.timashovy.socialnetwork.common.account.Password;
import com.getjavajob.training.timashovy.socialnetwork.dao.interfaces.account.PasswordDao;
import com.getjavajob.training.timashovy.socialnetwork.dao.util.DaoException;
import com.getjavajob.training.timashovy.socialnetwork.dao.util.dbutils.dbconnection.TransactionManager;
import org.junit.jupiter.api.*;

import static com.getjavajob.training.timashovy.socialnetwork.dao.daoimpl.account.PasswordDaoImpl.getInstance;
import static com.getjavajob.training.timashovy.socialnetwork.dao.util.dbutils.dbconnection.ConnectionManager.getConnection;
import static com.getjavajob.training.timashovy.socialnetwork.util.TestScriptsLoader.executeScript;
import static java.util.Optional.empty;
import static org.junit.jupiter.api.Assertions.*;
import static java.util.Optional.of;

class PasswordDaoImplTest {

    private static final String CREATE_TABLES_FILEPATH = "scripts/account/create.sql";
    private static final String LOAD_DATA_FILEPATH = "scripts/account/load.sql";
    private static final String DROP_DB_FILEPATH = "scripts/account/drop.sql";
    private static final PasswordDao PASSWORD_DAO = getInstance(TransactionManager.getInstance());
    private static final Password TEST_PASSWORD = new Password(1L, "test", "test");

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
            Password actualPassword = null;
            if (PASSWORD_DAO.getById(1L).isPresent()) {
                actualPassword = PASSWORD_DAO.getById(1L).get();
            }
            assertEquals(of(TEST_PASSWORD).get(), actualPassword);
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
            Password actualPassword = null;
            if (PASSWORD_DAO.findByEmail("test").isPresent()) {
                actualPassword = PASSWORD_DAO.findByEmail("test").get();
            }
            assertEquals(of(TEST_PASSWORD).get(), actualPassword);
        }

        @Test
        void shouldReturnEmptyOptionalWhenAccountEmailDoesNotExist() {
            String nonExistingEmail = "not@exist.com";
            assertEquals(empty(), PASSWORD_DAO.findByEmail(nonExistingEmail));
        }

    }

}
