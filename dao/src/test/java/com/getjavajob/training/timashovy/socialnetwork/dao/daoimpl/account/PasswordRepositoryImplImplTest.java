package com.getjavajob.training.timashovy.socialnetwork.dao.daoimpl.account;

import com.getjavajob.training.timashovy.socialnetwork.dao.interfaces.springdatarepositories.account.PasswordRepositorySpringData;
import com.getjavajob.training.timashovy.socialnetwork.domain.password.Password;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.util.Optional;

import static java.util.Optional.empty;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.openMocks;

class PasswordRepositoryImplImplTest {

    @Mock
    private PasswordRepositorySpringData passwordRepositorySpringData;

    @InjectMocks
    private PasswordRepositoryImpl passwordRepositoryImpl;

    private Password password;

    @BeforeEach
    public void setUp() {
        openMocks(this);
        password = new Password("test");
    }

    @Nested
    @DisplayName("Password save(Password password)")
    class TestSavePassword {

        @Test
        public void shouldReturnPasswordWhenPasswordSaved() {
            assertEquals(password, passwordRepositoryImpl.save(password));
        }

    }

    @Nested
    @DisplayName("Optional<Password> getById(Long id)")
    class TestGetPasswordById {

        @Test
        void shouldReturnOptionalWithPasswordWhenPasswordExists() {
            Long id = 1L;
            when(passwordRepositorySpringData.findById(id)).thenReturn(Optional.ofNullable(password));
            Optional<Password> optionalAccount = passwordRepositoryImpl.getById(id);
            assertTrue(optionalAccount.isPresent());
            assertEquals(password, optionalAccount.get());
        }

        @Test
        void shouldReturnEmptyOptionalWhenPasswordDoesNotExist() {
            assertEquals(empty(), passwordRepositoryImpl.getById(-1L));
        }

    }

    @Nested
    @DisplayName("void delete(Long id)")
    class TestDelete {

        @Test
        void shouldSuccessfullyDeletePasswordIfPossible() {
            Long id = 1L;
            when(passwordRepositorySpringData.existsById(id)).thenReturn(true);
            passwordRepositorySpringData.deleteById(id);
            verify(passwordRepositorySpringData).deleteById(id);
        }

    }

}
