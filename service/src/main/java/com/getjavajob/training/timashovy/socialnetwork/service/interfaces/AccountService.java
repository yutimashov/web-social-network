package com.getjavajob.training.timashovy.socialnetwork.service.interfaces;

import com.getjavajob.training.timashovy.socialnetwork.domain.account.Account;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.List;
import java.util.Optional;

/**
 * Interface contains all methods for organizing logic in communication with {@link Account} entity in application.
 */
public interface AccountService {

    Account create(Account account, String password, String personalPhones, String workingPhones);

    void update(Long accountId, Account updatedAccount);

    void delete(Long accountId);

    Optional<Account> getById(Long accountId);

    List<Account> getAll();

    void addFriend(Long accountId, Long friendId);

    void deleteFriend(Long accountId, Long friendId);

    List<Account> getFriends(Long accountId);

    List<Account> getIncomingFriendRequests(Long accountId);

    List<Account> getOutgoingFriendRequests(Long accountId);

    boolean checkFriendshipRecordExistence(Long requesterId, Long accepterId);

    void xmlFileUpdateAccount(InputStream inputStream);

    ByteArrayOutputStream xmlFileDownloadAccount(Long accountId, Account account);

}
