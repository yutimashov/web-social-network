package com.getjavajob.training.timashovy.socialnetwork.dao.daoimpl.account;

import com.getjavajob.training.timashovy.socialnetwork.common.account.Password;
import com.getjavajob.training.timashovy.socialnetwork.dao.interfaces.account.PasswordDao;
import com.getjavajob.training.timashovy.socialnetwork.dao.util.DaoException;
import com.getjavajob.training.timashovy.socialnetwork.dao.util.dbutils.dbconnection.TransactionManager;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

import static com.getjavajob.training.timashovy.socialnetwork.dao.util.dbutils.TableNames.ACCOUNTS_TABLE;
import static com.getjavajob.training.timashovy.socialnetwork.dao.util.dbutils.TableNames.ACCOUNT_PASSWORDS_TABLE;
import static com.getjavajob.training.timashovy.socialnetwork.dao.util.dbutils.dbconnection.ConnectionManager.getPreparedStatement;
import static com.getjavajob.training.timashovy.socialnetwork.dao.util.dbutils.fieldsnames.AccountTableFields.ACCOUNT_EMAIL;
import static com.getjavajob.training.timashovy.socialnetwork.dao.util.dbutils.fieldsnames.AccountTableFields.ACCOUNT_ID;
import static com.getjavajob.training.timashovy.socialnetwork.dao.util.dbutils.fieldsnames.PasswordTableFields.*;
import static java.util.Optional.empty;
import static java.util.Optional.of;

/**
 * Singleton class responsible for working with {@link com.getjavajob.training.timashovy.socialnetwork.dao.util.dbutils.TableNames#ACCOUNTS_TABLE accounts table} table in DB.
 * It provides safe multithreading approach for creating singleton object using synchronization mechanism.
 */
public class PasswordDaoImpl implements PasswordDao {

    private static final String CREATE = "INSERT INTO " + ACCOUNT_PASSWORDS_TABLE + " (" + PASSWORD_ACCOUNT_ID + ", "
            + PASSWORD_HASH + ", " + PASSWORD_SALT + ") VALUES(?, ?, ?)";
    private static final String GET_BY_ACCOUNT_ID = "SELECT " + PASSWORD_ACCOUNT_ID + ", " + PASSWORD_HASH + ", "
            + PASSWORD_SALT + " FROM " + ACCOUNT_PASSWORDS_TABLE + " WHERE " + PASSWORD_ACCOUNT_ID + " = ?;";
    private static final String GET_BY_ACCOUNT_EMAIL = "SELECT " + PASSWORD_ACCOUNT_ID + ", " + PASSWORD_HASH + ", "
            + PASSWORD_SALT + " FROM " + ACCOUNT_PASSWORDS_TABLE + " pass JOIN " + ACCOUNTS_TABLE + " acc ON acc."
            + ACCOUNT_ID + " = pass." + PASSWORD_ACCOUNT_ID + " WHERE acc." + ACCOUNT_EMAIL + " = ?;";
    private final TransactionManager transactionManager;

    public PasswordDaoImpl(TransactionManager transactionManager) {
        this.transactionManager = transactionManager;
    }

    @Override
    public Long create(Password password) {
        try (PreparedStatement statement
                     = transactionManager.getGetTransactionalPreparedStatementWithGeneratedKeys(CREATE)) {
            statement.setLong(1, password.getAccountId());
            statement.setString(2, password.getPassword());
            statement.setString(3, password.getSalt());
            if (statement.executeUpdate() > 0) {
                ResultSet generatedKeys = statement.getGeneratedKeys();
                if (generatedKeys.next()) {
                    password.setId(generatedKeys.getLong(1));
                }
                return password.getId();
            } else {
                throw new DaoException("dao: create password method failed: no rows affected.");
            }
        } catch (SQLException e) {
            throw new DaoException("dao: create password method failed: " + e.getMessage());
        }
    }

    @Override
    public Optional<Password> getById(Long accountId) {
        try (PreparedStatement preparedStatement = getPreparedStatement(GET_BY_ACCOUNT_ID)) {
            preparedStatement.setLong(1, accountId);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return of(new Password(accountId, resultSet.getString("hash_password"), resultSet.getString("salt")));
            } else {
                return empty();
            }
        } catch (SQLException e) {
            throw new DaoException("dao: get password by account id method failed: " + e.getMessage());
        }
    }

    @Override
    public Optional<Password> findByEmail(String email) {
        try (PreparedStatement preparedStatement = getPreparedStatement(GET_BY_ACCOUNT_EMAIL)) {
            preparedStatement.setString(1, email);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return of(new Password(resultSet.getLong("account_id"), resultSet.getString("hash_password"),
                        resultSet.getString("salt")));
            } else {
                return empty();
            }
        } catch (SQLException e) {
            throw new DaoException("dao: get password by email method failed: " + e.getMessage());
        }
    }

}
