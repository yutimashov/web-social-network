package com.getjavajob.training.timashovy.socialnetwork.dao.daoimpl.group;

import com.getjavajob.training.timashovy.socialnetwork.common.Group;
import com.getjavajob.training.timashovy.socialnetwork.dao.interfaces.BaseDao;
import com.getjavajob.training.timashovy.socialnetwork.dao.util.exceptions.DaoException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.getjavajob.training.timashovy.socialnetwork.dao.util.dbutils.TableNames.GROUPS_TABLE;
import static com.getjavajob.training.timashovy.socialnetwork.dao.util.dbutils.dbconnection.JdbcTemplateManager.getConnection;
import static com.getjavajob.training.timashovy.socialnetwork.dao.util.dbutils.fieldsnames.GroupTableFields.*;
import static java.util.Optional.empty;
import static java.util.Optional.of;

public class GroupDaoImpl implements BaseDao<Group> {

    private static final String SAVE_GROUP = "INSERT INTO " + GROUPS_TABLE + " (" + GROUP_NAME + ", "
            + GROUP_DESCRIPTION + ", " + GROUP_OWNER_ID + ", " + GROUP_AVATAR + ") VALUES(?, ?, ?, ?)";
    private static final String GET_GROUP_BY_ID = "SELECT " + GROUP_ID + ", " + GROUP_NAME + ", " + GROUP_DESCRIPTION
            + ", " + GROUP_OWNER_ID + ", " + GROUP_AVATAR + " FROM " + GROUPS_TABLE + " WHERE " + GROUP_ID + " = ?";
    private static final String GET_ALL_GROUPS = "SELECT " + GROUP_ID + ", " + GROUP_NAME + ", " + GROUP_DESCRIPTION
            + ", " + GROUP_OWNER_ID + ", " + GROUP_AVATAR + " FROM " + GROUPS_TABLE + ";";
    private static final String UPDATE_GROUP_BY_ID = "UPDATE " + GROUPS_TABLE + " SET " + GROUP_NAME + " = ?, "
            + GROUP_DESCRIPTION + " = ?, " + GROUP_OWNER_ID + " = ?, " + GROUP_AVATAR + " = ? WHERE " + GROUP_ID
            + " = ?";
    private static final String DELETE_GROUP_BY_ID = "DELETE FROM " + GROUPS_TABLE + " WHERE " + GROUP_ID + " = ?";

    @Override
    public Long create(Group group) {
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SAVE_GROUP)) {
            setGroupData(group, preparedStatement);
            if (preparedStatement.executeUpdate() > 0) {
                ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
                if (generatedKeys.next()) {
                    group.setId(generatedKeys.getLong(1));
                }
                return group.getId();
            } else {
                throw new DaoException("dao: create group method failed: no rows affected.");
            }
        } catch (SQLException e) {
            throw new DaoException("dao: create group method failed: " + e.getMessage());
        }
    }

    private void setGroupData(Group group, PreparedStatement preparedStatement) throws SQLException {
        preparedStatement.setString(1, group.getGroupName());
        preparedStatement.setString(2, group.getDescription());
        preparedStatement.setLong(3, group.getAccountOwnerId());
        preparedStatement.setBinaryStream(4, group.getAvatar());
    }

    @Override
    public Optional<Group> getById(Long id) {
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(GET_GROUP_BY_ID)) {
            preparedStatement.setLong(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return of(new Group.Builder()
                        .id(resultSet.getLong("id"))
                        .groupName(resultSet.getString("group_name"))
                        .description(resultSet.getString("description"))
                        .accountOwnerId(resultSet.getLong("owner_id"))
                        .avatar(resultSet.getBinaryStream("avatar"))
                        .build());
            } else {
                return empty();
            }
        } catch (SQLException e) {
            throw new DaoException("dao: get group by id method failed: " + e.getMessage());
        }
    }

    @Override
    public List<Group> getAll() {
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(GET_ALL_GROUPS)) {
            List<Group> receivedGroups = new ArrayList<>();
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                receivedGroups.add(new Group.Builder()
                        .id(resultSet.getLong("id"))
                        .groupName(resultSet.getString("group_name"))
                        .description(resultSet.getString("description"))
                        .accountOwnerId(resultSet.getLong("owner_id"))
                        .avatar(resultSet.getBinaryStream("avatar"))
                        .build());
            }
            return receivedGroups;
        } catch (SQLException e) {
            throw new DaoException("dao: get all groups method failed: " + e.getMessage());
        }
    }

    @Override
    public boolean updateById(Long id, Group group) {
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_GROUP_BY_ID)) {
            setGroupData(group, preparedStatement);
            preparedStatement.setLong(5, id);
            return preparedStatement.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new DaoException("dao: update group by id method failed: " + e.getMessage());
        }
    }

    @Override
    public boolean deleteById(Long id) {
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(DELETE_GROUP_BY_ID)) {
            preparedStatement.setLong(1, id);
            return preparedStatement.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new DaoException("dao: delete group by id method failed: ", e);
        }
    }

}
