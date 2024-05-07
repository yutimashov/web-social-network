package com.getjavajob.training.timashovy.socialnetwork.dao.daoimpl.message;

import com.getjavajob.training.timashovy.socialnetwork.common.message.Message;
import com.getjavajob.training.timashovy.socialnetwork.dao.interfaces.MessageDao;
import com.getjavajob.training.timashovy.socialnetwork.dao.util.DaoException;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.getjavajob.training.timashovy.socialnetwork.dao.util.dbutils.TableNames.PERSONAL_WALL_MESSAGE_TABLE;
import static com.getjavajob.training.timashovy.socialnetwork.dao.util.dbutils.dbconnection.ConnectionManager.getPreparedStatement;
import static com.getjavajob.training.timashovy.socialnetwork.dao.util.dbutils.dbconnection.ConnectionManager.getPreparedStatementWithGeneratedKeys;
import static java.util.Optional.empty;
import static java.util.Optional.of;

public final class PersonalWallMessageDaoImpl implements MessageDao {

    private static final String CREATE = "INSERT INTO " + PERSONAL_WALL_MESSAGE_TABLE + " (account_author_id," +
            "account_receiver_id, message_text, message_image) VALUES (?, ?, ?, ?);";
    private static final String GET_ALL = "SELECT id, account_author_id, account_receiver_id, message_text, " +
            "message_image, creation_date FROM " + PERSONAL_WALL_MESSAGE_TABLE + " WHERE account_receiver_id = ? " +
            "ORDER BY creation_date DESC;";
    private static final String GET_BY_ID = "SELECT id, account_author_id, creation_date, message_text, " +
            "message_image, account_receiver_id FROM " + PERSONAL_WALL_MESSAGE_TABLE + " WHERE id = ?;";
    private static final PersonalWallMessageDaoImpl PERSONAL_WALL_MESSAGE_DAO = new PersonalWallMessageDaoImpl();

    private PersonalWallMessageDaoImpl() {
    }

    public static PersonalWallMessageDaoImpl getInstance() {
        return PERSONAL_WALL_MESSAGE_DAO;
    }

    @Override
    public Long create(Message message) {
        try (PreparedStatement createMessageStatement = getPreparedStatementWithGeneratedKeys(CREATE)) {
            setMessageData(message, createMessageStatement);
            if (createMessageStatement.executeUpdate() > 0) {
                ResultSet generatedKeys = createMessageStatement.getGeneratedKeys();
                if (generatedKeys.next()) {
                    message.setId(generatedKeys.getLong(1));
                }
                return message.getId();
            } else {
                throw new DaoException("dao: create message method failed: no rows affected.");
            }
        } catch (SQLException e) {
            throw new DaoException("dao: create message method failed: " + e.getMessage());
        }
    }

    private void setMessageData(Message message, PreparedStatement preparedStatement) throws SQLException {
        preparedStatement.setLong(1, message.getAccountAuthorId());
        preparedStatement.setLong(2, message.getDestinationId());
        preparedStatement.setString(3, message.getText());
        preparedStatement.setBinaryStream(4, message.getPhoto());
    }

    @Override
    public Optional<Message> getById(Long id) {
        try (PreparedStatement getMessageByIdStatement = getPreparedStatement(GET_BY_ID)) {
            getMessageByIdStatement.setLong(1, id);
            ResultSet messageData = getMessageByIdStatement.executeQuery();
            if (messageData.next()) {
                Message message = new Message.Builder()
                        .id(messageData.getLong("id"))
                        .accountAuthorId(messageData.getLong("account_author_id"))
                        .creationDate(messageData.getDate("creation_date").toLocalDate())
                        .destinationId(messageData.getLong("account_receiver_id"))
                        .text(messageData.getString("message_text"))
                        .photo(messageData.getBinaryStream("message_image"))
                        .build();
                return of(message);
            } else {
                return empty();
            }
        } catch (SQLException e) {
            throw new DaoException("dao: get message by id method failed: " + e.getMessage());
        }
    }

    @Override
    public List<Message> getAll(Long destinationId) {
        try (PreparedStatement preparedStatement = getPreparedStatement(GET_ALL)) {
            List<Message> messages = new ArrayList<>();
            preparedStatement.setLong(1, destinationId);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                messages.add(new Message.Builder()
                        .id(resultSet.getLong("id"))
                        .accountAuthorId(resultSet.getLong("account_author_id"))
                        .text(resultSet.getString("message_text"))
                        .photo(resultSet.getBinaryStream("message_image"))
                        .creationDate(resultSet.getDate("creation_date").toLocalDate())
                        .build());
            }
            return messages;
        } catch (SQLException e) {
            throw new DaoException("dao: get all messages method failed: " + e.getMessage());
        }
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
