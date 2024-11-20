package com.getjavajob.training.timashovy.socialnetwork.dao.interfaces.account;

import com.getjavajob.training.timashovy.socialnetwork.dao.interfaces.Repository;
import com.getjavajob.training.timashovy.socialnetwork.domain.account.Account;

import java.util.List;

public interface AccountRepository extends Repository<Long, Account> {

    List<Account> getAll();

    boolean updateById(Long id, Account account);

}
