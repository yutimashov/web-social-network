package com.getjavajob.training.timashovy.socialnetwork.dao.daoimpl.account;

import com.getjavajob.training.timashovy.socialnetwork.common.account.Password;
import com.getjavajob.training.timashovy.socialnetwork.dao.interfaces.account.PasswordDao;
import com.getjavajob.training.timashovy.socialnetwork.dao.util.DaoException;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

import static com.getjavajob.training.timashovy.socialnetwork.dao.util.dbutils.TableNames.ACCOUNT_PASSWORDS_TABLE;
import static com.getjavajob.training.timashovy.socialnetwork.dao.util.dbutils.TableNames.ACCOUNT_TABLE;
import static com.getjavajob.training.timashovy.socialnetwork.dao.util.dbutils.dbconnection.ConnectionManager.getPreparedStatement;
import static com.getjavajob.training.timashovy.socialnetwork.dao.util.dbutils.dbconnection.ConnectionManager.getPreparedStatementWithGeneratedKeys;
import static java.util.Optional.empty;
import static java.util.Optional.of;

public class PasswordDaoImpl implements PasswordDao {

    private static final String CREATE = "INSERT INTO " + ACCOUNT_PASSWORDS_TABLE + " (account_id, hash_password, "
            + "salt) VALUES(?, ?, ?)";
    private static final String GET_BY_ACCOUNT_ID = "SELECT account_id, hash_password, salt FROM "
            + ACCOUNT_PASSWORDS_TABLE + " WHERE account_id = ?;";
    private static final String GET_BY_ACCOUNT_EMAIL = "SELECT account_id, hash_password, salt FROM "
            + ACCOUNT_PASSWORDS_TABLE + " pass JOIN " + ACCOUNT_TABLE + " acc ON acc.id = pass.account_id " +
            "WHERE acc.email = ?;";

    private static final PasswordDaoImpl PASSWORD_DAO_INSTANCE = new PasswordDaoImpl();

    private PasswordDaoImpl() {
    }

    public static PasswordDaoImpl createInstance() {
        return PASSWORD_DAO_INSTANCE;
    }

    @Override
    public Long create(Long accountId, Password password) {
        try (PreparedStatement createPasswordStatement = getPreparedStatementWithGeneratedKeys(CREATE)) {
            createPasswordStatement.setLong(1, accountId);
            createPasswordStatement.setString(2, password.getPassword());
            createPasswordStatement.setString(3, password.getSalt());
            if (createPasswordStatement.executeUpdate() > 0) {
                ResultSet generatedKeys = createPasswordStatement.getGeneratedKeys();
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
