package com.getjavajob.training.timashovy.socialnetwork.service.serviceimpl;

import com.getjavajob.training.timashovy.socialnetwork.common.account.Account;
import com.getjavajob.training.timashovy.socialnetwork.common.account.Phone;
import com.getjavajob.training.timashovy.socialnetwork.common.account.Role;
import com.getjavajob.training.timashovy.socialnetwork.dao.daoimpl.FriendshipCheckerImpl;
import com.getjavajob.training.timashovy.socialnetwork.dao.daoimpl.FriendshipDaoImpl;
import com.getjavajob.training.timashovy.socialnetwork.dao.daoimpl.account.AccountDaoImpl;
import com.getjavajob.training.timashovy.socialnetwork.dao.daoimpl.account.PhoneDaoImpl;
import com.getjavajob.training.timashovy.socialnetwork.dao.interfaces.AccountGroupDao;
import com.getjavajob.training.timashovy.socialnetwork.dao.interfaces.friendship.FriendshipChecker;
import com.getjavajob.training.timashovy.socialnetwork.dao.interfaces.friendship.FriendshipDao;
import com.getjavajob.training.timashovy.socialnetwork.dao.interfaces.util.TableConstraintsValidator;
import com.getjavajob.training.timashovy.socialnetwork.dao.interfaces.account.PhoneDao;
import com.getjavajob.training.timashovy.socialnetwork.service.interfaces.AccountService;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.getjavajob.training.timashovy.socialnetwork.common.account.PhoneType.PERSONAL;
import static com.getjavajob.training.timashovy.socialnetwork.common.account.PhoneType.WORKING;
import static java.util.Objects.isNull;

public class AccountServiceImpl implements AccountService {

    private final AccountGroupDao<Account> accountDao;
    private final FriendshipDao friendshipDao;
    private final FriendshipChecker friendshipChecker;
    private final PhoneDao phoneDao;

    private AccountServiceImpl(AccountGroupDao<Account> accountDao, FriendshipDao friendshipDao,
                               FriendshipChecker friendshipChecker, PhoneDao phoneDao) {
        this.accountDao = accountDao;
        this.friendshipDao = friendshipDao;
        this.friendshipChecker = friendshipChecker;
        this.phoneDao = phoneDao;
    }

    private static class SingletonHolder {

        private static final AccountServiceImpl INSTANCE;

        static {
            AccountGroupDao<Account> accountDao = AccountDaoImpl.getInstance();
            FriendshipDao friendshipDao = FriendshipDaoImpl.getInstance();
            FriendshipChecker friendshipChecker = FriendshipCheckerImpl.getInstance();
            PhoneDao phoneDao = PhoneDaoImpl.getInstance();
            INSTANCE = new AccountServiceImpl(accountDao, friendshipDao, friendshipChecker, phoneDao);
        }

    }

    public static AccountServiceImpl getInstance() {
        return SingletonHolder.INSTANCE;
    }

    /**
     * Create new account inserting it in database with auto generated incremented key
     *
     * @param account object which data will be inserted in db as new account
     * @return id of created account
     */
    @Override
    public Long createAccount(Account account) {
        validateAccount(account);
        return accountDao.create(account);
    }

    private void validateAccount(Account account) {
        if (isNull(account)) {
            throw new IllegalArgumentException("Account should not be null");
        }
        if (isNull(account.getFirstName()) || isNull(account.getLastName()) || isNull(account.getEmail())) {
            throw new IllegalArgumentException("Account validation error: field not null constraint violation");
        }
        if (!isNull(account.getIcq()) || !("".equals(account.getIcq()))) {
            ((TableConstraintsValidator) accountDao).validateEntityFieldUniqueness("icq", account.getIcq());
        }
        if (!isNull(account.getSkype())) {
            ((TableConstraintsValidator) accountDao).validateEntityFieldUniqueness("skype", account.getSkype());
        }
        ((TableConstraintsValidator) accountDao).validateEntityFieldUniqueness("email", account.getEmail());
    }

    @Override
    public boolean updateAccount(Long accountId, Account updatedAccount) {
        validateAccountId(accountId);
        validateAccount(updatedAccount);
        return accountDao.updateById(accountId, updatedAccount);
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
        // accountDao.getById(accountId);
    }

    private <T> void validateAccountFieldNotNull(T fieldName) {
        if (isNull(fieldName)) {
            throw new IllegalArgumentException("updating field should not be null");
        }
    }

    @Override
    public boolean updateFirstName(Long accountId, String firstName) {
        validateAccountId(accountId);
        validateAccountFieldNotNull(firstName);
        return accountDao.getById(accountId).isPresent() && accountDao.updateById(accountId,
                new Account.Builder(accountDao.getById(accountId).get()).firstName(firstName).build());
    }

    public boolean updateAccountLastName(Long accountId, String lastName) {
        validateAccountId(accountId);
        validateAccountFieldNotNull(lastName);
        return accountDao.getById(accountId).isPresent() && accountDao.updateById(accountId,
                new Account.Builder(accountDao.getById(accountId).get()).lastName(lastName).build());
    }

    public boolean updateAccountMiddleName(Long accountId, String middleName) {
        validateAccountId(accountId);
        validateAccountFieldNotNull(middleName);
        return accountDao.getById(accountId).isPresent() && accountDao.updateById(accountId,
                new Account.Builder(accountDao.getById(accountId).get()).middleName(middleName).build());
    }

    public boolean updateAccountBirthDate(Long accountId, LocalDate birthDate) {
        validateAccountId(accountId);
        validateAccountFieldNotNull(birthDate);
        return accountDao.getById(accountId).isPresent() && accountDao.updateById(accountId,
                new Account.Builder(accountDao.getById(accountId).get()).birthDate(birthDate).build());
    }

    public boolean updateAccountWorkAddress(Long accountId, String workAddress) {
        validateAccountId(accountId);
        validateAccountFieldNotNull(workAddress);
        return accountDao.getById(accountId).isPresent() && accountDao.updateById(accountId,
                new Account.Builder(accountDao.getById(accountId).get()).workAddress(workAddress).build());
    }

    public boolean updateAccountPersonalAddress(Long accountId, String personalAddress) {
        validateAccountId(accountId);
        validateAccountFieldNotNull(personalAddress);
        return accountDao.getById(accountId).isPresent() && accountDao.updateById(accountId,
                new Account.Builder(accountDao.getById(accountId).get()).personalAddress(personalAddress).build());
    }

    public boolean updateAccountEmail(Long accountId, String email) {
        validateAccountId(accountId);
        validateAccountFieldNotNull(email);
        return accountDao.getById(accountId).isPresent() && accountDao.updateById(accountId,
                new Account.Builder(accountDao.getById(accountId).get()).email(email).build());
    }

    public boolean updateAccountIcq(Long accountId, String icq) {
        validateAccountId(accountId);
        validateAccountFieldNotNull(icq);
        return accountDao.getById(accountId).isPresent() && accountDao.updateById(accountId,
                new Account.Builder(accountDao.getById(accountId).get()).icq(icq).build());
    }

    public boolean updateAccountSkype(Long accountId, String skype) {
        validateAccountId(accountId);
        validateAccountFieldNotNull(skype);
        return accountDao.getById(accountId).isPresent() && accountDao.updateById(accountId,
                new Account.Builder(accountDao.getById(accountId).get()).skype(skype).build());
    }

    public boolean updateAccountAdditionalInfo(Long accountId, String additionalInfo) {
        validateAccountId(accountId);
        validateAccountFieldNotNull(additionalInfo);
        return accountDao.getById(accountId).isPresent() && accountDao.updateById(accountId,
                new Account.Builder(accountDao.getById(accountId).get()).additionalInfo(additionalInfo).build());
    }

    @Override
    public boolean updateAccountRole(Long accountId, Role role) {
        validateAccountId(accountId);
        validateAccountFieldNotNull(role);
        return accountDao.getById(accountId).isPresent() && accountDao.updateById(accountId,
                new Account.Builder(accountDao.getById(accountId).get()).role(role).build());
    }

    @Override
    public boolean deleteAccount(Long accountId) {
        validateAccountId(accountId);
        return accountDao.deleteById(accountId);
    }

    @Override
    public Optional<Account> getAccountById(Long accountId) {
        return accountDao.getById(accountId);
    }

    @Override
    public List<Account> getAllAccounts() {
        List<Account> accounts = accountDao.getAll();
        for (Account account : accounts) {
            List<Phone> accountPhones = phoneDao.getAll(account.getId());
            if (!accountPhones.isEmpty()) {
                account.setPersonalPhoneNumber(accountPhones.stream().filter(phone -> phone.getPhoneType() == PERSONAL)
                        .collect(Collectors.toList()));
                account.setWorkPhoneNumber(accountPhones.stream().filter(phone -> phone.getPhoneType() == WORKING)
                        .collect(Collectors.toList()));
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
            return friendshipDao.sendFriendshipRequest(requesterId, accepterId);
        }
        if (friendshipChecker.checkUsersAreFriends(requesterId, accepterId)) {
            return false;
        }
        return friendshipDao.acceptFriendRequest(requesterId, accepterId);
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
        List<Long> friendRequestsId = friendshipDao.getIncomingFriendRequests(accountId);
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
        List<Long> friendRequestsId = friendshipDao.getOutgoingFriendRequests(accountId);
        List<Account> followers = new ArrayList<>();
        for (Long followerId : friendRequestsId) {
            if (accountDao.getById(followerId).isPresent()) {
                followers.add(accountDao.getById(followerId).get());
            }
        }
        return followers;
    }

}
