package com.getjavajob.training.timashovy.socialnetwork.dao.daoimpl.account;

import com.getjavajob.training.timashovy.socialnetwork.dao.interfaces.springdatarepositories.account.PhoneRepositorySpringData;
import com.getjavajob.training.timashovy.socialnetwork.domain.account.Account;
import com.getjavajob.training.timashovy.socialnetwork.domain.phone.Phone;
import com.getjavajob.training.timashovy.socialnetwork.domain.phone.PhoneType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.util.List;
import java.util.Optional;

import static com.getjavajob.training.timashovy.socialnetwork.domain.phone.PhoneType.PERSONAL;
import static com.getjavajob.training.timashovy.socialnetwork.domain.phone.PhoneType.WORKING;
import static java.util.Arrays.asList;
import static java.util.Collections.emptyList;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.openMocks;


class PhoneRepositoryImplImplTest {

    @Mock
    private PhoneRepositorySpringData phoneRepositorySpringData;

    @InjectMocks
    private PhoneRepositoryImpl phoneRepositoryImpl;

    private Phone phone;

    @BeforeEach
    public void setUp() {
        openMocks(this);
        phone = new Phone(PERSONAL, "", new Account());
    }


    @Nested
    @DisplayName("Phone save(Phone phone)")
    class TestCreatePhone {

        @Test
        void shouldReturnPhoneWhenPhoneSaved() {
            assertEquals(phone, phoneRepositoryImpl.save(phone));
        }

    }

    @Nested
    @DisplayName("Optional<Phone> getById(Long id)")
    class TestGetById {

        @Test
        void shouldReturnOptionalWithPhoneWhenPhoneExists() {
            Long id = 1L;
            when(phoneRepositorySpringData.findById(id)).thenReturn(Optional.ofNullable(phone));
            Optional<Phone> optionalAccount = phoneRepositoryImpl.getById(id);
            assertTrue(optionalAccount.isPresent());
            assertEquals(phone, optionalAccount.get());
        }

    }

    @Nested
    @DisplayName("List<Phone> getPhones(Long accountId, PhoneType phoneType)")
    class TestGetPhones {

        @Test
        void shouldReturnWorkingPhonesWhenRequestForWorkingPhones() {
            Long accountId = 1L;
            List<Phone> expectedPhones = asList(phone, phone);
            when(phoneRepositorySpringData.findAll()).thenReturn(expectedPhones);
            assertEquals(expectedPhones, phoneRepositoryImpl.getPhones(accountId, WORKING));
        }

        @Test
        void shouldReturnEmptyPhoneListWhenNoSuchPhonesExisting() {
            Long accountId = 1L;
            List<Phone> expectedPhones = emptyList();
            when(phoneRepositorySpringData.findAll()).thenReturn(expectedPhones);
            assertEquals(expectedPhones, phoneRepositoryImpl.getPhones(accountId, WORKING));
        }

    }

    @Nested
    @DisplayName("List<String> getPhoneNumbers(Long accountId, PhoneType phoneType)")
    class TestGetPhoneNumbers {

        @Test
        void shouldReturnWorkingPhonesValuesWhenRequestForWorkingPhonesValues() {
            PhoneType phoneType = WORKING;
            Long accountId = 1L;
            List<String> expectedPhoneValues = asList("", "");
            when(phoneRepositorySpringData.findPhoneNumbersByAccountIdAndPhoneType(accountId, phoneType))
                    .thenReturn(expectedPhoneValues);
            assertEquals(expectedPhoneValues, phoneRepositoryImpl.getPhoneNumbers(accountId, phoneType));
        }

        @Test
        void shouldReturnEmptyPhoneValuesListWhenNoSuchPhonesExisting() {
            PhoneType phoneType = WORKING;
            Long accountId = 1L;
            List<String> expectedPhoneValues = emptyList();
            when(phoneRepositorySpringData.findPhoneNumbersByAccountIdAndPhoneType(accountId, phoneType))
                    .thenReturn(expectedPhoneValues);
            assertEquals(expectedPhoneValues, phoneRepositoryImpl.getPhoneNumbers(accountId, phoneType));
        }

    }

    @Nested
    @DisplayName("void updateNumber(Long phoneId, String newNumber)")
    class TestUpdatePhoneNumber {

        @Test
        void shouldReturnTrueWhenUpdateWithSuccess() {
            Long id = 1L;
            when(phoneRepositorySpringData.findById(id)).thenReturn(Optional.ofNullable(phone));
            phoneRepositoryImpl.updateNumber(id, "test");
            assertEquals(phone.getNumber(), "test");
        }

    }

    @Nested
    @DisplayName("void delete(Long id)")
    class TestDelete {

        @Test
        void shouldSuccessfullyDeletePhoneIfPossible() {
            Long id = 1L;
            when(phoneRepositorySpringData.existsById(id)).thenReturn(true);
            phoneRepositoryImpl.delete(id);
            verify(phoneRepositorySpringData).deleteById(id);
        }

        @Test
        public void shouldNotCallDeleteIfAccountNotFound() {
            Long id = 1L;
            when(phoneRepositorySpringData.findById(id)).thenReturn(Optional.empty());
            phoneRepositoryImpl.delete(id);
            verify(phoneRepositorySpringData, never()).deleteById(id);
        }

    }

}
