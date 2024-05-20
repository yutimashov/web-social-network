package com.getjavajob.training.timashovy.socialnetwork.service.serviceimpl.account;

import com.getjavajob.training.timashovy.socialnetwork.common.account.Phone;
import com.getjavajob.training.timashovy.socialnetwork.common.account.PhoneType;
import com.getjavajob.training.timashovy.socialnetwork.dao.interfaces.account.PhoneDao;

import java.util.List;

import static com.getjavajob.training.timashovy.socialnetwork.common.account.PhoneType.PERSONAL;
import static com.getjavajob.training.timashovy.socialnetwork.common.account.PhoneType.WORKING;
import static java.util.stream.Collectors.toList;

public class PhoneServiceImpl {

    private final PhoneDao phoneDao;

    private PhoneServiceImpl(PhoneDao phoneDao) {
        this.phoneDao = phoneDao;
    }

    public static PhoneServiceImpl createInstance(PhoneDao phoneDao) {
        return new PhoneServiceImpl(phoneDao);
    }

    public void createPhone(Long accountId, String phoneNumbers, PhoneType phoneType) {
        String[] phoneNumbersSeparated = phoneNumbers.split(",");
        for (String phoneNumber : phoneNumbersSeparated) {
            phoneDao.create(new Phone(phoneType, phoneNumber, accountId));
        }
    }
    
    public List<Phone> getPersonalPhoneNumbers(Long accountId) {
        return phoneDao.getAll(accountId).stream().filter(phone -> phone.getPhoneType() == PERSONAL).collect(toList());
    }

    public List<Phone> getWorkPhoneNumbers(Long accountId) {
        return phoneDao.getAll(accountId).stream().filter(phone -> phone.getPhoneType() == WORKING).collect(toList());
    }

    public void updateById(Long phoneId, String newPhoneNumber) {
        phoneDao.update(phoneId, newPhoneNumber);
    }

}
