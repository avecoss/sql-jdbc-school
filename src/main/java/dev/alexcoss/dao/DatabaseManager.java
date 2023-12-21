package dev.alexcoss.dao;

public class DatabaseManager {
    private static final String SQL_PATH_CREATE_TABLES = "src/main/resources/sql/create_tables.sql";

    public void initializeDatabase(){
        new DatabaseInitializer(SQL_PATH_CREATE_TABLES);
    }

    public void removeDatabaseTables(){
        new DatabaseTableRemover();
    }
}
