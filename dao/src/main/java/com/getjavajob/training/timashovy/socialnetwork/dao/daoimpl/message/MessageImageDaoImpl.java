package com.getjavajob.training.timashovy.socialnetwork.dao.daoimpl.message;

import com.getjavajob.training.timashovy.socialnetwork.common.message.MessageImage;
import com.getjavajob.training.timashovy.socialnetwork.dao.interfaces.BaseDao;
import com.getjavajob.training.timashovy.socialnetwork.dao.util.DaoException;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import static com.getjavajob.training.timashovy.socialnetwork.dao.util.dbutils.TableNames.MESSAGE_IMAGE_TABLE;
import static com.getjavajob.training.timashovy.socialnetwork.dao.util.dbutils.dbconnection.ConnectionManager.getPreparedStatement;
import static com.getjavajob.training.timashovy.socialnetwork.dao.util.dbutils.dbconnection.ConnectionManager.getPreparedStatementWithGeneratedKeys;
import static java.util.Optional.empty;
import static java.util.Optional.of;

public class MessageImageDaoImpl implements BaseDao<MessageImage> {

    private static final MessageImageDaoImpl MESSAGE_IMAGE_DAO = new MessageImageDaoImpl();
    private static final String CREATE = "INSERT INTO " + MESSAGE_IMAGE_TABLE + " (image_blob, message_id) "
            + "VALUES(?, ?);";
    private static final String GET_BY_ID = "SELECT id, image_blob, message_id FROM "
            + MESSAGE_IMAGE_TABLE + " WHERE id = ?;";
    private static final String UPDATE_BY_ID = "UPDATE " + MESSAGE_IMAGE_TABLE + " SET image_blob = ? "
            + "WHERE id = ?;";
    private static final String DELETE_BY_ID = "DELETE FROM " + MESSAGE_IMAGE_TABLE + " WHERE id = ?;";

    public static MessageImageDaoImpl getInstance() {
        return MESSAGE_IMAGE_DAO;
    }

    private MessageImageDaoImpl() {
    }

    @Override
    public Long create(MessageImage messageImage) {
        try (PreparedStatement messageImageStatement = getPreparedStatementWithGeneratedKeys(CREATE)) {
            messageImageStatement.setBinaryStream(1, messageImage.getPhoto());
            messageImageStatement.setLong(2, messageImage.getMessageId());
            if (messageImageStatement.executeUpdate() > 0) {
                ResultSet generatedKeys = messageImageStatement.getGeneratedKeys();
                if (generatedKeys.next()) {
                    messageImage.setId(generatedKeys.getLong(1));
                }
                return messageImage.getId();
            } else {
                throw new DaoException("dao: create message image method failed: no rows affected.");
            }
        } catch (SQLException e) {
            throw new DaoException("dao: create message image method failed: " + e.getMessage());
        }
    }

    @Override
    public Optional<MessageImage> getById(Long id) {
        try (PreparedStatement getMessageImageByIdStatement = getPreparedStatement(GET_BY_ID)) {
            getMessageImageByIdStatement.setLong(1, id);
            ResultSet accountData = getMessageImageByIdStatement.executeQuery();
            if (accountData.next()) {
                MessageImage messageImage = new MessageImage(accountData.getLong("id"),
                        accountData.getBinaryStream("image_blob"), accountData.getLong("message_id"));
                return of(messageImage);
            } else {
                return empty();
            }
        } catch (SQLException e) {
            throw new DaoException("dao: get message image by id method failed: " + e.getMessage());
        }
    }

    @Override
    public List<MessageImage> getAll() {
        return null;
    }

    @Override
    public boolean updateById(Long id, MessageImage messageImage) {
        try (PreparedStatement updateByIdStatement = getPreparedStatement(UPDATE_BY_ID)) {
            updateByIdStatement.setBinaryStream(1, messageImage.getPhoto());
            updateByIdStatement.setLong(2, id);
            return updateByIdStatement.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new DaoException("dao: update message image method failed: ", e);
        }
    }

    @Override
    public boolean deleteById(Long id) {
        try (PreparedStatement deleteByIdStatement = getPreparedStatement(DELETE_BY_ID)) {
            deleteByIdStatement.setLong(1, id);
            return deleteByIdStatement.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new DaoException("dao: delete message image by id method failed: " + e.getMessage());
        }
    }

}
