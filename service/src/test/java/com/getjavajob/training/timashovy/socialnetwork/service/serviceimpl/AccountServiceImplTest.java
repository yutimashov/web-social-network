//package com.getjavajob.training.timashovy.socialnetwork.service.serviceimpl;
//
//import com.getjavajob.training.timashovy.socialnetwork.domain.account.Account;
//import com.getjavajob.training.timashovy.socialnetwork.dao.daoimpl.account.AccountDao;
//import com.getjavajob.training.timashovy.socialnetwork.dao.daoimpl.friendship.FriendshipCheckerDaoImpl;
//import com.getjavajob.training.timashovy.socialnetwork.dao.daoimpl.friendship.FriendshipDaoImpl;
//import com.getjavajob.training.timashovy.socialnetwork.service.serviceimpl.account.AccountServiceImpl;
//import org.junit.jupiter.api.DisplayName;
//import org.junit.jupiter.api.Nested;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.junit.jupiter.MockitoExtension;
//
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Optional;
//
//import static java.time.LocalDate.of;
//import static org.junit.jupiter.api.Assertions.*;
//import static org.mockito.ArgumentMatchers.anyLong;
//import static org.mockito.Mockito.when;
//
//@ExtendWith(MockitoExtension.class)
//class AccountServiceImplTest {
//
//    @Mock
//    private AccountDao accountDao;
//    @Mock
//    private FriendshipDaoImpl friendshipDao;
//    @Mock
//    private FriendshipCheckerDaoImpl friendshipChecker;
//    @InjectMocks
//    private AccountServiceImpl accountService;
//    final Long validAccountId = 1L;
//    final Long nonExistingAccountId = -1L;
//    private static final Account TEST_ACCOUNT = new Account.Builder()
//            .id(1L)
//            .firstName("")
//            .lastName("")
//            .middleName("")
//            .birthDate(of(1800, 1, 1))
//            .personalAddress("")
//            .workAddress("")
//            .email("")
//            .icq("")
//            .skype("")
//            .additionalInfo("")
//            .build();
//
//    @Nested
//    @DisplayName("deleteAccount(Long accountId)")
//    class TestDeleteAccount {
//
//        @Test
//        void whenAccountIdIsNull() {
//            Throwable exception = assertThrows(IllegalArgumentException.class, () -> {
//                accountService.delete(null);
//                throw new UnsupportedOperationException("Not supported");
//            });
//            assertEquals(IllegalArgumentException.class, exception.getClass());
//        }
//
//        @Test
//        void whenAccountIsNotExisted() {
//            Throwable exception = assertThrows(IllegalArgumentException.class, () -> {
//                accountService.delete(nonExistingAccountId);
//                throw new UnsupportedOperationException("Not supported");
//            });
//            assertEquals(IllegalArgumentException.class, exception.getClass());
//        }
//
////        @Test
////        void successfulAccountDeleting() {
////            when(accountDao.deleteById(validAccountId)).thenReturn(true);
////            assertTrue(accountService.delete(validAccountId));
////        }
//
//    }
//
//    @Nested
//    @DisplayName("addFriend(Long accountId, Long friendId)")
//    class TestAddFriend {
//
//        private final Long requesterId = 1L;
//        private final Long accepterId = 2L;
//
//        @Test
//        void whenRequesterIdIsNull() {
//            Throwable exception = assertThrows(IllegalArgumentException.class, () -> {
//                accountService.addFriend(null, accepterId);
//                throw new UnsupportedOperationException("Not supported");
//            });
//            assertEquals(IllegalArgumentException.class, exception.getClass());
//        }
//
//        @Test
//        void whenAccepterIdIsNull() {
//            Throwable exception = assertThrows(IllegalArgumentException.class, () -> {
//                accountService.addFriend(requesterId, null);
//                throw new UnsupportedOperationException("Not supported");
//            });
//            assertEquals(IllegalArgumentException.class, exception.getClass());
//        }
//
//        @Test
//        void whenAccountTriesToSendRequestToThemselves() {
//            Throwable exception = assertThrows(IllegalArgumentException.class, () -> {
//                accountService.addFriend(requesterId, requesterId);
//                throw new UnsupportedOperationException("Not supported");
//            });
//            assertEquals(IllegalArgumentException.class, exception.getClass());
//        }
//
////        @Test
////        void whenNoFriendshipRecordExisted() {
////            when(friendshipChecker.checkFriendshipRecordExistence(requesterId, accepterId)).thenReturn(false);
////            when(friendshipDao.sendRequest(requesterId, accepterId)).thenReturn(true);
////            assertTrue(accountService.addFriend(requesterId, accepterId));
////        }
//
//        @Test
//        void whenAccountsAreAlreadyFriends() {
//            when(friendshipChecker.checkFriendshipRecordExistence(requesterId, accepterId)).thenReturn(true);
//            when(friendshipChecker.checkUsersAreFriends(requesterId, accepterId)).thenReturn(true);
//            assertFalse(accountService.addFriend(requesterId, accepterId));
//        }
//
//    }
//
//    @Nested
//    @DisplayName("deleteFriend(Long accountId, Long deletingFriendId)")
//    class TestDeleteFriends {
//
//        private final Long deletingFriendId = 2L;
//
//        @Test
//        void whenAccountIdIsNull() {
//            Throwable exception = assertThrows(IllegalArgumentException.class, () -> {
//                accountService.deleteFriend(null, deletingFriendId);
//                throw new UnsupportedOperationException("Not supported");
//            });
//            assertEquals(IllegalArgumentException.class, exception.getClass());
//        }
//
//        @Test
//        void whenDeletingFriendAccountIdIsNull() {
//            Throwable exception = assertThrows(IllegalArgumentException.class, () -> {
//                accountService.deleteFriend(validAccountId, null);
//                throw new UnsupportedOperationException("Not supported");
//            });
//            assertEquals(IllegalArgumentException.class, exception.getClass());
//        }
//
//        @Test
//        void whenDeletingIsSuccessful() {
//            when(friendshipDao.deleteFriend(validAccountId, deletingFriendId)).thenReturn(true);
//            assertTrue(accountService.deleteFriend(validAccountId, deletingFriendId));
//        }
//
//    }
//
//    @Nested
//    @DisplayName("getFriends(Long accountId)")
//    class TestGetFriends {
//
//        @Test
//        void whenAccountIdIsNull() {
//            Throwable exception = assertThrows(IllegalArgumentException.class, () -> {
//                accountService.getFriends(null);
//                throw new UnsupportedOperationException("Not supported");
//            });
//            assertEquals(IllegalArgumentException.class, exception.getClass());
//        }
//
//        @Test
//        void whenAccountHasSeveralFriends() {
//            List<Long> friends = new ArrayList<>();
//            friends.add(1L);
//            friends.add(2L);
//            when(friendshipDao.getFriendsIds(validAccountId)).thenReturn(friends);
//            when(accountDao.getById(anyLong())).thenReturn(Optional.of(TEST_ACCOUNT));
//            assertEquals(2, accountService.getFriends(validAccountId).size());
//        }
//
//    }
//
//    @Nested
//    @DisplayName("List<Account> getAllAccounts")
//    class TestGetAllAccounts {
//
//        @Test
//        void whenAccountsTableIsEmpty() {
//            when(accountDao.getAll()).thenReturn(new ArrayList<>());
//            assertEquals(new ArrayList<Account>(), accountService.getAll());
//        }
//
//    }
//
//}
