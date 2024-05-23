package com.getjavajob.training.timashovy.socialnetwork.dao.daoimpl.account;

import com.getjavajob.training.timashovy.socialnetwork.common.account.Phone;
import com.getjavajob.training.timashovy.socialnetwork.common.account.PhoneType;
import com.getjavajob.training.timashovy.socialnetwork.dao.interfaces.account.PhoneDao;
import com.getjavajob.training.timashovy.socialnetwork.dao.util.DaoException;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static com.getjavajob.training.timashovy.socialnetwork.dao.util.dbutils.TableNames.ACCOUNT_PHONES_TABLE;
import static com.getjavajob.training.timashovy.socialnetwork.dao.util.dbutils.dbconnection.ConnectionManager.getPreparedStatement;
import static com.getjavajob.training.timashovy.socialnetwork.dao.util.dbutils.dbconnection.ConnectionManager.getPreparedStatementWithGeneratedKeys;

public class PhoneDaoImpl implements PhoneDao {

    private static final String CREATE_PHONE = "INSERT INTO " + ACCOUNT_PHONES_TABLE + " (account_id, phone_type, "
            + "phone_number) VALUES (?, ?, ?);";
    private static final String GET_PHONE = "SELECT id, phone_type, phone_number, account_id FROM "
            + ACCOUNT_PHONES_TABLE + " WHERE account_id = ?;";
    private static final String UPDATE = "UPDATE " + ACCOUNT_PHONES_TABLE + " SET phone_number = ? WHERE id = ?;";
    private static final PhoneDaoImpl PHONE_DAO_IMPL = new PhoneDaoImpl();

    private PhoneDaoImpl() {
    }

    public static PhoneDao createInstance() {
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
            throw new DaoException("dao: create phone failed: " + e.getMessage());
        }
    }

    private void setPhoneData(Phone phone, PreparedStatement preparedStatement) throws SQLException {
        preparedStatement.setLong(1, phone.getAccountId());
        preparedStatement.setString(2, phone.getPhoneType().name());
        preparedStatement.setString(3, phone.getNumber());
    }

    @Override
    public List<Phone> getAll(Long accountId) {
        try (PreparedStatement getPhoneStatement = getPreparedStatement(GET_PHONE)) {
            List<Phone> phones = new ArrayList<>();
            getPhoneStatement.setLong(1, accountId);
            ResultSet phonesData = getPhoneStatement.executeQuery();
            while (phonesData.next()) {
                phones.add(
                        new Phone(
                                phonesData.getLong("id"),
                                PhoneType.valueOf(phonesData.getString("phone_type")),
                                phonesData.getString("phone_number"),
                                phonesData.getLong("account_id")
                        )
                );
            }
            return phones;
        } catch (SQLException e) {
            throw new DaoException("dao: method get all phones failed: " + e.getMessage());
        }
    }

    @Override
    public boolean update(Long phoneId, String newPhoneNumber) {
        try (PreparedStatement updateByIdStatement = getPreparedStatement(UPDATE)) {
            updateByIdStatement.setString(1, newPhoneNumber);
            updateByIdStatement.setLong(2, phoneId);
            return updateByIdStatement.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new DaoException("dao: update phone number by id method failed: ", e);
        }
    }

}
