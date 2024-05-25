package com.getjavajob.training.timashovy.socialnetwork.service.serviceimpl.account;

import com.getjavajob.training.timashovy.socialnetwork.common.account.Phone;
import com.getjavajob.training.timashovy.socialnetwork.dao.interfaces.account.PhoneDao;
import com.getjavajob.training.timashovy.socialnetwork.service.interfaces.PhoneService;

import java.util.ArrayList;
import java.util.List;

import static com.getjavajob.training.timashovy.socialnetwork.common.account.PhoneType.PERSONAL;
import static com.getjavajob.training.timashovy.socialnetwork.common.account.PhoneType.WORKING;
import static java.util.stream.Collectors.toList;

public class PhoneServiceImpl implements PhoneService {

    private final PhoneDao phoneDao;

    private PhoneServiceImpl(PhoneDao phoneDao) {
        this.phoneDao = phoneDao;
    }

    public static PhoneService createInstance(PhoneDao phoneDao) {
        return new PhoneServiceImpl(phoneDao);
    }

    @Override
    public List<Phone> createPersonalPhones(Long accountId, String phoneNumbers) {
        List<Phone> phones = new ArrayList<>();
        String[] phoneNumbersSeparated = phoneNumbers.split(",");
        for (String phoneNumberSeparated : phoneNumbersSeparated) {
            phones.add(new Phone(PERSONAL, phoneNumberSeparated, accountId));
        }
        return phones;
    }

    @Override
    public List<Phone> createWorkingPhones(Long accountId, String phoneNumbers) {
        List<Phone> phones = new ArrayList<>();
        String[] phoneNumbersSeparated = phoneNumbers.split(",");
        for (String phoneNumberSeparated : phoneNumbersSeparated) {
            phones.add(new Phone(WORKING, phoneNumberSeparated, accountId));
        }
        return phones;
    }

    private Phone preparePersonalPhones(Long accountId, String phonesValue) {
        return new Phone(PERSONAL, phonesValue, accountId);
    }

    private Phone prepareWorkingPhones(Long accountId, String phonesValue) {
        return new Phone(WORKING, phonesValue, accountId);
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
    public void updateById(Long phoneId, String newPhoneNumber) {
        phoneDao.update(phoneId, newPhoneNumber);
    }

}
