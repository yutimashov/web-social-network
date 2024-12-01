package com.getjavajob.training.timashovy.socialnetwork.service.interfaces;

import com.getjavajob.training.timashovy.socialnetwork.domain.account.Account;

import java.io.InputStream;

public interface XmlDataHandler {

    void updateAccount(InputStream inputStream, Long accountId);

    byte[] loadAccountData(Account account);

}
