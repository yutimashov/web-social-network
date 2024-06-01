package com.getjavajob.training.timashovy.socialnetwork.dao.util.dbutils.dbconnection;

import java.sql.SQLException;

/**
 * Class is responsible for managing transactions in application.
 * It provides approach with using ThreadLocal as a guarantee that any connection within transaction will be belonged
 * to its own thread. It helps to manage transaction within different class of different layers (dao and service).
 */
public class TransactionManager implements AutoCloseable {

    private static final ThreadLocal<ConnectionWrapper> threadLocalConnection = new ThreadLocal<>();

    /**
     * Class is not intended to have any instances. It provides util methods for working with transactions.
     */
    private TransactionManager() {
        throw new AssertionError();
    }

    /**
     * @return connection from {@link com.getjavajob.training.timashovy.socialnetwork.dao.util.dbutils.dbconnection.ConnectionManager} class
     */
    public static ConnectionWrapper getConnection() {
        ConnectionWrapper connection = threadLocalConnection.get();
        if (connection == null) {
            connection = ConnectionManager.getConnection();
            threadLocalConnection.set(connection);
        }
        return connection;
    }

    public static void beginTransaction() {
        ConnectionWrapper connection = threadLocalConnection.get();
        try {
            connection.setAutoCommit(false);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static void commitTransaction() {
        ConnectionWrapper connection = getConnection();
        try {
            connection.commit();
            connection.setAutoCommit(true);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static void rollbackTransaction() {
        ConnectionWrapper connection = getConnection();
        try {
            connection.rollback();
            connection.setAutoCommit(true);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void close() throws Exception {
        ConnectionWrapper connection = threadLocalConnection.get();
        if (connection != null) {
            connection.close();
            threadLocalConnection.remove();
        }
    }

}
