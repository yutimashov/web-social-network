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
import java.util.List;
import java.util.Optional;

import static com.getjavajob.training.timashovy.socialnetwork.domain.account.AccountRole.ADMIN;
import static java.util.Arrays.asList;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.openMocks;

class AccountRepositoryImplImplTest {

    @Mock
    private EntityManager entityManager;

    @Mock
    private TypedQuery<Account> query;

    @InjectMocks
    private AccountRepositoryImpl accountRepositoryImpl;

    private Account account;

    @BeforeEach
    public void setUp() {
        openMocks(this);
        account = new Account();
    }

    @Nested
    @DisplayName("Account save(Account account)")
    class TestSaveAccount {

        @Test
        public void shouldReturnAccountWhenAccountSaved() {
            assertEquals(account, accountRepositoryImpl.save(account));
        }

        @Test
        public void shouldThrowDaoExceptionWhenAccountNotSaved() {
            doThrow(new PersistenceException()).when(entityManager).persist(account);
            assertThrows(DaoException.class, () -> accountRepositoryImpl.save(account));
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
            accountRepositoryImpl.changeRole(accountId, newRole);
            verify(entityManager).find(Account.class, accountId);
            assertEquals(newRole, account.getRole());
        }

        @Test
        public void shouldThrowDaoExceptionWhenRoleNotChanged() {
            Long accountId = 1L;
            when(entityManager.find(Account.class, accountId)).thenThrow(new PersistenceException());
            assertThrows(DaoException.class, () -> accountRepositoryImpl.changeRole(accountId, ADMIN));
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
            Optional<Account> optionalAccount = accountRepositoryImpl.getById(accountId);
            assertTrue(optionalAccount.isPresent());
            assertEquals(account, optionalAccount.get());
        }

        @Test
        void shouldReturnEmptyOptionalWhenAccountNotExists() {
            Long accountId = 1L;
            when(entityManager.find(Account.class, accountId)).thenThrow(new PersistenceException());
            assertThrows(DaoException.class, () -> accountRepositoryImpl.getById(accountId));
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
            assertEquals(expectedAccounts, accountRepositoryImpl.getAll());
        }

        @Test
        void shouldThrowDaoExceptionWhenCanNotGetAllAccounts() {
            when(entityManager.createQuery("select a from Account a", Account.class)).thenReturn(query);
            when(query.getResultList()).thenThrow(new PersistenceException());
            assertThrows(DaoException.class, () -> accountRepositoryImpl.getAll());
        }

    }

    @Nested
    @DisplayName("void delete(Long id)")
    class TestDelete {

        @Test
        void shouldSuccessfullyDeleteAccountIfPossible() {
            Long accountId = 1L;
            when(entityManager.find(Account.class, accountId)).thenReturn(account);
            accountRepositoryImpl.delete(accountId);
            verify(entityManager).remove(account);
        }

        @Test
        public void shouldNotCallDeleteIfAccountNotFound() {
            Long accountId = 1L;
            when(entityManager.find(Account.class, accountId)).thenReturn(null);
            accountRepositoryImpl.delete(accountId);
            verify(entityManager, never()).remove(any(Account.class));
        }

        @Test
        public void shouldThrowDaoExceptionIfAccountCannotBeDeleted() {
            Long accountId = 1L;
            when(entityManager.find(Account.class, accountId)).thenThrow(new PersistenceException());
            assertThrows(DaoException.class, () -> accountRepositoryImpl.delete(accountId));
            verify(entityManager, never()).remove(any(Account.class));
        }

    }

}
