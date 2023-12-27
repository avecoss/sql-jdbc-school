package dev.alexcoss.dao;

import dev.alexcoss.dao.exceptions.DatabaseManagerException;
import dev.alexcoss.util.logging.FileHandlerInitializer;
import dev.alexcoss.util.FileReader;

import java.sql.*;
import java.util.logging.*;

public class DatabaseInitializer extends DatabaseManager implements Initializer {

    private static final String SQL_PATH_CREATE_TABLES = "src/main/resources/sql/create_tables.sql";

    public DatabaseInitializer() {
        super(DatabaseInitializer.class.getName());
    }

    @Override
    public void initializeDatabase() {
        try (Connection connection = connectionFactory.getConnection()) {
            executeSqlScript(connection);
        } catch (SQLException e) {
            handleSQLException(e, "Error initializing database");
        }
    }

    private void executeSqlScript(Connection connection) throws SQLException {
        String sqlScript = readSqlScript();

        try (PreparedStatement preparedStatement = connection.prepareStatement(sqlScript)) {
            preparedStatement.executeUpdate();
            logger.info("Executed SQL script: " + sqlScript);
        } catch (SQLException e) {
            handleSQLException(e, "Error executing SQL script", sqlScript);
        }
    }

    private String readSqlScript() {
        FileReader fileReader = new FileReader();

        return fileReader.fileRead(SQL_PATH_CREATE_TABLES, bufferedReader -> {
            StringBuilder result = new StringBuilder();
            bufferedReader.lines().forEach(result::append);
            return result.toString();
        });
    }
}
