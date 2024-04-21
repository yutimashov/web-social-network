package com.getjavajob.training.timashovy.socialnetwork.service.serviceimpl.account;

import com.getjavajob.training.timashovy.socialnetwork.common.account.Phone;
import com.getjavajob.training.timashovy.socialnetwork.common.account.PhoneType;
import com.getjavajob.training.timashovy.socialnetwork.dao.daoimpl.account.PhoneDaoImpl;
import com.getjavajob.training.timashovy.socialnetwork.dao.interfaces.account.PhoneDao;

import java.util.List;

import static com.getjavajob.training.timashovy.socialnetwork.common.account.PhoneType.PERSONAL;
import static com.getjavajob.training.timashovy.socialnetwork.common.account.PhoneType.WORKING;
import static java.util.stream.Collectors.toList;

public class PhoneServiceImpl {

    private static final PhoneServiceImpl PHONE_SERVICE_IMPL = new PhoneServiceImpl();
    private final PhoneDao phoneDao = PhoneDaoImpl.getInstance();

    private PhoneServiceImpl() {
    }

    public static PhoneServiceImpl getInstance() {
        return PHONE_SERVICE_IMPL;
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

    public boolean updateById(Long phoneId, String newPhoneNumber) {
        return phoneDao.update(phoneId, newPhoneNumber);
    }

}
