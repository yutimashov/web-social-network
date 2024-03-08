package com.getjavajob.training.timashovy.socialnetwork.dao.daoimpl;

import com.getjavajob.training.timashovy.socialnetwork.common.Account;
import com.getjavajob.training.timashovy.socialnetwork.common.Password;
import com.getjavajob.training.timashovy.socialnetwork.dao.interfaces.PasswordDao;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import static com.getjavajob.training.timashovy.socialnetwork.dao.util.dbconnection.ConnectionManager.getPreparedStatement;

public class PasswordDaoImpl implements PasswordDao {

    private static final String SAVE_PASSWORD = "INSERT INTO account_data.account_passwords (account_id, hash_password,"
            + " salt) VALUES(?, ?, ?)";
    private static final String UPDATE_PASSWORD = "";
    private static final String CHECK_PASSWORD = "";

    private static final PasswordDaoImpl PASSWORD_DAO_INSTANCE = new PasswordDaoImpl();

    private PasswordDaoImpl() {
    }

    public static PasswordDaoImpl getPasswordDaoInstance() {
        return PASSWORD_DAO_INSTANCE;
    }

    @Override
    public boolean savePassword(Account account, Password password) {
        try (PreparedStatement savePasswordStatement = getPreparedStatement(SAVE_PASSWORD)) {
            savePasswordStatement.setLong(1, account.getId());
            savePasswordStatement.setString(2, password.getPassword());
            savePasswordStatement.setString(3, password.getSalt());
            return savePasswordStatement.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean updatePassword(Account account, Password password) {
        return false;
    }

    @Override
    public boolean checkPassword(Account account, Password password) {
        return false;
    }

}
