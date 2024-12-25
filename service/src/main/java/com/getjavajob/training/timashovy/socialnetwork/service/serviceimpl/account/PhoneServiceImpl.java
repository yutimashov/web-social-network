package com.getjavajob.training.timashovy.socialnetwork.service.serviceimpl.account;

import com.getjavajob.training.timashovy.socialnetwork.dao.interfaces.account.PhoneRepository;
import com.getjavajob.training.timashovy.socialnetwork.domain.account.Account;
import com.getjavajob.training.timashovy.socialnetwork.domain.phone.Phone;
import com.getjavajob.training.timashovy.socialnetwork.domain.phone.PhoneType;
import com.getjavajob.training.timashovy.socialnetwork.service.interfaces.PhoneService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.getjavajob.training.timashovy.socialnetwork.domain.phone.PhoneType.PERSONAL;
import static com.getjavajob.training.timashovy.socialnetwork.domain.phone.PhoneType.WORKING;

@Service
public class PhoneServiceImpl implements PhoneService {

    private final PhoneRepository phoneRepository;

    public PhoneServiceImpl(PhoneRepository phoneRepository) {
        this.phoneRepository = phoneRepository;
    }

    @Transactional
    @Override
    public void createPersonalPhones(Account account, String phoneNumbers) {
        String[] phoneNumbersSeparated = phoneNumbers.split(",");
        for (String phoneNumberSeparated : phoneNumbersSeparated) {
            phoneRepository.save(new Phone(PERSONAL, phoneNumberSeparated, account));
        }
    }

    @Transactional
    @Override
    public void createWorkingPhones(Account account, String phoneNumbers) {
        String[] phoneNumbersSeparated = phoneNumbers.split(",");
        for (String phoneNumberSeparated : phoneNumbersSeparated) {
            phoneRepository.save(new Phone(WORKING, phoneNumberSeparated, account));
        }
    }

    @Override
    public List<Phone> getPhones(Long accountId, PhoneType phoneType) {
        return phoneRepository.getPhones(accountId, phoneType);
    }

    @Override
    public List<String> getPhoneNumbers(Long accountId, PhoneType phoneType) {
        return phoneRepository.getPhoneNumbers(accountId, phoneType);
    }

    @Transactional
    @Override
    public void update(Long phoneId, String newPhoneNumber) {
        phoneRepository.updateNumber(phoneId, newPhoneNumber);
    }

    @Transactional
    @Override
    public Phone create(Phone phone) {
        return phoneRepository.save(phone);
    }

    @Transactional
    @Override
    public void delete(Long phoneId) {
        phoneRepository.delete(phoneId);
    }

}
