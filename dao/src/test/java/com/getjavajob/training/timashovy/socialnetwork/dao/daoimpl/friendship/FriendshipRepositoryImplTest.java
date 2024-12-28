package com.getjavajob.training.timashovy.socialnetwork.dao.daoimpl.friendship;

import com.getjavajob.training.timashovy.socialnetwork.dao.interfaces.springdatarepositories.FriendshipRepositorySpringData;
import com.getjavajob.training.timashovy.socialnetwork.domain.account.Account;
import com.getjavajob.training.timashovy.socialnetwork.domain.friendship.Friendship;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.util.List;

import static java.util.Collections.emptyList;
import static java.util.Collections.singletonList;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.openMocks;

class FriendshipRepositoryImplTest {

    @Mock
    private FriendshipRepositorySpringData friendshipRepositorySpringData;

    @InjectMocks
    private FriendshipRepositoryImpl friendshipDao;
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
            verify(friendshipRepositorySpringData).save(friendship);
        }

    }

    @Nested
    @DisplayName("boolean acceptRequest(Long requesterId, Long accepterId)")
    class TestAcceptFriendRequest {

        @Test
        void shouldReturnFalseWhenRecordExistsWithStatusFalse() {
            when(friendshipRepositorySpringData.acceptRequest(requester.getId(), accepter.getId())).thenReturn(-1);
            assertFalse(friendshipDao.acceptRequest(requester.getId(), accepter.getId()));
        }

        @Test
        void shouldReturnTrueWhenAcceptingRequestIsSucceed() {
            when(friendshipRepositorySpringData.acceptRequest(requester.getId(), accepter.getId())).thenReturn(1);
            assertTrue(friendshipDao.acceptRequest(requester.getId(), accepter.getId()));
        }

    }

    @Nested
    @DisplayName("List<Long> getFriendsIds(Long accountId)")
    class TestGetFriends {

        @Test
        void shouldReturnListWithOneRecordWhenOneFriendExists() {
            List<Long> friendsId = singletonList(1L);
            when(friendshipRepositorySpringData.getFriendsIds(1L)).thenReturn(friendsId);
            assertEquals(friendsId, friendshipDao.getFriendsIds(1L));
        }

        @Test
        void shouldReturnEmptyListWhenAccountHasNoFriends() {
            List<Long> friendsId = emptyList();
            when(friendshipRepositorySpringData.getFriendsIds(1L)).thenReturn(friendsId);
            assertEquals(friendsId, friendshipDao.getFriendsIds(1L));
        }

    }

    @Nested
    @DisplayName("List<Long> getIncomingRequests(Long accountId)")
    class TestGetIncomingRequests {

        @Test
        void shouldReturnListWithOneRecordWhenOneFriendExists() {
            List<Long> friendsId = singletonList(1L);
            when(friendshipRepositorySpringData.getIncomingRequests(1L)).thenReturn(friendsId);
            assertEquals(friendsId, friendshipDao.getIncomingRequests(1L));
        }

        @Test
        void shouldReturnEmptyListWhenAccountHasNoFriends() {
            List<Long> friendsId = emptyList();
            when(friendshipRepositorySpringData.getIncomingRequests(1L)).thenReturn(friendsId);
            assertEquals(friendsId, friendshipDao.getIncomingRequests(1L));
        }

    }

    @Nested
    @DisplayName("List<Long> getOutgoingRequests(Long accountId)")
    class TestGetOutgoingRequests {

        @Test
        void shouldReturnListWithOneRecordWhenOneFriendExists() {
            List<Long> friendsId = singletonList(1L);
            when(friendshipRepositorySpringData.getOutgoingRequests(1L)).thenReturn(friendsId);
            assertEquals(friendsId, friendshipDao.getOutgoingRequests(1L));
        }

        @Test
        void shouldReturnEmptyListWhenAccountHasNoFriends() {
            List<Long> friendsId = emptyList();
            when(friendshipRepositorySpringData.getOutgoingRequests(1L)).thenReturn(friendsId);
            assertEquals(friendsId, friendshipDao.getOutgoingRequests(1L));
        }

    }

    @Nested
    @DisplayName("void deleteFriend(Long accountId, Long deletingFriendId)")
    class TestDeleteFriend {

        @Test
        void shouldDeleteFriendWhenFriendCanBeDeleted() {
            Long accountId = 1L;
            Long deletingFriendId = 2L;
            friendshipRepositorySpringData.deleteFriend(accountId, deletingFriendId);
            verify(friendshipRepositorySpringData).deleteFriend(accountId, deletingFriendId);
        }

    }

}
