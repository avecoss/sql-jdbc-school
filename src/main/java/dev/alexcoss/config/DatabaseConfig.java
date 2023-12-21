package dev.alexcoss.config;

import dev.alexcoss.util.JdbcPropertiesReader;

public enum DatabaseConfig {
    URL, USERNAME, PASSWORD;

    private String value;

    public String getValue() {
        if (value == null) {
            initialize();
        }
        return value;
    }

    private void initialize() {
        JdbcPropertiesReader reader = new JdbcPropertiesReader();
        switch (this) {
            case URL:
                value = reader.getJdbcUrl();
                break;
            case USERNAME:
                value = reader.getJdbcUsername();
                break;
            case PASSWORD:
                value = reader.getJdbcPassword();
                break;
        }
    }
}
