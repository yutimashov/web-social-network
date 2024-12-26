package com.getjavajob.training.timashovy.socialnetwork.dao.daoimpl.friendship;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import java.util.List;

import static java.util.Collections.emptyList;
import static java.util.Collections.singletonList;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.openMocks;

class FriendshipCheckerDaoImplTest {

    @Mock
    private EntityManager entityManager;

    @Mock
    private TypedQuery<Boolean> query;

    @InjectMocks
    private FriendshipCheckerDaoImpl friendshipCheckerDao;

    @BeforeEach
    void setup() {
        openMocks(this);
    }

    @Nested
    @DisplayName("boolean checkFriendshipRecordExistence(Long requesterId, Long accepterId)")
    class TestCheckFriendshipRecordExistence {

        @Test
        void shouldReturnTrueWhenRecordExistsAndRequestedIdLessThanAccepterId() {
            Long requesterId = 1L;
            Long accepterId = 2L;
            List<Boolean> result = singletonList(true);
            when(entityManager.createQuery("select 1 from Friendship f where f.initiatorAccountId = :requesterId "
                    + "and f.friendAccountId = :accepterId")).thenReturn(query);
            when(query.setParameter("requesterId", requesterId)).thenReturn(query);
            when(query.setParameter("accepterId", accepterId)).thenReturn(query);
            when(query.getResultList()).thenReturn(result);
            assertTrue(friendshipCheckerDao.checkFriendshipRecordExistence(1L, 2L));
        }

        @Test
        void shouldReturnFalseWhenRecordIsNotExisted() {
            Long requesterId = 1L;
            Long accepterId = 2L;
            List<Boolean> result = emptyList();
            when(entityManager.createQuery("select 1 from Friendship f where f.initiatorAccountId = :requesterId "
                    + "and f.friendAccountId = :accepterId")).thenReturn(query);
            when(query.setParameter("requesterId", requesterId)).thenReturn(query);
            when(query.setParameter("accepterId", accepterId)).thenReturn(query);
            when(query.getResultList()).thenReturn(result);
            assertFalse(friendshipCheckerDao.checkFriendshipRecordExistence(1L, 2L));
        }

    }

    @Nested
    @DisplayName("boolean checkUsersAreFriends(Long requesterId, Long accepterId)")
    class TestCheckUsersAreFriends {

        @Test
        void shouldReturnTrueWhenUsersAreFriends() {
            Long requesterId = 1L;
            Long accepterId = 2L;
            List<Boolean> result = singletonList(true);
            when(entityManager.createQuery(
                    "select 1 from Friendship f where f.initiatorAccountId = :requesterId "
                            + "and f.friendAccountId = :accepterId and f.friendshipStatus = true"
            )).thenReturn(query);
            when(query.setParameter("requesterId", requesterId)).thenReturn(query);
            when(query.setParameter("accepterId", accepterId)).thenReturn(query);
            when(query.getResultList()).thenReturn(result);
            assertTrue(friendshipCheckerDao.checkUsersAreFriends(1L, 2L));
        }

        @Test
        void shouldReturnFalseWhenUsersAreNotFriends() {
            Long requesterId = 1L;
            Long accepterId = 2L;
            List<Boolean> result = emptyList();
            when(entityManager.createQuery("select 1 from Friendship f where f.initiatorAccountId = :requesterId "
                    + "and f.friendAccountId = :accepterId and f.friendshipStatus = true")).thenReturn(query);
            when(query.setParameter("requesterId", requesterId)).thenReturn(query);
            when(query.setParameter("accepterId", accepterId)).thenReturn(query);
            when(query.getResultList()).thenReturn(result);
            assertFalse(friendshipCheckerDao.checkUsersAreFriends(1L, 2L));
        }

    }

}
