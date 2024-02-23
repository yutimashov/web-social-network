package com.getjavajob.training.timashovy.socialnetwork.dao.daoimpl;

import com.getjavajob.training.timashovy.socialnetwork.common.Group;
import com.getjavajob.training.timashovy.socialnetwork.dao.interfaces.TableConstraintsValidator;
import com.getjavajob.training.timashovy.socialnetwork.dao.interfaces.AccountGroupDao;
import com.getjavajob.training.timashovy.socialnetwork.dao.util.dbconnection.ConnectionManager;
import com.getjavajob.training.timashovy.socialnetwork.dao.util.exceptions.DaoException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static java.sql.Statement.RETURN_GENERATED_KEYS;
import static java.util.Objects.isNull;

public class GroupDaoImpl implements AccountGroupDao<Group>, TableConstraintsValidator {

    private static final GroupDaoImpl GROUP_DAO_INSTANCE = new GroupDaoImpl();
    private static final String SAVE_GROUP = "INSERT INTO group_data.\"group\" (group_name, description, owner_id,"
            + " group_status) VALUES(?, ?, ?, ?)";
    private static final String GET_GROUP_BY_ID = "SELECT id, group_name, description, owner_id,"
            + " group_status FROM group_data.\"group\" WHERE id = ?";
    private static final String GET_ALL_GROUPS = "SELECT id, group_name, description, owner_id,"
            + " group_status FROM group_data.\"group\"";
    private static final String UPDATE_GROUP_BY_ID = "UPDATE group_data.\"group\" SET group_name = ?, description = ?,"
            + " owner_id = ?, group_status = ? WHERE id = ?";
    private static final String DELETE_GROUP_BY_ID = "DELETE FROM group_data.\"group\" WHERE id = ?";

    private GroupDaoImpl() {
    }

    public static GroupDaoImpl getGroupDaoInstance() {
        return GROUP_DAO_INSTANCE;
    }

    @Override
    public Long create(Group group) {
        try (Connection connection = ConnectionManager.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SAVE_GROUP, RETURN_GENERATED_KEYS)) {
            setGroupData(group, preparedStatement);
            preparedStatement.executeUpdate();
            ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
            if (generatedKeys.next()) {
                group.setId(generatedKeys.getLong(1));
            }
            return group.getId();
        } catch (SQLException e) {
            throw new DaoException("dao: create group method failed: " + e.getMessage());
        }
    }

    @Override
    public Group getById(Long id) {
        try (Connection connection = ConnectionManager.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(GET_GROUP_BY_ID)) {
            preparedStatement.setLong(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            Group receivedGroup = null;
            if (resultSet.next()) {
                receivedGroup = new Group(
                        resultSet.getLong("id"),
                        resultSet.getString("group_name"),
                        resultSet.getString("description"),
                        resultSet.getLong("owner_id"),
                        resultSet.getString("group_status")
                );
            } else {
                throw new DaoException("try to get non-existing group by id");
            }
            return receivedGroup;
        } catch (SQLException e) {
            throw new DaoException("dao: get group by id method failed: " + e.getMessage());
        }
    }

    @Override
    public List<Group> getAll() {
        try (Connection connection = ConnectionManager.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(GET_ALL_GROUPS)) {
            List<Group> receivedGroups = new ArrayList<>();
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                receivedGroups.add(new Group(
                        resultSet.getLong("id"),
                        resultSet.getString("group_name"),
                        resultSet.getString("description"),
                        resultSet.getLong("owner_id"),
                        resultSet.getString("group_status")
                ));
            }
            return receivedGroups;
        } catch (SQLException e) {
            throw new DaoException("dao: get all groups method failed: " + e.getMessage());
        }
    }

    @Override
    public boolean updateById(Long id, Group group) {
        try (Connection connection = ConnectionManager.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_GROUP_BY_ID)) {
            setGroupData(group, preparedStatement);
            preparedStatement.setLong(5, id);
            return preparedStatement.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new DaoException("dao: update group by id method failed: " + e.getMessage());
        }
    }

    private void setGroupData(Group group, PreparedStatement preparedStatement) throws SQLException {
        preparedStatement.setString(1, group.getGroupName());
        preparedStatement.setString(2, group.getDescription());
        preparedStatement.setLong(3, group.getOwnerId());
        preparedStatement.setString(4, group.getGroupStatus());
    }

    @Override
    public boolean deleteById(Long id) {
        try (Connection connection = ConnectionManager.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(DELETE_GROUP_BY_ID)) {
            preparedStatement.setLong(1, id);
            return preparedStatement.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new DaoException("dao: delete group by id method failed: ", e);
        }
    }

    @Override
    public <E> void validateEntityFieldUniqueness(String fieldName, E fieldValue) {
        if (isNull(fieldName) || isNull(fieldValue)) {
            throw new IllegalArgumentException("checking uniqueness field value: either fieldName or fieldValue is null");
        }
        final String sql = "SELECT id FROM group_data.\"group\" WHERE " + fieldName + " = ?";
        try (Connection connection = ConnectionManager.getConnection();
             PreparedStatement checkUniqueness = connection.prepareStatement(sql)) {
            checkUniqueness.setObject(1, fieldValue);
            ResultSet existedRecords = checkUniqueness.executeQuery();
            if (existedRecords.next()) {
                throw new IllegalArgumentException("group fields uniqueness failed while creating account");
            }
        } catch (SQLException e) {
            throw new DaoException("dao: create group method failed: " + e.getMessage());
        }
    }

}
