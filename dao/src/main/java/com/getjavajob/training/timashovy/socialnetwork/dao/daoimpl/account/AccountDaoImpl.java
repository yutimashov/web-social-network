package com.getjavajob.training.timashovy.socialnetwork.dao.daoimpl.account;

import com.getjavajob.training.timashovy.socialnetwork.common.account.Account;
import com.getjavajob.training.timashovy.socialnetwork.common.account.Phone;
import com.getjavajob.training.timashovy.socialnetwork.common.account.Role;
import com.getjavajob.training.timashovy.socialnetwork.dao.interfaces.AccountGroupDao;
import com.getjavajob.training.timashovy.socialnetwork.dao.interfaces.TableConstraintsValidator;
import com.getjavajob.training.timashovy.socialnetwork.dao.util.exceptions.DaoException;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.getjavajob.training.timashovy.socialnetwork.common.account.PhoneType.PERSONAL;
import static com.getjavajob.training.timashovy.socialnetwork.common.account.PhoneType.WORKING;
import static com.getjavajob.training.timashovy.socialnetwork.common.account.Role.REGULAR;
import static com.getjavajob.training.timashovy.socialnetwork.dao.daoimpl.account.PhoneDaoImpl.getPhoneDaoInstance;
import static com.getjavajob.training.timashovy.socialnetwork.dao.util.dbconnection.ConnectionManager.getPreparedStatement;
import static com.getjavajob.training.timashovy.socialnetwork.dao.util.dbconnection.ConnectionManager.getPreparedStatementWithGeneratedKeys;
import static java.lang.String.valueOf;
import static java.util.Objects.isNull;
import static java.util.Optional.empty;
import static java.util.Optional.of;
import static java.util.stream.Collectors.toList;

public class AccountDaoImpl implements AccountGroupDao<Account>, TableConstraintsValidator {

    private static final String CREATE_ACCOUNT = "INSERT INTO account_data.account (first_name, last_name, " +
            "middle_name, birth_date, personal_address, work_address, email, icq, skype, additional_info, role_type) " +
            "VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?);";
    private static final String GET_ACCOUNT = "SELECT id, first_name, last_name, middle_name, birth_date, " +
            "personal_address, work_address, email, icq, skype, additional_info, role_type FROM account_data.account " +
            "WHERE id = ?;";
    private static final String GET_ALL_ACCOUNTS = "SELECT id, first_name, last_name, middle_name, birth_date, "
            + "personal_address, work_address, email, icq, skype, additional_info, role_type FROM account_data.account;";
    private static final String UPDATE_ACCOUNT = "UPDATE account_data.account SET first_name = ?, last_name = ?, "
            + "middle_name = ?, birth_date = ?, personal_address = ?, work_address = ?, email = ?, icq = ?, skype = ?, "
            + "additional_info = ?, role_type = ? WHERE id = ?;";
    private static final String DELETE_ACCOUNT = "DELETE FROM account_data.account WHERE id = ?;";
    private static final AccountDaoImpl ACCOUNT_DAO_INSTANCE = new AccountDaoImpl();
    private final PhoneDaoImpl phoneDao = getPhoneDaoInstance();

    private AccountDaoImpl() {
    }

    public static AccountDaoImpl getAccountDaoInstance() {
        return ACCOUNT_DAO_INSTANCE;
    }

    @Override
    public <E> void validateEntityFieldUniqueness(String fieldName, E fieldValue) {
        if (isNull(fieldName) || isNull(fieldValue)) {
            throw new IllegalArgumentException("uniqueness field violation: either fieldName or fieldValue is null");
        }
        if (!fieldValue.equals("")) {
            String checkRecordExistenceQuery = "SELECT id FROM account_data.account WHERE " + fieldName + " = ?";
            try (PreparedStatement recordsSet = getPreparedStatement(checkRecordExistenceQuery)) {
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
        try (PreparedStatement createAccountStatement = getPreparedStatementWithGeneratedKeys(CREATE_ACCOUNT)) {
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
    public Optional<Account> getById(Long id) {
        try (PreparedStatement getAccountByIdStatement = getPreparedStatement(GET_ACCOUNT)) {
            getAccountByIdStatement.setLong(1, id);
            ResultSet accountData = getAccountByIdStatement.executeQuery();
            if (accountData.next()) {
                Account account = createAccountFromResultSet(accountData);
                List<Phone> accountPhones = phoneDao.getPhoneNumbers(id);
                if (!accountPhones.isEmpty()) {
                    account.setPersonalPhoneNumber(accountPhones.stream().filter(phone -> phone.getPhoneType() == PERSONAL)
                            .collect(toList()));
                    account.setWorkPhoneNumber(accountPhones.stream().filter(phone -> phone.getPhoneType() == WORKING)
                            .collect(toList()));
                }
                return of(account);
            } else {
                return empty();
            }
        } catch (SQLException e) {
            throw new DaoException("dao: get account by id method failed: " + e.getMessage());
        }
    }

    private Account createAccountFromResultSet(ResultSet resultSet) throws SQLException {
        return new Account.Builder()
                .id(resultSet.getLong("id"))
                .firstName(resultSet.getString("first_name"))
                .lastName(resultSet.getString("last_name"))
                .email(resultSet.getString("email"))
                .birthDate(resultSet.getDate("birth_date") != null
                        ? resultSet.getDate("birth_date").toLocalDate() : null)
                .middleName(resultSet.getString("middle_name"))
                .personalAddress(resultSet.getString("personal_address"))
                .workAddress(resultSet.getString("work_address"))
                .icq(resultSet.getString("icq"))
                .skype(resultSet.getString("skype"))
                .additionalInfo(resultSet.getString("additional_info"))
                .role(Role.valueOf(resultSet.getString("role_type")))
                .build();
    }

    @Override
    public List<Account> getAll() {
        try (PreparedStatement getAllAccountsStatement = getPreparedStatement(GET_ALL_ACCOUNTS)) {
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
        try (PreparedStatement updateByIdStatement = getPreparedStatement(UPDATE_ACCOUNT)) {
            setAccountData(account, updateByIdStatement);
            updateByIdStatement.setLong(12, id);
            return updateByIdStatement.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new DaoException("dao: update account by id method failed: ", e);
        }
    }

    @Override
    public boolean deleteById(Long id) {
        try (PreparedStatement deleteByIdStatement = getPreparedStatement(DELETE_ACCOUNT)) {
            deleteByIdStatement.setLong(1, id);
            return deleteByIdStatement.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new DaoException("dao: delete account by id method failed: " + e.getMessage());
        }
    }

}
