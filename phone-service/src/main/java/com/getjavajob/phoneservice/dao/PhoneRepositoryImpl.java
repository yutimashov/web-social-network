package com.getjavajob.phoneservice.dao;

import com.getjavajob.training.timashovy.socialnetwork.domain.phone.Phone;
import com.getjavajob.training.timashovy.socialnetwork.domain.phone.PhoneType;
import org.slf4j.Logger;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

import static org.slf4j.LoggerFactory.getLogger;

/**
 * Singleton class responsible for working with `account_data.phones` table in DB.
 * It provides safe multithreading approach for creating singleton object using synchronization mechanism.
 */
@Repository
public class PhoneRepositoryImpl implements PhoneRepository {

    private static final Logger logger = getLogger(PhoneRepositoryImpl.class);

    private final PhoneRepositorySpringData phoneRepositorySpringData;

    public PhoneRepositoryImpl(PhoneRepositorySpringData phoneRepositorySpringData) {
        this.phoneRepositorySpringData = phoneRepositorySpringData;
    }

    @Override
    public Phone save(Phone phone) {
        phoneRepositorySpringData.save(phone);
        return phone;
    }

    @Override
    public Optional<Phone> getById(Long id) {
        return phoneRepositorySpringData.findById(id);
    }

    @Override
    public List<String> getPhoneNumbers(Long accountId, PhoneType phoneType) {
        return phoneRepositorySpringData.findPhoneNumbersByAccountIdAndPhoneType(accountId, phoneType);
    }

    @Override
    public void updateNumber(Long id, String newNumber) {
        Optional<Phone> existingPhone = phoneRepositorySpringData.findById(id);
        existingPhone.ifPresent(phone -> phone.setNumber(newNumber));
    }

    @Override
    public void delete(Long id) {
        if (phoneRepositorySpringData.existsById(id)) {
            phoneRepositorySpringData.deleteById(id);
        }
    }

}
