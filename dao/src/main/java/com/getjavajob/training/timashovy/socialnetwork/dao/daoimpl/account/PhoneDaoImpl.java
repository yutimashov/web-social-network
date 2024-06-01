package com.getjavajob.training.timashovy.socialnetwork.dao.daoimpl.account;

import com.getjavajob.training.timashovy.socialnetwork.common.account.Phone;
import com.getjavajob.training.timashovy.socialnetwork.common.account.PhoneType;
import com.getjavajob.training.timashovy.socialnetwork.dao.interfaces.account.PhoneDao;
import com.getjavajob.training.timashovy.socialnetwork.dao.util.DaoException;
import com.getjavajob.training.timashovy.socialnetwork.dao.util.dbutils.dbconnection.ConnectionWrapper;
import com.getjavajob.training.timashovy.socialnetwork.dao.util.dbutils.dbconnection.TransactionManager;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static com.getjavajob.training.timashovy.socialnetwork.dao.util.dbutils.TableNames.ACCOUNT_PHONES_TABLE;
import static com.getjavajob.training.timashovy.socialnetwork.dao.util.dbutils.dbconnection.ConnectionManager.getPreparedStatement;
import static com.getjavajob.training.timashovy.socialnetwork.dao.util.dbutils.fieldsnames.PhonesTableFields.*;

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
    private final TransactionManager transactionManager;
    private static volatile PhoneDao instance;

    private PhoneDaoImpl(TransactionManager transactionManager) {
        this.transactionManager = transactionManager;
    }

    public static PhoneDao getInstance(TransactionManager transactionManager) {
        if (instance == null) {
            synchronized (PhoneDaoImpl.class) {
                if (instance == null) {
                    instance = new PhoneDaoImpl(transactionManager);
                }
            }
        }
        return instance;
    }

    @Override
    public Long create(Phone phone) {
        try (PreparedStatement createPhoneStatement
                     = transactionManager.getGetTransactionalPreparedStatementWithGeneratedKeys(CREATE)) {
            createPhoneStatement.setLong(1, phone.getAccountId());
            createPhoneStatement.setString(2, phone.getPhoneType().name());
            createPhoneStatement.setString(3, phone.getNumber());
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

    @Override
    public List<Phone> getAll(Long accountId) {
        try (PreparedStatement getPhoneStatement = getPreparedStatement(GET)) {
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
