package com.getjavajob.training.timashovy.socialnetwork.dao.daoimpl;

import com.getjavajob.training.timashovy.socialnetwork.common.account.Password;
import com.getjavajob.training.timashovy.socialnetwork.dao.interfaces.LoginDao;
import com.getjavajob.training.timashovy.socialnetwork.dao.util.exceptions.DaoException;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import static com.getjavajob.training.timashovy.socialnetwork.dao.util.dbconnection.ConnectionManager.getPreparedStatement;

public class LoginDaoImpl implements LoginDao {

    private static final LoginDaoImpl LOGIN_DAO_INSTANCE = new LoginDaoImpl();

    private LoginDaoImpl() {
    }

    public static LoginDaoImpl getLoginDaoImpl() {
        return LOGIN_DAO_INSTANCE;
    }

    private static final String GET_PASSWORD_BY_EMAIL = "SELECT pass.hash_password password, salt " +
            "FROM account_data.account_passwords pass " +
            "INNER JOIN account_data.account acc ON acc.id = pass.account_id " +
            "WHERE acc.email = ?";

    @Override
    public Password findPasswordByEmail(String email) {
        try (PreparedStatement getAccountIdByEmailStatement = getPreparedStatement(GET_PASSWORD_BY_EMAIL)) {
            getAccountIdByEmailStatement.setString(1, email);
            ResultSet accountData = getAccountIdByEmailStatement.executeQuery();
            if (accountData.next()) {
                return new Password(accountData.getString("password"), accountData.getString("salt"));
            } else {
                throw new IllegalArgumentException("dao: user with such email does not exist");
            }
        } catch (SQLException e) {
            throw new DaoException("dao: get account by id method failed: " + e.getMessage());
        }
    }

}
