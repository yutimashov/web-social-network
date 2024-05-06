package com.getjavajob.training.timashovy.socialnetwork.dao.daoimpl.search;

import com.getjavajob.training.timashovy.socialnetwork.common.account.Account;
import com.getjavajob.training.timashovy.socialnetwork.dao.interfaces.SearchDao;
import com.getjavajob.training.timashovy.socialnetwork.dao.util.DaoException;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static com.getjavajob.training.timashovy.socialnetwork.dao.util.dbutils.TableNames.ACCOUNT_TABLE;
import static com.getjavajob.training.timashovy.socialnetwork.dao.util.dbutils.dbconnection.ConnectionManager.getPreparedStatement;

public class SearchAccountDaoImpl implements SearchDao<Account> {

    private static final SearchAccountDaoImpl SEARCH_ACCOUNT_DAO = new SearchAccountDaoImpl();
    private static final String FIND_ACCOUNTS = "SELECT id, first_name, last_name FROM " + ACCOUNT_TABLE
            + " WHERE first_name ILIKE ? OR last_name ILIKE ? OFFSET ? LIMIT ?;";
    private static final String FIND_ACCOUNTS_AMOUNT = "SELECT COUNT(*) AS total FROM " + ACCOUNT_TABLE
            + " WHERE first_name ILIKE ? OR last_name ILIKE ?;";

    private SearchAccountDaoImpl() {
    }

    public static SearchAccountDaoImpl getInstance() {
        return SEARCH_ACCOUNT_DAO;
    }

    @Override
    public List<Account> searchAccounts(String searchQuery, int currentPage, int recordsPerPage) {
        try (PreparedStatement preparedStatement = getPreparedStatement(FIND_ACCOUNTS)) {
            preparedStatement.setString(1, "%" + searchQuery + "%");
            preparedStatement.setString(2, "%" + searchQuery + "%");
            preparedStatement.setInt(3, currentPage * recordsPerPage - recordsPerPage);
            preparedStatement.setInt(4, recordsPerPage);
            ResultSet friendRequestsResult = preparedStatement.executeQuery();
            List<Account> accounts = new ArrayList<>();
            while (friendRequestsResult.next()) {
                accounts.add(new Account.Builder()
                        .id(friendRequestsResult.getLong("id"))
                        .firstName(friendRequestsResult.getString("first_name"))
                        .lastName(friendRequestsResult.getString("last_name"))
                        .build());
            }
            return accounts;
        } catch (SQLException e) {
            throw new DaoException("dao: search method failed: " + e.getMessage());
        }
    }

    public int findResultsAmount(String searchQuery) {
        try (PreparedStatement preparedStatement = getPreparedStatement(FIND_ACCOUNTS_AMOUNT)) {
            preparedStatement.setString(1, "%" + searchQuery + "%");
            preparedStatement.setString(2, "%" + searchQuery + "%");
            ResultSet accountsAmount = preparedStatement.executeQuery();
            if (accountsAmount.next()) {
                return accountsAmount.getInt("total");
            }
            return -1;
        } catch (SQLException e) {
            throw new DaoException("dao: search method failed: " + e.getMessage());
        }
    }

}
