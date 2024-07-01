package com.getjavajob.training.timashovy.socialnetwork.dao.util.dbutils.dbconnection;

import com.getjavajob.training.timashovy.socialnetwork.dao.util.DaoException;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

/**
 * Class is responsible for creation and managing connections to DB.
 *
 * @author Yuriy Timashov
 * @since 01.03.2023
 */
public class ConnectionManager {

    private static final String ENV_CONTEXT = "java:/comp/env";
    private static final String DATASOURCE_NAME = "jdbc/socialNetwork";

    /**
     * Class is not intended to have any instances.
     */
    private ConnectionManager() {
        throw new AssertionError();
    }

    /**
     * Get connection from connection pool and provide it to the client.
     *
     * @return connection to DB
     */
    public static Connection getConnection() {
        try {
            Context initContext = new InitialContext();
            Context envContext = (Context) initContext.lookup(ENV_CONTEXT);
            DataSource ds = (DataSource) envContext.lookup(DATASOURCE_NAME);
            return ds.getConnection();
        } catch (NamingException | SQLException e) {
            throw new DaoException("Cannot get connection to DB: " + e.getMessage());
        }
    }

}
