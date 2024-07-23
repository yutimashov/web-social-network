package com.getjavajob.training.timashovy.socialnetwork.dao.daoimpl.group;

import com.getjavajob.training.timashovy.socialnetwork.dao.interfaces.GroupMembershipDao;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;
import java.util.List;

import static com.getjavajob.training.timashovy.socialnetwork.dao.util.dbutils.TableNames.GROUP_MEMBERS_TABLE;
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

    private JdbcTemplate jdbcTemplate;

    public void setDataSource(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public void sendRequest(Long groupId, Long accountId) {
        jdbcTemplate.update(ADD_USER, accountId, groupId);
    }

    @Override
    public void makeMember(Long groupId, Long accountId) {
        jdbcTemplate.update(MAKE_USER_GROUP_MEMBER, groupId, accountId);
    }

    @Override
    public List<Long> getRequests(Long groupId) {
        return jdbcTemplate.queryForList(GET_GROUP_FOLLOWERS, Long.class, groupId);
    }

    @Override
    public void deleteMember(Long groupId, Long accountId) {
        jdbcTemplate.update(DELETE_GROUP_MEMBER, groupId, accountId);
    }

    @Override
    public boolean isAdmin(Long groupId, Long accountId) {
        try {
            Integer adminStatus = jdbcTemplate.queryForObject(CHECK_ACCOUNT_ADMIN, Integer.class, groupId, accountId);
            return adminStatus != null && adminStatus > 0;
        } catch (EmptyResultDataAccessException e) {
            return false;
        }
    }

    @Override
    public boolean isSubscriber(Long groupId, Long accountId) {
        try {
            Integer subscriberStatus = jdbcTemplate.queryForObject(CHECK_ACCOUNT_SUBSCRIBER, Integer.class, groupId,
                    accountId);
            return subscriberStatus != null && subscriberStatus > 0;
        } catch (EmptyResultDataAccessException e) {
            return false;
        }
    }

    @Override
    public boolean isMember(Long groupId, Long accountId) {
        try {
            Integer memberStatus = jdbcTemplate.queryForObject(CHECK_ACCOUNT_MEMBER, Integer.class, groupId, accountId);
            return memberStatus != null && memberStatus > 0;
        } catch (EmptyResultDataAccessException e) {
            return false;
        }
    }

    @Override
    public List<Long> getRegularMembers(Long groupId) {
        return jdbcTemplate.queryForList(GET_REGULAR_MEMBERS, Long.class, groupId);
    }

    @Override
    public List<Long> getAdmins(Long groupId) {
        return jdbcTemplate.queryForList(GET_ADMINS, Long.class, groupId);
    }

    @Override
    public void makeAdmin(Long groupId, Long accountId) {
        jdbcTemplate.update(MAKE_USER_GROUP_ADMIN, groupId, accountId);
    }

}
