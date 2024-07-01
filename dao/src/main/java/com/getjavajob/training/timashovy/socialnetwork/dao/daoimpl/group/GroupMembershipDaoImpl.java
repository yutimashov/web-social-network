package com.getjavajob.training.timashovy.socialnetwork.dao.daoimpl.group;

import com.getjavajob.training.timashovy.socialnetwork.dao.interfaces.GroupMembershipDao;
import com.getjavajob.training.timashovy.socialnetwork.dao.util.DaoException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static com.getjavajob.training.timashovy.socialnetwork.dao.util.dbutils.TableNames.GROUP_MEMBERS_TABLE;
import static com.getjavajob.training.timashovy.socialnetwork.dao.util.dbutils.dbconnection.ConnectionManager.getConnection;
import static com.getjavajob.training.timashovy.socialnetwork.dao.util.dbutils.fieldsnames.GroupMembersFields.*;

public class GroupMembershipDaoImpl implements GroupMembershipDao {

    private static final String ADD_USER = "INSERT INTO " + GROUP_MEMBERS_TABLE + " (" + GROUP_MEMBERS_ACCOUNT_ID
            + ", " + GROUP_MEMBERS_GROUP_ID + ") VALUES(?, ?);";
    private static final String MAKE_USER_GROUP_ADMIN = "UPDATE " + GROUP_MEMBERS_TABLE + " SET "
            + GROUP_MEMBERS_IS_ADMIN + " = TRUE WHERE " + GROUP_MEMBERS_GROUP_ID + " = ? AND "
            + GROUP_MEMBERS_ACCOUNT_ID + " = ?;";
    private static final String MAKE_USER_GROUP_MEMBER = "UPDATE " + GROUP_MEMBERS_TABLE + " SET "
            + GROUP_MEMBERS_IS_MEMBER + " = TRUE " + "WHERE " + GROUP_MEMBERS_GROUP_ID + " = ? AND "
            + GROUP_MEMBERS_ACCOUNT_ID + " = ?;";
    private static final String GET_GROUP_FOLLOWERS = "SELECT " + GROUP_MEMBERS_ACCOUNT_ID + " FROM "
            + GROUP_MEMBERS_TABLE + " WHERE " + GROUP_MEMBERS_GROUP_ID + " = ? AND " + GROUP_MEMBERS_IS_MEMBER
            + " = FALSE ORDER BY " + GROUP_MEMBERS_REGISTRATION_DATE + " DESC;";
    private static final String DELETE_GROUP_MEMBER = "DELETE FROM " + GROUP_MEMBERS_TABLE + " WHERE "
            + GROUP_MEMBERS_GROUP_ID + " = ? AND " + GROUP_MEMBERS_ACCOUNT_ID + " = ?;";
    private static final String CHECK_ACCOUNT_ADMIN = "SELECT " + GROUP_MEMBERS_ID + " FROM " + GROUP_MEMBERS_TABLE
            + " WHERE " + GROUP_MEMBERS_GROUP_ID + " = ? AND " + GROUP_MEMBERS_ACCOUNT_ID + " = ? AND "
            + GROUP_MEMBERS_IS_ADMIN + " = TRUE;";
    private static final String CHECK_ACCOUNT_SUBSCRIBER = "SELECT " + GROUP_MEMBERS_ID + " FROM " + GROUP_MEMBERS_TABLE
            + " WHERE " + GROUP_MEMBERS_GROUP_ID + " = ? AND " + GROUP_MEMBERS_ACCOUNT_ID + " = ? AND "
            + GROUP_MEMBERS_IS_MEMBER + " = FALSE;";
    private static final String CHECK_ACCOUNT_MEMBER = "SELECT " + GROUP_MEMBERS_ID + " FROM " + GROUP_MEMBERS_TABLE
            + " WHERE " + GROUP_MEMBERS_GROUP_ID + " = ? AND " + GROUP_MEMBERS_ACCOUNT_ID + " = ? AND "
            + GROUP_MEMBERS_IS_MEMBER + " = TRUE;";
    private static final String GET_REGULAR_MEMBERS = "SELECT " + GROUP_MEMBERS_ACCOUNT_ID + " FROM "
            + GROUP_MEMBERS_TABLE + " WHERE " + GROUP_MEMBERS_GROUP_ID + " = ? AND " + GROUP_MEMBERS_IS_MEMBER
            + " = TRUE AND " + GROUP_MEMBERS_IS_ADMIN + " = FALSE;";
    private static final String GET_ADMINS = "SELECT " + GROUP_MEMBERS_ACCOUNT_ID + " FROM " + GROUP_MEMBERS_TABLE
            + " WHERE " + GROUP_MEMBERS_GROUP_ID + " = ? " + "AND " + GROUP_MEMBERS_IS_MEMBER + " = TRUE AND "
            + GROUP_MEMBERS_IS_ADMIN + " = TRUE;";

    @Override
    public void sendRequest(Long groupId, Long accountId) {
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(ADD_USER)) {
            preparedStatement.setLong(1, accountId);
            preparedStatement.setLong(2, groupId);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new DaoException("dao: create group method failed: " + e.getMessage());
        }
    }

    @Override
    public void makeMember(Long groupId, Long accountId) {
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(MAKE_USER_GROUP_MEMBER)) {
            preparedStatement.setLong(1, groupId);
            preparedStatement.setLong(2, accountId);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new DaoException("dao: make user group member method failed: " + e.getMessage());
        }
    }

    @Override
    public List<Long> getRequests(Long groupId) {
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(GET_GROUP_FOLLOWERS)) {
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
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(DELETE_GROUP_MEMBER)) {
            preparedStatement.setLong(1, groupId);
            preparedStatement.setLong(2, accountId);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new DaoException("dao: delete group by id method failed: ", e);
        }
    }

    @Override
    public boolean isAdmin(Long groupId, Long accountId) {
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(CHECK_ACCOUNT_ADMIN)) {
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
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(CHECK_ACCOUNT_SUBSCRIBER)) {
            preparedStatement.setLong(1, groupId);
            preparedStatement.setLong(2, accountId);
            return preparedStatement.executeQuery().next();
        } catch (SQLException e) {
            throw new DaoException("dao: check account is group subscriber method failed: ", e);
        }
    }

    @Override
    public boolean isMember(Long groupId, Long accountId) {
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(CHECK_ACCOUNT_MEMBER)) {
            preparedStatement.setLong(1, groupId);
            preparedStatement.setLong(2, accountId);
            return preparedStatement.executeQuery().next();
        } catch (SQLException e) {
            throw new DaoException("dao: check account is group member method failed: ", e);
        }
    }

    @Override
    public List<Long> getRegularMembers(Long groupId) {
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(GET_REGULAR_MEMBERS)) {
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
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(GET_ADMINS)) {
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
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(MAKE_USER_GROUP_ADMIN)) {
            preparedStatement.setLong(1, groupId);
            preparedStatement.setLong(2, accountId);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new DaoException("dao: make user group admin method failed: " + e.getMessage());
        }
    }

}
