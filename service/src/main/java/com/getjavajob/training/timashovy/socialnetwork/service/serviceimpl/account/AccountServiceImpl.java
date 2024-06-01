package com.getjavajob.training.timashovy.socialnetwork.service.serviceimpl.account;

import com.getjavajob.training.timashovy.socialnetwork.common.account.Account;
import com.getjavajob.training.timashovy.socialnetwork.common.account.AccountRole;
import com.getjavajob.training.timashovy.socialnetwork.common.account.Phone;
import com.getjavajob.training.timashovy.socialnetwork.common.util.AccountRegisterData;
import com.getjavajob.training.timashovy.socialnetwork.dao.interfaces.BaseDao;
import com.getjavajob.training.timashovy.socialnetwork.dao.interfaces.TableConstraintsValidator;
import com.getjavajob.training.timashovy.socialnetwork.dao.interfaces.account.PasswordDao;
import com.getjavajob.training.timashovy.socialnetwork.dao.interfaces.account.PhoneDao;
import com.getjavajob.training.timashovy.socialnetwork.dao.interfaces.friendship.FriendshipChecker;
import com.getjavajob.training.timashovy.socialnetwork.dao.interfaces.friendship.FriendshipDao;
import com.getjavajob.training.timashovy.socialnetwork.dao.util.dbutils.dbconnection.TransactionManager;
import com.getjavajob.training.timashovy.socialnetwork.service.interfaces.AccountService;
import com.getjavajob.training.timashovy.socialnetwork.service.interfaces.PasswordService;
import com.getjavajob.training.timashovy.socialnetwork.service.interfaces.PhoneService;
import com.getjavajob.training.timashovy.socialnetwork.service.util.exceptions.ServiceException;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.getjavajob.training.timashovy.socialnetwork.common.account.PhoneType.PERSONAL;
import static com.getjavajob.training.timashovy.socialnetwork.common.account.PhoneType.WORKING;
import static java.util.Objects.isNull;
import static java.util.stream.Collectors.toList;

public class AccountServiceImpl implements AccountService {

    private final BaseDao<Account> accountDao;
    private final FriendshipDao friendshipDao;
    private final FriendshipChecker friendshipChecker;
    private final PhoneService phoneService;
    private final PhoneDao phoneDao;
    private final PasswordService passwordService;
    private final PasswordDao passwordDao;
    private final TransactionManager transactionManager;
    private static volatile AccountService instance;

    private AccountServiceImpl(BaseDao<Account> accountDao, FriendshipDao friendshipDao,
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

    public static AccountService getInstance(BaseDao<Account> accountDao, FriendshipDao friendshipDao,
                                             FriendshipChecker friendshipChecker, PhoneService phoneService,
                                             PhoneDao phoneDao, PasswordService passwordService,
                                             PasswordDao passwordDao, TransactionManager transactionManager) {
        if (instance == null) {
            synchronized (AccountServiceImpl.class) {
                if (instance == null) {
                    instance = new AccountServiceImpl(accountDao, friendshipDao, friendshipChecker, phoneService,
                            phoneDao, passwordService, passwordDao, transactionManager);
                }
            }
        }
        return instance;
    }

    /**
     * Create new account inserting it in database with auto generated incremented key
     *
     * @param accountRegisterData object which data will be inserted in db as new account
     * @return id of created account
     */
    @Override
    public Long create(AccountRegisterData accountRegisterData) {
        try (Connection conn = transactionManager.getTransactionalConnection()) {
            transactionManager.beginTransaction(conn);
            try {
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
                transactionManager.commitTransaction(conn);
                return accountId;
            } catch (Exception e) {
                transactionManager.rollbackTransaction(conn);
                throw new ServiceException("create account failed : " + e.getMessage(), e);
            }
        } catch (SQLException e) {
            throw new ServiceException("create account failed : " + e.getMessage(), e);
        }
    }

    @Override
    public boolean update(Long accountId, Account updatedAccount) {
        validateAccountId(accountId);
        validateAccount(updatedAccount);
        return accountDao.updateById(accountId, updatedAccount);
    }

    private void validateAccount(Account account) {
        if (isNull(account) || isNull(account.getFirstName()) || isNull(account.getLastName())
                || isNull(account.getEmail())) {
            throw new IllegalArgumentException("Account validation error: field not null constraint violation");
        }
        if (!isNull(account.getIcq()) || !("".equals(account.getIcq()))) {
            ((TableConstraintsValidator) accountDao).validateEntityFieldUniqueness("icq", account.getIcq());
        }
        if (!isNull(account.getSkype())) {
            ((TableConstraintsValidator) accountDao).validateEntityFieldUniqueness("skype",
                    account.getSkype());
        }
        ((TableConstraintsValidator) accountDao).validateEntityFieldUniqueness("email", account.getEmail());
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
    public boolean updateFirstName(Long accountId, String firstName) {
        validateAccountId(accountId);
        validateAccountFieldNotNull(firstName);
        return accountDao.getById(accountId).isPresent() && accountDao.updateById(accountId,
                new Account.Builder(accountDao.getById(accountId).get()).firstName(firstName).build());
    }

    @Override
    public boolean updateLastName(Long accountId, String lastName) {
        validateAccountId(accountId);
        validateAccountFieldNotNull(lastName);
        return accountDao.getById(accountId).isPresent() && accountDao.updateById(accountId,
                new Account.Builder(accountDao.getById(accountId).get()).lastName(lastName).build());
    }

    @Override
    public boolean updateMiddleName(Long accountId, String middleName) {
        validateAccountId(accountId);
        validateAccountFieldNotNull(middleName);
        return accountDao.getById(accountId).isPresent() && accountDao.updateById(accountId,
                new Account.Builder(accountDao.getById(accountId).get()).middleName(middleName).build());
    }

    @Override
    public boolean updateBirthDate(Long accountId, LocalDate birthDate) {
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

    @Override
    public boolean updateEmail(Long accountId, String email) {
        validateAccountId(accountId);
        validateAccountFieldNotNull(email);
        return accountDao.getById(accountId).isPresent() && accountDao.updateById(accountId,
                new Account.Builder(accountDao.getById(accountId).get()).email(email).build());
    }

    @Override
    public boolean updateIcq(Long accountId, String icq) {
        validateAccountId(accountId);
        validateAccountFieldNotNull(icq);
        return accountDao.getById(accountId).isPresent() && accountDao.updateById(accountId,
                new Account.Builder(accountDao.getById(accountId).get()).icq(icq).build());
    }

    @Override
    public boolean updateSkype(Long accountId, String skype) {
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
    public boolean updateRole(Long accountId, AccountRole role) {
        validateAccountId(accountId);
        validateAccountFieldNotNull(role);
        return accountDao.getById(accountId).isPresent() && accountDao.updateById(accountId,
                new Account.Builder(accountDao.getById(accountId).get()).role(role).build());
    }

    @Override
    public boolean updateAvatar(Long accountId, InputStream updatedAvatar) {
        validateAccountId(accountId);
        return accountDao.getById(accountId).isPresent() && accountDao.updateById(accountId,
                new Account.Builder(accountDao.getById(accountId).get()).avatar(updatedAvatar).build());
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
