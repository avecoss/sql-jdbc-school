package dev.alexcoss.dao.exceptions;

public class GroupDaoException extends RuntimeException{
    public GroupDaoException(String message) {
        super(message);
    }

    public GroupDaoException(String message, Throwable cause) {
        super(message, cause);
    }
}
