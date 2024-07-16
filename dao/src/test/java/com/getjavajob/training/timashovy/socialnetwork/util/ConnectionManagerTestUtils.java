package com.getjavajob.training.timashovy.socialnetwork.util;

import com.getjavajob.training.timashovy.socialnetwork.util.exceptions.DaoTestException;
import org.mockito.MockedStatic;

import java.sql.Connection;
import java.sql.SQLException;

import static java.lang.Class.forName;
import static java.sql.DriverManager.getConnection;
import static org.mockito.Mockito.mockStatic;

/**
 * Class provides public methods for mocking connection, since in development environment
 * connection is obtained through DataSource with related configuration, working with tests (using H2 DB,
 * rather than Postgres), there is necessity to gain separate mocking connection.
 */
public class ConnectionManagerTestUtils {

    private static final String h2DbClassName = "org.h2.Driver";
    private static final String h2DbUrl = "jdbc:h2:mem:test;DB_CLOSE_DELAY=-1";
    private static final String h2DbUserName = "admin";
    private static final String h2DbPassword = "admin";
    private static MockedStatic<JdbcTemplateManager> mocked;

    /**
     * Class is not intended to have any instances.
     */
    private ConnectionManagerTestUtils() {
        throw new AssertionError();
    }

    /**
     * Mock Postgres connection within inner calls of methods working with connections
     */
    public static void mockConnectionManager() {
        mocked = mockStatic(JdbcTemplateManager.class);
        mocked.when(JdbcTemplateManager::getConnection).thenReturn(getH2Connection());
    }

    /**
     * @return connection to test database
     */
    public static Connection getH2Connection() {
        try {
            forName(h2DbClassName);
            return getConnection(h2DbUrl, h2DbUserName, h2DbPassword);
        } catch (ClassNotFoundException | SQLException e) {
            throw new DaoTestException("Failed to create H2DB connection" + e.getMessage());
        }
    }

    public static void clearConnectionManagerMocks() {
        mocked.close();
    }

}
