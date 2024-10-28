package com.getjavajob.training.timashovy.socialnetwork.dao.daoimpl.account;

import com.getjavajob.training.timashovy.socialnetwork.domain.phone.Phone;
import com.getjavajob.training.timashovy.socialnetwork.dao.interfaces.account.PhoneDao;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.List;

import static com.getjavajob.training.timashovy.socialnetwork.domain.phone.PhoneType.PERSONAL;
import static com.getjavajob.training.timashovy.socialnetwork.domain.phone.PhoneType.WORKING;
import static java.util.Collections.emptyList;
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
        },
        executionPhase = AFTER_TEST_METHOD
)
class PhoneDaoImplTest {

    @Autowired
    private PhoneDao PHONE_DAO;

    @Nested
    @DisplayName("Long create(Phone phone)")
    class TestCreatePhone {

        @Test
        void shouldReturnPhoneIdWhenPhoneWasCreated() {
            assertEquals(3L, PHONE_DAO.create(new Phone(PERSONAL, "test", 1L)));
        }

        @Test
        void shouldThrowExceptionWhenCreationFailed() {
            assertThrows(DataAccessException.class, () -> {
                PHONE_DAO.create(new Phone(PERSONAL, "test", -1L));
                throw new UnsupportedOperationException("Not supported");
            });
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
