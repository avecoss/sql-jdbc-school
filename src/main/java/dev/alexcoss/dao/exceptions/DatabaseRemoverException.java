package dev.alexcoss.dao.exceptions;

public class DatabaseRemoverException extends RuntimeException {
    public DatabaseRemoverException(String message) {
        super(message);
    }

    public DatabaseRemoverException(String message, Throwable cause) {
        super(message, cause);
    }
}
