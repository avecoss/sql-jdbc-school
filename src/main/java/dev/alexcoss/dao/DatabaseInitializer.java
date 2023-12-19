package dev.alexcoss.dao;

import dev.alexcoss.dao.exceptions.DatabaseInitializerException;
import dev.alexcoss.logging.FileHandlerInitializer;
import dev.alexcoss.resourceUtils.FileReader;
import dev.alexcoss.resourceUtils.JdbcPropertiesReader;

import java.sql.*;
import java.util.logging.*;

public class DatabaseInitializer {
    private static final Logger logger = Logger.getLogger(DatabaseInitializer.class.getName());

    static {
        FileHandlerInitializer.initializeFileHandler(logger, "database_initializer");
    }

    public DatabaseInitializer(String sqlPath) {
        try (Connection connection = createConnection()) {
            executeSqlScript(connection, sqlPath);
        } catch (SQLException e) {
            String message = "Error initializing database";
            logger.log(Level.SEVERE, message, e);
            throw new DatabaseInitializerException(message, e);
        }
    }

    private Connection createConnection() {
        JdbcPropertiesReader reader = new JdbcPropertiesReader();

        String url = reader.getJdbcUrl();
        String username = reader.getJdbcUsername();
        String password = reader.getJdbcPassword();

        try {
            Connection connection = DriverManager.getConnection(url, username, password);
            logger.info("Database connection established");
            return connection;
        } catch (SQLException e) {
            throw new DatabaseInitializerException("Error creating database connection", e);
        }
    }

    private void executeSqlScript(Connection connection, String sqlPath) throws SQLException {
        String sqlScript = readSqlScript(sqlPath);

        try (PreparedStatement preparedStatement = connection.prepareStatement(sqlScript)) {
            preparedStatement.executeUpdate();
            logger.info("Executed SQL script: " + sqlScript);
        } catch (SQLException e) {
            String message = "Error executing SQL script ";
            logger.log(Level.SEVERE, message + sqlScript, e);
            throw new DatabaseInitializerException(message + sqlScript, e);
        }
    }

    private String readSqlScript(String sqlPath) {
        FileReader fileReader = new FileReader();

        return fileReader.fileRead(sqlPath, bufferedReader -> {
            StringBuilder result = new StringBuilder();
            bufferedReader.lines().forEach(result::append);
            return result.toString();
        });
    }
}
