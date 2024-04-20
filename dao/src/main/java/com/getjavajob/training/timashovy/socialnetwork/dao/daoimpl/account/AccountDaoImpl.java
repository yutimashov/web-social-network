package com.getjavajob.training.timashovy.socialnetwork.dao.daoimpl.account;

import com.getjavajob.training.timashovy.socialnetwork.common.account.Account;
import com.getjavajob.training.timashovy.socialnetwork.common.account.Phone;
import com.getjavajob.training.timashovy.socialnetwork.common.account.Role;
import com.getjavajob.training.timashovy.socialnetwork.dao.interfaces.BaseDao;
import com.getjavajob.training.timashovy.socialnetwork.dao.interfaces.account.PhoneDao;
import com.getjavajob.training.timashovy.socialnetwork.dao.interfaces.TableConstraintsValidator;
import com.getjavajob.training.timashovy.socialnetwork.dao.util.DaoException;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.getjavajob.training.timashovy.socialnetwork.common.account.PhoneType.PERSONAL;
import static com.getjavajob.training.timashovy.socialnetwork.common.account.PhoneType.WORKING;
import static com.getjavajob.training.timashovy.socialnetwork.common.account.Role.REGULAR;
import static com.getjavajob.training.timashovy.socialnetwork.dao.util.dbutils.AccountTableFields.*;
import static com.getjavajob.training.timashovy.socialnetwork.dao.util.dbutils.TableNames.ACCOUNT_TABLE;
import static com.getjavajob.training.timashovy.socialnetwork.dao.util.dbutils.dbconnection.ConnectionManager.getPreparedStatement;
import static com.getjavajob.training.timashovy.socialnetwork.dao.util.dbutils.dbconnection.ConnectionManager.getPreparedStatementWithGeneratedKeys;
import static java.lang.String.valueOf;
import static java.util.Objects.isNull;
import static java.util.Optional.empty;
import static java.util.Optional.of;
import static java.util.stream.Collectors.toList;

public class AccountDaoImpl implements BaseDao<Account>, TableConstraintsValidator {

    private static final String CREATE = "INSERT INTO " + ACCOUNT_TABLE + " (first_name, last_name, "
            + "middle_name, birth_date, personal_address, work_address, email, icq, skype, additional_info, role_type) "
            + "VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?);";
    private static final String GET_BY_ID = "SELECT id, first_name, last_name, middle_name, birth_date, "
            + "personal_address, work_address, email, icq, skype, additional_info, role_type FROM "
            + ACCOUNT_TABLE + " WHERE id = ?;";
    private static final String GET_ALL = "SELECT id, first_name, last_name, middle_name, birth_date, personal_address, "
            + "work_address, email, icq, skype, additional_info, role_type "
            + "FROM " + ACCOUNT_TABLE + ";";
    private static final String UPDATE_BY_ID = "UPDATE " + ACCOUNT_TABLE + " SET first_name = ?, last_name = ?, "
            + "middle_name = ?, birth_date = ?, personal_address = ?, work_address = ?, email = ?, icq = ?, skype = ?, "
            + "additional_info = ?, role_type = ? WHERE id = ?;";
    private static final String DELETE_BY_ID = "DELETE FROM " + ACCOUNT_TABLE + " WHERE id = ?;";
    private static final AccountDaoImpl ACCOUNT_DAO_INSTANCE = new AccountDaoImpl();
    private static final PhoneDao PHONE_DAO = PhoneDaoImpl.getInstance();

    private AccountDaoImpl() {
    }

    public static AccountDaoImpl getInstance() {
        return ACCOUNT_DAO_INSTANCE;
    }

    @Override
    public <E> void validateEntityFieldUniqueness(String fieldName, E fieldValue) {
        if (isNull(fieldName) || isNull(fieldValue)) {
            throw new IllegalArgumentException("uniqueness field violation: either fieldName or fieldValue is null");
        }
        if (!fieldValue.toString().isEmpty()) {
            String CHECK_RECORD_EXISTENCE_QUERY = "SELECT id FROM " + ACCOUNT_TABLE + " WHERE " + fieldName + " = ?";
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
    public Long create(Account account) {
        try (PreparedStatement createAccountStatement = getPreparedStatementWithGeneratedKeys(CREATE)) {
            setAccountData(account, createAccountStatement);
            if (createAccountStatement.executeUpdate() > 0) {
                ResultSet generatedKeys = createAccountStatement.getGeneratedKeys();
                if (generatedKeys.next()) {
                    account.setId(generatedKeys.getLong(1));
                }
                return account.getId();
            } else {
                throw new DaoException("dao: create account method failed: no rows affected.");
            }
        } catch (SQLException e) {
            throw new DaoException("dao: create account method failed: " + e.getMessage());
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
    }

    @Override
    public Optional<Account> getById(Long accountId) {
        try (PreparedStatement getAccountByIdStatement = getPreparedStatement(GET_BY_ID)) {
            getAccountByIdStatement.setLong(1, accountId);
            ResultSet accountData = getAccountByIdStatement.executeQuery();
            if (accountData.next()) {
                Account account = createAccountFromResultSet(accountData);
                List<Phone> phones = PHONE_DAO.getAll(accountId);
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
                .id(resultSet.getLong(ACCOUNT_ID_FIELD))
                .firstName(resultSet.getString(ACCOUNT_FIRST_NAME_FIELD))
                .lastName(resultSet.getString(ACCOUNT_LAST_NAME_FIELD))
                .email(resultSet.getString(ACCOUNT_EMAIL_FIELD))
                .birthDate(resultSet.getDate(ACCOUNT_BIRTHDATE_FIELD) != null
                        ? resultSet.getDate(ACCOUNT_BIRTHDATE_FIELD).toLocalDate() : null)
                .middleName(resultSet.getString(ACCOUNT_MIDDLE_NAME_FIELD))
                .personalAddress(resultSet.getString(ACCOUNT_PERSONAL_ADDRESS_FIELD))
                .workAddress(resultSet.getString(ACCOUNT_WORK_ADDRESS_FIELD))
                .icq(resultSet.getString(ACCOUNT_ICQ_FIELD))
                .skype(resultSet.getString(ACCOUNT_SKYPE_FIELD))
                .additionalInfo(resultSet.getString(ACCOUNT_ADDITIONAL_INFO_FIELD))
                .role(Role.valueOf(resultSet.getString(ACCOUNT_ROLE_TYPE_FIELD)))
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
            updateByIdStatement.setLong(12, id);
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
