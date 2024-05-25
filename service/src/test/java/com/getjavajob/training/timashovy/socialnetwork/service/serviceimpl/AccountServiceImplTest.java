package com.getjavajob.training.timashovy.socialnetwork.service.serviceimpl;

import com.getjavajob.training.timashovy.socialnetwork.common.account.Account;
import com.getjavajob.training.timashovy.socialnetwork.dao.daoimpl.account.AccountDaoImpl;
import com.getjavajob.training.timashovy.socialnetwork.dao.daoimpl.friendship.FriendshipCheckerDaoImpl;
import com.getjavajob.training.timashovy.socialnetwork.dao.daoimpl.friendship.FriendshipDaoImpl;
import com.getjavajob.training.timashovy.socialnetwork.dao.daoimpl.account.PhoneDaoImpl;
import com.getjavajob.training.timashovy.socialnetwork.dao.util.dbutils.dbconnection.ConnectionManager;
import com.getjavajob.training.timashovy.socialnetwork.service.serviceimpl.account.AccountServiceImpl;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static java.time.LocalDate.of;
import static java.util.Arrays.asList;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AccountServiceImplTest {

    @Mock
    private AccountDaoImpl accountDao;
    @Mock
    private FriendshipDaoImpl friendshipDao;
    @Mock
    private FriendshipCheckerDaoImpl friendshipChecker;
    @Mock
    private PhoneDaoImpl phoneDao;
    @InjectMocks
    private AccountServiceImpl accountService;
    final Long validAccountId = 1L;
    final Long nonExistingAccountId = -1L;
    private static final Account TEST_ACCOUNT = new Account.Builder()
            .id(1L)
            .firstName("")
            .lastName("")
            .middleName("")
            .birthDate(of(1800, 1, 1))
            .personalAddress("")
            .workAddress("")
            .email("")
            .icq("")
            .skype("")
            .additionalInfo("")
            .build();

//    @Nested
//    @DisplayName("createAccount(Account account)")
//    class TestCreateAccount {
//
//        @Test
//        void whenAccountIsNull() {
//            Throwable exception = assertThrows(IllegalArgumentException.class, () -> {
//                accountService.create(null, null, null);
//                throw new UnsupportedOperationException("Not supported");
//            });
//            assertEquals(IllegalArgumentException.class, exception.getClass());
//        }
//
//        @Test
//        void whenAccountFirstNameIsNull() {
//            Throwable exception = assertThrows(IllegalArgumentException.class, () -> {
//                accountService.create(new Account.Builder().firstName(null).build());
//                throw new UnsupportedOperationException("Not supported");
//            });
//            assertEquals(IllegalArgumentException.class, exception.getClass());
//        }
//
//        @Test
//        void whenAccountLastNameIsNull() {
//            Throwable exception = assertThrows(IllegalArgumentException.class, () -> {
//                accountService.create(new Account.Builder().lastName(null).build());
//                throw new UnsupportedOperationException("Not supported");
//            });
//            assertEquals(IllegalArgumentException.class, exception.getClass());
//        }
//
//        @Test
//        void whenAccountEmailIsNull() {
//            Throwable exception = assertThrows(IllegalArgumentException.class, () -> {
//                accountService.create(new Account.Builder().email(null).build());
//                throw new UnsupportedOperationException("Not supported");
//            });
//            assertEquals(IllegalArgumentException.class, exception.getClass());
//        }
//
//        @Test
//        void whenAccountIsValid() {
//            final Long expectedIdAfterCreatingAccount = 1L;
//            when(accountDao.create(TEST_ACCOUNT)).thenReturn(expectedIdAfterCreatingAccount);
//            assertEquals(expectedIdAfterCreatingAccount, accountService.create(TEST_ACCOUNT));
//        }
//
//    }

    @Nested
    @DisplayName("updateAccount(Long accountId, Account updatedAccount)")
    class TestUpdateAccount {

        @Test
        void whenAccountIdIsNull() {
            Throwable exception = assertThrows(IllegalArgumentException.class, () -> {
                accountService.update(null, TEST_ACCOUNT);
                throw new UnsupportedOperationException("Not supported");
            });
            assertEquals(IllegalArgumentException.class, exception.getClass());
        }

        @Test
        void whenAccountIdIsLessOrEqualToZero() {
            Throwable exception = assertThrows(IllegalArgumentException.class, () -> {
                accountService.update(nonExistingAccountId, TEST_ACCOUNT);
                throw new UnsupportedOperationException("Not supported");
            });
            assertEquals(IllegalArgumentException.class, exception.getClass());
        }

        @Test
        void whenUpdatedAccountIsNull() {
            Throwable exception = assertThrows(IllegalArgumentException.class, () -> {
                accountService.update(1L, null);
                throw new UnsupportedOperationException("Not supported");
            });
            assertEquals(IllegalArgumentException.class, exception.getClass());
        }

        @Test
        void whenAccountDoesNotExist() {
            final Long nonExistingId = 1L;
            when(accountDao.updateById(nonExistingId, TEST_ACCOUNT)).thenReturn(false);
            assertFalse(accountService.update(nonExistingId, TEST_ACCOUNT));
        }

    }

    @Nested
    @DisplayName("updateAccountFirstName(Long accountId, String firstName)")
    class TestUpdateAccountFirstName {

        @Test
        void whenAccountIdIsNull() {
            Throwable exception = assertThrows(IllegalArgumentException.class, () -> {
                accountService.updateFirstName(null, "name");
                throw new UnsupportedOperationException("Not supported");
            });
            assertEquals(IllegalArgumentException.class, exception.getClass());
        }

        @Test
        void whenFirstNameIsNull() {
            Throwable exception = assertThrows(IllegalArgumentException.class, () -> {
                accountService.updateFirstName(validAccountId, null);
                throw new UnsupportedOperationException("Not supported");
            });
            assertEquals(IllegalArgumentException.class, exception.getClass());
        }

        @Test
        void successfulFirstNameUpdating() {
            String originalName = "John";
            String newName = "Robert";
            Account accountOriginal = new Account.Builder().id(validAccountId).firstName(originalName).build();
            when(accountDao.getById(validAccountId)).thenReturn(Optional.of(accountOriginal));
            Account accountChanged = new Account.Builder(accountOriginal).firstName(newName).build();
            when(accountDao.updateById(validAccountId, accountChanged)).thenReturn(true);
            assertTrue(accountService.updateFirstName(validAccountId, newName));
        }

    }

    @Nested
    @DisplayName("updateAccountLastName(Long accountId, String lastName)")
    class TestUpdateAccountLastName {

        @Test
        void whenAccountIdIsNull() {
            Throwable exception = assertThrows(IllegalArgumentException.class, () -> {
                accountService.updateLastName(null, "name");
                throw new UnsupportedOperationException("Not supported");
            });
            assertEquals(IllegalArgumentException.class, exception.getClass());
        }

        @Test
        void whenLastNameIsNull() {
            Throwable exception = assertThrows(IllegalArgumentException.class, () -> {
                accountService.updateLastName(validAccountId, null);
                throw new UnsupportedOperationException("Not supported");
            });
            assertEquals(IllegalArgumentException.class, exception.getClass());
        }

        @Test
        void successfulLastNameUpdating() {
            String originalLastName = "Ivanov";
            String newLastName = "Petrov";
            Account accountOriginal = new Account.Builder().id(validAccountId).lastName(originalLastName).build();
            when(accountDao.getById(validAccountId)).thenReturn(Optional.of(accountOriginal));
            Account accountChanged = new Account.Builder(accountOriginal).lastName(newLastName).build();
            when(accountDao.updateById(validAccountId, accountChanged)).thenReturn(true);
            assertTrue(accountService.updateLastName(validAccountId, newLastName));
        }

    }

    @Nested
    @DisplayName("updateAccountMiddleName(Long accountId, String middleName)")
    class TestUpdateAccountMiddleName {

        @Test
        void whenAccountIdIsNull() {
            Throwable exception = assertThrows(IllegalArgumentException.class, () -> {
                accountService.updateMiddleName(null, "name");
                throw new UnsupportedOperationException("Not supported");
            });
            assertEquals(IllegalArgumentException.class, exception.getClass());
        }

        @Test
        void whenMiddleNameIsNull() {
            Throwable exception = assertThrows(IllegalArgumentException.class, () -> {
                accountService.updateMiddleName(validAccountId, null);
                throw new UnsupportedOperationException("Not supported");
            });
            assertEquals(IllegalArgumentException.class, exception.getClass());
        }

        @Test
        void successfulMiddleNameUpdating() {
            String originalMiddleName = "Ivanovich";
            String newMiddleName = "Petrovich";
            Account accountOriginal = new Account.Builder().id(validAccountId).middleName(originalMiddleName).build();
            when(accountDao.getById(validAccountId)).thenReturn(Optional.of(accountOriginal));
            Account accountChanged = new Account.Builder(accountOriginal).middleName(newMiddleName).build();
            when(accountDao.updateById(validAccountId, accountChanged)).thenReturn(true);
            assertTrue(accountService.updateMiddleName(validAccountId, newMiddleName));
        }

    }

    @Nested
    @DisplayName("updateAccountBirthDate(Long accountId, String birthDate)")
    class TestUpdateAccountBirthDate {

        final LocalDate validAccountBirthDate = of(1990, 2, 24);

        @Test
        void updateAccountBirthDateWhenAccountIdIsNull() {
            Throwable exception = assertThrows(IllegalArgumentException.class, () -> {
                accountService.updateBirthDate(null, validAccountBirthDate);
                throw new UnsupportedOperationException("Not supported");
            });
            assertEquals(IllegalArgumentException.class, exception.getClass());
        }

        @Test
        void updateAccountBirthDateWhenBirthDateIsNull() {
            Throwable exception = assertThrows(IllegalArgumentException.class, () -> {
                accountService.updateBirthDate(validAccountId, null);
                throw new UnsupportedOperationException("Not supported");
            });
            assertEquals(IllegalArgumentException.class, exception.getClass());
        }

        @Test
        void successfulBirthDateUpdating() {
            LocalDate newBirthDate = of(2000, 2, 24);
            Account accountOriginal = new Account.Builder().id(validAccountId).birthDate(validAccountBirthDate).build();
            when(accountDao.getById(validAccountId)).thenReturn(Optional.of(accountOriginal));
            Account accountChanged = new Account.Builder(accountOriginal).birthDate(newBirthDate).build();
            when(accountDao.updateById(validAccountId, accountChanged)).thenReturn(true);
            assertTrue(accountService.updateBirthDate(validAccountId, newBirthDate));
        }

    }

    @Nested
    @DisplayName("updateAccountWorkAddress(Long accountId, String workAddress)")
    class TestUpdateAccountWorkAddress {

        @Test
        void updateAccountWorkAddressWhenAccountIdIsNull() {
            Throwable exception = assertThrows(IllegalArgumentException.class, () -> {
                accountService.updateAccountWorkAddress(null, "new_address");
                throw new UnsupportedOperationException("Not supported");
            });
            assertEquals(IllegalArgumentException.class, exception.getClass());
        }

        @Test
        void updateAccountWorkAddressWhenWorkAddressIsNull() {
            Throwable exception = assertThrows(IllegalArgumentException.class, () -> {
                accountService.updateAccountWorkAddress(validAccountId, null);
                throw new UnsupportedOperationException("Not supported");
            });
            assertEquals(IllegalArgumentException.class, exception.getClass());
        }

        @Test
        void successfulWorkAddressUpdating() {
            String originalWorkAddress = "work_old";
            String newWorkAddress = "work_new";
            Account accountOriginal = new Account.Builder().id(validAccountId).workAddress(originalWorkAddress).build();
            when(accountDao.getById(validAccountId)).thenReturn(Optional.of(accountOriginal));
            Account accountChanged = new Account.Builder(accountOriginal).workAddress(newWorkAddress).build();
            when(accountDao.updateById(validAccountId, accountChanged)).thenReturn(true);
            assertTrue(accountService.updateAccountWorkAddress(validAccountId, newWorkAddress));
        }

    }

    @Nested
    @DisplayName("updateAccountPersonalAddress(Long accountId, String personalAddress)")
    class TestUpdateAccountPersonalAddress {

        @Test
        void updateAccountPersonalAddressWhenAccountIdIsNull() {
            Throwable exception = assertThrows(IllegalArgumentException.class, () -> {
                accountService.updateAccountPersonalAddress(null, "new_address");
                throw new UnsupportedOperationException("Not supported");
            });
            assertEquals(IllegalArgumentException.class, exception.getClass());
        }

        @Test
        void updateAccountPersonalAddressWhenPersonalAddressIsNull() {
            Throwable exception = assertThrows(IllegalArgumentException.class, () -> {
                accountService.updateAccountPersonalAddress(validAccountId, null);
                throw new UnsupportedOperationException("Not supported");
            });
            assertEquals(IllegalArgumentException.class, exception.getClass());
        }

        @Test
        void successfulPersonalAddressUpdating() {
            String originalWorkAddress = "work_old";
            String newWorkAddress = "work_new";
            Account accountOriginal = new Account.Builder().id(validAccountId).personalAddress(originalWorkAddress)
                    .build();
            when(accountDao.getById(validAccountId)).thenReturn(Optional.of(accountOriginal));
            Account accountChanged = new Account.Builder(accountOriginal).personalAddress(newWorkAddress).build();
            when(accountDao.updateById(validAccountId, accountChanged)).thenReturn(true);
            assertTrue(accountService.updateAccountPersonalAddress(validAccountId, newWorkAddress));
        }

    }

    @Nested
    @DisplayName("updateAccountEmail(Long accountId, String email)")
    class TestUpdateAccountEmail {

        @Test
        void updateAccountEmailWhenAccountIdIsNull() {
            Throwable exception = assertThrows(IllegalArgumentException.class, () -> {
                accountService.updateEmail(null, "new_email");
                throw new UnsupportedOperationException("Not supported");
            });
            assertEquals(IllegalArgumentException.class, exception.getClass());
        }

        @Test
        void updateAccountEmailWhenEmailIsNull() {
            Throwable exception = assertThrows(IllegalArgumentException.class, () -> {
                accountService.updateEmail(validAccountId, null);
                throw new UnsupportedOperationException("Not supported");
            });
            assertEquals(IllegalArgumentException.class, exception.getClass());
        }

        @Test
        void successfulEmailUpdating() {
            String originalEmail = "email_old";
            String newEmail = "email_new";
            Account accountOriginal = new Account.Builder().id(validAccountId).email(originalEmail).build();
            when(accountDao.getById(validAccountId)).thenReturn(Optional.of(accountOriginal));
            Account accountChanged = new Account.Builder(accountOriginal).email(newEmail).build();
            when(accountDao.updateById(validAccountId, accountChanged)).thenReturn(true);
            assertTrue(accountService.updateEmail(validAccountId, newEmail));
        }

    }

    @Nested
    @DisplayName("updateAccountIcq(Long accountId, String icq)")
    class TestUpdateAccountIcq {

        @Test
        void updateAccountIcqWhenAccountIdIsNull() {
            Throwable exception = assertThrows(IllegalArgumentException.class, () -> {
                accountService.updateIcq(null, "new_icq");
                throw new UnsupportedOperationException("Not supported");
            });
            assertEquals(IllegalArgumentException.class, exception.getClass());
        }

        @Test
        void updateAccountIcqWhenIcqIsNull() {
            Throwable exception = assertThrows(IllegalArgumentException.class, () -> {
                accountService.updateIcq(validAccountId, null);
                throw new UnsupportedOperationException("Not supported");
            });
            assertEquals(IllegalArgumentException.class, exception.getClass());
        }

        @Test
        void successfulIcqUpdating() {
            String originalIcq = "icq_old";
            String newIcq = "icq_new";
            Account accountOriginal = new Account.Builder().id(validAccountId).icq(originalIcq).build();
            when(accountDao.getById(validAccountId)).thenReturn(Optional.of(accountOriginal));
            Account accountChanged = new Account.Builder(accountOriginal).icq(newIcq).build();
            when(accountDao.updateById(validAccountId, accountChanged)).thenReturn(true);
            assertTrue(accountService.updateIcq(validAccountId, newIcq));
        }

    }

    @Nested
    @DisplayName("updateAccountSkype(Long accountId, String skype)")
    class TestUpdateAccountSkype {

        @Test
        void updateAccountSkypeWhenAccountIdIsNull() {
            Throwable exception = assertThrows(IllegalArgumentException.class, () -> {
                accountService.updateSkype(null, "new_skype");
                throw new UnsupportedOperationException("Not supported");
            });
            assertEquals(IllegalArgumentException.class, exception.getClass());
        }

        @Test
        void updateAccountSkypeWhenSkypeIsNull() {
            Throwable exception = assertThrows(IllegalArgumentException.class, () -> {
                accountService.updateSkype(validAccountId, null);
                throw new UnsupportedOperationException("Not supported");
            });
            assertEquals(IllegalArgumentException.class, exception.getClass());
        }

        @Test
        void successfulSkypeUpdating() {
            String originalSkype = "skype_old";
            String newSkype = "skype_new";
            Account accountOriginal = new Account.Builder().id(validAccountId).skype(originalSkype).build();
            when(accountDao.getById(validAccountId)).thenReturn(Optional.of(accountOriginal));
            Account accountChanged = new Account.Builder(accountOriginal).skype(newSkype).build();
            when(accountDao.updateById(validAccountId, accountChanged)).thenReturn(true);
            assertTrue(accountService.updateSkype(validAccountId, newSkype));
        }

    }

    @Nested
    @DisplayName("updateAccountAdditionalInfo(Long accountId, String additionalInfo)")
    class TestUpdateAccountAdditionalInfo {

        @Test
        void whenAccountIdIsNull() {
            Throwable exception = assertThrows(IllegalArgumentException.class, () -> {
                accountService.updateAccountAdditionalInfo(null, "new_info");
                throw new UnsupportedOperationException("Not supported");
            });
            assertEquals(IllegalArgumentException.class, exception.getClass());
        }

        @Test
        void whenAdditionalInfoIsNull() {
            Throwable exception = assertThrows(IllegalArgumentException.class, () -> {
                accountService.updateAccountAdditionalInfo(validAccountId, null);
                throw new UnsupportedOperationException("Not supported");
            });
            assertEquals(IllegalArgumentException.class, exception.getClass());
        }

        @Test
        void successfulAdditionalInfoUpdating() {
            String originalAdditionalInfo = "additional_info_old";
            String newAdditionalInfo = "additional_info_new";
            Account accountOriginal = new Account.Builder().id(validAccountId).additionalInfo(originalAdditionalInfo).build();
            when(accountDao.getById(validAccountId)).thenReturn(Optional.of(accountOriginal));
            Account accountChanged = new Account.Builder(accountOriginal).additionalInfo(newAdditionalInfo).build();
            when(accountDao.updateById(validAccountId, accountChanged)).thenReturn(true);
            assertTrue(accountService.updateAccountAdditionalInfo(validAccountId, newAdditionalInfo));
        }

    }

    @Nested
    @DisplayName("deleteAccount(Long accountId)")
    class TestDeleteAccount {

        @Test
        void whenAccountIdIsNull() {
            Throwable exception = assertThrows(IllegalArgumentException.class, () -> {
                accountService.delete(null);
                throw new UnsupportedOperationException("Not supported");
            });
            assertEquals(IllegalArgumentException.class, exception.getClass());
        }

        @Test
        void whenAccountIsNotExisted() {
            Throwable exception = assertThrows(IllegalArgumentException.class, () -> {
                accountService.delete(nonExistingAccountId);
                throw new UnsupportedOperationException("Not supported");
            });
            assertEquals(IllegalArgumentException.class, exception.getClass());
        }

        @Test
        void successfulAccountDeleting() {
            when(accountDao.deleteById(validAccountId)).thenReturn(true);
            assertTrue(accountService.delete(validAccountId));
        }

    }

    @Nested
    @DisplayName("addFriend(Long accountId, Long friendId)")
    class TestAddFriend {

        private final Long requesterId = 1L;
        private final Long accepterId = 2L;

        @Test
        void whenRequesterIdIsNull() {
            Throwable exception = assertThrows(IllegalArgumentException.class, () -> {
                accountService.addFriend(null, accepterId);
                throw new UnsupportedOperationException("Not supported");
            });
            assertEquals(IllegalArgumentException.class, exception.getClass());
        }

        @Test
        void whenAccepterIdIsNull() {
            Throwable exception = assertThrows(IllegalArgumentException.class, () -> {
                accountService.addFriend(requesterId, null);
                throw new UnsupportedOperationException("Not supported");
            });
            assertEquals(IllegalArgumentException.class, exception.getClass());
        }

        @Test
        void whenAccountTriesToSendRequestToThemselves() {
            Throwable exception = assertThrows(IllegalArgumentException.class, () -> {
                accountService.addFriend(requesterId, requesterId);
                throw new UnsupportedOperationException("Not supported");
            });
            assertEquals(IllegalArgumentException.class, exception.getClass());
        }

        @Test
        void whenNoFriendshipRecordExisted() {
            when(friendshipChecker.checkFriendshipRecordExistence(requesterId, accepterId)).thenReturn(false);
            when(friendshipDao.sendRequest(requesterId, accepterId)).thenReturn(true);
            assertTrue(accountService.addFriend(requesterId, accepterId));
        }

        @Test
        void whenAccountsAreAlreadyFriends() {
            when(friendshipChecker.checkFriendshipRecordExistence(requesterId, accepterId)).thenReturn(true);
            when(friendshipChecker.checkUsersAreFriends(requesterId, accepterId)).thenReturn(true);
            assertFalse(accountService.addFriend(requesterId, accepterId));
        }

    }

    @Nested
    @DisplayName("deleteFriend(Long accountId, Long deletingFriendId)")
    class TestDeleteFriends {

        private final Long deletingFriendId = 2L;

        @Test
        void whenAccountIdIsNull() {
            Throwable exception = assertThrows(IllegalArgumentException.class, () -> {
                accountService.deleteFriend(null, deletingFriendId);
                throw new UnsupportedOperationException("Not supported");
            });
            assertEquals(IllegalArgumentException.class, exception.getClass());
        }

        @Test
        void whenDeletingFriendAccountIdIsNull() {
            Throwable exception = assertThrows(IllegalArgumentException.class, () -> {
                accountService.deleteFriend(validAccountId, null);
                throw new UnsupportedOperationException("Not supported");
            });
            assertEquals(IllegalArgumentException.class, exception.getClass());
        }

        @Test
        void whenDeletingIsSuccessful() {
            when(friendshipDao.deleteFriend(validAccountId, deletingFriendId)).thenReturn(true);
            assertTrue(accountService.deleteFriend(validAccountId, deletingFriendId));
        }

    }

    @Nested
    @DisplayName("getFriends(Long accountId)")
    class TestGetFriends {

        @Test
        void whenAccountIdIsNull() {
            Throwable exception = assertThrows(IllegalArgumentException.class, () -> {
                accountService.getFriends(null);
                throw new UnsupportedOperationException("Not supported");
            });
            assertEquals(IllegalArgumentException.class, exception.getClass());
        }

        @Test
        void whenAccountHasSeveralFriends() {
            List<Long> friends = new ArrayList<>();
            friends.add(1L);
            friends.add(2L);
            when(friendshipDao.getFriendsIds(validAccountId)).thenReturn(friends);
            when(accountDao.getById(anyLong())).thenReturn(Optional.of(TEST_ACCOUNT));
            assertEquals(2, accountService.getFriends(validAccountId).size());
        }

    }

    @Nested
    @DisplayName("List<Account> getAllAccounts")
    class TestGetAllAccounts {

        @Test
        void whenAccountsTableIsEmpty() {
            when(accountDao.getAll()).thenReturn(new ArrayList<>());
            assertEquals(new ArrayList<Account>(), accountService.getAll());
        }

        @Test
        void when2AccountsExisted() {
            when(accountDao.getAll()).thenReturn(new ArrayList<>(asList(TEST_ACCOUNT, TEST_ACCOUNT)));
            assertEquals(2, accountService.getAll().size());
        }

    }

}
