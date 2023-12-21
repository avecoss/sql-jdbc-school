package dev.alexcoss.dao.exceptions;

public class CourseDaoException extends RuntimeException{
    public CourseDaoException(String message) {
        super(message);
    }

    public CourseDaoException(String message, Throwable cause) {
        super(message, cause);
    }
}
