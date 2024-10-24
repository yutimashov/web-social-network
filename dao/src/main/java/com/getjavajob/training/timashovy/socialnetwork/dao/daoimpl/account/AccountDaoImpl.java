package com.getjavajob.training.timashovy.socialnetwork.dao.daoimpl.account;

import com.getjavajob.training.timashovy.socialnetwork.dao.interfaces.BaseDao;
import com.getjavajob.training.timashovy.socialnetwork.dao.interfaces.account.PhoneDao;
import com.getjavajob.training.timashovy.socialnetwork.domain.account.Account;
import com.getjavajob.training.timashovy.socialnetwork.domain.account.Phone;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import javax.sql.DataSource;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import static com.getjavajob.training.timashovy.socialnetwork.dao.util.dbutils.TableNames.ACCOUNTS_TABLE;
import static com.getjavajob.training.timashovy.socialnetwork.dao.util.dbutils.fieldsnames.AccountTableFields.*;
import static com.getjavajob.training.timashovy.socialnetwork.domain.account.AccountRole.REGULAR;
import static com.getjavajob.training.timashovy.socialnetwork.domain.account.AccountRole.valueOf;
import static com.getjavajob.training.timashovy.socialnetwork.domain.account.PhoneType.PERSONAL;
import static com.getjavajob.training.timashovy.socialnetwork.domain.account.PhoneType.WORKING;
import static java.lang.String.valueOf;
import static java.util.Objects.isNull;
import static java.util.Optional.empty;
import static java.util.Optional.of;
import static java.util.stream.Collectors.toList;

/**
 * Singleton class responsible for working with {@link com.getjavajob.training.timashovy.socialnetwork.dao.util.dbutils.TableNames#ACCOUNTS_TABLE accounts table}.
 * It provides functionality for working with data inside above-mentioned table.
 */
public class AccountDaoImpl implements BaseDao<Account> {

    private static final String CREATE = "INSERT INTO " + ACCOUNTS_TABLE + " (" + ACCOUNT_FIRST_NAME + ", "
            + ACCOUNT_LAST_NAME + ", " + ACCOUNT_MIDDLE_NAME + ", " + ACCOUNT_BIRTH_DATE + ", "
            + ACCOUNT_PERSONAL_ADDRESS + ", " + ACCOUNT_WORK_ADDRESS + ", " + ACCOUNT_EMAIL + ", " + ACCOUNT_ICQ + ", "
            + ACCOUNT_SKYPE + ", " + ACCOUNT_ADDITIONAL_INFO + ", " + ACCOUNT_ROLE_TYPE + ", " + ACCOUNT_AVATAR
            + ") VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?);";
    private static final String GET_BY_ID = "SELECT " + ACCOUNT_ID + ", " + ACCOUNT_FIRST_NAME + ", "
            + ACCOUNT_LAST_NAME + ", " + ACCOUNT_MIDDLE_NAME + ", " + ACCOUNT_BIRTH_DATE + ", "
            + ACCOUNT_PERSONAL_ADDRESS + ", " + ACCOUNT_WORK_ADDRESS + ", " + ACCOUNT_EMAIL + ", " + ACCOUNT_ICQ + ", "
            + ACCOUNT_SKYPE + ", " + ACCOUNT_ADDITIONAL_INFO + ", " + ACCOUNT_ROLE_TYPE + ", " + ACCOUNT_AVATAR
            + " FROM " + ACCOUNTS_TABLE + " WHERE " + ACCOUNT_ID + " = ?;";
    private static final String GET_ALL = "SELECT " + ACCOUNT_ID + ", " + ACCOUNT_FIRST_NAME + ", " + ACCOUNT_LAST_NAME
            + ", " + ACCOUNT_MIDDLE_NAME + ", " + ACCOUNT_BIRTH_DATE + ", " + ACCOUNT_PERSONAL_ADDRESS + ", "
            + ACCOUNT_WORK_ADDRESS + ", " + ACCOUNT_EMAIL + ", " + ACCOUNT_ICQ + ", " + ACCOUNT_SKYPE + ", "
            + ACCOUNT_ADDITIONAL_INFO + ", " + ACCOUNT_ROLE_TYPE + ", " + ACCOUNT_AVATAR + " FROM " + ACCOUNTS_TABLE
            + ";";
    private static final String UPDATE_BY_ID = "UPDATE " + ACCOUNTS_TABLE + " SET " + ACCOUNT_FIRST_NAME + " = ?, "
            + ACCOUNT_LAST_NAME + " = ?, " + ACCOUNT_MIDDLE_NAME + " = ?, " + ACCOUNT_BIRTH_DATE + " = ?, "
            + ACCOUNT_PERSONAL_ADDRESS + " = ?, " + ACCOUNT_WORK_ADDRESS + " = ?, " + ACCOUNT_EMAIL + " = ?, "
            + ACCOUNT_ICQ + "= ?, " + ACCOUNT_SKYPE + "= ?, " + ACCOUNT_ADDITIONAL_INFO + " = ?, " + ACCOUNT_ROLE_TYPE
            + " = ?, " + ACCOUNT_AVATAR + " = ? WHERE " + ACCOUNT_ID + " = ?;";
    private static final String DELETE_BY_ID = "DELETE FROM " + ACCOUNTS_TABLE + " WHERE " + ACCOUNT_ID + " = ?;";
    private JdbcTemplate jdbcTemplate;
    private final PhoneDao phoneDao;
    private final RowMapper<Account> accountRowMapper = (rs, rowNum) -> new Account.Builder()
            .id(rs.getLong(ACCOUNT_ID))
            .firstName(rs.getString(ACCOUNT_FIRST_NAME))
            .lastName(rs.getString(ACCOUNT_LAST_NAME))
            .email(rs.getString(ACCOUNT_EMAIL))
            .birthDate(!isNull(rs.getDate(ACCOUNT_BIRTH_DATE)) ? rs.getDate(ACCOUNT_BIRTH_DATE).toLocalDate() : null)
            .middleName(rs.getString(ACCOUNT_MIDDLE_NAME))
            .personalAddress(rs.getString(ACCOUNT_PERSONAL_ADDRESS))
            .workAddress(rs.getString(ACCOUNT_WORK_ADDRESS))
            .icq(rs.getString(ACCOUNT_ICQ))
            .skype(rs.getString(ACCOUNT_SKYPE))
            .additionalInfo(rs.getString(ACCOUNT_ADDITIONAL_INFO))
            .role(valueOf(rs.getString(ACCOUNT_ROLE_TYPE)))
            .avatar(rs.getBinaryStream(ACCOUNT_AVATAR))
            .build();

    public AccountDaoImpl(PhoneDao phoneDao) {
        this.phoneDao = phoneDao;
    }

    /**
     * @param dataSource specific DataSource, defining database to work with
     */
    public void setDataSource(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public Long create(Account account) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(conn -> {
            PreparedStatement ps = conn.prepareStatement(CREATE, new String[]{ACCOUNT_ID});
            setAccountData(account, ps);
            return ps;
        }, keyHolder);
        Number generatedId = keyHolder.getKey();
        if (generatedId != null) {
            Long id = generatedId.longValue();
            account.setId(id);
            return id;
        }
        return null;
    }

    private void setAccountData(Account account, PreparedStatement ps) throws SQLException {
        ps.setString(1, account.getFirstName());
        ps.setString(2, account.getLastName());
        ps.setString(3, account.getMiddleName());
        ps.setObject(4, account.getBirthDate());
        ps.setString(5, account.getPersonalAddress());
        ps.setString(6, account.getWorkAddress());
        ps.setString(7, account.getEmail());
        ps.setString(8, account.getIcq());
        ps.setString(9, account.getSkype());
        ps.setString(10, account.getAdditionalInfo());
        ps.setString(11, isNull(account.getRole()) ? valueOf(REGULAR) : account.getRole().name());
        ps.setBinaryStream(12, account.getAvatar());
    }

    @Override
    public boolean updateById(Long id, Account account) {
        return jdbcTemplate.update(UPDATE_BY_ID, ps -> {
            setAccountData(account, ps);
            ps.setLong(13, id);
        }) > 0;
    }

    @Override
    public boolean deleteById(Long id) {
        return jdbcTemplate.update(DELETE_BY_ID, id) > 0;
    }

    @Override
    public Optional<Account> getById(Long accountId) {
        Account account = jdbcTemplate.query(GET_BY_ID, rs -> rs.next() ? accountRowMapper.mapRow(rs, 1) : null,
                accountId);
        if (!isNull(account)) {
            List<Phone> phones = phoneDao.getAll(accountId);
            if (!phones.isEmpty()) {
                account.setPersonalPhoneNumber(phones.stream().filter(phone -> phone.getPhoneType() == PERSONAL)
                        .collect(toList()));
                account.setWorkPhoneNumber(phones.stream().filter(phone -> phone.getPhoneType() == WORKING)
                        .collect(toList()));
            }
            return of(account);
        } else {
            return empty();
        }
    }

    @Override
    public List<Account> getAll() {
        return jdbcTemplate.query(GET_ALL, accountRowMapper);
    }

}
