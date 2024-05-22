package com.getjavajob.training.timashovy.socialnetwork.dao.util.dbutils.dbconnection;

import com.getjavajob.training.timashovy.socialnetwork.dao.util.DaoException;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

import static com.getjavajob.training.timashovy.socialnetwork.dao.util.dbutils.dbconnection.PropertiesUtil.getProperty;
import static java.lang.Class.forName;
import static java.lang.Integer.parseInt;
import static java.sql.Statement.RETURN_GENERATED_KEYS;

/**
 * Class is responsible for creation and managing connections to DB.
 * It provides public methods for clients - {@link ConnectionManager#getPreparedStatement(String)} and
 * {@link ConnectionManager#getPreparedStatementWithGeneratedKeys(String)}.
 * All the work with connections to DB is organized via {@link ConnectionManager#connectionPool}.
 *
 * @author Yuriy Timashov
 * @since 01.03.2023
 */
public final class ConnectionManager {

    private static final String URL_KEY = "db.url";
    private static final String LOGIN_KEY = "db.login";
    private static final String PASSWORD_KEY = "db.password";
    private static final String POOL_SIZE_KEY = "db.pool.size";
    private static final String DRIVER_CLASS = "org.postgresql.Driver";
    private static volatile BlockingQueue<Connection> connectionPool;
    private static final int DEFAULT_POOL_SIZE = 10;

    /**
     * Class is not considered to have any instances.
     * It provides only one static util method.
     *
     * @throws AssertionError trying to create instance
     */
    private ConnectionManager() {
        throw new AssertionError();
    }

    public static PreparedStatement getPreparedStatement(String query) throws SQLException {
        try (Connection connection = getConnection()) {
            return connection.prepareStatement(query);
        } catch (SQLException e) {
            throw new DaoException("dao: create prepared statement failed: " + e.getMessage());
        }
    }

    public static PreparedStatement getPreparedStatementWithGeneratedKeys(String query) throws SQLException {
        try (Connection connection = getConnection()) {
            return connection.prepareStatement(query, RETURN_GENERATED_KEYS);
        } catch (SQLException e) {
            throw new DaoException("dao: create prepared statement failed: " + e.getMessage());
        }
    }

    /**
     * Method for further manipulation with created connection to db.
     *
     * @return connection to DB
     */
    private static Connection getConnection() {
        try {
            return getConnectionPool().take();
        } catch (InterruptedException e) {
            throw new DaoException("Cannot establish connection to db");
        }
    }

    private static BlockingQueue<Connection> getConnectionPool() {
        if (connectionPool == null) {
            initializeConnectionPool();
        }
        return connectionPool;
    }

    private static void initializeConnectionPool() {
        String poolSize = getProperty(POOL_SIZE_KEY);
        int size = poolSize == null ? DEFAULT_POOL_SIZE : parseInt(poolSize);
        connectionPool = new ArrayBlockingQueue<>(size);
        loadDriver();
        for (int i = 0; i < size; i++) {
            connectionPool.add(createConnection());
        }
    }

    private static void loadDriver() {
        try {
            forName(DRIVER_CLASS);
        } catch (ClassNotFoundException e) {
            throw new DaoException("Cannot load db driver");
        }
    }

    /**
     * This method is private in order to avoid any custom creation of connection to DB.
     * It only allows to work with connection through connection pool.
     * So this method is used to create connections to DB with connection pool.
     * This method uses fake connection - instance of ConnectionWrapper in order to
     * return used connection to connection pool, rather than just close it.
     *
     * @return connection instance to database, based on config extracted data
     */
    private static Connection createConnection() {
        try {
            Connection realConnection = DriverManager.getConnection(getProperty(URL_KEY), getProperty(LOGIN_KEY),
                    getProperty(PASSWORD_KEY));
            return new ConnectionWrapper(realConnection, connectionPool);
        } catch (SQLException e) {
            throw new DaoException("Cannot create connection to db");
        }
    }

}
