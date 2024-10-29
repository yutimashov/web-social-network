package com.getjavajob.training.timashovy.socialnetwork.dao.daoimpl.account;

import com.getjavajob.training.timashovy.socialnetwork.dao.interfaces.BaseDao;
import com.getjavajob.training.timashovy.socialnetwork.dao.interfaces.account.PhoneDao;
import com.getjavajob.training.timashovy.socialnetwork.domain.account.Account;
import com.getjavajob.training.timashovy.socialnetwork.domain.phone.Phone;
import org.springframework.jdbc.core.RowMapper;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Optional;

import static com.getjavajob.training.timashovy.socialnetwork.dao.util.dbutils.TableNames.ACCOUNTS_TABLE;
import static com.getjavajob.training.timashovy.socialnetwork.dao.util.dbutils.fieldsnames.AccountTableFields.*;
import static com.getjavajob.training.timashovy.socialnetwork.domain.account.AccountRole.valueOf;
import static com.getjavajob.training.timashovy.socialnetwork.domain.phone.PhoneType.PERSONAL;
import static com.getjavajob.training.timashovy.socialnetwork.domain.phone.PhoneType.WORKING;
import static java.util.Objects.isNull;
import static java.util.Optional.empty;
import static java.util.Optional.of;
import static java.util.stream.Collectors.toList;

/**
 * Singleton class responsible for working with {@link com.getjavajob.training.timashovy.socialnetwork.dao.util.dbutils.TableNames#ACCOUNTS_TABLE accounts table}.
 * It provides functionality for working with data inside above-mentioned table.
 */
public class AccountDaoImpl implements BaseDao<Account> {

    @PersistenceContext
    protected EntityManager entityManager;

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

    @Override
    public Long create(Account account) {
        entityManager.persist(account);
        entityManager.flush();
        return account.getId();
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
        try {
            entityManager.getTransaction().begin();
            Account account = entityManager.find(Account.class, id);
            if (account != null) {
                entityManager.remove(account);
                entityManager.getTransaction().commit();
                return true;
            } else {
                entityManager.getTransaction().rollback();
                return false;
            }
        } catch (IllegalArgumentException e) {
            if (entityManager.getTransaction().isActive()) {
                entityManager.getTransaction().rollback();
            }
            return false;
        }
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
