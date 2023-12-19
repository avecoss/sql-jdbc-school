package dev.alexcoss.dao.exceptions;

public class DatabaseInitializerException extends RuntimeException {
    public DatabaseInitializerException(String message) {
        super(message);
    }

    public DatabaseInitializerException(String message, Throwable cause) {
        super(message, cause);
    }
}
