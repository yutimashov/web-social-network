package com.getjavajob.training.timashovy.socialnetwork.dao.daoimpl.account;

import com.getjavajob.training.timashovy.socialnetwork.common.account.Phone;
import com.getjavajob.training.timashovy.socialnetwork.common.account.PhoneType;
import com.getjavajob.training.timashovy.socialnetwork.dao.interfaces.account.PhoneDao;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import static com.getjavajob.training.timashovy.socialnetwork.dao.util.dbutils.TableNames.ACCOUNT_PHONES_TABLE;
import static com.getjavajob.training.timashovy.socialnetwork.dao.util.dbutils.fieldsnames.PhonesTableFields.*;
import static java.util.Objects.isNull;

/**
 * Singleton class responsible for working with `account_data.phones` table in DB.
 * It provides safe multithreading approach for creating singleton object using synchronization mechanism.
 */
public class PhoneDaoImpl implements PhoneDao {

    private static final String CREATE = "INSERT INTO " + ACCOUNT_PHONES_TABLE + " (" + ACCOUNT_ID + ", " + PHONE_TYPE
            + ", " + PHONE_NUMBER + ") VALUES (?, ?, ?);";
    private static final String GET = "SELECT " + PHONE_ID + ", " + PHONE_TYPE + ", " + PHONE_NUMBER + ", "
            + ACCOUNT_ID + " FROM " + ACCOUNT_PHONES_TABLE + " WHERE " + ACCOUNT_ID + " = ?;";
    private static final String UPDATE = "UPDATE " + ACCOUNT_PHONES_TABLE + " SET " + PHONE_NUMBER + " = ? WHERE "
            + PHONE_ID + " = ?;";
    private final JdbcTemplate jdbcTemplate;

    public PhoneDaoImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Long create(Phone phone) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(CREATE, new String[]{PHONE_ID});
            ps.setLong(1, phone.getAccountId());
            ps.setString(2, phone.getPhoneType().name());
            ps.setString(3, phone.getNumber());
            return ps;
        }, keyHolder);
        if (!isNull(keyHolder.getKey())) {
            phone.setId(keyHolder.getKey().longValue());
        }
        return phone.getId();
    }

    @Override
    public List<Phone> getAll(Long accountId) {
        return jdbcTemplate.query(GET, new Object[]{accountId}, new PhoneRowMapper());
    }

    private static class PhoneRowMapper implements RowMapper<Phone> {
        @Override
        public Phone mapRow(ResultSet rs, int rowNum) throws SQLException {
            return new Phone(
                    rs.getLong("id"),
                    PhoneType.valueOf(rs.getString("phone_type")),
                    rs.getString("phone_number"),
                    rs.getLong("account_id")
            );
        }
    }

    @Override
    public boolean update(Long phoneId, String newPhoneNumber) {
        return jdbcTemplate.update(UPDATE, ps -> {
            ps.setString(1, newPhoneNumber);
            ps.setLong(2, phoneId);
        }) > 0;
    }

}
