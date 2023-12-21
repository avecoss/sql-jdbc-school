package dev.alexcoss;

import dev.alexcoss.dao.DatabaseManager;
import dev.alexcoss.service.GenerateStartData;

public class Main {
    public static void main(String[] args) {
        DatabaseManager manager = new DatabaseManager();
        manager.removeDatabaseTables();
        manager.initializeDatabase();

        GenerateStartData generateStartData = new GenerateStartData();
        generateStartData.generateDataForDatabase();
    }
}