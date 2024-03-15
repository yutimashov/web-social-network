package com.getjavajob.training.timashovy.socialnetwork.dao.daoimpl.account;

import com.getjavajob.training.timashovy.socialnetwork.common.account.Account;
import com.getjavajob.training.timashovy.socialnetwork.common.account.AuthToken;
import com.getjavajob.training.timashovy.socialnetwork.dao.interfaces.account.CredentialsProviderDao;
import com.getjavajob.training.timashovy.socialnetwork.dao.util.exceptions.DaoException;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import static com.getjavajob.training.timashovy.socialnetwork.dao.util.dbconnection.ConnectionManager.getPreparedStatementWithGeneratedKeys;

public class AuthTokenDaoImpl implements CredentialsProviderDao<AuthToken> {

    private static final AuthTokenDaoImpl AUTH_TOKEN_DAO = new AuthTokenDaoImpl();
    private static final String CREATE_TOKEN = "INSERT INTO account_data.account_auth_tokens (token, validator, "
            + "account_id) VALUES(?, ?, ?)";

    private AuthTokenDaoImpl() {
    }

    public static AuthTokenDaoImpl getAuthTokenDaoInstance() {
        return AUTH_TOKEN_DAO;
    }

    @Override
    public Long create(AuthToken authToken) {
        try (PreparedStatement createAuthTokenStatement = getPreparedStatementWithGeneratedKeys(CREATE_TOKEN)) {
            setTokenData(authToken, createAuthTokenStatement);
            if (createAuthTokenStatement.executeUpdate() > 0) {
                ResultSet generatedId = createAuthTokenStatement.getGeneratedKeys();
                if (generatedId.next()) {
                    authToken.setId(generatedId.getLong(1));
                }
                return authToken.getId();
            } else {
                throw new DaoException("dao: create auth_token method failed: no rows affected.");
            }
        } catch (SQLException e) {
            throw new DaoException("dao: create auth_token method failed: " + e.getMessage());
        }
    }

    private void setTokenData(AuthToken authToken, PreparedStatement preparedStatement) throws SQLException {
        preparedStatement.setString(1, authToken.getToken());
        preparedStatement.setString(2, authToken.getValidator());
        preparedStatement.setLong(3, authToken.getAccountId());
    }

    @Override
    public boolean update(AuthToken authToken) {
        return false;
    }

    @Override
    public boolean verify(Account account, AuthToken authToken) {
        return false;
    }

}
