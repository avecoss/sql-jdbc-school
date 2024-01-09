package dev.alexcoss.config;

import dev.alexcoss.util.JdbcPropertiesReader;

public class DatabaseConfigInitializer {
    public static void initialize(DatabaseConfig config) {
        JdbcPropertiesReader reader = new JdbcPropertiesReader();
        switch (config) {
            case URL:
                config.setValue(reader.getJdbcUrl());
                break;
            case USERNAME:
                config.setValue(reader.getJdbcUsername());
                break;
            case PASSWORD:
                config.setValue(reader.getJdbcPassword());
                break;
        }
    }
}
