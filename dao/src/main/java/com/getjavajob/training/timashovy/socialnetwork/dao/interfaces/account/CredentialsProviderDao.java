package com.getjavajob.training.timashovy.socialnetwork.dao.interfaces.account;

import com.getjavajob.training.timashovy.socialnetwork.common.account.Account;

public interface CredentialsProviderDao<T> {

    Long create(T t);

    boolean update(T t);

    boolean verify(Account account, T t);

}
