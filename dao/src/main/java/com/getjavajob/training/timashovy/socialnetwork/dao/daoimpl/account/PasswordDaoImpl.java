package com.getjavajob.training.timashovy.socialnetwork.dao.daoimpl.account;

import com.getjavajob.training.timashovy.socialnetwork.common.account.Password;
import com.getjavajob.training.timashovy.socialnetwork.dao.interfaces.account.PasswordDao;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import javax.sql.DataSource;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Optional;

import static com.getjavajob.training.timashovy.socialnetwork.dao.util.dbutils.TableNames.ACCOUNTS_TABLE;
import static com.getjavajob.training.timashovy.socialnetwork.dao.util.dbutils.TableNames.ACCOUNT_PASSWORDS_TABLE;
import static com.getjavajob.training.timashovy.socialnetwork.dao.util.dbutils.fieldsnames.AccountTableFields.ACCOUNT_EMAIL;
import static com.getjavajob.training.timashovy.socialnetwork.dao.util.dbutils.fieldsnames.AccountTableFields.ACCOUNT_ID;
import static com.getjavajob.training.timashovy.socialnetwork.dao.util.dbutils.fieldsnames.PasswordTableFields.*;
import static java.sql.Statement.RETURN_GENERATED_KEYS;
import static java.util.Objects.isNull;
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

    private JdbcTemplate jdbcTemplate;

    public void setDataSource(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public Long create(Password password) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(CREATE, RETURN_GENERATED_KEYS);
            setPasswordData(password, ps);
            return ps;
        }, keyHolder);
        if (!isNull(keyHolder.getKey())) {
            password.setId((long) keyHolder.getKey());
        }
        return password.getId();
    }

    private void setPasswordData(Password password, PreparedStatement ps) throws SQLException {
        ps.setLong(1, password.getAccountId());
        ps.setString(2, password.getPassword());
        ps.setString(3, password.getSalt());
    }

    @Override
    public Optional<Password> getById(Long accountId) {
        Password password = jdbcTemplate.queryForObject(GET_BY_ACCOUNT_ID, new PasswordDaoImpl.PasswordRowMapper(), accountId);
        if (!isNull(password)) {
            return of(password);
        } else {
            return empty();
        }
    }

    private static class PasswordRowMapper implements RowMapper<Password> {
        @Override
        public Password mapRow(ResultSet rs, int rowNum) throws SQLException {
            return createPasswordFromResultSet(rs);
        }
    }

    private static Password createPasswordFromResultSet(ResultSet rs) throws SQLException {
        return new Password(rs.getLong(PASSWORD_ACCOUNT_ID), rs.getString(PASSWORD_HASH), rs.getString(PASSWORD_SALT));
    }

    @Override
    public Optional<Password> findByEmail(String email) {
        Password password = jdbcTemplate.queryForObject(GET_BY_ACCOUNT_EMAIL, new PasswordDaoImpl.PasswordRowMapper(), email);
        if (!isNull(password)) {
            return of(password);
        } else {
            return empty();
        }
    }

}
