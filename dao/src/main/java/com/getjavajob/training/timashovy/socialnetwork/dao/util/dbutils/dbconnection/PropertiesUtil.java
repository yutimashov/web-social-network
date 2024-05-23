package com.getjavajob.training.timashovy.socialnetwork.dao.util.dbutils.dbconnection;

import com.getjavajob.training.timashovy.socialnetwork.dao.util.DaoException;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Class for extracting values from db config files essential for establishing connection to database.
 * Class provides only one static method for clients {@link PropertiesUtil#getDbConfigProperty(String)}.
 * This method is responsible for extracting desirable value from config file.
 *
 * @author Timashov Yuriy
 * @version 1.0
 * @since 1.0.0
 */
public final class PropertiesUtil {

    private static final Properties PROPERTIES = new Properties();
    public static final String PROPERTIES_FILE_NAME = "db.properties";

    /**
     * Class is not considered to have any instances.
     * It provides only one static util method.
     *
     * @throws AssertionError trying to create instance
     */
    private PropertiesUtil() {
        throw new AssertionError();
    }

    public static String getDbConfigProperty(String key) {
        if (PROPERTIES.isEmpty()) {
            loadProperties();
        }
        return PROPERTIES.getProperty(key);
    }

    private static void loadProperties() {
        try (InputStream inputStream = PropertiesUtil.class.getClassLoader().getResourceAsStream(PROPERTIES_FILE_NAME)) {
            PROPERTIES.load(inputStream);
        } catch (IOException e) {
            throw new DaoException("Cannot load properties for establishing connection to db");
        }
    }

}
