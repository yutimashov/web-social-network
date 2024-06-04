package com.getjavajob.training.timashovy.socialnetwork.dao.daoimpl.account;

import com.getjavajob.training.timashovy.socialnetwork.common.account.Account;
import com.getjavajob.training.timashovy.socialnetwork.common.account.Phone;
import com.getjavajob.training.timashovy.socialnetwork.dao.interfaces.BaseDao;
import com.getjavajob.training.timashovy.socialnetwork.dao.util.DaoException;
import com.getjavajob.training.timashovy.socialnetwork.dao.util.dbutils.dbconnection.TransactionManager;
import org.junit.jupiter.api.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.getjavajob.training.timashovy.socialnetwork.common.account.AccountRole.REGULAR;
import static com.getjavajob.training.timashovy.socialnetwork.common.account.PhoneType.PERSONAL;
import static com.getjavajob.training.timashovy.socialnetwork.common.account.PhoneType.WORKING;
import static com.getjavajob.training.timashovy.socialnetwork.util.TestScriptsLoader.executeScript;
import static java.time.LocalDate.of;
import static java.util.Optional.empty;
import static org.junit.jupiter.api.Assertions.*;

class AccountDaoImplTest {

    private static final String CREATE_TABLES_FILEPATH = "scripts/account/create.sql";
    private static final String LOAD_DATA_FILEPATH = "scripts/account/load.sql";
    private static final String CLEAR_TABLES_FILEPATH = "scripts/account/clear.sql";
    private static final String DROP_DB_FILEPATH = "scripts/account/drop.sql";
    private static final BaseDao<Account> ACCOUNT_DAO_INSTANCE = new AccountDaoImpl(
            new PhoneDaoImpl(new TransactionManager()), new TransactionManager());

    private static final Account TEST_ACCOUNT = new Account.Builder()
            .id(1L).firstName("").lastName("")
            .middleName("")
            .birthDate(of(2000, 1, 1))
            .personalPhoneNumber(new ArrayList<>())
            .workPhoneNumber(new ArrayList<>())
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
        TEST_ACCOUNT.setBirthDate(of(2000, 1, 1));
        TEST_ACCOUNT.setPersonalPhoneNumber(new ArrayList<>());
        TEST_ACCOUNT.setWorkPhoneNumber(new ArrayList<>());
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
        List<Phone> personalPhones = new ArrayList<>();
        personalPhones.add(new Phone(1L, PERSONAL, "+375291112233", 1L));
        List<Phone> workingPhones = new ArrayList<>();
        workingPhones.add(new Phone(2L, WORKING, "+375291112233", 1L));
        TEST_ACCOUNT.setId(1L);
        TEST_ACCOUNT.setFirstName("test");
        TEST_ACCOUNT.setLastName("test");
        TEST_ACCOUNT.setMiddleName("test");
        TEST_ACCOUNT.setBirthDate(of(2000, 1, 1));
        TEST_ACCOUNT.setPersonalPhoneNumber(personalPhones);
        TEST_ACCOUNT.setWorkPhoneNumber(workingPhones);
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
        executeScript(CREATE_TABLES_FILEPATH);
    }

    @BeforeEach
    public void fillTestTablesWith2Records() {
        restoreTestAccountDefaultState();
        executeScript(CLEAR_TABLES_FILEPATH);
        executeScript(LOAD_DATA_FILEPATH);
    }

    @AfterAll
    public static void dropDataBaseAfterTestExecution() {
        executeScript(DROP_DB_FILEPATH);
    }

    @Nested
    @DisplayName("Long create(Account account)")
    class TestCreateAccount {

        @Test
        void shouldReturn1LWhenAccountCreatedInEmptyTable() {
            executeScript(CLEAR_TABLES_FILEPATH);
            assertEquals(1L, ACCOUNT_DAO_INSTANCE.create(TEST_ACCOUNT));
        }

        @Test
        void shouldThrowExceptionWhenFirstNameIsNull() {
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
            Account actualAccount = null;
            if (ACCOUNT_DAO_INSTANCE.getById(1L).isPresent()) {
                actualAccount = ACCOUNT_DAO_INSTANCE.getById(1L).get();
            }
            assertEquals(Optional.of(TEST_ACCOUNT).get(), actualAccount);
        }

        @Test
        void shouldReturnEmptyOptionalWhenAccountNotExists() {
            executeScript(CLEAR_TABLES_FILEPATH);
            assertEquals(empty(), ACCOUNT_DAO_INSTANCE.getById(1L));
        }

    }

    @Nested
    @DisplayName("List<Account> getAll()")
    class TestGetAll {

        @Test
        void shouldReturnEmptyListWhenTableIsEmpty() {
            executeScript(CLEAR_TABLES_FILEPATH);
            assertEquals(new ArrayList<Account>(), ACCOUNT_DAO_INSTANCE.getAll());
        }

        @Test
        void shouldReturnActualListWhenTableIsNotEmpty() {
            List<Account> accounts = new ArrayList<>();
            accounts.add(new Account.Builder().id(1L).firstName("test").middleName("test").lastName("test")
                    .birthDate(of(2000, 1, 1)).personalAddress("test").workAddress("test")
                    .email("test").icq("test").skype("test").additionalInfo("test").role(REGULAR).build());
            accounts.add(new Account.Builder().id(2L).firstName("test1").middleName("test1").lastName("test1")
                    .birthDate(of(2000, 1, 1)).personalAddress("test1").workAddress("test1")
                    .email("test1").icq("test1").skype("test1").additionalInfo("test1").role(REGULAR).build());
            accounts.add(new Account.Builder().id(3L).firstName("test2").middleName("test2").lastName("test2")
                    .birthDate(of(2000, 1, 1)).personalAddress("test2").workAddress("test2")
                    .email("test2").icq("test2").skype("test2").additionalInfo("test2").role(REGULAR).build());
            accounts.add(new Account.Builder().id(4L).firstName("test3").middleName("test3").lastName("test3")
                    .birthDate(of(2000, 1, 1)).personalAddress("test3").workAddress("test3")
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
