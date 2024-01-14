package dev.alexcoss.dao;

import dev.alexcoss.config.DatabaseConfigValues;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class PostgreSqlConnectionFactory implements ConnectionFactory {

    private static final String URL = DatabaseConfigValues.getUrl();
    private static final String USERNAME = DatabaseConfigValues.getUsername();
    private static final String PASSWORD = DatabaseConfigValues.getPassword();


    @Override
    public Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USERNAME, PASSWORD);
    }
}