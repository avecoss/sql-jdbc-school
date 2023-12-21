package dev.alexcoss.dao;

import dev.alexcoss.dao.exceptions.DatabaseInitializerException;
import dev.alexcoss.util.logging.FileHandlerInitializer;
import dev.alexcoss.util.FileReader;

import java.sql.*;
import java.util.logging.*;

public class DatabaseInitializer {
    private static final Logger LOGGER = Logger.getLogger(DatabaseInitializer.class.getName());

    static {
        FileHandlerInitializer.initializeFileHandler(LOGGER, "database_initializer");
    }

    public DatabaseInitializer(String sqlPath) {
        ConnectionFactory connectionFactory = new PostgreSqlConnectionFactory();
        try (Connection connection = connectionFactory.getConnection()) {
            executeSqlScript(connection, sqlPath);
        } catch (SQLException e) {
            handleSQLException(e, "Error initializing database");
        }
    }

    private void executeSqlScript(Connection connection, String sqlPath) throws SQLException {
        String sqlScript = readSqlScript(sqlPath);

        try (PreparedStatement preparedStatement = connection.prepareStatement(sqlScript)) {
            preparedStatement.executeUpdate();
            LOGGER.info("Executed SQL script: " + sqlScript);
        } catch (SQLException e) {
            handleSQLException(e, "Error executing SQL script", sqlScript);
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

    private void handleSQLException(SQLException e, String message, String sql) {
        String fullMessage = String.format("%s\nSQL: %s", message, sql);
        throwException(e, fullMessage);
    }

    private void handleSQLException(SQLException e, String message) {
        throwException(e, message);
    }

    private void throwException(SQLException e, String message) {
        LOGGER.log(Level.SEVERE, message, e);
        throw new DatabaseInitializerException(message, e);
    }
}
