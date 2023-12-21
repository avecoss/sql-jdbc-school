package dev.alexcoss.dao;

import dev.alexcoss.dao.exceptions.StudentDaoException;
import dev.alexcoss.model.Student;
import dev.alexcoss.util.logging.FileHandlerInitializer;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class StudentDao {

    private static final String INSERT_SQL = "INSERT INTO students (group_id, first_name, last_name) VALUES (?, ?, ?)";
    private static final String UPDATE_SQL = "UPDATE students SET group_id = ?, first_name = ?, last_name = ? WHERE student_id = ?";
    private static final String SELECT_BY_ID_SQL = "SELECT * FROM students WHERE student_id = ?";
    private static final String SELECT_ALL_SQL = "SELECT * FROM students";

    private static final Logger LOGGER = Logger.getLogger(DatabaseInitializer.class.getName());

    static {
        FileHandlerInitializer.initializeFileHandler(LOGGER, "student_dao");
    }

    private final ConnectionFactory connectionFactory = new PostgreSqlConnectionFactory();

    public void addStudent(Student student) {
        try (Connection connection = connectionFactory.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(INSERT_SQL)) {

            preparedStatement.setInt(1, student.getGroupId());
            preparedStatement.setString(2, student.getFirstName());
            preparedStatement.setString(3, student.getLastName());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            handleSQLException(e, "Error adding student to database", INSERT_SQL, student);
        }
    }

    public void updateStudent(Student student) {
        try (Connection connection = connectionFactory.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_SQL)) {

            preparedStatement.setInt(1, student.getGroupId());
            preparedStatement.setString(2, student.getFirstName());
            preparedStatement.setString(3, student.getLastName());
            preparedStatement.setInt(4, student.getId());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            handleSQLException(e, "Error updating student to database", UPDATE_SQL, student);
        }
    }

    public Student getStudentById(int studentId) {
        try (Connection connection = connectionFactory.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_BY_ID_SQL)) {

            preparedStatement.setInt(1, studentId);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    return getStudent(resultSet);
                }
            }
        } catch (SQLException e) {
            handleSQLException(e, "Error getting student by id from database", SELECT_BY_ID_SQL, studentId);
        }

        return null;
    }

    public List<Student> getAllStudents() {
        List<Student> students = new ArrayList<>();

        try (Connection connection = connectionFactory.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ALL_SQL);
             ResultSet resultSet = preparedStatement.executeQuery()) {

            while (resultSet.next()) {
                Student student = getStudent(resultSet);
                students.add(student);
            }
        } catch (SQLException e) {
            handleSQLException(e, "Error getting students from database", SELECT_ALL_SQL);
        }

        return students;
    }

    public void addStudents(List<Student> studentList) {
        try (Connection connection = connectionFactory.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(INSERT_SQL)) {

            for (Student student : studentList) {
                preparedStatement.setInt(1, student.getGroupId());
                preparedStatement.setString(2, student.getFirstName());
                preparedStatement.setString(3, student.getLastName());
                preparedStatement.addBatch();
            }

            preparedStatement.executeBatch();
        } catch (SQLException e) {
            handleSQLException(e, "Error adding students to database", INSERT_SQL, studentList);
        }
    }

    private Student getStudent(ResultSet resultSet) throws SQLException {
        Student student = new Student();

        student.setId(resultSet.getInt("student_id"));
        student.setGroupId(resultSet.getInt("group_id"));
        student.setFirstName(resultSet.getString("first_name"));
        student.setLastName(resultSet.getString("last_name"));

        return student;
    }

    private void handleSQLException(SQLException e, String message, String sql, Object... params) {
        String fullMessage = String.format("%s\nSQL: %s\nParameters: %s", message, sql, Arrays.toString(params));
        LOGGER.log(Level.SEVERE, fullMessage, e);
        throw new StudentDaoException(fullMessage, e);
    }
}

