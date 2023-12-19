package dev.alexcoss.dao;

public class DatabaseManager {
    private static final String SQL_PATH = "src/main/resources/sql/create_tables.sql";

    public void initializeDatabase(){
        new DatabaseInitializer(SQL_PATH);
    }

    public void removeDatabaseTables(){
        new DatabaseTableRemover();
    }
}
