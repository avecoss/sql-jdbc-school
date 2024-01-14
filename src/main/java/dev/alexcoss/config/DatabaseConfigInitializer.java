package dev.alexcoss.config;

import dev.alexcoss.util.JdbcPropertiesReader;

public class DatabaseConfigInitializer {
    public static void initialize(DatabaseConfig config) {
        JdbcPropertiesReader reader = new JdbcPropertiesReader();

        switch (config) {
            case URL -> DatabaseConfigValues.setUrl(reader.getJdbcUrl());
            case USERNAME -> DatabaseConfigValues.setUsername(reader.getJdbcUsername());
            case PASSWORD -> DatabaseConfigValues.setPassword(reader.getJdbcPassword());
        }
    }
}
