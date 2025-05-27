package com.getjavajob.securityservice.service.account;

import com.getjavajob.training.timashovy.socialnetwork.domain.account.Account;

import java.util.Optional;

/**
 * Interface contains all methods for organizing logic in communication with {@link Account} entity in application.
 */
public interface AccountService {

    Optional<Account> findByEmail(String email);

}
