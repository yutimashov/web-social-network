package com.getjavajob.training.timashovy.socialnetwork.service.serviceimpl.account;

import com.getjavajob.training.timashovy.socialnetwork.dao.interfaces.account.PhoneDao;
import com.getjavajob.training.timashovy.socialnetwork.domain.account.Account;
import com.getjavajob.training.timashovy.socialnetwork.domain.phone.Phone;
import com.getjavajob.training.timashovy.socialnetwork.domain.phone.PhoneType;
import com.getjavajob.training.timashovy.socialnetwork.service.interfaces.PhoneService;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.getjavajob.training.timashovy.socialnetwork.domain.phone.PhoneType.PERSONAL;
import static com.getjavajob.training.timashovy.socialnetwork.domain.phone.PhoneType.WORKING;

public class PhoneServiceImpl implements PhoneService {

    private final PhoneDao phoneDao;

    public PhoneServiceImpl(PhoneDao phoneDao) {
        this.phoneDao = phoneDao;
    }

    @Transactional
    @Override
    public void createPersonalPhones(Account account, String phoneNumbers) {
        String[] phoneNumbersSeparated = phoneNumbers.split(",");
        for (String phoneNumberSeparated : phoneNumbersSeparated) {
            phoneDao.create(new Phone(PERSONAL, phoneNumberSeparated, account));
        }
    }

    @Transactional
    @Override
    public void createWorkingPhones(Account account, String phoneNumbers) {
        String[] phoneNumbersSeparated = phoneNumbers.split(",");
        for (String phoneNumberSeparated : phoneNumbersSeparated) {
            phoneDao.create(new Phone(WORKING, phoneNumberSeparated, account));
        }
    }

    @Override
    public List<Phone> getPhones(Long accountId, PhoneType phoneType) {
        return phoneDao.getPhones(accountId, phoneType);
    }

    @Override
    public List<String> getPhoneNumbers(Long accountId, PhoneType phoneType) {
        return phoneDao.getPhoneNumbers(accountId, phoneType);
    }

    @Transactional
    @Override
    public boolean update(Long phoneId, String newPhoneNumber) {
        return phoneDao.updateNumber(phoneId, newPhoneNumber);
    }

    @Transactional
    @Override
    public Long create(Phone phone) {
        return phoneDao.create(phone);
    }

    @Override
    public void delete(Long phoneId) {
        phoneDao.delete(phoneId);
    }

}
