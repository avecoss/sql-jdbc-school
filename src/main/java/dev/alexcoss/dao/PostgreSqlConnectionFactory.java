package dev.alexcoss.dao;

import dev.alexcoss.config.DatabaseConfig;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class PostgreSqlConnectionFactory implements ConnectionFactory {

    private static final String URL = DatabaseConfig.URL.getValue();
    private static final String USERNAME = DatabaseConfig.USERNAME.getValue();
    private static final String PASSWORD = DatabaseConfig.PASSWORD.getValue();

    @Override
    public Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USERNAME, PASSWORD);
    }
}