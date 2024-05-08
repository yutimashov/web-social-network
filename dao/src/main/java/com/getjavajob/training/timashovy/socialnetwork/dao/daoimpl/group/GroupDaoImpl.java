package com.getjavajob.training.timashovy.socialnetwork.dao.daoimpl.group;

import com.getjavajob.training.timashovy.socialnetwork.common.Group;
import com.getjavajob.training.timashovy.socialnetwork.dao.interfaces.BaseDao;
import com.getjavajob.training.timashovy.socialnetwork.dao.interfaces.GroupDao;
import com.getjavajob.training.timashovy.socialnetwork.dao.interfaces.TableConstraintsValidator;
import com.getjavajob.training.timashovy.socialnetwork.dao.util.DaoException;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.getjavajob.training.timashovy.socialnetwork.dao.util.dbutils.TableNames.GROUP_MEMBERS_TABLE;
import static com.getjavajob.training.timashovy.socialnetwork.dao.util.dbutils.TableNames.GROUP_TABLE;
import static com.getjavajob.training.timashovy.socialnetwork.dao.util.dbutils.dbconnection.ConnectionManager.getPreparedStatement;
import static com.getjavajob.training.timashovy.socialnetwork.dao.util.dbutils.dbconnection.ConnectionManager.getPreparedStatementWithGeneratedKeys;
import static java.util.Objects.isNull;
import static java.util.Optional.empty;
import static java.util.Optional.of;

public final class GroupDaoImpl implements BaseDao<Group>, GroupDao, TableConstraintsValidator {

    private static final GroupDaoImpl GROUP_DAO_INSTANCE = new GroupDaoImpl();
    private static final String SAVE_GROUP = "INSERT INTO " + GROUP_TABLE + " (group_name, description, owner_id) "
            + "VALUES(?, ?, ?)";
    private static final String GET_GROUP_BY_ID = "SELECT id, group_name, description, owner_id "
            + "FROM " + GROUP_TABLE + " WHERE id = ?";
    private static final String GET_ALL_GROUPS = "SELECT id, group_name, description, owner_id "
            + "FROM " + GROUP_TABLE + ";";
    private static final String UPDATE_GROUP_BY_ID = "UPDATE " + GROUP_TABLE + " SET group_name = ?, description = ?,"
            + " owner_id = ? WHERE id = ?";
    private static final String DELETE_GROUP_BY_ID = "DELETE FROM " + GROUP_TABLE + " WHERE id = ?";
    private static final String ADD_USER = "INSERT INTO " + GROUP_MEMBERS_TABLE + " (account_id, group_id) " +
            "VALUES(?, ?);";
    private static final String MAKE_USER_GROUP_ADMIN = "UPDATE " + GROUP_MEMBERS_TABLE + " SET is_admin = TRUE WHERE " +
            "group_id = ? AND account_id = ?;";
    private static final String MAKE_USER_GROUP_MEMBER = "UPDATE " + GROUP_MEMBERS_TABLE + " SET is_member = TRUE " +
            "WHERE group_id = ? AND account_id = ?;";
    private static final String GET_GROUP_FOLLOWERS = "SELECT account_id FROM " + GROUP_MEMBERS_TABLE
            + " WHERE group_id = ? AND is_member = FALSE ORDER BY registration_date DESC;";
    private static final String DELETE_GROUP_MEMBER = "DELETE FROM " + GROUP_MEMBERS_TABLE
            + " WHERE group_id = ? AND account_id = ?;";
    private static final String CHECK_ACCOUNT_ADMIN = "SELECT id FROM " + GROUP_MEMBERS_TABLE
            + " WHERE group_id = ? AND account_id = ? AND is_admin = TRUE;";
    private static final String CHECK_ACCOUNT_SUBSCRIBER = "SELECT id FROM " + GROUP_MEMBERS_TABLE
            + " WHERE group_id = ? AND account_id = ? AND is_member = FALSE;";
    private static final String CHECK_ACCOUNT_MEMBER = "SELECT id FROM " + GROUP_MEMBERS_TABLE
            + " WHERE group_id = ? AND account_id = ? AND is_member = TRUE;";
    private static final String GET_REGULAR_MEMBERS = "SELECT account_id FROM " + GROUP_MEMBERS_TABLE
            + " WHERE group_id = ? AND is_member = TRUE AND is_admin = FALSE;";
    private static final String GET_ADMINS = "SELECT account_id FROM " + GROUP_MEMBERS_TABLE
            + " WHERE group_id = ? AND is_member = TRUE AND is_admin = TRUE;";

    private GroupDaoImpl() {
    }

    public static GroupDaoImpl getInstance() {
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
        preparedStatement.setLong(3, group.getAccountOwnerId());
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
    public void sendRequest(Long groupId, Long accountId) {
        try (PreparedStatement preparedStatement = getPreparedStatement(ADD_USER)) {
            preparedStatement.setLong(1, accountId);
            preparedStatement.setLong(2, groupId);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new DaoException("dao: create group method failed: " + e.getMessage());
        }
    }

    @Override
    public void makeMember(Long groupId, Long accountId) {
        try (PreparedStatement preparedStatement = getPreparedStatement(MAKE_USER_GROUP_MEMBER)) {
            preparedStatement.setLong(1, groupId);
            preparedStatement.setLong(2, accountId);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new DaoException("dao: make user group member method failed: " + e.getMessage());
        }
    }

    @Override
    public List<Long> getRequests(Long groupId) {
        try (PreparedStatement preparedStatement = getPreparedStatement(GET_GROUP_FOLLOWERS)) {
            List<Long> groupFollowers = new ArrayList<>();
            preparedStatement.setLong(1, groupId);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                groupFollowers.add(resultSet.getLong(1));
            }
            return groupFollowers;
        } catch (SQLException e) {
            throw new DaoException("dao: get group followers method failed: " + e.getMessage());
        }
    }

    @Override
    public void deleteMember(Long groupId, Long accountId) {
        try (PreparedStatement preparedStatement = getPreparedStatement(DELETE_GROUP_MEMBER)) {
            preparedStatement.setLong(1, groupId);
            preparedStatement.setLong(2, accountId);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new DaoException("dao: delete group by id method failed: ", e);
        }
    }

    @Override
    public boolean isAdmin(Long groupId, Long accountId) {
        try (PreparedStatement preparedStatement = getPreparedStatement(CHECK_ACCOUNT_ADMIN)) {
            preparedStatement.setLong(1, groupId);
            preparedStatement.setLong(2, accountId);
            ResultSet resultSet = preparedStatement.executeQuery();
            return resultSet.next();
        } catch (SQLException e) {
            throw new DaoException("dao: check account is group admin method failed: ", e);
        }
    }

    @Override
    public boolean isSubscriber(Long groupId, Long accountId) {
        try (PreparedStatement preparedStatement = getPreparedStatement(CHECK_ACCOUNT_SUBSCRIBER)) {
            preparedStatement.setLong(1, groupId);
            preparedStatement.setLong(2, accountId);
            ResultSet resultSet = preparedStatement.executeQuery();
            return resultSet.next();
        } catch (SQLException e) {
            throw new DaoException("dao: check account is group subscriber method failed: ", e);
        }
    }

    @Override
    public boolean isMember(Long groupId, Long accountId) {
        try (PreparedStatement preparedStatement = getPreparedStatement(CHECK_ACCOUNT_MEMBER)) {
            preparedStatement.setLong(1, groupId);
            preparedStatement.setLong(2, accountId);
            ResultSet resultSet = preparedStatement.executeQuery();
            return resultSet.next();
        } catch (SQLException e) {
            throw new DaoException("dao: check account is group member method failed: ", e);
        }
    }

    @Override
    public List<Long> getRegularMembers(Long groupId) {
        try (PreparedStatement preparedStatement = getPreparedStatement(GET_REGULAR_MEMBERS)) {
            List<Long> groupMembers = new ArrayList<>();
            preparedStatement.setLong(1, groupId);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                groupMembers.add(resultSet.getLong(1));
            }
            return groupMembers;
        } catch (SQLException e) {
            throw new DaoException("dao: get group members method failed: " + e.getMessage());
        }
    }

    @Override
    public List<Long> getAdmins(Long groupId) {
        try (PreparedStatement preparedStatement = getPreparedStatement(GET_ADMINS)) {
            List<Long> groupMembers = new ArrayList<>();
            preparedStatement.setLong(1, groupId);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                groupMembers.add(resultSet.getLong(1));
            }
            return groupMembers;
        } catch (SQLException e) {
            throw new DaoException("dao: get group admins method failed: " + e.getMessage());
        }
    }

    @Override
    public void makeAdmin(Long groupId, Long accountId) {
        try (PreparedStatement preparedStatement = getPreparedStatement(MAKE_USER_GROUP_ADMIN)) {
            preparedStatement.setLong(1, groupId);
            preparedStatement.setLong(2, accountId);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new DaoException("dao: make user group admin method failed: " + e.getMessage());
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
