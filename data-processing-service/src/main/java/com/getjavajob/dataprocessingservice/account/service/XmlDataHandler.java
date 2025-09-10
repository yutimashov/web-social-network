package com.getjavajob.dataprocessingservice.account.service;

import com.getjavajob.training.timashovy.socialnetwork.domain.account.Account;

import java.io.InputStream;

public interface XmlDataHandler {

    byte[] loadAccountData(Account account);

    void updateAccount(InputStream inputStream, Long accountId);

}
