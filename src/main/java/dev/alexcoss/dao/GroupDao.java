package dev.alexcoss.dao;

import dev.alexcoss.dao.exceptions.GroupDaoException;
import dev.alexcoss.model.Group;
import dev.alexcoss.model.Student;
import dev.alexcoss.util.logging.FileHandlerInitializer;

import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class GroupDao {

    private static final String INSERT_SQL = "INSERT INTO groups (group_name) VALUES (?)";
    private static final String SELECT_ALL_SQL = "SELECT * FROM groups";

    private static final Logger LOGGER = Logger.getLogger(DatabaseInitializer.class.getName());

    static {
        FileHandlerInitializer.initializeFileHandler(LOGGER, "group_dao");
    }

    private final ConnectionFactory connectionFactory = new PostgreSqlConnectionFactory();

    public void addGroups(List<Group> groupList) {
        try (Connection connection = connectionFactory.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(INSERT_SQL)) {

            for (Group group : groupList) {
                preparedStatement.setString(1, group.getName());
                preparedStatement.addBatch();
            }

            preparedStatement.executeBatch();
        } catch (SQLException e) {
            handleSQLException(e, "Error adding groups to database", INSERT_SQL, groupList);
        }
    }

    public List<Group> getAllGroups() {
        List<Group> groups = new ArrayList<>();

        try (Connection connection = connectionFactory.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ALL_SQL);
             ResultSet resultSet = preparedStatement.executeQuery()) {

            while (resultSet.next()) {
                Group group = getGroup(resultSet);
                groups.add(group);
            }
        } catch (SQLException e) {
            handleSQLException(e, "Error getting groups from database", SELECT_ALL_SQL);
        }

        return groups;
    }

    private Group getGroup(ResultSet resultSet) throws SQLException {
        Group group = new Group();

        group.setId(resultSet.getInt("group_id"));
        group.setName(resultSet.getString("group_name"));

        return group;
    }

    private void handleSQLException(SQLException e, String message, String sql, Object... params) {
        String fullMessage = String.format("%s\nSQL: %s\nParameters: %s", message, sql, Arrays.toString(params));
        LOGGER.log(Level.SEVERE, fullMessage, e);
        throw new GroupDaoException(fullMessage, e);
    }
}
