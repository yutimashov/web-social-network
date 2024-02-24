package com.getjavajob.training.timashovy.socialnetwork.service.serviceimpl;

import com.getjavajob.training.timashovy.socialnetwork.common.Account;
import com.getjavajob.training.timashovy.socialnetwork.dao.daoimpl.AccountDaoImpl;
import com.getjavajob.training.timashovy.socialnetwork.dao.daoimpl.FriendshipCheckerImpl;
import com.getjavajob.training.timashovy.socialnetwork.dao.daoimpl.FriendshipDaoImpl;
import com.getjavajob.training.timashovy.socialnetwork.dao.util.exceptions.DaoException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static java.time.LocalDate.of;
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
    private FriendshipCheckerImpl friendshipChecker;
    @InjectMocks
    private AccountServiceImpl accountService;
    final Long validAccountId = 1L;
    private static final Account TEST_ACCOUNT = new Account.Builder()
            .id(1L)
            .firstName("")
            .lastName("")
            .middleName("")
            .birthDate(of(1800, 1, 1))
            .personalPhoneNumber("")
            .workPhoneNumber("")
            .personalAddress("")
            .workAddress("")
            .email("")
            .icq("")
            .skype("")
            .additionalInfo("")
            .build();

    @Nested
    @DisplayName("createAccount(Account account)")
    class TestCreateAccount {

        @Test
        void whenAccountIsNull() {
            Throwable exception = assertThrows(IllegalArgumentException.class, () -> {
                accountService.createAccount(null);
                throw new UnsupportedOperationException("Not supported");
            });
            assertEquals(IllegalArgumentException.class, exception.getClass());
        }

        @Test
        void whenAccountFirstNameIsNull() {
            Throwable exception = assertThrows(IllegalArgumentException.class, () -> {
                accountService.createAccount(new Account.Builder().firstName(null).build());
                throw new UnsupportedOperationException("Not supported");
            });
            assertEquals(IllegalArgumentException.class, exception.getClass());
        }

        @Test
        void whenAccountLastNameIsNull() {
            Throwable exception = assertThrows(IllegalArgumentException.class, () -> {
                accountService.createAccount(new Account.Builder().lastName(null).build());
                throw new UnsupportedOperationException("Not supported");
            });
            assertEquals(IllegalArgumentException.class, exception.getClass());
        }

        @Test
        void whenAccountBirthDateIsNull() {
            Throwable exception = assertThrows(IllegalArgumentException.class, () -> {
                accountService.createAccount(new Account.Builder().birthDate(null).build());
                throw new UnsupportedOperationException("Not supported");
            });
            assertEquals(IllegalArgumentException.class, exception.getClass());
        }

        @Test
        void whenAccountPersonalPhoneNumberIsNull() {
            Throwable exception = assertThrows(IllegalArgumentException.class, () -> {
                accountService.createAccount(new Account.Builder().personalPhoneNumber(null).build());
                throw new UnsupportedOperationException("Not supported");
            });
            assertEquals(IllegalArgumentException.class, exception.getClass());
        }

        @Test
        void whenAccountEmailIsNull() {
            Throwable exception = assertThrows(IllegalArgumentException.class, () -> {
                accountService.createAccount(new Account.Builder().email(null).build());
                throw new UnsupportedOperationException("Not supported");
            });
            assertEquals(IllegalArgumentException.class, exception.getClass());
        }

        @Test
        void whenAccountIsValid() {
            final Long expectedIdAfterCreatingAccount = 1L;
            when(accountDao.create(TEST_ACCOUNT)).thenReturn(expectedIdAfterCreatingAccount);
            assertEquals(expectedIdAfterCreatingAccount, accountService.createAccount(TEST_ACCOUNT));
        }

    }

    @Nested
    @DisplayName("updateAccount(Long accountId, Account updatedAccount)")
    class TestUpdateAccount {

        @Test
        void whenAccountIdIsNull() {
            Throwable exception = assertThrows(IllegalArgumentException.class, () -> {
                accountService.updateAccount(null, TEST_ACCOUNT);
                throw new UnsupportedOperationException("Not supported");
            });
            assertEquals(IllegalArgumentException.class, exception.getClass());
        }

        @Test
        void whenAccountIdIsLessOrEqualToZero() {
            Throwable exception = assertThrows(IllegalArgumentException.class, () -> {
                accountService.updateAccount(-1L, TEST_ACCOUNT);
                throw new UnsupportedOperationException("Not supported");
            });
            assertEquals(IllegalArgumentException.class, exception.getClass());
        }

        @Test
        void whenUpdatedAccountIsNull() {
            Throwable exception = assertThrows(IllegalArgumentException.class, () -> {
                accountService.updateAccount(1L, null);
                throw new UnsupportedOperationException("Not supported");
            });
            assertEquals(IllegalArgumentException.class, exception.getClass());
        }

        @Test
        void whenAccountDoesNotExist() {
            final Long nonExistingId = 1L;
            when(accountDao.getById(nonExistingId)).thenThrow(new IllegalArgumentException("id is not existed"));
            Throwable exception = assertThrows(IllegalArgumentException.class, () -> {
                accountService.updateAccount(nonExistingId, TEST_ACCOUNT);
            });
            assertEquals("id is not existed", exception.getMessage());
        }

    }

    @Nested
    @DisplayName("updateAccountFirstName(Long accountId, String firstName)")
    class TestUpdateAccountFirstName {

        @Test
        void whenAccountIdIsNull() {
            Throwable exception = assertThrows(IllegalArgumentException.class, () -> {
                accountService.updateAccountFirstName(null, "name");
                throw new UnsupportedOperationException("Not supported");
            });
            assertEquals(IllegalArgumentException.class, exception.getClass());
        }

        @Test
        void whenFirstNameIsNull() {
            Throwable exception = assertThrows(IllegalArgumentException.class, () -> {
                accountService.updateAccountFirstName(validAccountId, null);
                throw new UnsupportedOperationException("Not supported");
            });
            assertEquals(IllegalArgumentException.class, exception.getClass());
        }

        @Test
        void successfulFirstNameUpdating() {
            String originalName = "John";
            String newName = "Robert";
            Account accountOriginal = new Account.Builder().id(validAccountId).firstName(originalName).build();
            when(accountDao.getById(validAccountId)).thenReturn(accountOriginal);
            Account accountChanged = new Account.Builder(accountOriginal).firstName(newName).build();
            when(accountDao.updateById(validAccountId, accountChanged)).thenReturn(true);
            assertTrue(accountService.updateAccountFirstName(validAccountId, newName));
        }

    }

    @Nested
    @DisplayName("updateAccountLastName(Long accountId, String lastName)")
    class TestUpdateAccountLastName {

        @Test
        void whenAccountIdIsNull() {
            Throwable exception = assertThrows(IllegalArgumentException.class, () -> {
                accountService.updateAccountLastName(null, "name");
                throw new UnsupportedOperationException("Not supported");
            });
            assertEquals(IllegalArgumentException.class, exception.getClass());
        }

        @Test
        void whenLastNameIsNull() {
            Throwable exception = assertThrows(IllegalArgumentException.class, () -> {
                accountService.updateAccountLastName(validAccountId, null);
                throw new UnsupportedOperationException("Not supported");
            });
            assertEquals(IllegalArgumentException.class, exception.getClass());
        }

        @Test
        void successfulLastNameUpdating() {
            String originalLastName = "Ivanov";
            String newLastName = "Petrov";
            Account accountOriginal = new Account.Builder().id(validAccountId).lastName(originalLastName).build();
            when(accountDao.getById(validAccountId)).thenReturn(accountOriginal);
            Account accountChanged = new Account.Builder(accountOriginal).lastName(newLastName).build();
            when(accountDao.updateById(validAccountId, accountChanged)).thenReturn(true);
            assertTrue(accountService.updateAccountLastName(validAccountId, newLastName));
        }

    }

    @Nested
    @DisplayName("updateAccountMiddleName(Long accountId, String middleName)")
    class TestUpdateAccountMiddleName {

        @Test
        void whenAccountIdIsNull() {
            Throwable exception = assertThrows(IllegalArgumentException.class, () -> {
                accountService.updateAccountMiddleName(null, "name");
                throw new UnsupportedOperationException("Not supported");
            });
            assertEquals(IllegalArgumentException.class, exception.getClass());
        }

        @Test
        void whenMiddleNameIsNull() {
            Throwable exception = assertThrows(IllegalArgumentException.class, () -> {
                accountService.updateAccountMiddleName(validAccountId, null);
                throw new UnsupportedOperationException("Not supported");
            });
            assertEquals(IllegalArgumentException.class, exception.getClass());
        }

        @Test
        void successfulMiddleNameUpdating() {
            String originalMiddleName = "Ivanovich";
            String newMiddleName = "Petrovich";
            Account accountOriginal = new Account.Builder().id(validAccountId).middleName(originalMiddleName).build();
            when(accountDao.getById(validAccountId)).thenReturn(accountOriginal);
            Account accountChanged = new Account.Builder(accountOriginal).middleName(newMiddleName).build();
            when(accountDao.updateById(validAccountId, accountChanged)).thenReturn(true);
            assertTrue(accountService.updateAccountMiddleName(validAccountId, newMiddleName));
        }

    }

    @Nested
    @DisplayName("updateAccountBirthDate(Long accountId, String birthDate)")
    class TestUpdateAccountBirthDate {

        final LocalDate validAccountBirthDate = of(1990, 2, 24);

        @Test
        void updateAccountBirthDateWhenAccountIdIsNull() {
            Throwable exception = assertThrows(IllegalArgumentException.class, () -> {
                accountService.updateAccountBirthDate(null, validAccountBirthDate);
                throw new UnsupportedOperationException("Not supported");
            });
            assertEquals(IllegalArgumentException.class, exception.getClass());
        }

        @Test
        void updateAccountBirthDateWhenBirthDateIsNull() {
            Throwable exception = assertThrows(IllegalArgumentException.class, () -> {
                accountService.updateAccountBirthDate(validAccountId, null);
                throw new UnsupportedOperationException("Not supported");
            });
            assertEquals(IllegalArgumentException.class, exception.getClass());
        }

        @Test
        void successfulBirthDateUpdating() {
            LocalDate newBirthDate = of(2000, 2, 24);
            Account accountOriginal = new Account.Builder().id(validAccountId).birthDate(validAccountBirthDate).build();
            when(accountDao.getById(validAccountId)).thenReturn(accountOriginal);
            Account accountChanged = new Account.Builder(accountOriginal).birthDate(newBirthDate).build();
            when(accountDao.updateById(validAccountId, accountChanged)).thenReturn(true);
            assertTrue(accountService.updateAccountBirthDate(validAccountId, newBirthDate));
        }

    }

    @Nested
    @DisplayName("updateAccountPersonalPhoneNumber(Long accountId, String personalPhoneNumber)")
    class TestUpdateAccountPersonalPhoneNumber {

        final String validPersonalNumber = "+7";

        @Test
        void updateAccountPersonalPhoneNumberWhenAccountIdIsNull() {
            Throwable exception = assertThrows(IllegalArgumentException.class, () -> {
                accountService.updateAccountPersonalPhoneNumber(null, validPersonalNumber);
                throw new UnsupportedOperationException("Not supported");
            });
            assertEquals(IllegalArgumentException.class, exception.getClass());
        }

        @Test
        void updateAccountPersonalPhoneNumberWhenPersonalPhoneNumberIsNull() {
            Throwable exception = assertThrows(IllegalArgumentException.class, () -> {
                accountService.updateAccountPersonalPhoneNumber(validAccountId, null);
                throw new UnsupportedOperationException("Not supported");
            });
            assertEquals(IllegalArgumentException.class, exception.getClass());
        }

        @Test
        void successfulPersonalPhoneNumberUpdating() {
            String newPersonalPhoneNumber = "+3";
            Account accountOriginal = new Account.Builder().id(validAccountId)
                    .personalPhoneNumber(validPersonalNumber).build();
            when(accountDao.getById(validAccountId)).thenReturn(accountOriginal);
            Account accountChanged = new Account.Builder(accountOriginal).personalPhoneNumber(newPersonalPhoneNumber)
                    .build();
            when(accountDao.updateById(validAccountId, accountChanged)).thenReturn(true);
            assertTrue(accountService.updateAccountPersonalPhoneNumber(validAccountId, newPersonalPhoneNumber));
        }

    }

    @Nested
    @DisplayName("updateAccountWorkPhoneNumber(Long accountId, String workPhoneNumber)")
    class TestUpdateAccountWorkPhoneNumber {

        final String validPersonalNumber = "+7";

        @Test
        void updateAccountWorkPhoneNumberWhenAccountIdIsNull() {
            Throwable exception = assertThrows(IllegalArgumentException.class, () -> {
                accountService.updateAccountWorkAddress(null, validPersonalNumber);
                throw new UnsupportedOperationException("Not supported");
            });
            assertEquals(IllegalArgumentException.class, exception.getClass());
        }

        @Test
        void updateAccountWorkPhoneNumberWhenWorkPhoneNumberIsNull() {
            Throwable exception = assertThrows(IllegalArgumentException.class, () -> {
                accountService.updateAccountWorkAddress(validAccountId, null);
                throw new UnsupportedOperationException("Not supported");
            });
            assertEquals(IllegalArgumentException.class, exception.getClass());
        }

        @Test
        void successfulWorkPhoneNumberUpdating() {
            String newWorkPhoneNumber = "+3";
            Account accountOriginal = new Account.Builder().id(validAccountId).workPhoneNumber(validPersonalNumber)
                    .build();
            when(accountDao.getById(validAccountId)).thenReturn(accountOriginal);
            Account accountChanged = new Account.Builder(accountOriginal).workPhoneNumber(newWorkPhoneNumber).build();
            when(accountDao.updateById(validAccountId, accountChanged)).thenReturn(true);
            assertTrue(accountService.updateAccountWorkPhoneNumber(validAccountId, newWorkPhoneNumber));
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
            when(accountDao.getById(validAccountId)).thenReturn(accountOriginal);
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
            when(accountDao.getById(validAccountId)).thenReturn(accountOriginal);
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
                accountService.updateAccountEmail(null, "new_email");
                throw new UnsupportedOperationException("Not supported");
            });
            assertEquals(IllegalArgumentException.class, exception.getClass());
        }

        @Test
        void updateAccountEmailWhenEmailIsNull() {
            Throwable exception = assertThrows(IllegalArgumentException.class, () -> {
                accountService.updateAccountEmail(validAccountId, null);
                throw new UnsupportedOperationException("Not supported");
            });
            assertEquals(IllegalArgumentException.class, exception.getClass());
        }

        @Test
        void successfulEmailUpdating() {
            String originalEmail = "email_old";
            String newEmail = "email_new";
            Account accountOriginal = new Account.Builder().id(validAccountId).email(originalEmail).build();
            when(accountDao.getById(validAccountId)).thenReturn(accountOriginal);
            Account accountChanged = new Account.Builder(accountOriginal).email(newEmail).build();
            when(accountDao.updateById(validAccountId, accountChanged)).thenReturn(true);
            assertTrue(accountService.updateAccountEmail(validAccountId, newEmail));
        }

    }

    @Nested
    @DisplayName("updateAccountIcq(Long accountId, String icq)")
    class TestUpdateAccountIcq {

        @Test
        void updateAccountIcqWhenAccountIdIsNull() {
            Throwable exception = assertThrows(IllegalArgumentException.class, () -> {
                accountService.updateAccountIcq(null, "new_icq");
                throw new UnsupportedOperationException("Not supported");
            });
            assertEquals(IllegalArgumentException.class, exception.getClass());
        }

        @Test
        void updateAccountIcqWhenIcqIsNull() {
            Throwable exception = assertThrows(IllegalArgumentException.class, () -> {
                accountService.updateAccountIcq(validAccountId, null);
                throw new UnsupportedOperationException("Not supported");
            });
            assertEquals(IllegalArgumentException.class, exception.getClass());
        }

        @Test
        void successfulIcqUpdating() {
            String originalIcq = "icq_old";
            String newIcq = "icq_new";
            Account accountOriginal = new Account.Builder().id(validAccountId).icq(originalIcq).build();
            when(accountDao.getById(validAccountId)).thenReturn(accountOriginal);
            Account accountChanged = new Account.Builder(accountOriginal).icq(newIcq).build();
            when(accountDao.updateById(validAccountId, accountChanged)).thenReturn(true);
            assertTrue(accountService.updateAccountIcq(validAccountId, newIcq));
        }

    }

    @Nested
    @DisplayName("updateAccountSkype(Long accountId, String skype)")
    class TestUpdateAccountSkype {

        @Test
        void updateAccountSkypeWhenAccountIdIsNull() {
            Throwable exception = assertThrows(IllegalArgumentException.class, () -> {
                accountService.updateAccountSkype(null, "new_skype");
                throw new UnsupportedOperationException("Not supported");
            });
            assertEquals(IllegalArgumentException.class, exception.getClass());
        }

        @Test
        void updateAccountSkypeWhenSkypeIsNull() {
            Throwable exception = assertThrows(IllegalArgumentException.class, () -> {
                accountService.updateAccountSkype(validAccountId, null);
                throw new UnsupportedOperationException("Not supported");
            });
            assertEquals(IllegalArgumentException.class, exception.getClass());
        }

        @Test
        void successfulSkypeUpdating() {
            String originalSkype = "skype_old";
            String newSkype = "skype_new";
            Account accountOriginal = new Account.Builder().id(validAccountId).skype(originalSkype).build();
            when(accountDao.getById(validAccountId)).thenReturn(accountOriginal);
            Account accountChanged = new Account.Builder(accountOriginal).skype(newSkype).build();
            when(accountDao.updateById(validAccountId, accountChanged)).thenReturn(true);
            assertTrue(accountService.updateAccountSkype(validAccountId, newSkype));
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
            when(accountDao.getById(validAccountId)).thenReturn(accountOriginal);
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
                accountService.deleteAccount(null);
                throw new UnsupportedOperationException("Not supported");
            });
            assertEquals(IllegalArgumentException.class, exception.getClass());
        }

        @Test
        void whenAccountIsNotExisted() {
            when(accountDao.getById(validAccountId)).thenThrow(DaoException.class);
            Throwable exception = assertThrows(DaoException.class, () -> {
                accountService.deleteAccount(validAccountId);
                throw new UnsupportedOperationException("Not supported");
            });
            assertEquals(DaoException.class, exception.getClass());
        }

        @Test
        void successfulAccountDeleting() {
            when(accountDao.getById(validAccountId)).thenReturn(TEST_ACCOUNT);
            when(accountDao.deleteById(validAccountId)).thenReturn(true);
            assertTrue(accountService.deleteAccount(validAccountId));
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
        void whenRequesterAccountIsNotExisted() {
            when(accountDao.getById(requesterId)).thenThrow(DaoException.class);
            Throwable exception = assertThrows(DaoException.class, () -> {
                accountService.addFriend(requesterId, accepterId);
                throw new UnsupportedOperationException("Not supported");
            });
            assertEquals(DaoException.class, exception.getClass());
        }

        @Test
        void whenAccepterAccountIsNotExisted() {
            when(accountDao.getById(requesterId)).thenReturn(TEST_ACCOUNT);
            when(accountDao.getById(accepterId)).thenThrow(DaoException.class);
            Throwable exception = assertThrows(DaoException.class, () -> {
                accountService.addFriend(requesterId, accepterId);
                throw new UnsupportedOperationException("Not supported");
            });
            assertEquals(DaoException.class, exception.getClass());
        }

        @Test
        void whenNoFriendshipRecordExisted() {
            when(friendshipChecker.checkFriendshipRecordExistence(requesterId, accepterId)).thenReturn(false);
            when(friendshipDao.sendFriendshipRequest(requesterId, accepterId)).thenReturn(true);
            assertTrue(accountService.addFriend(requesterId, accepterId));
        }

        @Test
        void whenAccountsAreAlreadyFriends() {
            when(friendshipChecker.checkFriendshipRecordExistence(requesterId, accepterId)).thenReturn(true);
            when(friendshipChecker.checkUsersAreFriends(requesterId, accepterId)).thenReturn(true);
            assertFalse(accountService.addFriend(requesterId, accepterId));
        }

        @Test
        void whenAccountsTriesDuplicateAlreadyExistedFriendRequest() {
            when(friendshipChecker.checkFriendshipRecordExistence(requesterId, accepterId)).thenReturn(true);
            when(friendshipChecker.checkUsersAreFriends(requesterId, accepterId)).thenReturn(false);
            when(friendshipChecker.checkFriendRequestAlreadyExist(requesterId, accepterId)).thenReturn(true);
            assertFalse(accountService.addFriend(requesterId, accepterId));
        }

        @Test
        void whenAccountAcceptFriendRequest() {
            when(friendshipChecker.checkFriendshipRecordExistence(requesterId, accepterId)).thenReturn(true);
            when(friendshipChecker.checkUsersAreFriends(requesterId, accepterId)).thenReturn(false);
            when(friendshipChecker.checkFriendRequestAlreadyExist(requesterId, accepterId)).thenReturn(false);
            when(friendshipDao.acceptFriendRequest(requesterId, accepterId)).thenReturn(true);
            assertTrue(accountService.addFriend(requesterId, accepterId));
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
        void whenDeletingFriendAccountIsEqualToAccountId() {
            Throwable exception = assertThrows(IllegalArgumentException.class, () -> {
                accountService.deleteFriend(validAccountId, validAccountId);
                throw new UnsupportedOperationException("Not supported");
            });
            assertEquals(IllegalArgumentException.class, exception.getClass());
        }

        @Test
        void whenAccountIsNotExisted() {
            when(accountDao.getById(validAccountId)).thenThrow(DaoException.class);
            Throwable exception = assertThrows(DaoException.class, () -> {
                accountService.deleteFriend(validAccountId, deletingFriendId);
                throw new UnsupportedOperationException("Not supported");
            });
            assertEquals(DaoException.class, exception.getClass());
        }

        @Test
        void whenDeletingFriendAccountIsNotExisted() {
            when(accountDao.getById(validAccountId)).thenReturn(TEST_ACCOUNT);
            when(accountDao.getById(deletingFriendId)).thenThrow(DaoException.class);
            Throwable exception = assertThrows(DaoException.class, () -> {
                accountService.deleteFriend(validAccountId, deletingFriendId);
                throw new UnsupportedOperationException("Not supported");
            });
            assertEquals(DaoException.class, exception.getClass());
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
        void whenAccountIsNotExisted() {
            when(accountDao.getById(validAccountId)).thenThrow(DaoException.class);
            Throwable exception = assertThrows(DaoException.class, () -> {
                accountService.getFriends(validAccountId);
                throw new UnsupportedOperationException("Not supported");
            });
            assertEquals(DaoException.class, exception.getClass());
        }

        @Test
        void whenAccountHasSeveralFriends() {
            List<Long> friends = new ArrayList<>();
            friends.add(1L);
            friends.add(2L);
            when(friendshipDao.getFriendsIds(validAccountId)).thenReturn(friends);
            when(accountDao.getById(anyLong())).thenReturn(TEST_ACCOUNT);
            assertEquals(2, accountService.getFriends(validAccountId).size());
        }

    }

    @Nested
    @DisplayName("List<Account> getAllAccounts")
    class TestGetAllAccounts {

        @Test
        void whenAccountsTableIsEmpty() {
            when(accountDao.getAll()).thenReturn(new ArrayList<>());
            assertEquals(new ArrayList<Account>(), accountService.getAllAccounts());
        }

        @Test
        void when2AccountsExisted() {
            when(accountDao.getAll()).thenReturn(new ArrayList<>(Arrays.asList(TEST_ACCOUNT, TEST_ACCOUNT)));
            assertEquals(2, accountService.getAllAccounts().size());
        }

    }

}
