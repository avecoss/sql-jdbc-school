package dev.alexcoss.dao;

import dev.alexcoss.dao.exceptions.CourseDaoException;
import dev.alexcoss.model.Course;
import dev.alexcoss.util.logging.FileHandlerInitializer;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class CourseDao {

    private static final String INSERT_SQL = "INSERT INTO courses (course_name, course_description) VALUES (?, ?)";

    private static final Logger LOGGER = Logger.getLogger(DatabaseInitializer.class.getName());

    static {
        FileHandlerInitializer.initializeFileHandler(LOGGER, "course_dao");
    }

    private final ConnectionFactory connectionFactory = new PostgreSqlConnectionFactory();

    public void addGroups(List<Course> courseList) {
        try (Connection connection = connectionFactory.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(INSERT_SQL)) {

            for (Course course : courseList) {
                preparedStatement.setString(1, course.getName());
                preparedStatement.setString(2, course.getDescription());
                preparedStatement.addBatch();
            }

            preparedStatement.executeBatch();
        } catch (SQLException e) {
            handleSQLException(e, "Error adding courses to database", INSERT_SQL, courseList);
        }
    }

    private void handleSQLException(SQLException e, String message, String sql, Object... params) {
        String fullMessage = String.format("%s\nSQL: %s\nParameters: %s", message, sql, Arrays.toString(params));
        LOGGER.log(Level.SEVERE, fullMessage, e);
        throw new CourseDaoException(fullMessage, e);
    }
}
