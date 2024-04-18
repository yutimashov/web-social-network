package com.getjavajob.training.timashovy.socialnetwork.dao.daoimpl;

import com.getjavajob.training.timashovy.socialnetwork.common.Group;
import com.getjavajob.training.timashovy.socialnetwork.dao.interfaces.BaseDao;
import com.getjavajob.training.timashovy.socialnetwork.dao.util.DaoException;
import org.junit.jupiter.api.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.getjavajob.training.timashovy.socialnetwork.dao.daoimpl.group.GroupDaoImpl.getInstance;
import static com.getjavajob.training.timashovy.socialnetwork.util.TestScriptsLoader.executeScript;
import static java.util.Optional.empty;
import static org.junit.jupiter.api.Assertions.*;

class GroupDaoImplTest {

    private static final String CREATE_TEST_TABLES_FILEPATH = "scripts/create_test_db.sql";
    private static final String LOAD_DATA_INTO_TEST_TABLES_FILEPATH = "scripts/load_test_data.sql";
    private static final String EMPTY_TEST_TABLES_FILEPATH = "scripts/clear_test_db.sql";
    private static final String DROP_TEST_DB_FILEPATH = "scripts/drop_test_db.sql";
    private static final BaseDao<Group> GROUP_DAO_INSTANCE = getInstance();
    private static final Group TEST_GROUP = new Group("", "", 1L);

    private void restoreTestGroupDefaultState() {
        TEST_GROUP.setGroupName("");
        TEST_GROUP.setDescription("");
        TEST_GROUP.setOwnerId(1L);
    }

    private void setTestGroupEqualsToRecordInTestTable() {
        TEST_GROUP.setId(1L);
        TEST_GROUP.setGroupName("test");
        TEST_GROUP.setDescription("test");
        TEST_GROUP.setOwnerId(1L);
    }

    @BeforeAll
    static void createTestTables() {
        executeScript(CREATE_TEST_TABLES_FILEPATH);
    }

    @BeforeEach
    public void fillTestTablesWith2Records() {
        executeScript(LOAD_DATA_INTO_TEST_TABLES_FILEPATH);
    }

    @AfterEach
    public void emptyTestTables() {
        restoreTestGroupDefaultState();
        executeScript(EMPTY_TEST_TABLES_FILEPATH);
    }

    @AfterAll
    public static void dropDataBaseAfterTestExecution() {
        executeScript(DROP_TEST_DB_FILEPATH);
    }

    @Nested
    @DisplayName("Long create(Group group)")
    class TestCreateGroup {

        @Test
        public void testCreateOneNewRecord() {
            assertEquals(2L, GROUP_DAO_INSTANCE.create(TEST_GROUP));
        }

    }

    @Nested
    @DisplayName("Group getById(Long id)")
    class TestGetGroupById {

        @Test
        public void testGetByIdGetNonExistingGroup() {
            emptyTestTables();
            assertEquals(empty(), GROUP_DAO_INSTANCE.getById(1L));
        }

    }

    @Nested
    @DisplayName("List<Group> getAll()")
    class TestGetAllGroups {

        @Test
        public void testGetAllOnEmptyTable() {
            emptyTestTables();
            assertEquals(new ArrayList<Group>(), GROUP_DAO_INSTANCE.getAll());
        }

        @Test
        public void testGetAllWithOneExistingGroup() {
            setTestGroupEqualsToRecordInTestTable();
            List<Group> groups = new ArrayList<>();
            groups.add(TEST_GROUP);
            assertEquals(groups, GROUP_DAO_INSTANCE.getAll());
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
