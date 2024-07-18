package com.getjavajob.training.timashovy.socialnetwork.dao.daoimpl.account;

import com.getjavajob.training.timashovy.socialnetwork.common.account.Phone;
import com.getjavajob.training.timashovy.socialnetwork.dao.interfaces.account.PhoneDao;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import javax.sql.DataSource;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import static com.getjavajob.training.timashovy.socialnetwork.common.account.PhoneType.valueOf;
import static com.getjavajob.training.timashovy.socialnetwork.dao.util.dbutils.TableNames.ACCOUNT_PHONES_TABLE;
import static com.getjavajob.training.timashovy.socialnetwork.dao.util.dbutils.fieldsnames.PhonesTableFields.*;

/**
 * Singleton class responsible for working with `account_data.phones` table in DB.
 * It provides safe multithreading approach for creating singleton object using synchronization mechanism.
 */
public class PhoneDaoImpl implements PhoneDao {

    private static final String CREATE = "INSERT INTO " + ACCOUNT_PHONES_TABLE + " (" + ACCOUNT_ID + ", " + PHONE_TYPE
            + ", " + PHONE_NUMBER + ") VALUES (?, ?, ?);";
    private static final String GET_BY_ACCOUNT_ID = "SELECT " + PHONE_ID + ", " + PHONE_TYPE + ", " + PHONE_NUMBER
            + ", " + ACCOUNT_ID + " FROM " + ACCOUNT_PHONES_TABLE + " WHERE " + ACCOUNT_ID + " = ?;";
    private static final String UPDATE = "UPDATE " + ACCOUNT_PHONES_TABLE + " SET " + PHONE_NUMBER + " = ? WHERE "
            + PHONE_ID + " = ?;";
    private JdbcTemplate jdbcTemplate;

    public void setDataSource(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public Long create(Phone phone) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(CREATE, new String[]{PHONE_ID});
            setPhoneData(phone, ps);
            return ps;
        }, keyHolder);
        Number generatedId = keyHolder.getKey();
        if (generatedId != null) {
            Long id = generatedId.longValue();
            phone.setId(id);
            return id;
        }
        return null;
    }

    private void setPhoneData(Phone phone, PreparedStatement ps) throws SQLException {
        ps.setLong(1, phone.getAccountId());
        ps.setString(2, phone.getPhoneType().name());
        ps.setString(3, phone.getNumber());
    }

    @Override
    public List<Phone> getAll(Long accountId) {
        return jdbcTemplate.query(GET_BY_ACCOUNT_ID, (rs, rowNum) -> new Phone(
                rs.getLong(PHONE_ID),
                valueOf(rs.getString(PHONE_TYPE)),
                rs.getString(PHONE_NUMBER),
                rs.getLong(ACCOUNT_ID)
        ), accountId);
    }

    @Override
    public boolean update(Long phoneId, String newPhoneNumber) {
        return jdbcTemplate.update(UPDATE, ps -> {
            ps.setString(1, newPhoneNumber);
            ps.setLong(2, phoneId);
        }) > 0;
    }

}
