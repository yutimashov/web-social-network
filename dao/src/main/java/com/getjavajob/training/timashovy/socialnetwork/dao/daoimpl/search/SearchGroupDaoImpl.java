package com.getjavajob.training.timashovy.socialnetwork.dao.daoimpl.search;

import com.getjavajob.training.timashovy.socialnetwork.common.Group;
import com.getjavajob.training.timashovy.socialnetwork.dao.interfaces.SearchDao;
import com.getjavajob.training.timashovy.socialnetwork.dao.util.DaoException;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static com.getjavajob.training.timashovy.socialnetwork.dao.util.dbutils.TableNames.GROUP_TABLE;
import static com.getjavajob.training.timashovy.socialnetwork.dao.util.dbutils.dbconnection.ConnectionManager.getPreparedStatement;

public class SearchGroupDaoImpl implements SearchDao<Group> {

    private static final SearchGroupDaoImpl SEARCH_GROUP_DAO = new SearchGroupDaoImpl();
    private static final String FIND_GROUPS = "SELECT * FROM " + GROUP_TABLE + " WHERE group_name ILIKE ? OFFSET ? "
            + "LIMIT ?;";
    private static final String FIND_GROUPS_AMOUNT = "SELECT COUNT(*) AS total FROM " + GROUP_TABLE
            + " WHERE group_name ILIKE ?;";

    private SearchGroupDaoImpl() {
    }

    public static SearchGroupDaoImpl createInstance() {
        return SEARCH_GROUP_DAO;
    }

    @Override
    public List<Group> searchAccounts(String searchQuery, int currentPage, int recordsPerPage) {
        try (PreparedStatement preparedStatement = getPreparedStatement(FIND_GROUPS)) {
            List<Group> groups = new ArrayList<>();
            preparedStatement.setString(1, "%" + searchQuery + "%");
            preparedStatement.setInt(2, currentPage * recordsPerPage - recordsPerPage);
            preparedStatement.setInt(3, recordsPerPage);
            ResultSet groupRequestsResult = preparedStatement.executeQuery();
            while (groupRequestsResult.next()) {
                groups.add(new Group(
                        groupRequestsResult.getLong("id"),
                        groupRequestsResult.getString("group_name"),
                        groupRequestsResult.getString("description"),
                        groupRequestsResult.getLong("owner_id")
                ));
            }
            return groups;
        } catch (SQLException e) {
            throw new DaoException("dao: search method failed: " + e.getMessage());
        }
    }

    @Override
    public int findResultsAmount(String searchQuery) {
        try (PreparedStatement preparedStatement = getPreparedStatement(FIND_GROUPS_AMOUNT)) {
            preparedStatement.setString(1, "%" + searchQuery + "%");
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
