package com.getjavajob.training.timashovy.socialnetwork.service.serviceimpl;

import com.getjavajob.training.timashovy.socialnetwork.common.account.Account;
import com.getjavajob.training.timashovy.socialnetwork.common.account.Phone;
import com.getjavajob.training.timashovy.socialnetwork.common.account.PhoneType;
import com.getjavajob.training.timashovy.socialnetwork.dao.daoimpl.account.PhoneDaoImpl;

import java.util.List;

public class PhoneServiceImpl {

    private static final PhoneServiceImpl PHONE_SERVICE_IMPL = new PhoneServiceImpl();

    private PhoneServiceImpl() {
    }

    public static PhoneServiceImpl getPhoneServiceImpl() {
        return PHONE_SERVICE_IMPL;
    }

    public void createPhone(Account account, String phoneNumbers, PhoneType phoneType) {
        Long accountId = account.getId();
        String[] phoneNumbersSeparated = phoneNumbers.split(",");
        for (String phoneNumber : phoneNumbersSeparated) {
             Phone phone = new Phone(phoneType, phoneNumber, accountId);
            PhoneDaoImpl.getPhoneDaoInstance().create(account, phone);
        }
    }

    public List<Phone> getPhoneNumbers(Account account) {
        return PHONE_SERVICE_IMPL.getPhoneNumbers(account);
    }

}
