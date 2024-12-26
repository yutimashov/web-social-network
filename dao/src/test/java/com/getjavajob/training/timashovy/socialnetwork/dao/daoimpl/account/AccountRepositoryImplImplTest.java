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

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceException;
import jakarta.persistence.TypedQuery;
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
    private Long id;

    @BeforeEach
    public void setUp() {
        openMocks(this);
        account = new Account();
        id = 1L;
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
            AccountRole newRole = ADMIN;
            when(entityManager.find(Account.class, id)).thenReturn(account);
            accountRepositoryImpl.changeRole(id, newRole);
            verify(entityManager).find(Account.class, id);
            assertEquals(newRole, account.getRole());
        }

        @Test
        public void shouldThrowDaoExceptionWhenRoleNotChanged() {
            when(entityManager.find(Account.class, id)).thenThrow(new PersistenceException());
            assertThrows(DaoException.class, () -> accountRepositoryImpl.changeRole(id, ADMIN));
            verify(entityManager).find(Account.class, id);
        }

    }

    @Nested
    @DisplayName("Optional<Account> getById(Long id)")
    class TestGetById {

        @Test
        void shouldReturnOptionalWithAccountWhenAccountExists() {
            when(entityManager.find(Account.class, id)).thenReturn(account);
            Optional<Account> optionalAccount = accountRepositoryImpl.getById(id);
            assertTrue(optionalAccount.isPresent());
            assertEquals(account, optionalAccount.get());
        }

        @Test
        void shouldReturnEmptyOptionalWhenAccountNotExists() {
            when(entityManager.find(Account.class, id)).thenThrow(new PersistenceException());
            assertThrows(DaoException.class, () -> accountRepositoryImpl.getById(id));
            verify(entityManager).find(Account.class, id);
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
            when(entityManager.find(Account.class, id)).thenReturn(account);
            accountRepositoryImpl.delete(id);
            verify(entityManager).remove(account);
        }

        @Test
        public void shouldNotCallDeleteIfAccountNotFound() {
            when(entityManager.find(Account.class, id)).thenReturn(null);
            accountRepositoryImpl.delete(id);
            verify(entityManager, never()).remove(any(Account.class));
        }

        @Test
        public void shouldThrowDaoExceptionIfAccountCannotBeDeleted() {
            when(entityManager.find(Account.class, id)).thenThrow(new PersistenceException());
            assertThrows(DaoException.class, () -> accountRepositoryImpl.delete(id));
            verify(entityManager, never()).remove(any(Account.class));
        }

    }

}
