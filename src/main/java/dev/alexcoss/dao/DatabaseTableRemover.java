package dev.alexcoss.dao;

import dev.alexcoss.dao.exceptions.DatabaseInitializerException;
import dev.alexcoss.dao.exceptions.DatabaseRemoverException;
import dev.alexcoss.dao.exceptions.StudentDaoException;
import dev.alexcoss.util.logging.FileHandlerInitializer;

import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DatabaseTableRemover {

    private static final String DROP_TABLE_SQL = "DROP TABLE %s CASCADE";

    private static final Logger LOGGER = Logger.getLogger(DatabaseTableRemover.class.getName());

    static {
        FileHandlerInitializer.initializeFileHandler(LOGGER, "database_remover");
    }

    public DatabaseTableRemover() {
        ConnectionFactory connectionFactory = new PostgreSqlConnectionFactory();
        try (Connection connection = connectionFactory.getConnection()) {
            dropAllTables(connection);
        } catch (SQLException e) {
            handleSQLException(e, "Error deleting database");
        }
    }

    private void dropAllTables(Connection connection) {
        try (ResultSet resultSet = getAllTableNames(connection);
             Statement statement = connection.createStatement()) {

            while (resultSet.next()) {
                String tableName = resultSet.getString("TABLE_NAME");
                statement.executeUpdate(String.format(DROP_TABLE_SQL, tableName));
                LOGGER.info("Dropped table: " + tableName);
            }
        } catch (SQLException e) {
            handleSQLException(e, "Error dropping tables");
        }
    }

    private ResultSet getAllTableNames(Connection connection) {
        try {
            DatabaseMetaData metaData = connection.getMetaData();
            ResultSet resultSet = metaData.getTables(null, null, "%", new String[]{"TABLE"});
            LOGGER.info("Retrieved table names");
            return resultSet;
        } catch (SQLException e) {
            handleSQLException(e, "Error fetching table names");
            throw new DatabaseRemoverException("Error fetching table names");
        }
    }

    private void handleSQLException(SQLException e, String message) {
        LOGGER.log(Level.SEVERE, message, e);
        throw new DatabaseRemoverException(message, e);
    }
}
