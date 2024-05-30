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

import static com.getjavajob.training.timashovy.socialnetwork.dao.util.dbutils.TableNames.GROUP_MESSAGE_TABLE;
import static com.getjavajob.training.timashovy.socialnetwork.dao.util.dbutils.dbconnection.ConnectionManager.getPreparedStatement;
import static com.getjavajob.training.timashovy.socialnetwork.dao.util.dbutils.dbconnection.ConnectionManager.getPreparedStatementWithGeneratedKeys;
import static com.getjavajob.training.timashovy.socialnetwork.dao.util.dbutils.fieldsnames.GroupMessageTableFields.*;
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
    private static volatile MessageDao instance;

    private GroupMessageDaoImpl() {
    }

    public static MessageDao getInstance() {
        if (instance == null) {
            synchronized (MessageDao.class) {
                if (instance == null) {
                    instance = new GroupMessageDaoImpl();
                }
            }
        }
        return instance;
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
                        .destinationId(messageData.getLong("group_id"))
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

    public List<Message> getAll(Long groupId) {
        try (PreparedStatement preparedStatement = getPreparedStatement(GET_ALL)) {
            List<Message> messages = new ArrayList<>();
            preparedStatement.setLong(1, groupId);
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
