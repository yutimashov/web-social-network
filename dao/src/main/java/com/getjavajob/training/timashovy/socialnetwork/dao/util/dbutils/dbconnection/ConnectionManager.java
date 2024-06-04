package com.getjavajob.training.timashovy.socialnetwork.dao.util.dbutils.dbconnection;

import com.getjavajob.training.timashovy.socialnetwork.dao.util.DaoException;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

import static com.getjavajob.training.timashovy.socialnetwork.dao.util.dbutils.dbconnection.PropertiesUtil.getDbConfigProperty;
import static java.lang.Class.forName;
import static java.lang.Integer.parseInt;
import static java.sql.Statement.RETURN_GENERATED_KEYS;
import static java.util.Objects.isNull;

/**
 * Class is responsible for creation and managing connections to DB.
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

    private ConnectionManager() {
    }

    /**
     * Get connection from connection pool and provide it to the client.
     *
     * @return connection to DB
     */
    public static synchronized Connection getConnection() {
        try {
            BlockingQueue<Connection> pool = getConnectionPool();
            System.out.println("About to take connection: size: " + connectionPool.size());
            StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();
            System.out.println("Method take() called from:" + Thread.currentThread().getName());
            for (StackTraceElement element : stackTrace) {
                System.out.println("  " + element.getClassName() + "." + element.getMethodName() + "() at line " + element.getLineNumber());
            }
            return pool.take();
        } catch (InterruptedException e) {
            throw new DaoException("Cannot establish connection to db");
        }
    }

    private static BlockingQueue<Connection> getConnectionPool() {
        if (isNull(connectionPool)) {
            synchronized (ConnectionManager.class) {
                if (isNull(connectionPool)) {
                    initializeConnectionPool();
                }
            }
        }
        return connectionPool;
    }

    /**
     * Get size of connection pool. Create and fill connection pool with connections, available for usage.
     */
    private static void initializeConnectionPool() {
        String poolSize = getDbConfigProperty(POOL_SIZE_KEY);
        int size = poolSize == null ? DEFAULT_POOL_SIZE : parseInt(poolSize);
        connectionPool = new ArrayBlockingQueue<>(size);
        loadDriver();
        for (int i = 0; i < size; i++) {
            connectionPool.add(createWrappedConnection());
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
     * Create new connection to DB using config data.
     * ConnectionWrapper is a fake class, mocking behaviour of true Connection class.
     * The difference with real Connection is in method close().
     * This method of ConnectionWrapper is overridden such that after closing it is returned
     * to the existing connection pool.
     *
     * @return connection instance to database, based on config extracted data
     */
    private static ConnectionWrapper createWrappedConnection() {
        try {
            Connection realConnection = DriverManager.getConnection(
                    getDbConfigProperty(URL_KEY),
                    getDbConfigProperty(LOGIN_KEY),
                    getDbConfigProperty(PASSWORD_KEY)
            );
            return new ConnectionWrapper(realConnection, connectionPool);
        } catch (SQLException e) {
            throw new DaoException("Cannot create connection to db");
        }
    }

}
