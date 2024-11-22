package com.getjavajob.training.timashovy.socialnetwork.dao.daoimpl.account;

import com.getjavajob.training.timashovy.socialnetwork.dao.exception.DaoException;
import com.getjavajob.training.timashovy.socialnetwork.domain.account.Account;
import com.getjavajob.training.timashovy.socialnetwork.domain.account.AccountRole;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceException;
import javax.persistence.TypedQuery;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.getjavajob.training.timashovy.socialnetwork.domain.account.AccountRole.ADMIN;
import static com.getjavajob.training.timashovy.socialnetwork.domain.account.AccountRole.REGULAR;
import static java.time.LocalDate.of;
import static java.util.Arrays.asList;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.openMocks;

class AccountDaoImplTest {

    @Mock
    private EntityManager entityManager;

    @Mock
    private TypedQuery<Account> query;

    @InjectMocks
    private AccountDao accountDao;

    private Account account;

    @BeforeEach
    public void setUp() {
        openMocks(this);
        account = new Account.Builder()
                .firstName("")
                .lastName("")
                .middleName("")
                .birthDate(of(2000, 1, 1))
                .phones(new ArrayList<>())
                .personalAddress("")
                .workAddress("")
                .email("")
                .icq("")
                .skype("")
                .additionalInfo("")
                .role(REGULAR)
                .build();
    }

    @Nested
    @DisplayName("Account save(Account account)")
    class TestSaveAccount {

        @Test
        public void shouldReturnAccountWhenAccountSaved() {
            assertEquals(account, accountDao.save(account));
        }

        @Test
        public void shouldThrowDaoExceptionWhenAccountNotSaved() {
            doThrow(new PersistenceException()).when(entityManager).persist(account);
            assertThrows(DaoException.class, () -> accountDao.save(account));
        }

    }

    @Nested
    @DisplayName("void changeRole(Long id, AccountRole role)")
    class TestMakeAdmin {

        @Test
        public void shouldCallChangeRoleWhenRoleChanged() {
            Long accountId = 1L;
            AccountRole newRole = ADMIN;
            when(entityManager.find(Account.class, accountId)).thenReturn(account);
            accountDao.changeRole(accountId, newRole);
            verify(entityManager).find(Account.class, accountId);
            assertEquals(newRole, account.getRole());
        }

        @Test
        public void shouldThrowDaoExceptionWhenRoleNotChanged() {
            Long accountId = 1L;
            when(entityManager.find(Account.class, accountId)).thenThrow(new PersistenceException());
            assertThrows(DaoException.class, () -> accountDao.changeRole(accountId, ADMIN));
            verify(entityManager).find(Account.class, accountId);
        }

    }

    @Nested
    @DisplayName("Optional<Account> getById(Long id)")
    class TestGetById {

        @Test
        void shouldReturnOptionalWithAccountWhenAccountExists() {
            Long accountId = 1L;
            when(entityManager.find(Account.class, accountId)).thenReturn(account);
            Optional<Account> optionalAccount = accountDao.getById(accountId);
            assertTrue(optionalAccount.isPresent());
            assertEquals(account, optionalAccount.get());
        }

        @Test
        void shouldReturnEmptyOptionalWhenAccountNotExists() {
            Long accountId = 1L;
            when(entityManager.find(Account.class, accountId)).thenThrow(new PersistenceException());
            assertThrows(DaoException.class, () -> accountDao.getById(accountId));
            verify(entityManager).find(Account.class, accountId);
        }

    }

    @Nested
    @DisplayName("List<Account> getAll()")
    class TestGetAll {

        @Test
        void shouldReturnActualListWhenTableIsNotEmpty() {
            List<Account> expectedAccounts = asList(account, account);
            when(entityManager.createQuery("select a from Account a", Account.class)).thenReturn(query);
            when(query.getResultList()).thenReturn(expectedAccounts);
            assertEquals(expectedAccounts, accountDao.getAll());
        }

        @Test
        void shouldThrowDaoExceptionWhenCanNotGetAllAccounts() {
            when(entityManager.createQuery("select a from Account a", Account.class)).thenReturn(query);
            when(query.getResultList()).thenThrow(new PersistenceException());
            assertThrows(DaoException.class, () -> accountDao.getAll());
        }

    }

    @Nested
    @DisplayName("void delete(Long id)")
    class TestDelete {

        @Test
        void shouldSuccessfullyDeleteAccountIfPossible() {
            Long accountId = 1L;
            Account account = new Account();
            when(entityManager.find(Account.class, accountId)).thenReturn(account);
            accountDao.delete(accountId);
            verify(entityManager).find(Account.class, accountId);
            verify(entityManager).remove(account);
        }

        @Test
        public void shouldNotCallDeleteIfAccountNotFound() {
            Long accountId = 1L;
            when(entityManager.find(Account.class, accountId)).thenReturn(null);
            accountDao.delete(accountId);
            verify(entityManager).find(Account.class, accountId);
            verify(entityManager, never()).remove(any(Account.class));
        }

        @Test
        public void shouldThrowDaoExceptionIfAccountCannotBeDeleted() {
            Long accountId = 1L;
            when(entityManager.find(Account.class, accountId)).thenThrow(new PersistenceException());
            assertThrows(DaoException.class, () -> accountDao.delete(accountId));
            verify(entityManager).find(Account.class, accountId);
            verify(entityManager, never()).remove(any(Account.class));
        }

    }

}
