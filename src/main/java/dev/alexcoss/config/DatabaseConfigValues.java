package dev.alexcoss.config;

import dev.alexcoss.util.JdbcPropertiesReader;

public class DatabaseConfigValues {
    private static String url;
    private static String username;
    private static String password;

    public static String getUrl() {
        if (url == null) {
            url = new JdbcPropertiesReader().getJdbcUrl();
        }
        return url;
    }

    public static String getUsername() {
        if (username == null) {
            username = new JdbcPropertiesReader().getJdbcUsername();
        }
        return username;
    }

    public static String getPassword() {
        if (password == null) {
            password = new JdbcPropertiesReader().getJdbcPassword();
        }
        return password;
    }
}
