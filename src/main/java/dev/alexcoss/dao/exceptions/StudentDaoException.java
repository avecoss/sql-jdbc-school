package dev.alexcoss.dao.exceptions;

public class StudentDaoException extends RuntimeException{
    public StudentDaoException(String message) {
        super(message);
    }

    public StudentDaoException(String message, Throwable cause) {
        super(message, cause);
    }
}
