package com.getjavajob.training.timashovy.socialnetwork.util;

import com.getjavajob.training.timashovy.socialnetwork.dao.util.dbutils.dbconnection.ConnectionManager;
import org.mockito.MockedStatic;

import static com.getjavajob.training.timashovy.socialnetwork.util.TestScriptsLoader.getH2Connection;
import static org.mockito.Mockito.mockStatic;

/**
 * Class provides 1 public method for mocking connection, since in development environment
 * connection is obtained through DataSource with related configuration, working with tests (using H2 DB,
 * rather than Postgres), there is necessity to gain separate mocking connection.
 */
public class ConnectionManagerTestUtils {

    private static MockedStatic<ConnectionManager> mocked;

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
        mocked = mockStatic(ConnectionManager.class);
        mocked.when(ConnectionManager::getConnection).thenReturn(getH2Connection());
    }

    public static void clearConnectionManagerMocks() {
        mocked.close();
    }

}
