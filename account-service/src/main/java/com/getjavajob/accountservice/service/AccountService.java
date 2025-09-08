package com.getjavajob.accountservice.service;

import com.getjavajob.training.timashovy.socialnetwork.domain.account.Account;
import org.springframework.security.access.prepost.PreAuthorize;

import java.util.List;
import java.util.Optional;

/**
 * Interface contains all methods for organizing logic in communication with {@link Account} entity in application.
 */
public interface AccountService {

    @PreAuthorize("hasAuthority('ADMIN')")
    void makeAdmin(Long accountId);

    Account create(Account account, String password, String personalPhones, String workingPhones);

    void update(Long accountId, Account updatedAccount);

    @PreAuthorize("hasAuthority('ADMIN')")
    void delete(Long accountId);

    Optional<Account> getById(Long accountId);

    List<Account> getAccounts(Long accountId, Long lastId, int limit);

    List<Account> getAccountWithBirthdayToday(int month, int day);

    void deleteFriend(Long accountId, Long friendId);

    List<Long> getFriendsIds(Long accountId);

    List<Account> getFollowerAccounts(Long accountId, Long lastId, int pageSize);

    List<Account> getFollowingAccounts(Long accountId, Long lastId, int pageSize);

    Optional<Account> findByEmail(String email);

    void updateById(Account account, Long accountId);

}
