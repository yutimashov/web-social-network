package com.getjavajob.training.timashovy.socialnetwork.dao.daoimpl.search;

import com.getjavajob.training.timashovy.socialnetwork.common.account.Account;
import com.getjavajob.training.timashovy.socialnetwork.dao.interfaces.SearchDao;
import com.getjavajob.training.timashovy.socialnetwork.dao.util.DaoException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static com.getjavajob.training.timashovy.socialnetwork.dao.util.dbutils.TableNames.ACCOUNTS_TABLE;
import static com.getjavajob.training.timashovy.socialnetwork.dao.util.dbutils.dbconnection.ConnectionManager.getConnection;
import static com.getjavajob.training.timashovy.socialnetwork.dao.util.dbutils.fieldsnames.AccountTableFields.*;

/**
 * Singleton class responsible for working with {@link com.getjavajob.training.timashovy.socialnetwork.dao.util.dbutils.TableNames#ACCOUNTS_TABLE accounts table}.
 * It provides safe multithreading approach for creating singleton object using synchronization mechanism.
 */
public class SearchAccountDaoImpl implements SearchDao<Account> {

    private static final String FIND_ACCOUNTS = "SELECT " + ACCOUNT_ID + ", " + ACCOUNT_FIRST_NAME + ", "
            + ACCOUNT_LAST_NAME + " FROM " + ACCOUNTS_TABLE + " WHERE " + ACCOUNT_FIRST_NAME + " ILIKE ? OR "
            + ACCOUNT_LAST_NAME + " ILIKE ? OFFSET ? LIMIT ?;";
    private static final String FIND_ACCOUNTS_AMOUNT = "SELECT COUNT(*) AS " + TOTAL_ACCOUNTS_AMOUNT_ALIAS + " FROM "
            + ACCOUNTS_TABLE + " WHERE " + ACCOUNT_FIRST_NAME + " ILIKE ? OR " + ACCOUNT_LAST_NAME + " ILIKE ?;";

    @Override
    public List<Account> searchAccounts(String searchQuery, int currentPage, int recordsPerPage) {
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(FIND_ACCOUNTS)) {
            preparedStatement.setString(1, "%" + searchQuery + "%");
            preparedStatement.setString(2, "%" + searchQuery + "%");
            preparedStatement.setInt(3, currentPage * recordsPerPage - recordsPerPage);
            preparedStatement.setInt(4, recordsPerPage);
            ResultSet friendRequestsResult = preparedStatement.executeQuery();
            List<Account> accounts = new ArrayList<>();
            while (friendRequestsResult.next()) {
                accounts.add(new Account.Builder()
                        .id(friendRequestsResult.getLong(ACCOUNT_ID))
                        .firstName(friendRequestsResult.getString(ACCOUNT_FIRST_NAME))
                        .lastName(friendRequestsResult.getString(ACCOUNT_LAST_NAME))
                        .build());
            }
            return accounts;
        } catch (SQLException e) {
            throw new DaoException("dao: search method failed: " + e.getMessage());
        }
    }

    public int findResultsAmount(String searchQuery) {
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(FIND_ACCOUNTS_AMOUNT)) {
            preparedStatement.setString(1, "%" + searchQuery + "%");
            preparedStatement.setString(2, "%" + searchQuery + "%");
            ResultSet accountsAmount = preparedStatement.executeQuery();
            if (accountsAmount.next()) {
                return accountsAmount.getInt(TOTAL_ACCOUNTS_AMOUNT_ALIAS);
            }
            return -1;
        } catch (SQLException e) {
            throw new DaoException("dao: search method failed: " + e.getMessage());
        }
    }

}
