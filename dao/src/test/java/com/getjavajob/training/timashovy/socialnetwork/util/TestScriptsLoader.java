package com.getjavajob.training.timashovy.socialnetwork.util;

import com.getjavajob.training.timashovy.socialnetwork.util.exceptions.DaoTestException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import static com.getjavajob.training.timashovy.socialnetwork.dao.util.dbutils.dbconnection.ConnectionManager.getPreparedStatement;
import static java.lang.System.lineSeparator;
import static java.util.Objects.isNull;
import static java.util.Objects.requireNonNull;

/**
 * Class provides only one public static method for clients {@link TestScriptsLoader#executeScript(String)}.
 * This method is responsible for loading and executing sql script file.
 *
 * @author Yuriy Timashov
 * @since 11.01.2024
 */
public final class TestScriptsLoader {

    /**
     * Class is not supposed to have any instances.
     */
    private TestScriptsLoader() {
        throw new AssertionError();
    }

    /**
     * Read and execute sql script for working with test data.
     * Script located in `filePath` is read line by line, added to string.
     * After that string with script text is executed.
     *
     * @param filePath path of the script file to execute
     */
    public static void executeScript(String filePath) {
        try (PreparedStatement statement = getPreparedStatement(readTestScriptFile(filePath))) {
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new DaoTestException("Cannot execute test script file: " + e.getMessage());
        }
    }

    private static String readTestScriptFile(String filePath) {
        validateScriptFilepath(filePath);
        try (InputStream inputStream = TestScriptsLoader.class.getClassLoader().getResourceAsStream(filePath);
             InputStreamReader inputStreamReader = new InputStreamReader(requireNonNull(inputStream));
             BufferedReader bufferedReader = new BufferedReader(inputStreamReader)) {
            StringBuilder testScript = new StringBuilder();
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                testScript.append(line).append(lineSeparator());
            }
            return testScript.toString();
        } catch (IOException e) {
            throw new DaoTestException("Cannot read test script file: " + e.getMessage());
        }
    }

    private static void validateScriptFilepath(String filePath) {
        if (isNull(filePath) || isNull(TestScriptsLoader.class.getClassLoader().getResourceAsStream(filePath))) {
            throw new DaoTestException("Invalid filePath of script file");
        }
    }

}
