package dev.alexcoss.dao;

import java.sql.*;
import java.util.Map;
import java.util.Set;

public class StudentsCoursesDao extends AbstractDao<Integer, Map<Integer, Set<Integer>>> {

    private static final String INSERT_SQL = "INSERT INTO students_courses (student_id, course_id) VALUES (?, ?)";

    public StudentsCoursesDao() {
        super(StudentsCoursesDao.class.getName());
    }

    @Override
    public void addAllItems(Map<Integer, Set<Integer>> map) {
        try (Connection connection = connectionFactory.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(INSERT_SQL)) {

            for (Map.Entry<Integer, Set<Integer>> entry : map.entrySet()) {
                int studentId = entry.getKey();
                Set<Integer> coursesIdList = entry.getValue();

                setValuesToStatement(preparedStatement, coursesIdList, studentId);
            }
            preparedStatement.executeBatch();
        } catch (SQLException e) {
            handleSQLException(e, "Error adding related table to database", INSERT_SQL, map);
        }
    }

    @Override
    public void addItem(Integer item) {

    }

    @Override
    public Map<Integer, Set<Integer>> getAllItems() {
        return null;
    }

    @Override
    protected Integer resultSetToObject(ResultSet resultSet) throws SQLException {
        return 0;
    }

    private void setValuesToStatement(PreparedStatement preparedStatement, Set<Integer> coursesIdList, int studentId) throws SQLException {
        for (Integer courseId : coursesIdList) {
            preparedStatement.setInt(1, studentId);
            preparedStatement.setInt(2, courseId);
            preparedStatement.addBatch();
        }
    }
}
