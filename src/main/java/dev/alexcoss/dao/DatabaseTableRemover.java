package dev.alexcoss.dao;

import dev.alexcoss.dao.exceptions.DatabaseRemoverException;
import dev.alexcoss.logging.FileHandlerInitializer;
import dev.alexcoss.resourceUtils.JdbcPropertiesReader;

import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DatabaseTableRemover {
    private static final Logger logger = Logger.getLogger(DatabaseTableRemover.class.getName());

    static {
        FileHandlerInitializer.initializeFileHandler(logger, "database_remover");
    }

    public DatabaseTableRemover() {
        try (Connection connection = createConnection()) {
            dropAllTables(connection);
        } catch (SQLException e) {
            String message = "Error deleting database";
            logger.log(Level.SEVERE, message, e);
            throw new DatabaseRemoverException(message, e);
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
            throw new DatabaseRemoverException("Error creating database connection", e);
        }
    }

    private void dropAllTables(Connection connection) {
        ResultSet resultSet = getAllTableNames(connection);

        try (Statement statement = connection.createStatement()) {
            while (resultSet.next()) {
                String tableName = resultSet.getString("TABLE_NAME");
                statement.executeUpdate("DROP TABLE " + tableName + " CASCADE");
                logger.info("Dropped table: " + tableName);
            }
        } catch (SQLException e) {
            throw new DatabaseRemoverException("Error dropping tables ", e);
        }
    }

    private ResultSet getAllTableNames(Connection connection) {
        try {
            DatabaseMetaData metaData = connection.getMetaData();
            ResultSet resultSet = metaData.getTables(null, null, "%", new String[]{"TABLE"});
            logger.info("Retrieved table names");
            return resultSet;
        } catch (SQLException e) {
            throw new DatabaseRemoverException("Error fetching table names", e);
        }
    }
}
