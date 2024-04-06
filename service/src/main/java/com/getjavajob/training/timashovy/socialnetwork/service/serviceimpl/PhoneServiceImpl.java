package com.getjavajob.training.timashovy.socialnetwork.service.serviceimpl;

import com.getjavajob.training.timashovy.socialnetwork.common.account.Phone;
import com.getjavajob.training.timashovy.socialnetwork.common.account.PhoneType;
import com.getjavajob.training.timashovy.socialnetwork.dao.daoimpl.account.PhoneDaoImpl;
import com.getjavajob.training.timashovy.socialnetwork.dao.interfaces.account.PhoneDao;

import java.util.List;

public class PhoneServiceImpl {

    private static final PhoneServiceImpl PHONE_SERVICE_IMPL = new PhoneServiceImpl();
    private final PhoneDao phoneDao = PhoneDaoImpl.getPhoneDaoInstance();

    private PhoneServiceImpl() {
    }

    public static PhoneServiceImpl getPhoneServiceInstance() {
        return PHONE_SERVICE_IMPL;
    }

    public void createPhone(Long accountId, String phoneNumbers, PhoneType phoneType) {
        String[] phoneNumbersSeparated = phoneNumbers.split(",");
        for (String phoneNumber : phoneNumbersSeparated) {
            phoneDao.create(new Phone(phoneType, phoneNumber, accountId));
        }
    }
    
    public List<Phone> getPhoneNumbers(Long accountId) {
        return phoneDao.getPhoneNumbers(accountId);
    }

}
