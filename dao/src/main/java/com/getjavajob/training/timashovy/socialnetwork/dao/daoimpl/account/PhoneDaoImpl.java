package com.getjavajob.training.timashovy.socialnetwork.dao.daoimpl.account;

import com.getjavajob.training.timashovy.socialnetwork.common.account.Phone;
import com.getjavajob.training.timashovy.socialnetwork.common.account.PhoneType;
import com.getjavajob.training.timashovy.socialnetwork.dao.interfaces.account.PhoneDao;
import com.getjavajob.training.timashovy.socialnetwork.dao.util.exceptions.DaoException;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static com.getjavajob.training.timashovy.socialnetwork.dao.util.dbconnection.ConnectionManager.getPreparedStatement;
import static com.getjavajob.training.timashovy.socialnetwork.dao.util.dbconnection.ConnectionManager.getPreparedStatementWithGeneratedKeys;

public class PhoneDaoImpl implements PhoneDao {

    private static final String CREATE_PHONE = "INSERT INTO account_data.account_phones (account_id, phone_type,"
            + " phone_number) VALUES (?, ?, ?);";
    private static final String GET_PHONE = "SELECT phone_type, phone_number FROM account_data.account_phones WHERE" +
            " account_id = ?;";
    private static final PhoneDaoImpl PHONE_DAO_IMPL = new PhoneDaoImpl();

    private PhoneDaoImpl() {
    }

    public static PhoneDaoImpl getInstance() {
        return PHONE_DAO_IMPL;
    }

    @Override
    public Long create(Phone phone) {
        try (PreparedStatement createPhoneStatement = getPreparedStatementWithGeneratedKeys(CREATE_PHONE)) {
            setPhoneData(phone, createPhoneStatement);
            if (createPhoneStatement.executeUpdate() > 0) {
                ResultSet generatedKeys = createPhoneStatement.getGeneratedKeys();
                if (generatedKeys.next()) {
                    phone.setId(generatedKeys.getLong(1));
                }
                return phone.getId();
            } else {
                throw new DaoException("Failed to create phone");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void setPhoneData(Phone phone, PreparedStatement preparedStatement) throws SQLException {
        preparedStatement.setLong(1, phone.getAccountId());
        preparedStatement.setObject(2, phone.getPhoneType().name());
        preparedStatement.setString(3, phone.getNumber());
    }

    @Override
    public List<Phone> getAll(Long accountId) {
        List<Phone> phones = new ArrayList<>();
        try (PreparedStatement getPhoneStatement = getPreparedStatement(GET_PHONE)) {
            getPhoneStatement.setLong(1, accountId);
            ResultSet phonesData = getPhoneStatement.executeQuery();
            while (phonesData.next()) {
                phones.add(
                        new Phone(
                                PhoneType.valueOf(phonesData.getString("phone_type")),
                                phonesData.getString("phone_number")
                        )
                );
            }
        } catch (SQLException e) {
            throw new DaoException("dao: get phone failed: " + e.getMessage());
        }
        return phones;
    }

    @Override
    public boolean delete() {
        return false;
    }

    @Override
    public boolean update() {
        return false;
    }

}
