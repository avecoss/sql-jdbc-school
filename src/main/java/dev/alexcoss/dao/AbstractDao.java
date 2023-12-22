package dev.alexcoss.dao;

import dev.alexcoss.dao.exceptions.DaoException;
import dev.alexcoss.util.logging.FileHandlerInitializer;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;

public abstract class AbstractDao<T, E> implements Dao<T, E> {
    protected final ConnectionFactory connectionFactory;
    protected final Logger logger;

    public AbstractDao(String loggerName) {
        this.connectionFactory = new PostgreSqlConnectionFactory();
        this.logger = Logger.getLogger(loggerName);
        FileHandlerInitializer.initializeFileHandler(logger, loggerName);
    }

    protected abstract T resultSetToObject(ResultSet resultSet) throws SQLException;

    protected void handleSQLException(SQLException e, String message, String sql, Object... params) {
        String fullMessage = String.format("%s\nSQL: %s\nParameters: %s", message, sql, Arrays.toString(params));
        logger.log(Level.SEVERE, fullMessage, e);
        throw new DaoException(fullMessage, e);
    }
}
