package com.getjavajob.training.timashovy.socialnetwork.dao.daoimpl.account;

import com.getjavajob.training.timashovy.socialnetwork.dao.exception.DaoException;
import com.getjavajob.training.timashovy.socialnetwork.dao.interfaces.account.AccountRepositorySpringData;
import com.getjavajob.training.timashovy.socialnetwork.domain.account.Account;
import com.getjavajob.training.timashovy.socialnetwork.domain.account.AccountRole;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.util.List;
import java.util.Optional;

import static com.getjavajob.training.timashovy.socialnetwork.domain.account.AccountRole.ADMIN;
import static java.util.Arrays.asList;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.openMocks;

class AccountRepositoryImplImplTest {

    @Mock
    private AccountRepositorySpringData accountRepositorySpringData;

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
            doThrow(new DaoException()).when(accountRepositorySpringData).save(account);
            assertThrows(DaoException.class, () -> accountRepositoryImpl.save(account));
        }

    }

    @Nested
    @DisplayName("void changeRole(Long id, AccountRole role)")
    class TestMakeAdmin {

        @Test
        public void shouldCallChangeRoleWhenRoleChanged() {
            AccountRole newRole = ADMIN;
            when(accountRepositorySpringData.findById(id)).thenReturn(Optional.ofNullable(account));
            accountRepositoryImpl.changeRole(id, newRole);
            verify(accountRepositorySpringData).findById(id);
            assertEquals(newRole, account.getRole());
        }

    }

    @Nested
    @DisplayName("Optional<Account> getById(Long id)")
    class TestGetById {

        @Test
        void shouldReturnOptionalWithAccountWhenAccountExists() {
            when(accountRepositorySpringData.findById(id)).thenReturn(Optional.ofNullable(account));
            Optional<Account> optionalAccount = accountRepositoryImpl.getById(id);
            assertTrue(optionalAccount.isPresent());
            assertEquals(account, optionalAccount.get());
        }

    }

    @Nested
    @DisplayName("List<Account> getAll()")
    class TestGetAll {

        @Test
        void shouldReturnActualListWhenTableIsNotEmpty() {
            List<Account> expectedAccounts = asList(account, account);
            when(accountRepositorySpringData.findAll()).thenReturn(expectedAccounts);
            assertEquals(expectedAccounts, accountRepositoryImpl.getAll());
        }

    }

    @Nested
    @DisplayName("void delete(Long id)")
    class TestDelete {

        @Test
        void shouldSuccessfullyDeleteAccountIfPossible() {
            when(accountRepositorySpringData.existsById(id)).thenReturn(true);
            accountRepositoryImpl.delete(id);
            verify(accountRepositorySpringData).deleteById(id);
        }

        @Test
        public void shouldNotCallDeleteIfAccountNotFound() {
            when(accountRepositorySpringData.findById(id)).thenReturn(null);
            accountRepositoryImpl.delete(id);
        }

    }

}
