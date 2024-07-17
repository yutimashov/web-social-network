package com.getjavajob.training.timashovy.socialnetwork.dao.daoimpl.message;

import com.getjavajob.training.timashovy.socialnetwork.common.message.Message;
import com.getjavajob.training.timashovy.socialnetwork.dao.interfaces.MessageDao;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import javax.sql.DataSource;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import static com.getjavajob.training.timashovy.socialnetwork.dao.util.dbutils.TableNames.GROUP_MESSAGE_TABLE;
import static com.getjavajob.training.timashovy.socialnetwork.dao.util.dbutils.fieldsnames.GroupMessageTableFields.*;
import static java.sql.Statement.RETURN_GENERATED_KEYS;
import static java.util.Objects.isNull;
import static java.util.Optional.empty;
import static java.util.Optional.of;

public class GroupMessageDaoImpl implements MessageDao {

    private static final String CREATE = "INSERT INTO " + GROUP_MESSAGE_TABLE + " (" + GROUP_MESSAGE_ACCOUNT_AUTHOR_ID
            + ", " + GROUP_MESSAGE_GROUP_ID + "," + GROUP_MESSAGE_MESSAGE_TEXT + ", " + GROUP_MESSAGE_MESSAGE_IMAGE
            + ") VALUES (?, ?, ?, ?);";
    private static final String DELETE_BY_ID = "DELETE FROM " + GROUP_MESSAGE_TABLE + " WHERE " + GROUP_MESSAGE_ID
            + " = ?;";
    private static final String UPDATE_BY_ID = "UPDATE " + GROUP_MESSAGE_TABLE + " SET " + GROUP_MESSAGE_MESSAGE_TEXT
            + " = ? " + "WHERE " + GROUP_MESSAGE_ID + " = ?;";
    private static final String GET_BY_ID = "SELECT " + GROUP_MESSAGE_ID + ", " + GROUP_MESSAGE_ACCOUNT_AUTHOR_ID
            + ", " + GROUP_MESSAGE_CREATION_DATE + ", " + GROUP_MESSAGE_MESSAGE_TEXT + ", "
            + GROUP_MESSAGE_MESSAGE_IMAGE + ", " + GROUP_MESSAGE_GROUP_ID + " FROM " + GROUP_MESSAGE_TABLE + " WHERE "
            + GROUP_MESSAGE_ID + " = ?;";
    private static final String GET_ALL = "SELECT " + GROUP_MESSAGE_ID + ", " + GROUP_MESSAGE_ACCOUNT_AUTHOR_ID + ", "
            + GROUP_MESSAGE_GROUP_ID + ", " + GROUP_MESSAGE_MESSAGE_TEXT + ", " + GROUP_MESSAGE_MESSAGE_IMAGE + ", "
            + GROUP_MESSAGE_CREATION_DATE + " FROM " + GROUP_MESSAGE_TABLE + " WHERE " + GROUP_MESSAGE_GROUP_ID
            + " = ? ORDER BY " + GROUP_MESSAGE_CREATION_DATE + " DESC;";
    private final RowMapper<Message> groupMessageRowMapper = (rs, rowNum) -> new Message.Builder()
            .id(rs.getLong(GROUP_MESSAGE_ID))
            .accountAuthorId(rs.getLong(GROUP_MESSAGE_ACCOUNT_AUTHOR_ID))
            .creationDate(rs.getDate(GROUP_MESSAGE_CREATION_DATE).toLocalDate())
            .destinationId(rs.getLong(GROUP_MESSAGE_GROUP_ID))
            .text(rs.getString(GROUP_MESSAGE_MESSAGE_TEXT))
            .photo(rs.getBinaryStream(GROUP_MESSAGE_MESSAGE_IMAGE))
            .build();

    private JdbcTemplate jdbcTemplate;

    public void setDataSource(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public Long create(Message message) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(CREATE, RETURN_GENERATED_KEYS);
            setMessageData(message, ps);
            return ps;
        }, keyHolder);
        if (!isNull(keyHolder.getKey())) {
            message.setId((long) keyHolder.getKey());
        }
        return message.getId();
    }

    private void setMessageData(Message message, PreparedStatement preparedStatement) throws SQLException {
        preparedStatement.setLong(1, message.getAccountAuthorId());
        preparedStatement.setLong(2, message.getDestinationId());
        preparedStatement.setString(3, message.getText());
        preparedStatement.setBinaryStream(4, message.getPhoto());
    }

    @Override
    public Optional<Message> getById(Long id) {
        Message message = jdbcTemplate.queryForObject(GET_BY_ID, groupMessageRowMapper, id);
        return !isNull(message) ? of(message) : empty();
    }

    public List<Message> getAll(Long groupId) {
        return jdbcTemplate.query(GET_ALL, groupMessageRowMapper, groupId);
    }

    @Override
    public boolean updateById(Long id, Message message) {
        return jdbcTemplate.update(UPDATE_BY_ID, ps -> {
            ps.setString(1, message.getText());
            ps.setLong(2, id);
        }) > 0;
    }

    @Override
    public boolean deleteById(Long id) {
        return jdbcTemplate.update(DELETE_BY_ID, id) > 0;
    }

}
