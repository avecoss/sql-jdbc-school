package dev.alexcoss;

import dev.alexcoss.dao.*;
import dev.alexcoss.service.GenerateStartingData;

public class Main {
    public static void main(String[] args) {
        Remover removerManager = new DatabaseRemover();
        removerManager.removeDatabase();

        Initializer initializerManager = new DatabaseInitializer();
        initializerManager.initializeDatabase();

        GenerateStartingData generateStartingData = new GenerateStartingData();
        generateStartingData.generateDataForDatabase();
    }
}