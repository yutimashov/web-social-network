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
import static com.getjavajob.training.timashovy.socialnetwork.dao.util.dbutils.fieldsnames.PersonalWallMessagesTableFields.*;
import static java.util.Optional.empty;
import static java.util.Optional.of;

/**
 * Singleton class responsible for working with {@link com.getjavajob.training.timashovy.socialnetwork.dao.util.dbutils.TableNames#PERSONAL_WALL_MESSAGE_TABLE personal wall messages table}.
 * It provides safe multithreading approach for creating singleton object using synchronization mechanism.
 */
public class PersonalWallMessageDaoImpl implements MessageDao {

    private static final String CREATE = "INSERT INTO " + PERSONAL_WALL_MESSAGE_TABLE + " (" + ACCOUNT_AUTHOR_ID + ","
            + ACCOUNT_RECEIVER_ID + ", " + MESSAGE_TEXT + ", " + MESSAGE_IMAGE + ") VALUES (?, ?, ?, ?);";
    private static final String GET_ALL = "SELECT " + ID + ", " + ACCOUNT_AUTHOR_ID + ", " + ACCOUNT_RECEIVER_ID
            + ", " + MESSAGE_TEXT + ", " + MESSAGE_IMAGE + ", " + CREATION_DATE + " FROM "
            + PERSONAL_WALL_MESSAGE_TABLE + " WHERE " + ACCOUNT_RECEIVER_ID + " = ? " + "ORDER BY " + CREATION_DATE
            + " DESC;";
    private static final String GET_BY_ID = "SELECT " + ID + ", " + ACCOUNT_AUTHOR_ID + ", " + CREATION_DATE + ", "
            + MESSAGE_TEXT + ", " + MESSAGE_IMAGE + ", " + ACCOUNT_RECEIVER_ID + " FROM " + PERSONAL_WALL_MESSAGE_TABLE
            + " WHERE " + ID + " = ?;";
    private static volatile MessageDao instance;

    private PersonalWallMessageDaoImpl() {
    }

    public static MessageDao createInstance() {
        if (instance == null) {
            synchronized (PersonalWallMessageDaoImpl.class) {
                if (instance == null) {
                    instance = new PersonalWallMessageDaoImpl();
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
                        .id(messageData.getLong(ID))
                        .accountAuthorId(messageData.getLong(ACCOUNT_AUTHOR_ID))
                        .creationDate(messageData.getDate(CREATION_DATE).toLocalDate())
                        .destinationId(messageData.getLong(ACCOUNT_RECEIVER_ID))
                        .text(messageData.getString(MESSAGE_TEXT))
                        .photo(messageData.getBinaryStream(MESSAGE_IMAGE))
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
                        .id(resultSet.getLong(ID))
                        .accountAuthorId(resultSet.getLong(ACCOUNT_AUTHOR_ID))
                        .text(resultSet.getString(MESSAGE_TEXT))
                        .photo(resultSet.getBinaryStream(MESSAGE_IMAGE))
                        .creationDate(resultSet.getDate(CREATION_DATE).toLocalDate())
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
