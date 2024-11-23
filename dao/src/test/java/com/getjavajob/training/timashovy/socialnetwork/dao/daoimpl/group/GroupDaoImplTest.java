package com.getjavajob.training.timashovy.socialnetwork.dao.daoimpl.group;

import com.getjavajob.training.timashovy.socialnetwork.domain.group.Group;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.List;
import java.util.Optional;

import static java.util.Collections.emptyList;
import static java.util.Collections.singletonList;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.openMocks;

class GroupDaoImplTest {

    @Mock
    private EntityManager entityManager;

    @Mock
    private TypedQuery<Group> groupsQuery;

    @InjectMocks
    private GroupDao groupDao;
    private Group group;

    @BeforeEach
    void setUp() {
        openMocks(this);
        group = new Group();
    }

    @Nested
    @DisplayName("Group save(Group group)")
    class TestSaveGroup {

        @Test
        public void shouldReturnGroupWhenGroupWasSaved() {
            assertEquals(group, groupDao.save(group));
            verify(entityManager).persist(group);
        }

    }

    @Nested
    @DisplayName("Group getById(Long id)")
    class TestGetGroupById {

        @Test
        public void shouldReturnOptionalWithGroupWhenGroupExisted() {
            Long id = 1L;
            when(entityManager.find(Group.class, id)).thenReturn(group);
            Optional<Group> optionalGroup = groupDao.getById(id);
            assertTrue(optionalGroup.isPresent());
            assertEquals(group, optionalGroup.get());
        }

        @Test
        public void shouldReturnEmptyOptionalWhenGroupIsNotExisted() {
            Long id = 1L;
            when(entityManager.find(Group.class, id)).thenReturn(null);
            assertFalse(groupDao.getById(id).isPresent());
        }

    }

    @Nested
    @DisplayName("List<Group> getAll()")
    class TestGetAllGroups {

        @Test
        public void shouldReturnListWithOneExistingGroupWhenOneGroupExists() {
            List<Group> groups = singletonList(group);
            when(entityManager.createQuery("select g from Group g", Group.class)).thenReturn(groupsQuery);
            when(groupsQuery.getResultList()).thenReturn(groups);
            assertEquals(groups, groupDao.getAll());
        }

        @Test
        public void shouldReturnEmptyListWhenNoGroupExists() {
            List<Group> groups = emptyList();
            when(entityManager.createQuery("select g from Group g", Group.class)).thenReturn(groupsQuery);
            when(groupsQuery.getResultList()).thenReturn(groups);
            assertEquals(groups, groupDao.getAll());
        }

    }

    @Nested
    @DisplayName("void delete(Long id)")
    class testDeleteGroup {

        @Test
        public void shouldNotDeleteGroupWhenGroupIsNotExisted() {
            Long id = 1L;
            when(entityManager.find(Group.class, id)).thenReturn(null);
            groupDao.delete(id);
            verify(entityManager, never()).remove(group);
        }

        @Test
        public void shouldSuccessfullyDeleteGroupWhenGroupExists() {
            Long id = 1L;
            when(entityManager.find(Group.class, id)).thenReturn(group);
            groupDao.delete(id);
            verify(entityManager).remove(group);
        }

    }

}
