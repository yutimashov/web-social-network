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

import static com.getjavajob.training.timashovy.socialnetwork.dao.util.dbutils.TableNames.PERSONAL_MESSAGE_TABLE;
import static com.getjavajob.training.timashovy.socialnetwork.dao.util.dbutils.dbconnection.ConnectionManager.getPreparedStatement;
import static com.getjavajob.training.timashovy.socialnetwork.dao.util.dbutils.dbconnection.ConnectionManager.getPreparedStatementWithGeneratedKeys;

public class PersonalMessageDaoImpl implements MessageDao {

    private static final String CREATE = "INSERT INTO " + PERSONAL_MESSAGE_TABLE + " (account_author_id, " +
            "destination_id, message_text, message_image) VALUES (?, ?, ?, ?);";
    private static final String GET_ALL_ACCOUNT_IDS = "SELECT DISTINCT account_author_id FROM " + PERSONAL_MESSAGE_TABLE
            + " WHERE destination_id = ?;";
    private static final PersonalMessageDaoImpl PERSONAL_MESSAGE_DAO = new PersonalMessageDaoImpl();

    private PersonalMessageDaoImpl() {
    }

    public static PersonalMessageDaoImpl getInstance() {
        return PERSONAL_MESSAGE_DAO;
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
        return Optional.empty();
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
        try (PreparedStatement preparedStatement = getPreparedStatement(GET_ALL_ACCOUNT_IDS)) {
            preparedStatement.setLong(1, accountId);
            List<Long> ids = new ArrayList<>();
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                ids.add(resultSet.getLong("account_author_id"));
            }
            return ids;
        } catch (SQLException e) {
            throw new DaoException("dao: get all accounts ids method failed: " + e.getMessage());
        }
    }

}
