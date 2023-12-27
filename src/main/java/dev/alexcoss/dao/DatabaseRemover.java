package dev.alexcoss.dao;

import dev.alexcoss.dao.exceptions.DaoException;
import dev.alexcoss.dao.exceptions.DatabaseManagerException;
import dev.alexcoss.util.logging.FileHandlerInitializer;

import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DatabaseRemover extends DatabaseManager implements Remover{

    private static final String DROP_TABLE_SQL = "DROP TABLE %s CASCADE";

    public DatabaseRemover() {
        super(DatabaseRemover.class.getName());
    }

    @Override
    public void removeDatabase() {
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
                dropTable(statement, tableName);
            }
        } catch (SQLException e) {
            handleSQLException(e, "Error dropping tables");
        }
    }

    private void dropTable(Statement statement, String tableName) throws SQLException {
        statement.executeUpdate(String.format(DROP_TABLE_SQL, tableName));
        logger.info("Dropped table: " + tableName);
    }

    private ResultSet getAllTableNames(Connection connection) {
        try {
            DatabaseMetaData metaData = connection.getMetaData();
            ResultSet resultSet = metaData.getTables(null, null, "%", new String[]{"TABLE"});
            logger.info("Retrieved table names");
            return resultSet;
        } catch (SQLException e) {
            throw new DatabaseManagerException("Error fetching table names", e);
        }
    }
}