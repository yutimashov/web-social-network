package com.getjavajob.training.timashovy.socialnetwork.dao.daoimpl.account;

import com.getjavajob.training.timashovy.socialnetwork.domain.password.Password;
import com.getjavajob.training.timashovy.socialnetwork.dao.interfaces.account.PasswordDao;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

import static java.util.Optional.empty;
import static org.junit.jupiter.api.Assertions.*;
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
        }, executionPhase = AFTER_TEST_METHOD
)
class PasswordDaoImplTest {

    @Autowired
    private PasswordDao PASSWORD_DAO;
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
            assertThrows(DataAccessException.class, () -> {
                PASSWORD_DAO.create(new Password(-1L, "test", "test"));
                throw new UnsupportedOperationException("Not supported");
            });
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
            String nonExistingEmail = "non@exist.com";
            assertEquals(empty(), PASSWORD_DAO.findByEmail(nonExistingEmail));
        }

    }

}
