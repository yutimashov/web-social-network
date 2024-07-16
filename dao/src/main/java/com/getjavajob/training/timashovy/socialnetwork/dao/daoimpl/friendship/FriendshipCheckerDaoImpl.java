package com.getjavajob.training.timashovy.socialnetwork.dao.daoimpl.friendship;

import com.getjavajob.training.timashovy.socialnetwork.dao.interfaces.friendship.FriendshipCheckerDao;
import com.getjavajob.training.timashovy.socialnetwork.dao.util.exceptions.DaoException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import static com.getjavajob.training.timashovy.socialnetwork.dao.util.dbutils.TableNames.FRIENDSHIP_TABLE;
import static com.getjavajob.training.timashovy.socialnetwork.dao.util.dbutils.dbconnection.JdbcTemplateManager.getConnection;
import static com.getjavajob.training.timashovy.socialnetwork.dao.util.dbutils.fieldsnames.FriendshipTableFields.*;

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
    private final JdbcTemplate jdbcTemplate;

    public FriendshipCheckerDaoImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public boolean checkFriendshipRecordExistence(Long requesterId, Long accepterId) {
        return Boolean.TRUE.equals(jdbcTemplate.query(FRIENDSHIP_RECORD_EXISTENCE,
                new Object[]{getFirstId(requesterId, accepterId), getSecondId(requesterId, accepterId)}, (rs -> return rs.next());
                new ResultSetExtractor<Boolean>() {
                    @Override
                    public Boolean extractData(ResultSet rs) throws SQLException {
                        return rs.next();
                    }
                }));
    }

    private Long getFirstId(Long requesterId, Long accepterId) {
        return requesterId < accepterId ? requesterId : accepterId;
    }

    private Long getSecondId(Long requesterId, Long accepterId) {
        return requesterId < accepterId ? accepterId : requesterId;
    }

    @Override
    public boolean checkUsersAreFriends(Long requesterId, Long accepterId) {
        return jdbcTemplate.query(ARE_USERS_FRIENDS,
                new Object[]{getFirstId(requesterId, accepterId), getSecondId(requesterId, accepterId)},
                ResultSet::next);
    }

}
