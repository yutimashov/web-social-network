package com.getjavajob.training.timashovy.socialnetwork.service.serviceimpl.account;

import com.getjavajob.training.timashovy.socialnetwork.common.account.Phone;
import com.getjavajob.training.timashovy.socialnetwork.common.account.PhoneType;
import com.getjavajob.training.timashovy.socialnetwork.dao.interfaces.account.PhoneDao;
import com.getjavajob.training.timashovy.socialnetwork.service.interfaces.PhoneService;

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
    public void createPhone(Long accountId, String phoneNumbers, PhoneType phoneType) {
        String[] phoneNumbersSeparated = phoneNumbers.split(",");
        for (String phoneNumber : phoneNumbersSeparated) {
            phoneDao.create(new Phone(phoneType, phoneNumber, accountId));
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
    public void updateById(Long phoneId, String newPhoneNumber) {
        phoneDao.update(phoneId, newPhoneNumber);
    }

}
