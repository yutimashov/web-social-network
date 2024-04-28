package com.getjavajob.training.timashovy.socialnetwork.service.interfaces;

import com.getjavajob.training.timashovy.socialnetwork.common.account.Account;
import com.getjavajob.training.timashovy.socialnetwork.common.account.AccountRole;

import java.io.InputStream;
import java.time.LocalDate;
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

    boolean updateFirstName(Long accountId, String firstName);

    List<Account> getIncomingFriendRequests(Long accountId);

    List<Account> getOutgoingFriendRequests(Long accountId);

    boolean updateAccountLastName(Long accountId, String updatedLastName);

    boolean updateAccountMiddleName(Long accountId, String updatedMiddleName);

    boolean updateAccountBirthDate(Long accountId, LocalDate parse);

    boolean updateAccountSkype(Long accountId, String updatedSkype);

    boolean updateAccountIcq(Long accountId, String updatedICQ);

    boolean updateAccountEmail(Long accountId, String updatedEmail);

    boolean updateAccountRole(Long accountId, AccountRole role);

    boolean checkFriendshipRecordExistence(Long requesterId, Long accepterId);

    boolean updateFirstAvatar(Long accountId, InputStream updatedAvatar);

}
