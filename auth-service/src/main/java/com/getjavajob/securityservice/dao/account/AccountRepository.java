package com.getjavajob.securityservice.dao.account;

import com.getjavajob.training.timashovy.socialnetwork.domain.account.Account;

import java.util.Optional;

/**
 * Contains specific methods for working with Account entity.
 */
public interface AccountRepository {

    Optional<Account> findByEmail(String email);

}
