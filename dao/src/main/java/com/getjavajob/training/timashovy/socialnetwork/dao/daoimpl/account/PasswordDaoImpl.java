package com.getjavajob.training.timashovy.socialnetwork.dao.daoimpl.account;

import com.getjavajob.training.timashovy.socialnetwork.common.account.Account;
import com.getjavajob.training.timashovy.socialnetwork.common.account.Password;
import com.getjavajob.training.timashovy.socialnetwork.dao.interfaces.account.PasswordDao;
import com.getjavajob.training.timashovy.socialnetwork.dao.util.exceptions.DaoException;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import static com.getjavajob.training.timashovy.socialnetwork.dao.util.dbconnection.ConnectionManager.getPreparedStatement;
import static com.getjavajob.training.timashovy.socialnetwork.dao.util.dbconnection.ConnectionManager.getPreparedStatementWithGeneratedKeys;

public class PasswordDaoImpl implements PasswordDao {

    private static final String SAVE_PASSWORD = "INSERT INTO account_data.account_passwords (account_id, hash_password,"
            + " salt) VALUES(?, ?, ?)";
    private static final String GET_PASSWORD = "SELECT account_id, hash_password, salt " +
            "FROM account_data.account_passwords WHERE account_id = ?;";
    private static final String GET_PASSWORD_BY_EMAIL = "SELECT account_id, hash_password, salt " +
            "FROM account_data.account_passwords pass JOIN account_data.account acc ON acc.id = pass.account_id " +
            "WHERE acc.email = ?;";
    private static final String UPDATE_PASSWORD = "";
    private static final String CHECK_PASSWORD = "";

    private static final PasswordDaoImpl PASSWORD_DAO_INSTANCE = new PasswordDaoImpl();

    private PasswordDaoImpl() {
    }

    public static PasswordDaoImpl getPasswordDaoInstance() {
        return PASSWORD_DAO_INSTANCE;
    }

    @Override
    public Long create(Password password) {
        try (PreparedStatement savePasswordStatement = getPreparedStatementWithGeneratedKeys(SAVE_PASSWORD)) {
            setPasswordData(password, savePasswordStatement);
            if (savePasswordStatement.executeUpdate() > 0) {
                ResultSet generatedId = savePasswordStatement.getGeneratedKeys();
                if (generatedId.next()) {
                    password.setId(generatedId.getLong(1));
                }
                return password.getId();
            } else {
                throw new DaoException("dao: create password method failed: no rows affected.");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void setPasswordData(Password password, PreparedStatement preparedStatement) throws SQLException {
        preparedStatement.setLong(1, password.getAccountId());
        preparedStatement.setString(2, password.getPassword());
        preparedStatement.setString(3, password.getSalt());
    }

    @Override
    public boolean update(Password password) {
        return false;
    }

    @Override
    public boolean verify(Account account, Password password) {
        return false;
    }

    @Override
    public Password get(Account account) {
        try (PreparedStatement preparedStatement = getPreparedStatement(GET_PASSWORD)) {
            Long accountId = account.getId();
            preparedStatement.setLong(1, accountId);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return new Password(accountId, resultSet.getString("hash_password"), resultSet.getString("salt"));
            } else {
                return null;
            }
        } catch (SQLException e) {
            throw new DaoException("dao: get password by account method failed: " + e.getMessage());
        }
    }

    public Password findByEmail(String email) {
        try (PreparedStatement preparedStatement = getPreparedStatement(GET_PASSWORD_BY_EMAIL)) {
            preparedStatement.setString(1, email);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return new Password(resultSet.getLong("account_id"), resultSet.getString("hash_password"),
                        resultSet.getString("salt"));
            } else {
                return null;
            }
        } catch (SQLException e) {
            throw new DaoException("dao: get password by email method failed: " + e.getMessage());
        }
    }

}
