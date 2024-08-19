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

import static com.getjavajob.training.timashovy.socialnetwork.dao.util.dbutils.TableNames.PERSONAL_MESSAGE_TABLE;
import static com.getjavajob.training.timashovy.socialnetwork.dao.util.dbutils.fieldsnames.PersonalMessagesTableFields.*;

public class PersonalMessageDaoImpl implements MessageDao {

    private static final String CREATE = "INSERT INTO " + PERSONAL_MESSAGE_TABLE + " ("
            + PERSONAL_MESSAGE_ACCOUNT_AUTHOR_ID + ", " + PERSONAL_MESSAGE_ACCOUNT_DESTINATION_ID + ", "
            + PERSONAL_MESSAGE_TEXT + ", " + PERSONAL_MESSAGE_IMAGE + ") VALUES (?, ?, ?, ?);";
    private static final String GET_ALL_ACCOUNT_IDS = "SELECT DISTINCT " + PERSONAL_MESSAGE_ACCOUNT_AUTHOR_ID
            + " FROM " + PERSONAL_MESSAGE_TABLE + " WHERE " + PERSONAL_MESSAGE_ACCOUNT_DESTINATION_ID
            + " = ? UNION SELECT DISTINCT " + PERSONAL_MESSAGE_ACCOUNT_DESTINATION_ID + " FROM "
            + PERSONAL_MESSAGE_TABLE + " WHERE " + PERSONAL_MESSAGE_ACCOUNT_AUTHOR_ID + " = ?;";
    private static final String GET_ALL_MESSAGES_WITH_ACCOUNT = "SELECT " + PERSONAL_MESSAGE_ID + ", "
            + PERSONAL_MESSAGE_ACCOUNT_AUTHOR_ID + ", " + PERSONAL_MESSAGE_ACCOUNT_DESTINATION_ID + ", "
            + PERSONAL_MESSAGE_TEXT + ", " + PERSONAL_MESSAGE_IMAGE + ", " + PERSONAL_MESSAGE_CREATION_DATE + " FROM "
            + PERSONAL_MESSAGE_TABLE + " WHERE " + PERSONAL_MESSAGE_ACCOUNT_AUTHOR_ID + " = "
            + "? AND " + PERSONAL_MESSAGE_ACCOUNT_DESTINATION_ID + " = ? UNION SELECT " + PERSONAL_MESSAGE_ID + ", "
            + PERSONAL_MESSAGE_ACCOUNT_AUTHOR_ID + ", " + PERSONAL_MESSAGE_ACCOUNT_DESTINATION_ID + ", "
            + PERSONAL_MESSAGE_TEXT + ", " + PERSONAL_MESSAGE_IMAGE + ", " + PERSONAL_MESSAGE_CREATION_DATE + " FROM "
            + PERSONAL_MESSAGE_TABLE + " WHERE " + PERSONAL_MESSAGE_ACCOUNT_AUTHOR_ID + " = ? AND "
            + PERSONAL_MESSAGE_ACCOUNT_DESTINATION_ID + " = ? " + "ORDER BY " + PERSONAL_MESSAGE_CREATION_DATE + ";";
    private static final String GET_BY_ID = "SELECT " + PERSONAL_MESSAGE_ID + ", " + PERSONAL_MESSAGE_ACCOUNT_AUTHOR_ID
            + ", " + PERSONAL_MESSAGE_CREATION_DATE + ", " + PERSONAL_MESSAGE_TEXT + ", " + PERSONAL_MESSAGE_IMAGE
            + ", " + PERSONAL_MESSAGE_ACCOUNT_DESTINATION_ID + " FROM " + PERSONAL_MESSAGE_TABLE + " WHERE "
            + PERSONAL_MESSAGE_ID + " = ?;";
    private final RowMapper<Message> personalMessageRowMapper = (rs, rowNum) -> new Message.Builder()
            .id(rs.getLong(PERSONAL_MESSAGE_ID))
            .accountAuthorId(rs.getLong(PERSONAL_MESSAGE_ACCOUNT_AUTHOR_ID))
            .creationDate(rs.getDate(PERSONAL_MESSAGE_CREATION_DATE).toLocalDate())
            .destinationId(rs.getLong(PERSONAL_MESSAGE_ACCOUNT_DESTINATION_ID))
            .text(rs.getString(PERSONAL_MESSAGE_TEXT))
            .photo(rs.getBinaryStream(PERSONAL_MESSAGE_IMAGE))
            .build();

    private JdbcTemplate jdbcTemplate;

    public void setDataSource(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public Long create(Message message) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        int rowsUpdated = jdbcTemplate.update(
                con -> {
                    PreparedStatement ps = con.prepareStatement(CREATE, new String[]{PERSONAL_MESSAGE_ID});
                    setMessageData(message, ps);
                    return ps;
                },
                keyHolder
        );
        if (rowsUpdated > 0) {
            Number generatedId = keyHolder.getKey();
            if (generatedId != null) {
                Long id = generatedId.longValue();
                message.setId(id);
                return id;
            }
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
        return jdbcTemplate.query(GET_BY_ID, personalMessageRowMapper, id).stream().findFirst();
    }

    @Override
    public List<Message> getAll(Long destinationId) {
        return null;
    }

    @Override
    public boolean updateById(Long id, Message message) {
        return false;
    }

    @Override
    public boolean deleteById(Long id) {
        return false;
    }

    public List<Long> getAllAccountsIds(Long accountId) {
        return jdbcTemplate.queryForList(GET_ALL_ACCOUNT_IDS, Long.class, accountId, accountId);
    }

    public List<Message> getAllPersonalMessagesWithAccount(Long authorId, Long receiverId) {
        return jdbcTemplate.query(GET_ALL_MESSAGES_WITH_ACCOUNT, personalMessageRowMapper, authorId, receiverId,
                receiverId, authorId);
    }

}
