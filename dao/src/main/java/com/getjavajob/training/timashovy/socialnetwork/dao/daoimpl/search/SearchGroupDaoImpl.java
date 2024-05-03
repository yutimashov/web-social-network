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
    private static final String FIND_GROUPS = "SELECT * FROM " + GROUP_TABLE + " WHERE group_name ILIKE ? LIMIT ? "
            + "OFFSET ?;";

    private SearchGroupDaoImpl() {
    }

    public static SearchGroupDaoImpl getInstance() {
        return SEARCH_GROUP_DAO;
    }

    @Override
    public List<Group> searchAccounts(String searchQuery, int offset, int resultsPerPage) {
        try (PreparedStatement preparedStatement = getPreparedStatement(FIND_GROUPS)) {
            List<Group> groups = new ArrayList<>();
            preparedStatement.setString(1, "%" + searchQuery + "%");
            preparedStatement.setInt(2, resultsPerPage);
            preparedStatement.setInt(3, offset);
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

}
