package com.getjavajob.training.timashovy.socialnetwork.dao.interfaces.account;

import com.getjavajob.training.timashovy.socialnetwork.dao.interfaces.Repository;
import com.getjavajob.training.timashovy.socialnetwork.domain.account.Account;
import com.getjavajob.training.timashovy.socialnetwork.domain.account.AccountRole;

import java.util.List;

public interface AccountRepository extends Repository<Long, Account> {

    List<Account> getAll();

    void changeRole(Long id, AccountRole role);

}
