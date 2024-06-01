package com.getjavajob.training.timashovy.socialnetwork.dao.util.dbutils.dbconnection;

import com.getjavajob.training.timashovy.socialnetwork.dao.util.DaoException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import static com.getjavajob.training.timashovy.socialnetwork.dao.util.dbutils.dbconnection.ConnectionManager.getConnection;
import static com.getjavajob.training.timashovy.socialnetwork.dao.util.dbutils.dbconnection.ConnectionManager.getPoolSize;
import static java.sql.Statement.RETURN_GENERATED_KEYS;

/**
 * Class is responsible for managing transactions in application.
 * It provides approach with using ThreadLocal as a guarantee that any connection within transaction will be belonged
 * to its own thread. It helps to manage transaction within different class of different layers (dao and service).
 */
public class TransactionManager implements AutoCloseable {

    private static final ThreadLocal<Connection> threadLocalConnection = new ThreadLocal<>();
    private static volatile TransactionManager instance;

    private TransactionManager() {
    }

    public static TransactionManager getInstance() {
        if (instance == null) {
            synchronized (TransactionManager.class) {
                if (instance == null) {
                    instance = new TransactionManager();
                }
            }
        }
        return instance;
    }

    /**
     * @return connection from {@link com.getjavajob.training.timashovy.socialnetwork.dao.util.dbutils.dbconnection.ConnectionManager} class
     */
    public Connection getTransactionalConnection() {
        Connection connection = threadLocalConnection.get();
        if (connection == null) {
            connection = getConnection();
            threadLocalConnection.set(connection);
        }
        return connection;
    }

    // взял соединение и закрыл его. Было 9 свободных, стало 10 свободных.
    public PreparedStatement getTransactionalPreparedStatement(String query) throws SQLException {
        return getTransactionalConnection().prepareStatement(query);
    }

    public PreparedStatement getGetTransactionalPreparedStatementWithGeneratedKeys(String query) throws SQLException {
        return getTransactionalConnection().prepareStatement(query, RETURN_GENERATED_KEYS);
    }

    public void beginTransaction(Connection connection) {
        try {
            connection.setAutoCommit(false);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void commitTransaction(Connection connectionWrapper) {
        try {
            connectionWrapper.commit();
            connectionWrapper.setAutoCommit(true);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void rollbackTransaction(Connection connectionWrapper) {
        try {
            connectionWrapper.rollback();
            connectionWrapper.setAutoCommit(true);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void close() throws Exception {
        Connection connection = getTransactionalConnection();
        if (connection != null) {
            connection.close();
            threadLocalConnection.remove();
        }
    }

}
