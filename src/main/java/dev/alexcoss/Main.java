package dev.alexcoss;

import dev.alexcoss.dao.DatabaseInitializer;
import dev.alexcoss.dao.DatabaseManager;

public class Main {
    public static void main(String[] args) {
        DatabaseManager manager = new DatabaseManager();
        manager.removeDatabaseTables();
        manager.initializeDatabase();

    }
}