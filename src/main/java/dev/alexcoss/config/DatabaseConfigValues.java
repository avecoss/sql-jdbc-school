package dev.alexcoss.config;

public class DatabaseConfigValues {
    private static String url;
    private static String username;
    private static String password;

    public static String getUrl() {
        if (url == null) {
            DatabaseConfigInitializer.initialize(DatabaseConfig.URL);
        }
        return url;
    }

    public static String getUsername() {
        if (username == null) {
            DatabaseConfigInitializer.initialize(DatabaseConfig.USERNAME);
        }
        return username;
    }

    public static String getPassword() {
        if (password == null) {
            DatabaseConfigInitializer.initialize(DatabaseConfig.PASSWORD);
        }
        return password;
    }

    static void setUrl(String value) {
        url = value;
    }

    static void setUsername(String value) {
        username = value;
    }

    static void setPassword(String value) {
        password = value;
    }
}
