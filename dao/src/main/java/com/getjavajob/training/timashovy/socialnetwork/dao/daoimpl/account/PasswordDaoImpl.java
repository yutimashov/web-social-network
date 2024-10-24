package com.getjavajob.training.timashovy.socialnetwork.dao.daoimpl.account;

import com.getjavajob.training.timashovy.socialnetwork.domain.account.Password;
import com.getjavajob.training.timashovy.socialnetwork.dao.interfaces.account.PasswordDao;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import javax.sql.DataSource;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Optional;

import static com.getjavajob.training.timashovy.socialnetwork.dao.util.dbutils.TableNames.ACCOUNTS_TABLE;
import static com.getjavajob.training.timashovy.socialnetwork.dao.util.dbutils.TableNames.ACCOUNT_PASSWORDS_TABLE;
import static com.getjavajob.training.timashovy.socialnetwork.dao.util.dbutils.fieldsnames.AccountTableFields.ACCOUNT_EMAIL;
import static com.getjavajob.training.timashovy.socialnetwork.dao.util.dbutils.fieldsnames.AccountTableFields.ACCOUNT_ID;
import static com.getjavajob.training.timashovy.socialnetwork.dao.util.dbutils.fieldsnames.PasswordTableFields.*;

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

    private JdbcTemplate jdbcTemplate;

    private final RowMapper<Password> passwordRowMapper = (rs, rowNum) -> new Password(rs.getLong(PASSWORD_ACCOUNT_ID),
            rs.getString(PASSWORD_HASH), rs.getString(PASSWORD_SALT));

    public void setDataSource(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public Long create(Password password) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(CREATE, new String[]{PASSWORD_ID});
            setPasswordData(password, ps);
            return ps;
        }, keyHolder);
        Number generatedId = keyHolder.getKey();
        if (generatedId != null) {
            Long id = generatedId.longValue();
            password.setId(id);
            return id;
        }
        return null;
    }

    private void setPasswordData(Password password, PreparedStatement ps) throws SQLException {
        ps.setLong(1, password.getAccountId());
        ps.setString(2, password.getPassword());
        ps.setString(3, password.getSalt());
    }

    @Override
    public Optional<Password> getById(Long accountId) {
        return jdbcTemplate.query(GET_BY_ACCOUNT_ID, passwordRowMapper, accountId).stream().findFirst();
    }

    @Override
    public Optional<Password> findByEmail(String email) {
        return jdbcTemplate.query(GET_BY_ACCOUNT_EMAIL, passwordRowMapper, email).stream().findFirst();
    }

}
