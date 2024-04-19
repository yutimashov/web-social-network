package com.getjavajob.training.timashovy.socialnetwork.dao.daoimpl.account;

import com.getjavajob.training.timashovy.socialnetwork.common.account.Account;
import com.getjavajob.training.timashovy.socialnetwork.dao.interfaces.BaseDao;
import com.getjavajob.training.timashovy.socialnetwork.dao.interfaces.TableConstraintsValidator;
import com.getjavajob.training.timashovy.socialnetwork.dao.util.DaoException;
import org.junit.jupiter.api.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.getjavajob.training.timashovy.socialnetwork.common.account.Role.REGULAR;
import static com.getjavajob.training.timashovy.socialnetwork.dao.daoimpl.account.AccountDaoImpl.getInstance;
import static com.getjavajob.training.timashovy.socialnetwork.util.TestScriptsLoader.executeScript;
import static java.time.LocalDate.of;
import static java.util.Optional.empty;
import static org.junit.jupiter.api.Assertions.*;

class AccountDaoImplTest {

    private static final String CREATE_TEST_TABLES_FILEPATH = "scripts/create_test_db.sql";
    private static final String LOAD_DATA_INTO_TEST_TABLES_FILEPATH = "scripts/load_test_data.sql";
    private static final String EMPTY_TEST_TABLES_FILEPATH = "scripts/clear_test_db.sql";
    private static final String DROP_TEST_DB_FILEPATH = "scripts/drop_test_db.sql";
    private static final BaseDao<Account> ACCOUNT_DAO_INSTANCE = getInstance();
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
            .role(REGULAR)
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
        void shouldThrowExceptionWhenEmailIsNotUnique() {
            String duplicatedEmail = "test";
            Throwable exception = assertThrows(IllegalArgumentException.class, () -> {
                ((TableConstraintsValidator) ACCOUNT_DAO_INSTANCE).validateEntityFieldUniqueness("email",
                        duplicatedEmail);
                throw new UnsupportedOperationException("Not supported");
            });
            assertEquals(IllegalArgumentException.class, exception.getClass());
        }

        @Test
        void shouldThrowExceptionWhenICQIsNotUnique() {
            String duplicatedIcq = "test";
            Throwable exception = assertThrows(IllegalArgumentException.class, () -> {
                ((TableConstraintsValidator) ACCOUNT_DAO_INSTANCE).validateEntityFieldUniqueness("icq",
                        duplicatedIcq);
                throw new UnsupportedOperationException("Not supported");
            });
            assertEquals(IllegalArgumentException.class, exception.getClass());
        }

        @Test
        void shouldThrowExceptionWhenSkypeIsNotUnique() {
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
        void shouldReturn1LWhenAccountCreatedInEmptyTable() {
            emptyTestTables();
            assertEquals(1L, ACCOUNT_DAO_INSTANCE.create(TEST_ACCOUNT));
        }

        @Test
        void shouldThrowExceptionWhenFirstNameIsNull() {
            emptyTestTables();
            TEST_ACCOUNT.setFirstName(null);
            Throwable exception = assertThrows(DaoException.class, () -> {
                ACCOUNT_DAO_INSTANCE.create(TEST_ACCOUNT);
                throw new UnsupportedOperationException("Not supported");
            });
            restoreTestAccountDefaultState();
            assertTrue(exception.getMessage().contains("NULL not allowed for column \"FIRST_NAME\""));
        }

        @Test
        void shouldThrowExceptionWhenLastNameIsNull() {
            emptyTestTables();
            TEST_ACCOUNT.setLastName(null);
            Throwable exception = assertThrows(DaoException.class, () -> {
                ACCOUNT_DAO_INSTANCE.create(TEST_ACCOUNT);
                throw new UnsupportedOperationException("Not supported");
            });
            restoreTestAccountDefaultState();
            assertTrue(exception.getMessage().contains("NULL not allowed for column \"LAST_NAME\""));
        }

    }

    @Nested
    @DisplayName("Optional<Account> getById(Long id)")
    class TestGetById {

        @Test
        void shouldReturnOptionalWithAccountWhenAccountExists() {
            setTestAccountEqualsToRecordInTestTable();
            assertEquals(Optional.of(TEST_ACCOUNT), ACCOUNT_DAO_INSTANCE.getById(1L));
        }

        @Test
        void shouldReturnEmptyOptionalWhenAccountNotExists() {
            emptyTestTables();
            assertEquals(empty(), ACCOUNT_DAO_INSTANCE.getById(1L));
        }

    }

    @Nested
    @DisplayName("List<Account> getAll()")
    class TestGetAll {

        @Test
        void shouldReturnEmptyListWhenTableIsEmpty() {
            emptyTestTables();
            assertEquals(new ArrayList<Account>(), ACCOUNT_DAO_INSTANCE.getAll());
        }

        @Test
        void shouldReturnActualListWhenTableIsNotEmpty() {
            List<Account> accounts = new ArrayList<>();
            accounts.add(new Account.Builder().id(1L).firstName("test").middleName("test").lastName("test")
                    .birthDate(of(1800, 1, 1)).personalAddress("test").workAddress("test")
                    .email("test").icq("test").skype("test").additionalInfo("test").role(REGULAR).build());
            accounts.add(new Account.Builder().id(2L).firstName("test1").middleName("test1").lastName("test1")
                    .birthDate(of(1800, 1, 1)).personalAddress("test1").workAddress("test1")
                    .email("test1").icq("test1").skype("test1").additionalInfo("test1").role(REGULAR).build());
            accounts.add(new Account.Builder().id(3L).firstName("test2").middleName("test2").lastName("test2")
                    .birthDate(of(1800, 1, 1)).personalAddress("test2").workAddress("test2")
                    .email("test2").icq("test2").skype("test2").additionalInfo("test2").role(REGULAR).build());
            accounts.add(new Account.Builder().id(4L).firstName("test3").middleName("test3").lastName("test3")
                    .birthDate(of(1800, 1, 1)).personalAddress("test3").workAddress("test3")
                    .email("test3").icq("test3").skype("test3").additionalInfo("test3").role(REGULAR).build());
            assertEquals(accounts, ACCOUNT_DAO_INSTANCE.getAll());
        }

    }

    @Nested
    @DisplayName("boolean updateById(Long id, Account account)")
    class TestUpdateById {

        @Test
        void shouldReturnFalseWhenAccountNotExists() {
            assertFalse(ACCOUNT_DAO_INSTANCE.updateById(-1L, TEST_ACCOUNT));
        }

        @Test
        void shouldReturnTrueWhenAccountExists() {
            assertTrue(ACCOUNT_DAO_INSTANCE.updateById(1L, TEST_ACCOUNT));
        }

    }

    @Nested
    @DisplayName("boolean deleteById(Long id)")
    class TestDeleteById {

        @Test
        void shouldReturnFalseWhenAccountNotExists() {
            assertFalse(ACCOUNT_DAO_INSTANCE.deleteById(-1L));
        }

        @Test
        void shouldReturnTrueWhenAccountExists() {
            assertTrue(ACCOUNT_DAO_INSTANCE.deleteById(1L));
        }

    }

}
