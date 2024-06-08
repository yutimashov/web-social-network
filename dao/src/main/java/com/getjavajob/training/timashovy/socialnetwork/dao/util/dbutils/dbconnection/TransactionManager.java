package com.getjavajob.training.timashovy.socialnetwork.dao.util.dbutils.dbconnection;

import com.getjavajob.training.timashovy.socialnetwork.dao.util.DaoException;

import java.sql.Connection;
import java.sql.SQLException;

import static com.getjavajob.training.timashovy.socialnetwork.dao.util.dbutils.dbconnection.ConnectionManager.getConnection;
import static java.util.Objects.isNull;

/**
 * Class is responsible for managing transactions in application.
 * It provides approach with using ThreadLocal as a guarantee that any connection within transaction will be belonged
 * to its own thread. It helps to manage transaction within different class of different layers (dao and service).
 */
public class TransactionManager {

    private static final ThreadLocal<Connection> threadLocalConnection = new ThreadLocal<>();

    public TransactionManager() {
    }

    /**
     * @return connection from {@link com.getjavajob.training.timashovy.socialnetwork.dao.util.dbutils.dbconnection.ConnectionManager} class
     */
    public Connection getTransactionalConnection() {
        Connection connection = threadLocalConnection.get();
        if (isNull(connection)) {
            connection = getConnection();
            threadLocalConnection.set(connection);
        }
        return connection;
    }

    public void beginTransaction() {
        try {
            getTransactionalConnection().setAutoCommit(false);
        } catch (SQLException e) {
            throw new DaoException("Transaction failed: cannot begin transaction: " + e.getMessage());
        }
    }

    public void commitTransaction() {
        try {
            getTransactionalConnection().commit();
            closeTransactionConnection();
        } catch (SQLException e) {
            throw new DaoException("Transaction failed: cannot commit transaction: " + e.getMessage());
        }
    }

    public void rollbackTransaction() {
        try {
            getTransactionalConnection().rollback();
            closeTransactionConnection();
        } catch (SQLException e) {
            throw new DaoException("Transaction failed: cannot rollback transaction: " + e.getMessage());
        }
    }

    /**
     * Close transactional connection, remove value from {@link TransactionManager#threadLocalConnection}
     * variable and return connection to connection pool.
     *
     * @throws DaoException if connection cannot be closed
     */
    private void closeTransactionConnection() {
        try {
            getTransactionalConnection().close();
            threadLocalConnection.remove();
        } catch (SQLException e) {
            throw new DaoException("Transaction failed: cannot close transaction: " + e.getMessage());
        }
    }

}
