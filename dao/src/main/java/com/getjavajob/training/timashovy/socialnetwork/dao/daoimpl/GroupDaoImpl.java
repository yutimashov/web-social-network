package com.getjavajob.training.timashovy.socialnetwork.dao.daoimpl;

import com.getjavajob.training.timashovy.socialnetwork.common.Group;
import com.getjavajob.training.timashovy.socialnetwork.dao.interfaces.AccountGroupDao;
import com.getjavajob.training.timashovy.socialnetwork.dao.interfaces.TableConstraintsValidator;
import com.getjavajob.training.timashovy.socialnetwork.dao.util.exceptions.DaoException;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.getjavajob.training.timashovy.socialnetwork.dao.util.dbconnection.ConnectionManager.getPreparedStatement;
import static com.getjavajob.training.timashovy.socialnetwork.dao.util.dbconnection.ConnectionManager.getPreparedStatementWithGeneratedKeys;
import static java.util.Objects.isNull;
import static java.util.Optional.empty;
import static java.util.Optional.of;

public class GroupDaoImpl implements AccountGroupDao<Group>, TableConstraintsValidator {

    private static final GroupDaoImpl GROUP_DAO_INSTANCE = new GroupDaoImpl();
    private static final String SAVE_GROUP = "INSERT INTO group_data.\"group\" (group_name, description, owner_id) "
            + "VALUES(?, ?, ?)";
    private static final String GET_GROUP_BY_ID = "SELECT id, group_name, description, owner_id,"
            + " FROM group_data.\"group\" WHERE id = ?";
    private static final String GET_ALL_GROUPS = "SELECT id, group_name, description, owner_id,"
            + " FROM group_data.\"group\"";
    private static final String UPDATE_GROUP_BY_ID = "UPDATE group_data.\"group\" SET group_name = ?, description = ?,"
            + " owner_id = ? WHERE id = ?";
    private static final String DELETE_GROUP_BY_ID = "DELETE FROM group_data.\"group\" WHERE id = ?";

    private GroupDaoImpl() {
    }

    public static GroupDaoImpl getGroupDaoInstance() {
        return GROUP_DAO_INSTANCE;
    }

    @Override
    public Long create(Group group) {
        try (PreparedStatement preparedStatement = getPreparedStatementWithGeneratedKeys(SAVE_GROUP)) {
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
        preparedStatement.setLong(3, group.getOwnerId());
    }

    @Override
    public Optional<Group> getById(Long id) {
        try (PreparedStatement preparedStatement = getPreparedStatement(GET_GROUP_BY_ID)) {
            preparedStatement.setLong(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            Group receivedGroup;
            if (resultSet.next()) {
                receivedGroup = new Group(
                        resultSet.getLong("id"),
                        resultSet.getString("group_name"),
                        resultSet.getString("description"),
                        resultSet.getLong("owner_id")
                );
                return of(receivedGroup);
            } else {
                return empty();
            }
        } catch (SQLException e) {
            throw new DaoException("dao: get group by id method failed: " + e.getMessage());
        }
    }

    @Override
    public List<Group> getAll() {
        try (PreparedStatement preparedStatement = getPreparedStatement(GET_ALL_GROUPS)) {
            List<Group> receivedGroups = new ArrayList<>();
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                receivedGroups.add(new Group(
                        resultSet.getLong("id"),
                        resultSet.getString("group_name"),
                        resultSet.getString("description"),
                        resultSet.getLong("owner_id")
                ));
            }
            return receivedGroups;
        } catch (SQLException e) {
            throw new DaoException("dao: get all groups method failed: " + e.getMessage());
        }
    }

    @Override
    public boolean updateById(Long id, Group group) {
        try (PreparedStatement preparedStatement = getPreparedStatement(UPDATE_GROUP_BY_ID)) {
            setGroupData(group, preparedStatement);
            preparedStatement.setLong(4, id);
            return preparedStatement.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new DaoException("dao: update group by id method failed: " + e.getMessage());
        }
    }

    @Override
    public boolean deleteById(Long id) {
        try (PreparedStatement preparedStatement = getPreparedStatement(DELETE_GROUP_BY_ID)) {
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
        String checkRecordExistenceQuery = "SELECT id FROM group_data.\"group\" WHERE " + fieldName + " = ?";
        try (PreparedStatement checkUniqueness = getPreparedStatement(checkRecordExistenceQuery)) {
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
