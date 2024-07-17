package com.getjavajob.training.timashovy.socialnetwork.dao.daoimpl.friendship;

import com.getjavajob.training.timashovy.socialnetwork.dao.interfaces.friendship.FriendshipCheckerDao;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;

import static com.getjavajob.training.timashovy.socialnetwork.dao.util.dbutils.TableNames.FRIENDSHIP_TABLE;
import static com.getjavajob.training.timashovy.socialnetwork.dao.util.dbutils.fieldsnames.FriendshipTableFields.*;
import static java.util.Objects.isNull;

/**
 * Singleton class responsible for working with `account_data.friendship` table in DB.
 * It provides safe multithreading approach for creating singleton object using synchronization mechanism.
 */
public class FriendshipCheckerDaoImpl implements FriendshipCheckerDao {

    private static final String FRIENDSHIP_RECORD_EXISTENCE = "SELECT 1 FROM " + FRIENDSHIP_TABLE + " WHERE "
            + FRIENDSHIP_ACCOUNT_ID_1 + " = ? AND " + FRIENDSHIP_ACCOUNT_ID_2 + " = ?;";
    private static final String ARE_USERS_FRIENDS = "SELECT 1 FROM " + FRIENDSHIP_TABLE + " WHERE "
            + FRIENDSHIP_ACCOUNT_ID_1 + " = ? AND " + FRIENDSHIP_ACCOUNT_ID_2 + " = ? AND " + FRIENDSHIP_STATUS
            + " = TRUE;";
    private JdbcTemplate jdbcTemplate;

    public void setDataSource(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public boolean checkFriendshipRecordExistence(Long requesterId, Long accepterId) {
        return !isNull(jdbcTemplate.queryForObject(FRIENDSHIP_RECORD_EXISTENCE, Boolean.class,
                getFirstId(requesterId, accepterId), getSecondId(requesterId, accepterId)));
    }

    private Long getFirstId(Long requesterId, Long accepterId) {
        return requesterId < accepterId ? requesterId : accepterId;
    }

    private Long getSecondId(Long requesterId, Long accepterId) {
        return requesterId < accepterId ? accepterId : requesterId;
    }

    @Override
    public boolean checkUsersAreFriends(Long requesterId, Long accepterId) {
        return !isNull(jdbcTemplate.queryForObject(ARE_USERS_FRIENDS, Boolean.class,
                getFirstId(requesterId, accepterId), getSecondId(requesterId, accepterId)));
    }

}
