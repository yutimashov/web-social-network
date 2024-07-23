package com.getjavajob.training.timashovy.socialnetwork.dao.daoimpl.message;

import com.getjavajob.training.timashovy.socialnetwork.common.message.Message;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

import static java.time.LocalDate.of;
import static java.util.Optional.empty;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.context.jdbc.Sql.ExecutionPhase.AFTER_TEST_METHOD;
import static org.springframework.test.context.jdbc.Sql.ExecutionPhase.BEFORE_TEST_METHOD;

@ExtendWith(SpringExtension.class)
@ContextConfiguration("classpath:test-config.xml")
@Sql(
        scripts = {
                "classpath:scripts/message/create.sql",
                "classpath:scripts/message/load.sql"
        },
        executionPhase = BEFORE_TEST_METHOD
)
@Sql(
        scripts = {
                "classpath:scripts/message/clear.sql",
                "classpath:scripts/message/drop.sql"
        },
        executionPhase = AFTER_TEST_METHOD
)
class GroupMessageDaoImplTest {

    @Autowired
    private GroupMessageDaoImpl MESSAGE_DAO;
    private static final Message TEST_MESSAGE = new Message.Builder()
            .id(1L)
            .destinationId(1L)
            .accountAuthorId(1L)
            .text("test")
            .creationDate(of(2020, 1, 1))
            .build();

    @Nested
    @DisplayName("Long create()")
    class TestCreate {

        @Test
        void shouldReturn1LWhenMessageCreatedInEmptyTable() {
            assertEquals(2L, MESSAGE_DAO.create(TEST_MESSAGE));
        }

    }

    @Nested
    @DisplayName("void getById()")
    class TestGetById {

        @Test
        void shouldReturnEmptyOptionalWhenMessageNotExists() {
            assertEquals(empty(), MESSAGE_DAO.getById(-11L));
        }

        @Test
        void shouldRerun1LWhenTryToGetExistingOnlyOneInTableMessage() {
            assertEquals(Optional.of(TEST_MESSAGE), MESSAGE_DAO.getById(1L));
        }

    }

    @Nested
    @DisplayName("boolean updateById(Long id, Message message")
    class TestUpdateById {

        @Test
        void shouldReturnFalseWhenMessageNotExists() {
            assertFalse(MESSAGE_DAO.updateById(-1L, TEST_MESSAGE));
        }

        @Test
        void shouldReturnTrueWhenAccountExists() {
            assertTrue(MESSAGE_DAO.updateById(1L, TEST_MESSAGE));
        }

    }

    @Nested
    @DisplayName("boolean deleteById(Long id)")
    class TestDeleteById {

        @Test
        void shouldReturnFalseWhenMessageNotExists() {
            assertFalse(MESSAGE_DAO.deleteById(-1L));
        }

        @Test
        void shouldReturnTrueWhenMessageExists() {
            assertTrue(MESSAGE_DAO.deleteById(1L));
        }

    }

}
