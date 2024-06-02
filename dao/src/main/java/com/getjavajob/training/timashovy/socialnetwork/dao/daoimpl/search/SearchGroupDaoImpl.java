package com.getjavajob.training.timashovy.socialnetwork.dao.daoimpl.search;

import com.getjavajob.training.timashovy.socialnetwork.common.Group;
import com.getjavajob.training.timashovy.socialnetwork.dao.interfaces.SearchDao;
import com.getjavajob.training.timashovy.socialnetwork.dao.util.DaoException;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static com.getjavajob.training.timashovy.socialnetwork.dao.util.dbutils.TableNames.GROUPS_TABLE;
import static com.getjavajob.training.timashovy.socialnetwork.dao.util.dbutils.dbconnection.ConnectionManager.getPreparedStatement;
import static com.getjavajob.training.timashovy.socialnetwork.dao.util.dbutils.fieldsnames.GroupTableFields.*;

/**
 * Singleton class responsible for working with {@link com.getjavajob.training.timashovy.socialnetwork.dao.util.dbutils.TableNames#GROUPS_TABLE groups table}.
 * It provides safe multithreading approach for creating singleton object using synchronization mechanism.
 */
public class SearchGroupDaoImpl implements SearchDao<Group> {

    private static final String FIND_GROUPS = "SELECT * FROM " + GROUPS_TABLE + " WHERE " + GROUP_NAME
            + " ILIKE ? OFFSET ? LIMIT ?;";
    private static final String FIND_GROUPS_AMOUNT = "SELECT COUNT(*) AS " + TOTAL_GROUP_AMOUNT_ALIAS + " FROM "
            + GROUPS_TABLE + " WHERE " + GROUP_NAME + " ILIKE ?;";

    @Override
    public List<Group> searchAccounts(String searchQuery, int currentPage, int recordsPerPage) {
        try (PreparedStatement preparedStatement = getPreparedStatement(FIND_GROUPS)) {
            List<Group> groups = new ArrayList<>();
            preparedStatement.setString(1, "%" + searchQuery + "%");
            preparedStatement.setInt(2, currentPage * recordsPerPage - recordsPerPage);
            preparedStatement.setInt(3, recordsPerPage);
            ResultSet groupRequestsResult = preparedStatement.executeQuery();
            while (groupRequestsResult.next()) {
                groups.add(new Group.Builder().id(groupRequestsResult.getLong(GROUP_ID))
                        .groupName(groupRequestsResult.getString(GROUP_NAME))
                        .description(groupRequestsResult.getString(GROUP_DESCRIPTION))
                        .accountOwnerId(groupRequestsResult.getLong(GROUP_OWNER_ID))
                        .avatar(groupRequestsResult.getBinaryStream(GROUP_AVATAR)).build());
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
                return accountsAmount.getInt(TOTAL_GROUP_AMOUNT_ALIAS);
            }
            return -1;
        } catch (SQLException e) {
            throw new DaoException("dao: search method failed: " + e.getMessage());
        }
    }

}
