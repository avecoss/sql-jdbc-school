package dev.alexcoss.resourceUtils.exceptions;

public class JdbcPropertiesReaderException extends RuntimeException {

    public JdbcPropertiesReaderException(String message) {
        super(message);
    }

    public JdbcPropertiesReaderException(String message, Throwable cause) {
        super(message, cause);
    }
}
