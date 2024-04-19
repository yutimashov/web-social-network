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
import static com.getjavajob.training.timashovy.socialnetwork.dao.util.dbutils.dbconnection.ConnectionManager.getPreparedStatementWithGeneratedKeys;

public class MessageImageDaoImpl implements BaseDao<MessageImage> {

    private static final MessageImageDaoImpl MESSAGE_IMAGE_DAO = new MessageImageDaoImpl();
    private static final String CREATE = "INSERT INTO " + MESSAGE_IMAGE_TABLE + " (image_blob, message_id) "
            + "VALUES(?, ?);";

    public static MessageImageDaoImpl getInstance() {
        return MESSAGE_IMAGE_DAO;
    }

    private MessageImageDaoImpl() {
    }

    @Override
    public Long create(MessageImage messageImage) {
        try (PreparedStatement createMessageImageStatement = getPreparedStatementWithGeneratedKeys(CREATE)) {
            createMessageImageStatement.setString(1, messageImage.getPhoto().toString());
            createMessageImageStatement.setLong(2, messageImage.getId());
            if (createMessageImageStatement.executeUpdate() > 0) {
                ResultSet generatedKeys = createMessageImageStatement.getGeneratedKeys();
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
        return Optional.empty();
    }

    @Override
    public List<MessageImage> getAll() {
        return null;
    }

    @Override
    public boolean updateById(Long id, MessageImage messageImage) {
        return false;
    }

    @Override
    public boolean deleteById(Long id) {
        return false;
    }

}
