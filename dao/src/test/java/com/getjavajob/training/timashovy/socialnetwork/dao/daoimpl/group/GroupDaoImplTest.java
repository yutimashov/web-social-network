package com.getjavajob.training.timashovy.socialnetwork.dao.daoimpl.group;

import com.getjavajob.training.timashovy.socialnetwork.common.Group;
import com.getjavajob.training.timashovy.socialnetwork.dao.interfaces.BaseDao;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.io.ByteArrayInputStream;
import java.util.ArrayList;
import java.util.List;

import static java.util.Optional.empty;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.context.jdbc.Sql.ExecutionPhase.AFTER_TEST_METHOD;
import static org.springframework.test.context.jdbc.Sql.ExecutionPhase.BEFORE_TEST_METHOD;

@ExtendWith(SpringExtension.class)
@ContextConfiguration("classpath:test-config.xml")
@Sql(
        scripts = {
                "classpath:scripts/group/create.sql",
                "classpath:scripts/group/load.sql"
        },
        executionPhase = BEFORE_TEST_METHOD
)
@Sql(
        scripts = {
                "classpath:scripts/group/clear.sql",
                "classpath:scripts/group/drop.sql"
        },
        executionPhase = AFTER_TEST_METHOD
)
class GroupDaoImplTest {

    @Autowired
    private BaseDao<Group> GROUP_DAO_INSTANCE;
    private static final Group TEST_GROUP = new Group.Builder().groupName("").description("").accountOwnerId(1L)
            .build();

    private void restoreTestGroupDefaultState() {
        TEST_GROUP.setGroupName("");
        TEST_GROUP.setDescription("");
        TEST_GROUP.setAccountOwnerId(1L);
    }

    private void setTestGroupEqualsToRecordInTestTable() {
        TEST_GROUP.setId(1L);
        TEST_GROUP.setGroupName("test");
        TEST_GROUP.setDescription("test");
        TEST_GROUP.setAccountOwnerId(1L);
        TEST_GROUP.setAvatar(new ByteArrayInputStream("testAvatar".getBytes()));
    }

    @Nested
    @DisplayName("Long create(Group group)")
    class TestCreateGroup {

        @Test
        public void shouldReturn1WhenCreateGroup() {
            restoreTestGroupDefaultState();
            assertEquals(2L, GROUP_DAO_INSTANCE.create(TEST_GROUP));
        }

    }

    @Nested
    @DisplayName("Group getById(Long id)")
    class TestGetGroupById {

        @Test
        public void shouldReturnEmptyOptionalWhenGroupIsNotExisted() {
            assertEquals(empty(), GROUP_DAO_INSTANCE.getById(10L));
        }

    }

    @Nested
    @DisplayName("List<Group> getAll()")
    class TestGetAllGroups {

        @Test
        public void testGetAllWithOneExistingGroup() {
            setTestGroupEqualsToRecordInTestTable();
            List<Group> groups = new ArrayList<>();
            groups.add(TEST_GROUP);
            assertIterableEquals(groups, GROUP_DAO_INSTANCE.getAll());
        }

    }

    @Nested
    @DisplayName("boolean updateById(Long id, Group group)")
    class TestUpdateGroupById {

        @Test
        public void testUpdateByIdUpdateNonExistingId() {
            assertFalse(GROUP_DAO_INSTANCE.updateById(-1L, TEST_GROUP));
        }

        @Test
        public void testUpdateByIdUpdateExistingGroup() {
            assertTrue(GROUP_DAO_INSTANCE.updateById(1L, TEST_GROUP));
        }

    }

    @Nested
    @DisplayName("boolean deleteById(Long id)")
    class testDeleteGroupById {

        @Test
        public void testDeleteByIdWhenIdDoesNotExist() {
            assertFalse(GROUP_DAO_INSTANCE.deleteById(-1L));
        }

        @Test
        public void deleteByIdDeleteExistingGroup() {
            assertTrue(GROUP_DAO_INSTANCE.deleteById(1L));
        }

    }

}
