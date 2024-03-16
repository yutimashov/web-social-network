package com.getjavajob.training.timashovy.socialnetwork.dao.daoimpl;

import com.getjavajob.training.timashovy.socialnetwork.common.account.Account;
import com.getjavajob.training.timashovy.socialnetwork.dao.interfaces.AccountGroupDao;
import com.getjavajob.training.timashovy.socialnetwork.dao.interfaces.TableConstraintsValidator;
import com.getjavajob.training.timashovy.socialnetwork.dao.util.exceptions.DaoException;
import org.junit.jupiter.api.*;

import java.util.ArrayList;

import static com.getjavajob.training.timashovy.socialnetwork.dao.daoimpl.account.AccountDaoImpl.getAccountDaoInstance;
import static com.getjavajob.training.timashovy.socialnetwork.util.TestScriptsLoader.executeScript;
import static java.time.LocalDate.of;
import static org.junit.jupiter.api.Assertions.*;

class AccountDaoImplTest {

    private static final String CREATE_TEST_TABLES_FILEPATH = "scripts/create_test_db.sql";
    private static final String LOAD_DATA_INTO_TEST_TABLES_FILEPATH = "scripts/load_test_data.sql";
    private static final String EMPTY_TEST_TABLES_FILEPATH = "scripts/clear_test_db.sql";
    private static final String DROP_TEST_DB_FILEPATH = "scripts/drop_test_db.sql";
    private static final AccountGroupDao<Account> ACCOUNT_DAO_INSTANCE = getAccountDaoInstance();
    private static final Account TEST_ACCOUNT = new Account.Builder()
            .id(1L)
            .firstName("")
            .lastName("")
            .middleName("")
            .birthDate(of(1800, 1, 1))
            .personalAddress("")
            .workAddress("")
            .email("")
            .icq("")
            .skype("")
            .additionalInfo("")
            .build();

    private void restoreTestAccountDefaultState() {
        TEST_ACCOUNT.setId(1L);
        TEST_ACCOUNT.setFirstName("");
        TEST_ACCOUNT.setLastName("");
        TEST_ACCOUNT.setMiddleName("");
        TEST_ACCOUNT.setBirthDate(of(1800, 1, 1));
        TEST_ACCOUNT.setPersonalAddress("");
        TEST_ACCOUNT.setPersonalAddress("");
        TEST_ACCOUNT.setWorkAddress("");
        TEST_ACCOUNT.setEmail("");
        TEST_ACCOUNT.setEmail("");
        TEST_ACCOUNT.setIcq("");
        TEST_ACCOUNT.setSkype("");
        TEST_ACCOUNT.setAdditionalInfo("");
    }

    private void setTestAccountEqualsToRecordInTestTable() {
        TEST_ACCOUNT.setId(1L);
        TEST_ACCOUNT.setFirstName("test");
        TEST_ACCOUNT.setLastName("test");
        TEST_ACCOUNT.setMiddleName("test");
        TEST_ACCOUNT.setBirthDate(of(1800, 1, 1));
        TEST_ACCOUNT.setPersonalAddress("test");
        TEST_ACCOUNT.setPersonalAddress("test");
        TEST_ACCOUNT.setWorkAddress("test");
        TEST_ACCOUNT.setEmail("test");
        TEST_ACCOUNT.setEmail("test");
        TEST_ACCOUNT.setIcq("test");
        TEST_ACCOUNT.setSkype("test");
        TEST_ACCOUNT.setAdditionalInfo("test");
    }

    @BeforeAll
    public static void createTestTables() {
        executeScript(CREATE_TEST_TABLES_FILEPATH);
    }

    @BeforeEach
    public void fillTestTablesWith2Records() {
        restoreTestAccountDefaultState();
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
    @DisplayName("validateEntityUniquenessFieldsConstraint(String fieldName, E fieldValue)")
    class TestValidateUniquenessFieldConstraint {

        @Test
        void whenAccountEmailIsNotUnique() {
            String duplicatedEmail = "test";
            Throwable exception = assertThrows(IllegalArgumentException.class, () -> {
                ((TableConstraintsValidator) ACCOUNT_DAO_INSTANCE).validateEntityFieldUniqueness("email",
                        duplicatedEmail);
                throw new UnsupportedOperationException("Not supported");
            });
            assertEquals(IllegalArgumentException.class, exception.getClass());
        }

        @Test
        void createAccountWithPersonalPhoneNumberUniqueViolation() {
            String duplicatedPersonalPhoneNumber = "test";
            Throwable exception = assertThrows(IllegalArgumentException.class, () -> {
                ((TableConstraintsValidator) ACCOUNT_DAO_INSTANCE).validateEntityFieldUniqueness("personal_phone_number",
                        duplicatedPersonalPhoneNumber);
                throw new UnsupportedOperationException("Not supported");
            });
            assertEquals(IllegalArgumentException.class, exception.getClass());
        }

        @Test
        void createAccountWithIcqUniqueViolation() {
            String duplicatedIcq = "test";
            Throwable exception = assertThrows(IllegalArgumentException.class, () -> {
                ((TableConstraintsValidator) ACCOUNT_DAO_INSTANCE).validateEntityFieldUniqueness("icq",
                        duplicatedIcq);
                throw new UnsupportedOperationException("Not supported");
            });
            assertEquals(IllegalArgumentException.class, exception.getClass());
        }

        @Test
        void createAccountWithSkypeUniqueViolation() {
            String duplicatedSkype = "test";
            Throwable exception = assertThrows(IllegalArgumentException.class, () -> {
                ((TableConstraintsValidator) ACCOUNT_DAO_INSTANCE).validateEntityFieldUniqueness("skype",
                        duplicatedSkype);
                throw new UnsupportedOperationException("Not supported");
            });
            assertEquals(IllegalArgumentException.class, exception.getClass());
        }

    }

    @Nested
    @DisplayName("Long create(Account account)")
    class TestCreateAccount {

        @Test
        void createInEmptyTableReturnsId1() {
            emptyTestTables();
            Long actual = getAccountDaoInstance().create(TEST_ACCOUNT);
            assertEquals(1L, actual);
        }

        @Test
        void createAccountWithFirstNameNotNullViolation() {
            emptyTestTables();
            TEST_ACCOUNT.setFirstName(null);
            Throwable exception = assertThrows(DaoException.class, () -> {
                getAccountDaoInstance().create(TEST_ACCOUNT);
                throw new UnsupportedOperationException("Not supported");
            });
            restoreTestAccountDefaultState();
            assertTrue(exception.getMessage().contains("NULL not allowed for column \"FIRST_NAME\""));
        }

        @Test
        void createAccountWithLastNameNotNullViolation() {
            emptyTestTables();
            TEST_ACCOUNT.setLastName(null);
            Throwable exception = assertThrows(DaoException.class, () -> {
                getAccountDaoInstance().create(TEST_ACCOUNT);
                throw new UnsupportedOperationException("Not supported");
            });
            restoreTestAccountDefaultState();
            assertTrue(exception.getMessage().contains("NULL not allowed for column \"LAST_NAME\""));
        }

        @Test
        void createAccountWithPersonalPhoneNumberNotNullViolation() {
            emptyTestTables();
            TEST_ACCOUNT.setPersonalPhoneNumber(null);
            Throwable exception = assertThrows(DaoException.class, () -> {
                getAccountDaoInstance().create(TEST_ACCOUNT);
                throw new UnsupportedOperationException("Not supported");
            });
            restoreTestAccountDefaultState();
            assertTrue(exception.getMessage().contains("NULL not allowed for column \"PERSONAL_PHONE_NUMBER\""));
        }

    }

    @Nested
    @DisplayName("Optional<Account> getById(Long id)")
    class TestGetById {

        @Test
        void getByIdGetExistingAccount() {
            setTestAccountEqualsToRecordInTestTable();
            assertEquals(TEST_ACCOUNT, getAccountDaoInstance().getById(1L));
        }

        @Test
        void getByIdGetNonExistingAccount() {
            emptyTestTables();
            Throwable exception = assertThrows(IllegalArgumentException.class, () -> {
                getAccountDaoInstance().getById(1L);
                throw new UnsupportedOperationException("Not supported");
            });
            assertEquals(IllegalArgumentException.class, exception.getClass());
        }

    }

    @Nested
    @DisplayName("List<Account> getAll()")
    class TestGetAll {

        @Test
        void getAllOnEmptyTable() {
            emptyTestTables();
            assertEquals(new ArrayList<Account>(), getAccountDaoInstance().getAll());
        }

    }

    @Nested
    @DisplayName("boolean updateById(Long id, Account account)")
    class TestUpdateById {

        @Test
        void updateByIdUpdateNonExistingId() {
            assertFalse(getAccountDaoInstance().updateById(-1L, TEST_ACCOUNT));
        }

        @Test
        void updateByIdUpdateExistingAccount() {
            assertTrue(getAccountDaoInstance().updateById(1L, TEST_ACCOUNT));
        }

    }

    @Nested
    @DisplayName("boolean deleteById(Long id)")
    class TestDeleteById {

        @Test
        void deleteByIdNonExistingId() {
            assertFalse(getAccountDaoInstance().deleteById(-1L));
        }

        @Test
        void deleteByIdDeleteExistingAccount() {
            assertTrue(getAccountDaoInstance().deleteById(1L));
        }

    }

}
