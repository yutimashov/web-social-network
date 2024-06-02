package com.getjavajob.training.timashovy.socialnetwork.service.interfaces;

import com.getjavajob.training.timashovy.socialnetwork.common.account.Account;
import com.getjavajob.training.timashovy.socialnetwork.common.account.AccountRole;
import com.getjavajob.training.timashovy.socialnetwork.common.util.AccountRegistrationData;
import com.getjavajob.training.timashovy.socialnetwork.common.util.AccountUpdatingData;

import java.util.List;
import java.util.Optional;

/**
 * Interface contains all methods for organizing logic in communication with {@link Account} entity in application.
 *
 */
public interface AccountService {

    Long create(AccountRegistrationData accountRegisterData);

    void update(Long accountId, AccountUpdatingData accountUpdatingData);

    boolean delete(Long accountId);

    Optional<Account> getById(Long accountId);

    List<Account> getAll();

    boolean addFriend(Long accountId, Long friendId);

    boolean deleteFriend(Long accountId, Long friendId);

    List<Account> getFriends(Long accountId);

    List<Account> getIncomingFriendRequests(Long accountId);

    List<Account> getOutgoingFriendRequests(Long accountId);

    void updateRole(Long accountId, AccountRole role);

    boolean checkFriendshipRecordExistence(Long requesterId, Long accepterId);

}
