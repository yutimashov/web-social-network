package com.getjavajob.training.timashovy.socialnetwork.service.interfaces;

import com.getjavajob.training.timashovy.socialnetwork.common.account.Account;
import com.getjavajob.training.timashovy.socialnetwork.common.account.Role;

import java.util.List;
import java.util.Optional;

/**
 * Interface contains all methods for organizing logic in communication with {@link Account} entity in application.
 *
 */
public interface AccountService {

    Long createAccount(Account account);

    boolean updateAccount(Long accountId, Account updatedAccount);

    boolean deleteAccount(Long accountId);

    Optional<Account> getAccountById(Long accountId);

    List<Account> getAllAccounts();

    boolean addFriend(Long accountId, Long friendId);

    boolean deleteFriend(Long accountId, Long friendId);

    List<Account> getFriends(Long accountId);

    boolean updateAccountRole(Long accountId, Role role);

    boolean updateFirstName(Long accountId, String firstName);

    List<Account> getFriendsRequests(Long accountId);

}
