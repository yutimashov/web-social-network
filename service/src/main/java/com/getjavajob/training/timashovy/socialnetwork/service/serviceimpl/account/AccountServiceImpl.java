package com.getjavajob.training.timashovy.socialnetwork.service.serviceimpl.account;

import com.getjavajob.training.timashovy.socialnetwork.common.account.Account;
import com.getjavajob.training.timashovy.socialnetwork.common.account.AccountRole;
import com.getjavajob.training.timashovy.socialnetwork.common.account.Phone;
import com.getjavajob.training.timashovy.socialnetwork.common.util.AccountRegistrationData;
import com.getjavajob.training.timashovy.socialnetwork.common.util.AccountUpdatingData;
import com.getjavajob.training.timashovy.socialnetwork.dao.interfaces.BaseDao;
import com.getjavajob.training.timashovy.socialnetwork.dao.interfaces.account.PasswordDao;
import com.getjavajob.training.timashovy.socialnetwork.dao.interfaces.account.PhoneDao;
import com.getjavajob.training.timashovy.socialnetwork.dao.interfaces.friendship.FriendshipChecker;
import com.getjavajob.training.timashovy.socialnetwork.dao.interfaces.friendship.FriendshipDao;
import com.getjavajob.training.timashovy.socialnetwork.dao.util.DaoException;
import com.getjavajob.training.timashovy.socialnetwork.dao.util.dbutils.dbconnection.TransactionManager;
import com.getjavajob.training.timashovy.socialnetwork.service.interfaces.AccountService;
import com.getjavajob.training.timashovy.socialnetwork.service.interfaces.PasswordService;
import com.getjavajob.training.timashovy.socialnetwork.service.interfaces.PhoneService;
import com.getjavajob.training.timashovy.socialnetwork.service.util.exceptions.ServiceException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.getjavajob.training.timashovy.socialnetwork.common.account.PhoneType.PERSONAL;
import static com.getjavajob.training.timashovy.socialnetwork.common.account.PhoneType.WORKING;
import static java.util.Objects.isNull;
import static java.util.stream.Collectors.toList;

/**
 * Singleton class for working with methods, managing Account functionality.
 */
public class AccountServiceImpl implements AccountService {

    private final BaseDao<Account> accountDao;
    private final FriendshipDao friendshipDao;
    private final FriendshipChecker friendshipChecker;
    private final PhoneService phoneService;
    private final PhoneDao phoneDao;
    private final PasswordService passwordService;
    private final PasswordDao passwordDao;
    private final TransactionManager transactionManager;

    public AccountServiceImpl(BaseDao<Account> accountDao, FriendshipDao friendshipDao,
                               FriendshipChecker friendshipChecker, PhoneService phoneService, PhoneDao phoneDao,
                               PasswordService passwordService, PasswordDao passwordDao,
                               TransactionManager transactionManager) {
        this.accountDao = accountDao;
        this.friendshipDao = friendshipDao;
        this.friendshipChecker = friendshipChecker;
        this.phoneService = phoneService;
        this.phoneDao = phoneDao;
        this.passwordService = passwordService;
        this.passwordDao = passwordDao;
        this.transactionManager = transactionManager;
    }

    /**
     * Create new account inserting it in database with auto generated incremented key
     *
     * @param accountRegisterData object which data will be inserted in db as new account
     */
    @Override
    public void create(AccountRegistrationData accountRegisterData) {
        transactionManager.executeTransaction(() -> {
            Long accountId = accountDao.create(accountRegisterData.getAccount());
            passwordDao.create(passwordService.create(accountId, accountRegisterData.getPassword()));
            List<Phone> personalPhones = phoneService.createPersonalPhones(accountId,
                    accountRegisterData.getPersonalPhoneNumbers());
            for (Phone personalPhone : personalPhones) {
                phoneDao.create(personalPhone);
            }
            List<Phone> workingPhones = phoneService.createWorkingPhones(accountId,
                    accountRegisterData.getWorkingPhoneNumbers());
            for (Phone workingPhone : workingPhones) {
                phoneDao.create(workingPhone);
            }
        });
    }

    @Override
    public void update(Long accountId, AccountUpdatingData accountUpdatingData) {
        transactionManager.executeTransaction(() -> {
            Account updatedAccountData = accountUpdatingData.getAccount();
            Account newAccount = accountDao.getById(accountId).isPresent() ? accountDao.getById(accountId).get() : null;
            if (newAccount == null) {
                throw new ServiceException("updating non-existing account");
            }
            if (updatedAccountData.getAvatar() != null) {
                newAccount.setAvatar(updatedAccountData.getAvatar());
            }
            if (updatedAccountData.getFirstName() != null && !updatedAccountData.getFirstName().isEmpty()) {
                newAccount.setFirstName(updatedAccountData.getFirstName());
            }
            if (updatedAccountData.getLastName() != null && !updatedAccountData.getLastName().isEmpty()) {
                newAccount.setLastName(updatedAccountData.getLastName());
            }
            if (updatedAccountData.getMiddleName() != null && !updatedAccountData.getMiddleName().isEmpty()) {
                newAccount.setMiddleName(updatedAccountData.getMiddleName());
            }
            if (updatedAccountData.getBirthDate() != null) {
                newAccount.setBirthDate(updatedAccountData.getBirthDate());
            }
            if (updatedAccountData.getSkype() != null && !updatedAccountData.getSkype().isEmpty()) {
                newAccount.setSkype(updatedAccountData.getSkype());
            }
            if (updatedAccountData.getIcq() != null && !updatedAccountData.getIcq().isEmpty()) {
                newAccount.setIcq(updatedAccountData.getIcq());
            }
            if (updatedAccountData.getEmail() != null && !updatedAccountData.getEmail().isEmpty()) {
                newAccount.setEmail(updatedAccountData.getEmail());
            }
            accountDao.updateById(accountId, newAccount);
            if (updatedAccountData.getPersonalPhoneNumber() != null) {
                for (Phone phone : updatedAccountData.getPersonalPhoneNumber()) {
                    phoneDao.update(phone.getId(), phone.getNumber());
                }
            }
            if (updatedAccountData.getWorkPhoneNumber() != null) {
                for (Phone phone : updatedAccountData.getWorkPhoneNumber()) {
                    phoneDao.update(phone.getId(), phone.getNumber());
                }
            }
        });
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
        List<Account> accounts = accountDao.getAll();
        for (Account account : accounts) {
            List<Phone> accountPhones = phoneDao.getAll(account.getId());
            if (!accountPhones.isEmpty()) {
                account.setPersonalPhoneNumber(accountPhones.stream().filter(phone -> phone.getPhoneType() == PERSONAL)
                        .collect(toList()));
                account.setWorkPhoneNumber(accountPhones.stream().filter(phone -> phone.getPhoneType() == WORKING)
                        .collect(toList()));
            }
        }
        return accounts;
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
        if (!friendshipChecker.checkFriendshipRecordExistence(requesterId, accepterId)) {
            return friendshipDao.sendRequest(requesterId, accepterId);
        }
        if (friendshipChecker.checkUsersAreFriends(requesterId, accepterId)) {
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
        return friendshipChecker.checkFriendshipRecordExistence(requesterId, accepterId);
    }

}
