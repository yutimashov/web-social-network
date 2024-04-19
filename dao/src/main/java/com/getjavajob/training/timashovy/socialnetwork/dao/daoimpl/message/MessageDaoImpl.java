package com.getjavajob.training.timashovy.socialnetwork.dao.daoimpl.message;

import com.getjavajob.training.timashovy.socialnetwork.common.message.Message;
import com.getjavajob.training.timashovy.socialnetwork.common.message.MessageImage;
import com.getjavajob.training.timashovy.socialnetwork.common.message.MessageType;
import com.getjavajob.training.timashovy.socialnetwork.dao.interfaces.BaseDao;
import com.getjavajob.training.timashovy.socialnetwork.dao.util.DaoException;

import java.io.InputStream;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import static com.getjavajob.training.timashovy.socialnetwork.dao.util.dbutils.TableNames.MESSAGE_TABLE;
import static com.getjavajob.training.timashovy.socialnetwork.dao.util.dbutils.dbconnection.ConnectionManager.getPreparedStatement;
import static com.getjavajob.training.timashovy.socialnetwork.dao.util.dbutils.dbconnection.ConnectionManager.getPreparedStatementWithGeneratedKeys;
import static java.util.Optional.empty;
import static java.util.Optional.of;

public class MessageDaoImpl implements BaseDao<Message> {

    private static final MessageDaoImpl MESSAGE_DAO = new MessageDaoImpl();
    private static final String CREATE = "INSERT INTO " + MESSAGE_TABLE + " (account_author_id, message_text, " +
            "destination_type) VALUES (?, ?, ?);";
    private static final String DELETE_BY_ID = "DELETE FROM " + MESSAGE_TABLE + " WHERE id = ?;";
    private static final String UPDATE_BY_ID = "UPDATE " + MESSAGE_TABLE + " SET message_text = ? " + "WHERE id = ?;";
    private static final String GET_BY_ID = "SELECT id, account_author_id, creation_date, message_text, " +
            "destination_type FROM " + MESSAGE_TABLE + " WHERE id = ?;";

    public static MessageDaoImpl getInstance() {
        return MESSAGE_DAO;
    }

    private MessageDaoImpl() {
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
        preparedStatement.setString(2, message.getText());
        preparedStatement.setString(3, message.getDestination().name());
    }

    @Override
    public Optional<Message> getById(Long id) {
        try (PreparedStatement getMessageImageByIdStatement = getPreparedStatement(GET_BY_ID)) {
            getMessageImageByIdStatement.setLong(1, id);
            ResultSet messageData = getMessageImageByIdStatement.executeQuery();
            BaseDao<MessageImage> MESSAGE_IMAGE_DAO = MessageImageDaoImpl.getInstance();
            InputStream imagePhoto = null;
            if (MESSAGE_IMAGE_DAO.getById(id).isPresent()) {
                imagePhoto = MESSAGE_IMAGE_DAO.getById(id).get().getPhoto();
            }
            if (messageData.next()) {
                Message message = new Message(
                        messageData.getLong("account_author_id"),
                        messageData.getDate("creation_date").toLocalDate(),
                        messageData.getString("message_text"),
                        MessageType.valueOf(messageData.getString("destination_type")),
                        imagePhoto
                );
                return of(message);
            } else {
                return empty();
            }
        } catch (SQLException e) {
            throw new DaoException("dao: get message by id method failed: " + e.getMessage());
        }
    }

    @Override
    public List<Message> getAll() {
        return null;
    }

    @Override
    public boolean updateById(Long id, Message message) {
        try (PreparedStatement updateByIdStatement = getPreparedStatement(UPDATE_BY_ID)) {
            updateByIdStatement.setString(1, message.getText());
            updateByIdStatement.setLong(2, id);
            return updateByIdStatement.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new DaoException("dao: update message method failed: ", e);
        }
    }

    @Override
    public boolean deleteById(Long id) {
        try (PreparedStatement deleteByIdStatement = getPreparedStatement(DELETE_BY_ID)) {
            deleteByIdStatement.setLong(1, id);
            return deleteByIdStatement.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new DaoException("dao: delete message by id method failed: " + e.getMessage());
        }
    }

}
