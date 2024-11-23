package com.getjavajob.training.timashovy.socialnetwork.dao.daoimpl.account;

import com.getjavajob.training.timashovy.socialnetwork.dao.exception.DaoException;
import com.getjavajob.training.timashovy.socialnetwork.domain.account.Account;
import com.getjavajob.training.timashovy.socialnetwork.domain.phone.Phone;
import com.getjavajob.training.timashovy.socialnetwork.domain.phone.PhoneType;
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

import static com.getjavajob.training.timashovy.socialnetwork.domain.phone.PhoneType.PERSONAL;
import static com.getjavajob.training.timashovy.socialnetwork.domain.phone.PhoneType.WORKING;
import static java.util.Arrays.asList;
import static java.util.Collections.emptyList;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.openMocks;


class PhoneDaoImplTest {

    @Mock
    private EntityManager entityManager;

    @Mock
    private TypedQuery<Phone> query;

    @Mock
    private TypedQuery<String> phoneValuesQuery;

    @InjectMocks
    private PhoneDao phoneDao;

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
            assertEquals(phone, phoneDao.save(phone));
        }

        @Test
        public void shouldThrowDaoExceptionWhenPhoneNotSaved() {
            doThrow(new PersistenceException()).when(entityManager).persist(phone);
            assertThrows(DaoException.class, () -> phoneDao.save(phone));
        }

    }

    @Nested
    @DisplayName("Optional<Phone> getById(Long id)")
    class TestGetById {

        @Test
        void shouldReturnOptionalWithPhoneWhenPhoneExists() {
            Long id = 1L;
            when(entityManager.find(Phone.class, id)).thenReturn(phone);
            Optional<Phone> optionalAccount = phoneDao.getById(id);
            assertTrue(optionalAccount.isPresent());
            assertEquals(phone, optionalAccount.get());
        }

        @Test
        void shouldReturnEmptyOptionalWhenPhoneNotExists() {
            Long id = 1L;
            when(entityManager.find(Phone.class, id)).thenThrow(new PersistenceException());
            assertThrows(DaoException.class, () -> phoneDao.getById(id));
        }

    }

    @Nested
    @DisplayName("List<Phone> getPhones(Long accountId, PhoneType phoneType)")
    class TestGetPhones {

        @Test
        void shouldReturnWorkingPhonesWhenRequestForWorkingPhones() {
            PhoneType phoneType = WORKING;
            Long accountId = 1L;
            List<Phone> expectedPhones = asList(phone, phone);
            when(entityManager.createQuery("select p from Phone p where p.account.id = :accountId "
                    + "and p.phoneType = :phoneType", Phone.class)).thenReturn(query);
            when(query.setParameter("accountId", accountId)).thenReturn(query);
            when(query.setParameter("phoneType", phoneType)).thenReturn(query);
            when(query.getResultList()).thenReturn(expectedPhones);
            assertEquals(expectedPhones, phoneDao.getPhones(accountId, phoneType));
        }

        @Test
        void shouldReturnEmptyPhoneListWhenNoSuchPhonesExisting() {
            PhoneType phoneType = WORKING;
            Long accountId = 1L;
            List<Phone> expectedPhones = emptyList();
            when(entityManager.createQuery("select p from Phone p where p.account.id = :accountId "
                    + "and p.phoneType = :phoneType", Phone.class)).thenReturn(query);
            when(query.setParameter("accountId", accountId)).thenReturn(query);
            when(query.setParameter("phoneType", phoneType)).thenReturn(query);
            when(query.getResultList()).thenReturn(expectedPhones);
            assertEquals(expectedPhones, phoneDao.getPhones(accountId, phoneType));
        }

        @Test
        void shouldThrowDaoExceptionWhenCanNotGetPhones() {
            PhoneType phoneType = WORKING;
            Long accountId = 1L;
            when(entityManager.createQuery("select p from Phone p where p.account.id = :accountId "
                    + "and p.phoneType = :phoneType", Phone.class)).thenReturn(query);
            when(query.setParameter("accountId", accountId)).thenReturn(query);
            when(query.setParameter("phoneType", phoneType)).thenReturn(query);
            when(query.getResultList()).thenThrow(new PersistenceException());
            assertThrows(DaoException.class, () -> phoneDao.getPhones(accountId, phoneType));
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
            when(entityManager.createQuery("select p.number from Phone p where p.account.id = :accountId "
                    + "and p.phoneType = :phoneType", String.class)).thenReturn(phoneValuesQuery);
            when(phoneValuesQuery.setParameter("accountId", accountId)).thenReturn(phoneValuesQuery);
            when(phoneValuesQuery.setParameter("phoneType", phoneType)).thenReturn(phoneValuesQuery);
            when(phoneValuesQuery.getResultList()).thenReturn(expectedPhoneValues);
            assertEquals(expectedPhoneValues, phoneDao.getPhoneNumbers(accountId, phoneType));
        }

        @Test
        void shouldReturnEmptyPhoneValuesListWhenNoSuchPhonesExisting() {
            PhoneType phoneType = WORKING;
            Long accountId = 1L;
            List<String> expectedPhoneValues = emptyList();
            when(entityManager.createQuery("select p.number from Phone p where p.account.id = :accountId "
                    + "and p.phoneType = :phoneType", String.class)).thenReturn(phoneValuesQuery);
            when(phoneValuesQuery.setParameter("accountId", accountId)).thenReturn(phoneValuesQuery);
            when(phoneValuesQuery.setParameter("phoneType", phoneType)).thenReturn(phoneValuesQuery);
            when(phoneValuesQuery.getResultList()).thenReturn(expectedPhoneValues);
            assertEquals(expectedPhoneValues, phoneDao.getPhoneNumbers(accountId, phoneType));
        }

        @Test
        void shouldThrowDaoExceptionWhenCanNotGetPhonesValues() {
            PhoneType phoneType = WORKING;
            Long accountId = 1L;
            when(entityManager.createQuery("select p.number from Phone p where p.account.id = :accountId "
                    + "and p.phoneType = :phoneType", String.class)).thenReturn(phoneValuesQuery);
            when(phoneValuesQuery.setParameter("accountId", accountId)).thenReturn(phoneValuesQuery);
            when(phoneValuesQuery.setParameter("phoneType", phoneType)).thenReturn(phoneValuesQuery);
            when(phoneValuesQuery.getResultList()).thenThrow(new PersistenceException());
            assertThrows(DaoException.class, () -> phoneDao.getPhoneNumbers(accountId, phoneType));
        }

    }

    @Nested
    @DisplayName("void updateNumber(Long phoneId, String newNumber)")
    class TestUpdatePhoneNumber {

        @Test
        void shouldReturnTrueWhenUpdateWithSuccess() {
            Long id = 1L;
            when(entityManager.find(Phone.class, id)).thenReturn(phone);
            phoneDao.updateNumber(id, "test");
            assertEquals(phone.getNumber(), "test");
        }

        @Test
        void shouldThrowDaoExceptionWhenCanNotUpdatePhone() {
            Long id = 1L;
            when(entityManager.find(Phone.class, id)).thenThrow(new PersistenceException());
            assertThrows(DaoException.class, () -> phoneDao.updateNumber(id, ""));
        }

    }

    @Nested
    @DisplayName("void delete(Long id)")
    class TestDelete {

        @Test
        void shouldSuccessfullyDeletePhoneIfPossible() {
            Long id = 1L;
            when(entityManager.find(Phone.class, id)).thenReturn(phone);
            phoneDao.delete(id);
            verify(entityManager).remove(phone);
        }

        @Test
        public void shouldNotCallDeleteIfAccountNotFound() {
            Long id = 1L;
            when(entityManager.find(Phone.class, id)).thenReturn(null);
            phoneDao.delete(id);
            verify(entityManager, never()).remove(any(Phone.class));
        }

        @Test
        public void shouldThrowDaoExceptionIfAccountCannotBeDeleted() {
            Long id = 1L;
            when(entityManager.find(Phone.class, id)).thenThrow(new PersistenceException());
            assertThrows(DaoException.class, () -> phoneDao.delete(id));
            verify(entityManager, never()).remove(any(Phone.class));
        }

    }

}
