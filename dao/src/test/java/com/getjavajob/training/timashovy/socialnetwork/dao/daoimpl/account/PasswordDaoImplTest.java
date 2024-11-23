package com.getjavajob.training.timashovy.socialnetwork.dao.daoimpl.account;

import com.getjavajob.training.timashovy.socialnetwork.dao.exception.DaoException;
import com.getjavajob.training.timashovy.socialnetwork.domain.password.Password;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceException;
import javax.persistence.TypedQuery;
import java.util.Optional;

import static java.util.Optional.empty;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.openMocks;

class PasswordDaoImplTest {

    @Mock
    private EntityManager entityManager;

    @Mock
    private TypedQuery<Password> query;

    @InjectMocks
    private PasswordDao passwordDao;

    private Password password;

    @BeforeEach
    public void setUp() {
        openMocks(this);
        password = new Password("test", "test");
    }

    @Nested
    @DisplayName("Password save(Password password)")
    class TestSavePassword {

        @Test
        public void shouldReturnPasswordWhenPasswordSaved() {
            assertEquals(password, passwordDao.save(password));
        }

        @Test
        void shouldThrowExceptionWhenPasswordCannotBeSaved() {
            doThrow(new PersistenceException()).when(entityManager).persist(password);
            assertThrows(DaoException.class, () -> passwordDao.save(password));
        }

    }

    @Nested
    @DisplayName("Optional<Password> getById(Long id)")
    class TestGetPasswordById {

        @Test
        void shouldReturnOptionalWithPasswordWhenPasswordExists() {
            Long id = 1L;
            when(entityManager.find(Password.class, id)).thenReturn(password);
            Optional<Password> optionalAccount = passwordDao.getById(id);
            assertTrue(optionalAccount.isPresent());
            assertEquals(password, optionalAccount.get());
        }

        @Test
        void shouldReturnEmptyOptionalWhenPasswordDoesNotExist() {
            assertEquals(empty(), passwordDao.getById(-1L));
        }

    }

    @Nested
    @DisplayName("Optional<Password> findByEmail(String email)")
    class TestGetPasswordByEmail {

        @Test
        void shouldReturnOptionalWithPasswordWhenPasswordExists() {
            String email = "test@example.com";
            when(entityManager.createQuery(
                    "select p from Password p join p.account a where a.email = :email", Password.class))
                    .thenReturn(query);
            when(query.setParameter("email", email)).thenReturn(query);
            when(query.getSingleResult()).thenReturn(password);
            Optional<Password> result = passwordDao.findByEmail(email);
            assertEquals(Optional.of(password), result);
        }

        @Test
        void shouldReturnEmptyOptionalWhenAccountEmailDoesNotExist() {
            String email = "test@example.com";
            when(entityManager.createQuery(
                    "select p from Password p join p.account a where a.email = :email", Password.class))
                    .thenReturn(query);
            when(query.setParameter("email", email)).thenReturn(query);
            when(query.getSingleResult()).thenThrow(new NoResultException());
            Optional<Password> result = passwordDao.findByEmail(email);
            assertEquals(Optional.empty(), result);
        }

        @Test
        void shouldReturnDaoExceptionWhenProblemsOccurredExecutingQuery() {
            String email = "error@example.com";
            when(entityManager.createQuery(
                    "select p from Password p join p.account a where a.email = :email", Password.class))
                    .thenReturn(query);
            when(query.setParameter("email", email)).thenReturn(query);
            when(query.getSingleResult()).thenThrow(new PersistenceException());
            assertThrows(DaoException.class, () -> passwordDao.findByEmail(email));
        }

    }

    @Nested
    @DisplayName("void delete(Long id)")
    class TestDelete {

        @Test
        void shouldSuccessfullyDeletePasswordIfPossible() {
            Long id = 1L;
            when(entityManager.find(Password.class, id)).thenReturn(password);
            passwordDao.delete(id);
            verify(entityManager).remove(password);
        }

        @Test
        public void shouldNotCallDeleteIfPasswordNotFound() {
            Long id = 1L;
            when(entityManager.find(Password.class, id)).thenReturn(null);
            passwordDao.delete(id);
            verify(entityManager, never()).remove(any(Password.class));
        }

        @Test
        public void shouldThrowDaoExceptionIfPasswordCannotBeDeleted() {
            Long id = 1L;
            when(entityManager.find(Password.class, id)).thenThrow(new PersistenceException());
            assertThrows(DaoException.class, () -> passwordDao.delete(id));
            verify(entityManager, never()).remove(any(Password.class));
        }

    }

}
