package com.getjavajob.training.timashovy.socialnetwork.dao.daoimpl.account;

import com.getjavajob.training.timashovy.socialnetwork.common.account.Password;
import com.getjavajob.training.timashovy.socialnetwork.dao.interfaces.account.PasswordDao;
import com.getjavajob.training.timashovy.socialnetwork.dao.util.exceptions.DaoException;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

import static java.util.Optional.empty;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.context.jdbc.Sql.ExecutionPhase.AFTER_TEST_METHOD;
import static org.springframework.test.context.jdbc.Sql.ExecutionPhase.BEFORE_TEST_METHOD;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(locations = {"classpath:test-beans-dao.xml"})
@Sql(scripts = "classpath:scripts/account/create.sql", executionPhase = BEFORE_TEST_METHOD)
@Sql(scripts = "classpath:scripts/account/load.sql", executionPhase = BEFORE_TEST_METHOD)
@Sql(scripts = "classpath:scripts/account/clear.sql", executionPhase = AFTER_TEST_METHOD)
@Sql(scripts = "classpath:scripts/account/drop.sql", executionPhase = AFTER_TEST_METHOD)
class PasswordDaoImplTest {

    private static final PasswordDao PASSWORD_DAO = new ClassPathXmlApplicationContext("test-beans-dao.xml")
            .getBean("passwordDao", PasswordDaoImpl.class);
    private static final Password TEST_PASSWORD = new Password(1L, "test", "test");

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
