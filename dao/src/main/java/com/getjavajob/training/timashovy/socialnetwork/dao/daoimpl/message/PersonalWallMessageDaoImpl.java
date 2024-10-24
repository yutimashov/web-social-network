package com.getjavajob.training.timashovy.socialnetwork.dao.daoimpl.message;

import com.getjavajob.training.timashovy.socialnetwork.domain.message.Message;
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

import static com.getjavajob.training.timashovy.socialnetwork.dao.util.dbutils.TableNames.PERSONAL_WALL_MESSAGE_TABLE;
import static com.getjavajob.training.timashovy.socialnetwork.dao.util.dbutils.fieldsnames.PersonalWallMessagesTableFields.*;

/**
 * Singleton class responsible for working with {@link com.getjavajob.training.timashovy.socialnetwork.dao.util.dbutils.TableNames#PERSONAL_WALL_MESSAGE_TABLE personal wall messages table}.
 * It provides safe multithreading approach for creating singleton object using synchronization mechanism.
 */
public class PersonalWallMessageDaoImpl implements MessageDao {

    private static final String CREATE = "INSERT INTO " + PERSONAL_WALL_MESSAGE_TABLE + " ("
            + PERSONAL_WALL_MESSAGE_AUTHOR_ID + "," + PERSONAL_WALL_MESSAGE_RECEIVER_ID + ", "
            + PERSONAL_WALL_MESSAGE_TEXT + ", " + PERSONAL_WALL_MESSAGE_IMAGE + ") VALUES (?, ?, ?, ?);";
    private static final String GET_ALL = "SELECT " + PERSONAL_WALL_MESSAGE_ID + ", " + PERSONAL_WALL_MESSAGE_AUTHOR_ID
            + ", " + PERSONAL_WALL_MESSAGE_RECEIVER_ID + ", " + PERSONAL_WALL_MESSAGE_TEXT + ", "
            + PERSONAL_WALL_MESSAGE_IMAGE + ", " + PERSONAL_WALL_MESSAGE_CREATION_DATE + " FROM "
            + PERSONAL_WALL_MESSAGE_TABLE + " WHERE " + PERSONAL_WALL_MESSAGE_RECEIVER_ID + " = ? " + "ORDER BY "
            + PERSONAL_WALL_MESSAGE_CREATION_DATE + " DESC;";
    private static final String GET_BY_ID = "SELECT " + PERSONAL_WALL_MESSAGE_ID + ", "
            + PERSONAL_WALL_MESSAGE_AUTHOR_ID + ", " + PERSONAL_WALL_MESSAGE_CREATION_DATE + ", "
            + PERSONAL_WALL_MESSAGE_TEXT + ", " + PERSONAL_WALL_MESSAGE_IMAGE + ", " + PERSONAL_WALL_MESSAGE_RECEIVER_ID
            + " FROM " + PERSONAL_WALL_MESSAGE_TABLE + " WHERE " + PERSONAL_WALL_MESSAGE_ID + " = ?;";
    private JdbcTemplate jdbcTemplate;

    private final RowMapper<Message> personalWallMessageRowMapper = (rs, rowNumber) -> new Message.Builder()
            .id(rs.getLong(PERSONAL_WALL_MESSAGE_ID))
            .accountAuthorId(rs.getLong(PERSONAL_WALL_MESSAGE_AUTHOR_ID))
            .creationDate(rs.getDate(PERSONAL_WALL_MESSAGE_CREATION_DATE).toLocalDate())
            .destinationId(rs.getLong(PERSONAL_WALL_MESSAGE_RECEIVER_ID))
            .text(rs.getString(PERSONAL_WALL_MESSAGE_TEXT))
            .photo(rs.getBinaryStream(PERSONAL_WALL_MESSAGE_IMAGE))
            .build();

    public void setDataSource(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public Long create(Message message) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(conn -> {
            PreparedStatement ps = conn.prepareStatement(CREATE, new String[]{PERSONAL_WALL_MESSAGE_ID});
            setMessageData(message, ps);
            return ps;
        }, keyHolder);
        Number generatedId = keyHolder.getKey();
        if (generatedId != null) {
            Long id = generatedId.longValue();
            message.setId(id);
            return id;
        }
        return null;
    }

    private void setMessageData(Message message, PreparedStatement preparedStatement) throws SQLException {
        preparedStatement.setLong(1, message.getAccountAuthorId());
        preparedStatement.setLong(2, message.getDestinationId());
        preparedStatement.setString(3, message.getText());
        preparedStatement.setBinaryStream(4, message.getPhoto());
    }

    @Override
    public Optional<Message> getById(Long id) {
        return jdbcTemplate.query(GET_BY_ID, personalWallMessageRowMapper, id).stream().findFirst();
    }

    @Override
    public List<Message> getAll(Long destinationId) {
        return jdbcTemplate.query(GET_ALL, personalWallMessageRowMapper, destinationId);
    }

    @Override
    public boolean updateById(Long id, Message message) {
        return false;
    }

    @Override
    public boolean deleteById(Long id) {
        return false;
    }

}
