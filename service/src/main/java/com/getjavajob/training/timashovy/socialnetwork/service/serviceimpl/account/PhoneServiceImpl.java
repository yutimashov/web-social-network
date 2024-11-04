package com.getjavajob.training.timashovy.socialnetwork.service.serviceimpl.account;

import com.getjavajob.training.timashovy.socialnetwork.dao.interfaces.account.PhoneDao;
import com.getjavajob.training.timashovy.socialnetwork.domain.account.Account;
import com.getjavajob.training.timashovy.socialnetwork.domain.phone.Phone;
import com.getjavajob.training.timashovy.socialnetwork.service.interfaces.PhoneService;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.getjavajob.training.timashovy.socialnetwork.domain.phone.PhoneType.PERSONAL;
import static com.getjavajob.training.timashovy.socialnetwork.domain.phone.PhoneType.WORKING;
import static java.util.stream.Collectors.toList;

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
    public List<Phone> getPersonalPhoneNumbers(Long accountId) {
        return phoneDao.getAll(accountId).stream().filter(phone -> phone.getPhoneType() == PERSONAL).collect(toList());
    }

    @Override
    public List<Phone> getWorkPhoneNumbers(Long accountId) {
        return phoneDao.getAll(accountId).stream().filter(phone -> phone.getPhoneType() == WORKING).collect(toList());
    }

    @Override
    public boolean update(Long phoneId, String newPhoneNumber) {
        return phoneDao.updateNumber(phoneId, newPhoneNumber);
    }

    @Override
    public Long create(Phone phone) {
        return phoneDao.create(phone);
    }

    @Override
    public void delete(Long phoneId) {
        phoneDao.delete(phoneId);
    }

}
