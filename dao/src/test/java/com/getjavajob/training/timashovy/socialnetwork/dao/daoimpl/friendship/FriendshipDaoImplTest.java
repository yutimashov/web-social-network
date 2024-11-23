package com.getjavajob.training.timashovy.socialnetwork.dao.daoimpl.friendship;

import com.getjavajob.training.timashovy.socialnetwork.dao.exception.DaoException;
import com.getjavajob.training.timashovy.socialnetwork.domain.account.Account;
import com.getjavajob.training.timashovy.socialnetwork.domain.friendship.Friendship;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceException;
import javax.persistence.TypedQuery;
import java.util.List;

import static java.util.Collections.emptyList;
import static java.util.Collections.singletonList;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.openMocks;

class FriendshipDaoImplTest {

    @Mock
    private EntityManager entityManager;

    @Mock
    private TypedQuery<Boolean> query;

    @Mock
    private TypedQuery<Long> friendsIdsQuery;

    @InjectMocks
    private FriendshipDaoImpl friendshipDao;
    private Account requester;
    private Account accepter;
    private Friendship friendship;

    @BeforeEach
    void setUp() {
        openMocks(this);
        requester = new Account.Builder().id(1L).build();
        accepter = new Account.Builder().id(2L).build();
        friendship = new Friendship(requester.getId(), accepter.getId(), requester, accepter, false);
    }

    @Nested
    @DisplayName("void sendRequest(Account requester, Account accepter)")
    class TestSendFriendRequest {

        @Test
        void shouldSuccessfullySendFriendRequestIfNoProblemsOccurred() {
            friendshipDao.sendRequest(requester, accepter);
            verify(entityManager).persist(friendship);
        }

        @Test
        void shouldThrowDaoExceptionWhenProblemsOccurredSendingRequest() {
            doThrow(new PersistenceException()).when(entityManager).persist(friendship);
            assertThrows(DaoException.class, () -> friendshipDao.sendRequest(requester, accepter));
        }

    }

    @Nested
    @DisplayName("boolean acceptRequest(Long requesterId, Long accepterId)")
    class TestAcceptFriendRequest {

        @Test
        void shouldReturnFalseWhenRecordExistsWithStatusFalse() {
            when(entityManager.createQuery(
                    "update Friendship f set f.friendshipStatus = true where f.requester.id = :requester "
                            + "and f.receiver.id = :accepter")).thenReturn(query);
            when(query.setParameter("requester", 1L)).thenReturn(query);
            when(query.setParameter("accepter", 2L)).thenReturn(query);
            when(query.executeUpdate()).thenReturn(-1);
            assertFalse(friendshipDao.acceptRequest(1L, 2L));
        }

        @Test
        void shouldReturnTrueWhenAcceptingRequestIsSucceed() {
            when(entityManager.createQuery(
                    "update Friendship f set f.friendshipStatus = true where f.requester.id = :requester "
                            + "and f.receiver.id = :accepter")).thenReturn(query);
            when(query.setParameter("requester", 1L)).thenReturn(query);
            when(query.setParameter("accepter", 2L)).thenReturn(query);
            when(query.executeUpdate()).thenReturn(1);
            assertTrue(friendshipDao.acceptRequest(1L, 2L));
        }

    }

    @Nested
    @DisplayName("List<Long> getFriendsIds(Long accountId)")
    class TestGetFriends {

        @Test
        void shouldReturnListWithOneRecordWhenOneFriendExists() {
            List<Long> friendsId = singletonList(1L);
            when(entityManager.createQuery(
                    "select case when f.initiatorAccountId = :accountId "
                            + "then f.friendAccountId else f.initiatorAccountId end from Friendship f "
                            + "where (f.initiatorAccountId = :accountId or f.friendAccountId = :accountId) "
                            + "and f.friendshipStatus = true", Long.class)).thenReturn(friendsIdsQuery);
            when(friendsIdsQuery.setParameter("accountId", 1L)).thenReturn(friendsIdsQuery);
            when(friendsIdsQuery.getResultList()).thenReturn(friendsId);
            assertEquals(friendsId, friendshipDao.getFriendsIds(1L));
        }

        @Test
        void shouldReturnEmptyListWhenAccountHasNoFriends() {
            List<Long> friendsId = emptyList();
            when(entityManager.createQuery(
                    "select case when f.initiatorAccountId = :accountId "
                            + "then f.friendAccountId else f.initiatorAccountId end from Friendship f "
                            + "where (f.initiatorAccountId = :accountId or f.friendAccountId = :accountId) "
                            + "and f.friendshipStatus = true", Long.class)).thenReturn(friendsIdsQuery);
            when(friendsIdsQuery.setParameter("accountId", 1L)).thenReturn(friendsIdsQuery);
            when(friendsIdsQuery.getResultList()).thenReturn(friendsId);
            assertEquals(friendsId, friendshipDao.getFriendsIds(1L));
        }

    }

    @Nested
    @DisplayName("List<Long> getIncomingRequests(Long accountId)")
    class TestGetIncomingRequests {

        @Test
        void shouldReturnListWithOneRecordWhenOneFriendExists() {
            List<Long> friendsId = singletonList(1L);
            when(entityManager.createQuery(
                    "select f.requester.id from Friendship f where f.friendshipStatus = false "
                            + "and f.receiver.id = :accountId", Long.class)).thenReturn(friendsIdsQuery);
            when(friendsIdsQuery.setParameter("accountId", 1L)).thenReturn(friendsIdsQuery);
            when(friendsIdsQuery.getResultList()).thenReturn(friendsId);
            assertEquals(friendsId, friendshipDao.getIncomingRequests(1L));
        }

        @Test
        void shouldReturnEmptyListWhenAccountHasNoFriends() {
            List<Long> friendsId = emptyList();
            when(entityManager.createQuery("select f.requester.id from Friendship f where f.friendshipStatus = false "
                    + "and f.receiver.id = :accountId", Long.class)).thenReturn(friendsIdsQuery);
            when(friendsIdsQuery.setParameter("accountId", 1L)).thenReturn(friendsIdsQuery);
            when(friendsIdsQuery.getResultList()).thenReturn(friendsId);
            assertEquals(friendsId, friendshipDao.getIncomingRequests(1L));
        }

    }

    @Nested
    @DisplayName("List<Long> getOutgoingRequests(Long accountId)")
    class TestGetOutgoingRequests {

        @Test
        void shouldReturnListWithOneRecordWhenOneFriendExists() {
            List<Long> friendsId = singletonList(1L);
            when(entityManager.createQuery(
                    "select f.receiver.id from Friendship f where f.friendshipStatus = false "
                            + "and f.requester.id = :accountId", Long.class)).thenReturn(friendsIdsQuery);
            when(friendsIdsQuery.setParameter("accountId", 1L)).thenReturn(friendsIdsQuery);
            when(friendsIdsQuery.getResultList()).thenReturn(friendsId);
            assertEquals(friendsId, friendshipDao.getOutgoingRequests(1L));
        }

        @Test
        void shouldReturnEmptyListWhenAccountHasNoFriends() {
            List<Long> friendsId = emptyList();
            when(entityManager.createQuery("select f.receiver.id from Friendship f where f.friendshipStatus = false "
                    + "and f.requester.id = :accountId", Long.class)).thenReturn(friendsIdsQuery);
            when(friendsIdsQuery.setParameter("accountId", 1L)).thenReturn(friendsIdsQuery);
            when(friendsIdsQuery.getResultList()).thenReturn(friendsId);
            assertEquals(friendsId, friendshipDao.getOutgoingRequests(1L));
        }

    }

    @Nested
    @DisplayName("void deleteFriend(Long accountId, Long deletingFriendId)")
    class TestDeleteFriend {

        @Test
        void shouldDeleteFriendWhenFriendCanBeDeleted() {
            when(entityManager.find(Friendship.class, new Friendship.FriendshipId(1L, 2L))).thenReturn(friendship);
            friendshipDao.deleteFriend(1L, 2L);
            verify(entityManager).remove(friendship);
        }

        @Test
        void shouldNotCallRemoveMethodOfEntityManagerWhenFriendCannotBeDeleted() {
            when(entityManager.find(Friendship.class, new Friendship.FriendshipId(1L, 2L))).thenReturn(null);
            friendshipDao.deleteFriend(1L, 2L);
            verify(entityManager, never()).remove(friendship);
        }

    }

}
