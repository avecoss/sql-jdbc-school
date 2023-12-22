package dev.alexcoss.dao;

import dev.alexcoss.model.Group;
import dev.alexcoss.model.Student;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class GroupDao extends AbstractDao<Group, List<Group>> {

    private static final String INSERT_SQL = "INSERT INTO groups (group_name) VALUES (?)";
    private static final String SELECT_ALL_SQL = "SELECT * FROM groups";

    public GroupDao() {
        super(GroupDao.class.getName());
    }

    @Override
    public void addItem(Group group) {
        try (Connection connection = connectionFactory.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(INSERT_SQL)) {

            preparedStatement.setString(1, group.getName());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            handleSQLException(e, "Error adding group to database", INSERT_SQL, group);
        }
    }

    @Override
    public void addAllItems(List<Group> groupList) {
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

    @Override
    public List<Group> getAllItems() {
        List<Group> groups = new ArrayList<>();

        try (Connection connection = connectionFactory.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ALL_SQL);
             ResultSet resultSet = preparedStatement.executeQuery()) {

            while (resultSet.next()) {
                Group group = resultSetToObject(resultSet);
                groups.add(group);
            }
        } catch (SQLException e) {
            handleSQLException(e, "Error getting groups from database", SELECT_ALL_SQL);
        }

        return groups;
    }

    @Override
    protected Group resultSetToObject(ResultSet resultSet) throws SQLException {
        Group group = new Group();

        group.setId(resultSet.getInt("group_id"));
        group.setName(resultSet.getString("group_name"));

        return group;
    }
}
