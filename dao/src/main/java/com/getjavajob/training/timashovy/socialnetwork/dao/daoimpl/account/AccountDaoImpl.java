package com.getjavajob.training.timashovy.socialnetwork.dao.daoimpl.account;

import com.getjavajob.training.timashovy.socialnetwork.common.account.Account;
import com.getjavajob.training.timashovy.socialnetwork.common.account.AccountRole;
import com.getjavajob.training.timashovy.socialnetwork.common.account.Phone;
import com.getjavajob.training.timashovy.socialnetwork.dao.interfaces.BaseDao;
import com.getjavajob.training.timashovy.socialnetwork.dao.interfaces.TableConstraintsValidator;
import com.getjavajob.training.timashovy.socialnetwork.dao.interfaces.account.PhoneDao;
import com.getjavajob.training.timashovy.socialnetwork.dao.util.DaoException;
import com.getjavajob.training.timashovy.socialnetwork.dao.util.dbutils.dbconnection.ConnectionWrapper;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.getjavajob.training.timashovy.socialnetwork.common.account.AccountRole.REGULAR;
import static com.getjavajob.training.timashovy.socialnetwork.common.account.PhoneType.PERSONAL;
import static com.getjavajob.training.timashovy.socialnetwork.common.account.PhoneType.WORKING;
import static com.getjavajob.training.timashovy.socialnetwork.dao.util.dbutils.fieldsnames.AccountTableFields.*;
import static com.getjavajob.training.timashovy.socialnetwork.dao.util.dbutils.TableNames.ACCOUNTS_TABLE;
import static com.getjavajob.training.timashovy.socialnetwork.dao.util.dbutils.dbconnection.ConnectionManager.getPreparedStatement;
import static java.lang.String.valueOf;
import static java.sql.Statement.RETURN_GENERATED_KEYS;
import static java.util.Objects.isNull;
import static java.util.Optional.empty;
import static java.util.Optional.of;
import static java.util.stream.Collectors.toList;

/**
 * Singleton class responsible for working with {@link com.getjavajob.training.timashovy.socialnetwork.dao.util.dbutils.TableNames#ACCOUNTS_TABLE accounts table}.
 * It provides safe multithreading approach for creating singleton object using synchronization mechanism.
 */
public class AccountDaoImpl implements BaseDao<Account>, TableConstraintsValidator {

    private static final String CREATE = "INSERT INTO " + ACCOUNTS_TABLE + " (" + ACCOUNT_FIRST_NAME + ", "
            + ACCOUNT_LAST_NAME + ", " + ACCOUNT_MIDDLE_NAME + ", " + ACCOUNT_BIRTH_DATE + ", "
            + ACCOUNT_PERSONAL_ADDRESS + ", " + ACCOUNT_WORK_ADDRESS + ", " + ACCOUNT_EMAIL + ", " + ACCOUNT_ICQ + ", "
            + ACCOUNT_SKYPE + ", " + ACCOUNT_ADDITIONAL_INFO + ", " + ACCOUNT_ROLE_TYPE + ", " + ACCOUNT_AVATAR
            + ") VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?);";
    private static final String GET_BY_ID = "SELECT " + ACCOUNT_ID + ", " + ACCOUNT_FIRST_NAME + ", "
            + ACCOUNT_LAST_NAME + ", " + ACCOUNT_MIDDLE_NAME + ", " + ACCOUNT_BIRTH_DATE + ", "
            + ACCOUNT_PERSONAL_ADDRESS + ", " + ACCOUNT_WORK_ADDRESS + ", " + ACCOUNT_EMAIL + ", " + ACCOUNT_ICQ + ", "
            + ACCOUNT_SKYPE + ", " + ACCOUNT_ADDITIONAL_INFO + ", " + ACCOUNT_ROLE_TYPE + ", " + ACCOUNT_AVATAR
            + " FROM " + ACCOUNTS_TABLE + " WHERE " + ACCOUNT_ID + " = ?;";
    private static final String GET_ALL = "SELECT " + ACCOUNT_ID + ", " + ACCOUNT_FIRST_NAME + ", " + ACCOUNT_LAST_NAME
            + ", " + ACCOUNT_MIDDLE_NAME + ", " + ACCOUNT_BIRTH_DATE + ", " + ACCOUNT_PERSONAL_ADDRESS + ", "
            + ACCOUNT_WORK_ADDRESS + ", " + ACCOUNT_EMAIL + ", " + ACCOUNT_ICQ + ", " + ACCOUNT_SKYPE + ", "
            + ACCOUNT_ADDITIONAL_INFO + ", " + ACCOUNT_ROLE_TYPE + ", " + ACCOUNT_AVATAR + " FROM " + ACCOUNTS_TABLE
            + ";";
    private static final String UPDATE_BY_ID = "UPDATE " + ACCOUNTS_TABLE + " SET " + ACCOUNT_FIRST_NAME + " = ?, "
            + ACCOUNT_LAST_NAME + " = ?, " + ACCOUNT_MIDDLE_NAME + " = ?, " + ACCOUNT_BIRTH_DATE + " = ?, "
            + ACCOUNT_PERSONAL_ADDRESS + " = ?, " + ACCOUNT_WORK_ADDRESS + " = ?, " + ACCOUNT_EMAIL + " = ?, "
            + ACCOUNT_ICQ + "= ?, " + ACCOUNT_SKYPE + "= ?, " + ACCOUNT_ADDITIONAL_INFO + " = ?, " + ACCOUNT_ROLE_TYPE
            + " = ?, " + ACCOUNT_AVATAR + " = ? WHERE " + ACCOUNT_ID + " = ?;";
    private static final String DELETE_BY_ID = "DELETE FROM " + ACCOUNTS_TABLE + " WHERE " + ACCOUNT_ID + " = ?;";
    private final PhoneDao phoneDao;
    private static volatile BaseDao<Account> instance;

    private AccountDaoImpl(PhoneDao phoneDao) {
        this.phoneDao = phoneDao;
    }

    public static BaseDao<Account> getInstance(PhoneDao phoneDao) {
        if (instance == null) {
            synchronized (AccountDaoImpl.class) {
                if (instance == null) {
                    instance = new AccountDaoImpl(phoneDao);
                }
            }
        }
        return instance;
    }

    @Override
    public <E> void validateEntityFieldUniqueness(String fieldName, E fieldValue) {
        if (isNull(fieldName) || isNull(fieldValue)) {
            throw new IllegalArgumentException("uniqueness field violation: either fieldName or fieldValue is null");
        }
        if (!fieldValue.toString().isEmpty()) {
            String CHECK_RECORD_EXISTENCE_QUERY = "SELECT id FROM " + ACCOUNTS_TABLE + " WHERE " + fieldName + " = ?";
            try (PreparedStatement recordsSet = getPreparedStatement(CHECK_RECORD_EXISTENCE_QUERY)) {
                recordsSet.setObject(1, fieldValue);
                ResultSet existedRecords = recordsSet.executeQuery();
                if (existedRecords.next()) {
                    throw new IllegalArgumentException("account fields uniqueness violation");
                }
            } catch (SQLException e) {
                throw new DaoException("dao: validate entity uniqueness method failed: " + e.getMessage());
            }
        }
    }

    @Override
    public Long create(ConnectionWrapper connection, Account account) {
        try (PreparedStatement accountStatement = connection.prepareStatement(CREATE, RETURN_GENERATED_KEYS)) {
            setAccountData(account, accountStatement);
            if (accountStatement.executeUpdate() > 0) {
                try (ResultSet generatedKeys = accountStatement.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        account.setId(generatedKeys.getLong(1));
                    }
                }
                return account.getId();
            } else {
                throw new DaoException("dao: create account method failed: no rows affected.");
            }
        } catch (SQLException e) {
            throw new DaoException("dao: create account method failed: " + e.getMessage(), e);
        }
    }

    private void setAccountData(Account account, PreparedStatement preparedStatement) throws SQLException {
        preparedStatement.setString(1, account.getFirstName());
        preparedStatement.setString(2, account.getLastName());
        preparedStatement.setString(3, account.getMiddleName());
        preparedStatement.setObject(4, account.getBirthDate());
        preparedStatement.setString(5, account.getPersonalAddress());
        preparedStatement.setString(6, account.getWorkAddress());
        preparedStatement.setString(7, account.getEmail());
        preparedStatement.setString(8, account.getIcq());
        preparedStatement.setString(9, account.getSkype());
        preparedStatement.setString(10, account.getAdditionalInfo());
        if (isNull(account.getRole())) {
            preparedStatement.setString(11, valueOf(REGULAR));
        } else {
            preparedStatement.setString(11, account.getRole().name());
        }
        preparedStatement.setBinaryStream(12, account.getAvatar());
    }

    @Override
    public Optional<Account> getById(Long accountId) {
        try (PreparedStatement getAccountByIdStatement = getPreparedStatement(GET_BY_ID)) {
            getAccountByIdStatement.setLong(1, accountId);
            ResultSet accountData = getAccountByIdStatement.executeQuery();
            if (accountData.next()) {
                Account account = createAccountFromResultSet(accountData);
                List<Phone> phones = phoneDao.getAll(accountId);
                if (!phones.isEmpty()) {
                    account.setPersonalPhoneNumber(phones.stream().filter(phone -> phone.getPhoneType() == PERSONAL)
                            .collect(toList()));
                    account.setWorkPhoneNumber(phones.stream().filter(phone -> phone.getPhoneType() == WORKING)
                            .collect(toList()));
                }
                return of(account);
            } else {
                return empty();
            }
        } catch (SQLException e) {
            throw new DaoException("dao: get account by accountId method failed: " + e.getMessage());
        }
    }

    private Account createAccountFromResultSet(ResultSet resultSet) throws SQLException {
        return new Account.Builder()
                .id(resultSet.getLong(ACCOUNT_ID))
                .firstName(resultSet.getString(ACCOUNT_FIRST_NAME))
                .lastName(resultSet.getString(ACCOUNT_LAST_NAME))
                .email(resultSet.getString(ACCOUNT_EMAIL))
                .birthDate(resultSet.getDate(ACCOUNT_BIRTH_DATE) != null
                        ? resultSet.getDate(ACCOUNT_BIRTH_DATE).toLocalDate() : null)
                .middleName(resultSet.getString(ACCOUNT_MIDDLE_NAME))
                .personalAddress(resultSet.getString(ACCOUNT_PERSONAL_ADDRESS))
                .workAddress(resultSet.getString(ACCOUNT_WORK_ADDRESS))
                .icq(resultSet.getString(ACCOUNT_ICQ))
                .skype(resultSet.getString(ACCOUNT_SKYPE))
                .additionalInfo(resultSet.getString(ACCOUNT_ADDITIONAL_INFO))
                .role(AccountRole.valueOf(resultSet.getString(ACCOUNT_ROLE_TYPE)))
                .avatar(resultSet.getBinaryStream(ACCOUNT_AVATAR))
                .build();
    }

    @Override
    public List<Account> getAll() {
        try (PreparedStatement getAllAccountsStatement = getPreparedStatement(GET_ALL)) {
            List<Account> accounts = new ArrayList<>();
            ResultSet accountsSet = getAllAccountsStatement.executeQuery();
            while (accountsSet.next()) {
                accounts.add(createAccountFromResultSet(accountsSet));
            }
            return accounts;
        } catch (SQLException e) {
            throw new DaoException("dao: get all accounts method failed: " + e.getMessage());
        }
    }

    @Override
    public boolean updateById(Long id, Account account) {
        try (PreparedStatement updateByIdStatement = getPreparedStatement(UPDATE_BY_ID)) {
            setAccountData(account, updateByIdStatement);
            updateByIdStatement.setLong(13, id);
            return updateByIdStatement.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new DaoException("dao: update account by id method failed: ", e);
        }
    }

    @Override
    public boolean deleteById(Long id) {
        try (PreparedStatement deleteByIdStatement = getPreparedStatement(DELETE_BY_ID)) {
            deleteByIdStatement.setLong(1, id);
            return deleteByIdStatement.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new DaoException("dao: delete account by id method failed: " + e.getMessage());
        }
    }

}
