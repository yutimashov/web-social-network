package com.getjavajob.training.timashovy.socialnetwork.service.serviceimpl.account;

import com.getjavajob.training.timashovy.socialnetwork.dao.interfaces.BaseDao;
import com.getjavajob.training.timashovy.socialnetwork.dao.interfaces.account.PasswordDao;
import com.getjavajob.training.timashovy.socialnetwork.dao.interfaces.account.PhoneDao;
import com.getjavajob.training.timashovy.socialnetwork.dao.interfaces.friendship.FriendshipCheckerDao;
import com.getjavajob.training.timashovy.socialnetwork.dao.interfaces.friendship.FriendshipDao;
import com.getjavajob.training.timashovy.socialnetwork.domain.account.Account;
import com.getjavajob.training.timashovy.socialnetwork.domain.account.AccountRole;
import com.getjavajob.training.timashovy.socialnetwork.service.interfaces.AccountService;
import com.getjavajob.training.timashovy.socialnetwork.service.interfaces.PasswordService;
import com.getjavajob.training.timashovy.socialnetwork.service.interfaces.PhoneService;
import com.getjavajob.training.timashovy.socialnetwork.service.util.exceptions.ServiceException;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static java.util.Objects.isNull;

/**
 * Singleton class for working with methods, managing Account functionality.
 */
public class AccountServiceImpl implements AccountService {

    private final BaseDao<Account> accountDao;
    private final FriendshipDao friendshipDao;
    private final FriendshipCheckerDao friendshipCheckerDao;
    private final PhoneService phoneService;
    private final PhoneDao phoneDao;
    private final PasswordService passwordService;
    private final PasswordDao passwordDao;

    public AccountServiceImpl(BaseDao<Account> accountDao, FriendshipDao friendshipDao,
                              FriendshipCheckerDao friendshipCheckerDao, PhoneService phoneService, PhoneDao phoneDao,
                              PasswordService passwordService, PasswordDao passwordDao) {
        this.accountDao = accountDao;
        this.friendshipDao = friendshipDao;
        this.friendshipCheckerDao = friendshipCheckerDao;
        this.phoneService = phoneService;
        this.phoneDao = phoneDao;
        this.passwordService = passwordService;
        this.passwordDao = passwordDao;
    }

    @Transactional
    @Override
    public Long create(Account account, String password, String personalPhones, String workingPhones) {
        Long accountId = accountDao.create(account);
        passwordService.create(account, password);
        System.out.println(account);
        phoneService.createPersonalPhones(account, personalPhones);
        phoneService.createWorkingPhones(account, workingPhones);
        return accountId;
    }

    @Override
    public void update(Long accountId, Account updatedAccount) {
        Account newAccount = accountDao.getById(accountId).isPresent() ? accountDao.getById(accountId).get() : null;
        if (newAccount == null) {
            throw new ServiceException("updating non-existing account");
        }
        if (updatedAccount.getAvatar() != null) {
            newAccount.setAvatar(updatedAccount.getAvatar());
        }
        if (updatedAccount.getFirstName() != null && !updatedAccount.getFirstName().isEmpty()) {
            newAccount.setFirstName(updatedAccount.getFirstName());
        }
        if (updatedAccount.getLastName() != null && !updatedAccount.getLastName().isEmpty()) {
            newAccount.setLastName(updatedAccount.getLastName());
        }
        if (updatedAccount.getMiddleName() != null && !updatedAccount.getMiddleName().isEmpty()) {
            newAccount.setMiddleName(updatedAccount.getMiddleName());
        }
        if (updatedAccount.getBirthDate() != null) {
            newAccount.setBirthDate(updatedAccount.getBirthDate());
        }
        if (updatedAccount.getSkype() != null && !updatedAccount.getSkype().isEmpty()) {
            newAccount.setSkype(updatedAccount.getSkype());
        }
        if (updatedAccount.getIcq() != null && !updatedAccount.getIcq().isEmpty()) {
            newAccount.setIcq(updatedAccount.getIcq());
        }
        if (updatedAccount.getEmail() != null && !updatedAccount.getEmail().isEmpty()) {
            newAccount.setEmail(updatedAccount.getEmail());
        }
        accountDao.updateById(accountId, newAccount);
    }

    /**
     * According to database constraints, accountId cannot be less or equal to zero, and it cannot be null.
     *
     * @param accountId id of account passed as argument
     */
    private void validateAccountId(Long accountId) {
        if (isNull(accountId) || accountId <= 0) {
            throw new IllegalArgumentException("Account id should be positive number greater than 0");
        }
    }

    private <T> void validateAccountFieldNotNull(T fieldName) {
        if (isNull(fieldName)) {
            throw new IllegalArgumentException("updating field should not be null");
        }
    }

    @Override
    public void updateRole(Long accountId, AccountRole role) {
        validateAccountId(accountId);
        validateAccountFieldNotNull(role);
        if (accountDao.getById(accountId).isPresent()) {
            accountDao.updateById(accountId,
                    new Account.Builder(accountDao.getById(accountId).get()).role(role).build());
        }
    }

    @Override
    public boolean delete(Long accountId) {
        validateAccountId(accountId);
        return accountDao.deleteById(accountId);
    }

    @Override
    public Optional<Account> getById(Long accountId) {
        return accountDao.getById(accountId);
    }

    @Override
    public List<Account> getAll() {
        return accountDao.getAll();
    }

    /**
     * If there is no record in friendship table, it means that no friendship connection exist.
     * Make a record - requester becomes follower of accepter.
     * If record is already exist, check if users are already friends.
     * If they are - return false.
     * If they are not friends, but friendship record exists, check requester.
     * If requester is the same as it is in table, it means that requester tries to add friend one more time.
     * If requester is accepter, it means that requester confirms friendship request already existed in the table.
     *
     * @param requesterId id of account, who has initiated friendship request
     * @param accepterId  if of account, who is addresses of friendship request
     * @return whether two account becomes friends
     */
    @Override
    public boolean addFriend(Long requesterId, Long accepterId) {
        validateAccountId(requesterId);
        validateAccountId(accepterId);
        if (requesterId.equals(accepterId)) {
            throw new IllegalArgumentException("Account cannot send friend request to themselves");
        }
        if (!friendshipCheckerDao.checkFriendshipRecordExistence(requesterId, accepterId)) {
            friendshipDao.sendRequest(accountDao.getById(requesterId).get(), accountDao.getById(accepterId).get());
            return true;
        }
        if (friendshipCheckerDao.checkUsersAreFriends(requesterId, accepterId)) {
            return false;
        }
        return friendshipDao.acceptRequest(requesterId, accepterId);
    }

    @Override
    public boolean deleteFriend(Long accountId, Long deletingFriendId) {
        validateAccountId(accountId);
        validateAccountId(deletingFriendId);
        return friendshipDao.deleteFriend(accountId, deletingFriendId);
    }

    @Override
    public List<Account> getFriends(Long accountId) {
        validateAccountId(accountId);
        List<Long> friendsId = friendshipDao.getFriendsIds(accountId);
        List<Account> friends = new ArrayList<>();
        for (Long friendId : friendsId) {
            if (accountDao.getById(friendId).isPresent()) {
                friends.add(accountDao.getById(friendId).get());
            }
        }
        return friends;
    }

    @Override
    public List<Account> getIncomingFriendRequests(Long accountId) {
        validateAccountId(accountId);
        List<Long> friendRequestsId = friendshipDao.getIncomingRequests(accountId);
        List<Account> followers = new ArrayList<>();
        for (Long followerId : friendRequestsId) {
            if (accountDao.getById(followerId).isPresent()) {
                followers.add(accountDao.getById(followerId).get());
            }
        }
        return followers;
    }

    @Override
    public List<Account> getOutgoingFriendRequests(Long accountId) {
        validateAccountId(accountId);
        List<Long> friendRequestsId = friendshipDao.getOutgoingRequests(accountId);
        List<Account> followers = new ArrayList<>();
        for (Long followerId : friendRequestsId) {
            if (accountDao.getById(followerId).isPresent()) {
                followers.add(accountDao.getById(followerId).get());
            }
        }
        return followers;
    }

    @Override
    public boolean checkFriendshipRecordExistence(Long requesterId, Long accepterId) {
        validateAccountId(requesterId);
        validateAccountId(accepterId);
        return friendshipCheckerDao.checkFriendshipRecordExistence(requesterId, accepterId);
    }

}
