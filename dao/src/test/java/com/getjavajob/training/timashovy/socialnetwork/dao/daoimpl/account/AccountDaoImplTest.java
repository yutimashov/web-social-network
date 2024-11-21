package com.getjavajob.training.timashovy.socialnetwork.dao.daoimpl.account;

import com.getjavajob.training.timashovy.socialnetwork.dao.interfaces.account.AccountRepository;
import com.getjavajob.training.timashovy.socialnetwork.domain.account.Account;
import com.getjavajob.training.timashovy.socialnetwork.domain.phone.Phone;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.getjavajob.training.timashovy.socialnetwork.domain.account.AccountRole.REGULAR;
import static com.getjavajob.training.timashovy.socialnetwork.domain.phone.PhoneType.PERSONAL;
import static java.time.LocalDate.of;
import static java.util.Optional.empty;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.context.jdbc.Sql.ExecutionPhase.AFTER_TEST_METHOD;
import static org.springframework.test.context.jdbc.Sql.ExecutionPhase.BEFORE_TEST_METHOD;

@ExtendWith(SpringExtension.class)
@ContextConfiguration("classpath:test-config.xml")
@Sql(
        scripts = {
                "classpath:scripts/account/create.sql",
                "classpath:scripts/account/load.sql"
        },
        executionPhase = BEFORE_TEST_METHOD
)
@Sql(
        scripts = {
                "classpath:scripts/account/clear.sql",
                "classpath:scripts/account/drop.sql"
        },
        executionPhase = AFTER_TEST_METHOD
)
@Transactional
class AccountDaoImplTest {

    @Autowired
    private AccountRepository accountDao;

    private static final Account TEST_ACCOUNT = new Account.Builder()
            .firstName("").lastName("").middleName("").birthDate(of(2000, 1, 1))
            .phones(new ArrayList<>()).personalAddress("").workAddress("").email("").icq("").skype("")
            .additionalInfo("").role(REGULAR).build();

    private void restoreTestAccountDefaultState() {
        TEST_ACCOUNT.setId(1L);
        TEST_ACCOUNT.setFirstName("");
        TEST_ACCOUNT.setLastName("");
        TEST_ACCOUNT.setMiddleName("");
        TEST_ACCOUNT.setBirthDate(of(2000, 1, 1));
        TEST_ACCOUNT.setPhones(new ArrayList<>());
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
        List<Phone> phones = new ArrayList<>();
        Phone testPhone = new Phone(PERSONAL, "+375291112233", TEST_ACCOUNT);
        testPhone.setId(1L);
        phones.add(testPhone);
        TEST_ACCOUNT.setId(1L);
        TEST_ACCOUNT.setFirstName("test");
        TEST_ACCOUNT.setLastName("test");
        TEST_ACCOUNT.setMiddleName("test");
        TEST_ACCOUNT.setBirthDate(of(2000, 1, 1));
        TEST_ACCOUNT.setPhones(phones);
        TEST_ACCOUNT.setPersonalAddress("test");
        TEST_ACCOUNT.setPersonalAddress("test");
        TEST_ACCOUNT.setWorkAddress("test");
        TEST_ACCOUNT.setEmail("test");
        TEST_ACCOUNT.setEmail("test");
        TEST_ACCOUNT.setIcq("test");
        TEST_ACCOUNT.setSkype("test");
        TEST_ACCOUNT.setAdditionalInfo("test");
    }

    @Nested
    @DisplayName("Account create(Account account)")
    class TestCreateAccount {

        @Test
        void shouldReturn5LWhenAccountCreatedInTableWith4ExistingAccounts() {
            restoreTestAccountDefaultState();
            Account TEST_ACCOUNT = new Account.Builder()
                    .firstName("").lastName("").middleName("").birthDate(of(2000, 1, 1))
                    .phones(new ArrayList<>()).personalAddress("").workAddress("").email("").icq("").skype("")
                    .additionalInfo("").role(REGULAR).build();
            assertEquals(TEST_ACCOUNT, accountDao.save(TEST_ACCOUNT));
        }

    }

    @Nested
    @DisplayName("Optional<Account> getById(Long id)")
    class TestGetById {

        @Test
        void shouldReturnOptionalWithAccountWhenAccountExists() {
            setTestAccountEqualsToRecordInTestTable();
            Optional<Account> optionalAccount = accountDao.getById(1L);
            assertTrue(optionalAccount.isPresent());
            assertEquals(TEST_ACCOUNT, optionalAccount.get());
        }

        @Test
        void shouldReturnEmptyOptionalWhenAccountNotExists() {
            assertEquals(empty(), accountDao.getById(-1L));
        }

    }

    @Nested
    @DisplayName("List<Account> getAll()")
    class TestGetAll {

        @Test
        void shouldReturnActualListWhenTableIsNotEmpty() {
            Phone testPhone = new Phone(PERSONAL, "+375291112233", TEST_ACCOUNT);
            testPhone.setId(1L);
            List<Phone> phones = new ArrayList<>();
            phones.add(testPhone);
            List<Phone> emptyPhoneList = new ArrayList<>();
            List<Account> accounts = new ArrayList<>();
            accounts.add(new Account.Builder().id(1L).firstName("test").middleName("test").lastName("test")
                    .birthDate(of(2000, 1, 1)).personalAddress("test").workAddress("test")
                    .email("test").icq("test").skype("test").additionalInfo("test").role(REGULAR)
                    .phones(phones).build());
            accounts.add(new Account.Builder().id(2L).firstName("test1").middleName("test1").lastName("test1")
                    .birthDate(of(2000, 1, 1)).personalAddress("test1").workAddress("test1")
                    .email("test1").icq("test1").skype("test1").additionalInfo("test1").role(REGULAR)
                    .phones(emptyPhoneList).build());
            accounts.add(new Account.Builder().id(3L).firstName("test2").middleName("test2").lastName("test2")
                    .birthDate(of(2000, 1, 1)).personalAddress("test2").workAddress("test2")
                    .email("test2").icq("test2").skype("test2").additionalInfo("test2").role(REGULAR)
                    .phones(emptyPhoneList).build());
            accounts.add(new Account.Builder().id(4L).firstName("test3").middleName("test3").lastName("test3")
                    .birthDate(of(2000, 1, 1)).personalAddress("test3").workAddress("test3")
                    .email("test3").icq("test3").skype("test3").additionalInfo("test3").role(REGULAR)
                    .phones(emptyPhoneList).build());
            assertEquals(accounts, accountDao.getAll());
        }

    }

    @Nested
    @DisplayName("boolean updateById(Long id, Account account)")
    class TestUpdateById {

        @Test
        void shouldReturnFalseWhenAccountNotExists() {
            assertFalse(accountDao.updateById(-1L, TEST_ACCOUNT));
        }

        @Test
        void shouldReturnTrueWhenAccountExists() {
            assertTrue(accountDao.updateById(1L, TEST_ACCOUNT));
        }

    }

    @Nested
    @DisplayName("boolean deleteById(Long id)")
    class TestDeleteById {

        @Test
        void shouldReturnFalseWhenAccountNotExists() {
            accountDao.delete(-1L);
        }

        @Test
        void shouldReturnTrueWhenAccountExists() {
            accountDao.delete(1L);
        }

    }

}
