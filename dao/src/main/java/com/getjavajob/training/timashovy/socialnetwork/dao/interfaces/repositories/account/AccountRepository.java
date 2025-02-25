package com.getjavajob.training.timashovy.socialnetwork.dao.interfaces.repositories.account;

import com.getjavajob.training.timashovy.socialnetwork.dao.interfaces.Repository;
import com.getjavajob.training.timashovy.socialnetwork.domain.account.Account;
import com.getjavajob.training.timashovy.socialnetwork.domain.account.AccountRole;

import java.util.List;
import java.util.Optional;

/**
 * Contains specific methods for working with Account entity.
 */
public interface AccountRepository extends Repository<Long, Account> {

    List<Account> getAccounts(Long accountId, Long lastId, int limit);

    void changeRole(Long id, AccountRole role);

    void updateById(Account account, Long accountId);

    Optional<Account> findByEmail(String email);

}
