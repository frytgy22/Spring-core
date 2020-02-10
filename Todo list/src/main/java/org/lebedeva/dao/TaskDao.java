package org.lebedeva.dao;

import lombok.Cleanup;
import org.lebedeva.model.Category;
import org.lebedeva.model.Status;
import org.lebedeva.model.Task;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class TaskDao extends GenericDao<Task> {
    /**
     * in Task field Category category, so I do SELECT categoryID
     */
    private static final String INSERT = "INSERT INTO `Tasks`(" +
            "`shortDescription`,`longDescription`,`categoryID`, `start`,`end`) " +
            "           VALUES(" +
            "           (?), " +
            "           (?), " +
            "           (SELECT categoryID FROM Categories WHERE Categories.name = ?), " +
            "           (?), " +
            "           (?))";

    private static final String GET_ALL = "SELECT `taskID`,`shortDescription`,`longDescription`,`Categories`.`name` ," +
            "`created`,`start`,`end`,`statusID` FROM Tasks " +
            "JOIN `Categories` ON `Categories`.`categoryID` = `Tasks`.`categoryID`";

    private static final String UPDATE = "UPDATE Tasks SET `statusID` = ? WHERE `taskID` = ?";

    private static final String DELETE = "DELETE FROM Tasks WHERE `taskID` = ?";

    public TaskDao(String url, String user, String password) throws SQLException {
        super(url, user, password);
    }

    @Override
    public List<Task> getAll() throws SQLException {
        List<Task> tasks = new ArrayList<>();

        try {
            startTransaction();
            @Cleanup PreparedStatement preparedStatement = connection.prepareStatement(GET_ALL);
            @Cleanup ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                tasks.add(Task.builder()
                        .id(resultSet.getLong("taskID"))
                        .shortDescription(resultSet.getString("shortDescription"))
                        .longDescription(resultSet.getString("longDescription"))
                        .category(Category.builder().name(resultSet.getString("Categories.name")).build())
                        .created(resultSet.getTimestamp("created"))
                        .start(resultSet.getDate("start"))
                        .end(resultSet.getDate("end"))
                        .status(resultSet.getInt("statusID") == 1 ? Status.CHECKED : Status.UNCHECKED)
                        .build());
            }
            commit();
        } catch (SQLException e) {
            e.printStackTrace();
            rollback();
        }
        return tasks;
    }

    @Override
    public void save(Task data) throws SQLException {
        try {
            startTransaction();
            @Cleanup PreparedStatement preparedStatement = connection.prepareStatement(INSERT);

            preparedStatement.setString(1, data.getShortDescription());
            preparedStatement.setString(2, data.getLongDescription());
            preparedStatement.setString(3, data.getCategory().getName());
            preparedStatement.setDate(4, data.getStart());
            preparedStatement.setDate(5, data.getEnd());
            preparedStatement.executeUpdate();

            commit();
        } catch (SQLException e) {
            e.printStackTrace();
            rollback();
        }
    }

    @Override
    public void update(long id, String... params) throws SQLException {
        try {
            startTransaction();
            @Cleanup PreparedStatement preparedStatement = connection.prepareStatement(UPDATE);

            preparedStatement.setString(1, params[0]);
            preparedStatement.setLong(2, id);
            preparedStatement.executeUpdate();

            commit();
        } catch (SQLException e) {
            e.printStackTrace();
            rollback();
        }
    }

    @Override
    public void delete(long id) throws SQLException {
        try {
            startTransaction();
            @Cleanup PreparedStatement preparedStatement = connection.prepareStatement(DELETE);

            preparedStatement.setLong(1, id);
            preparedStatement.execute();

            commit();
        } catch (SQLException e) {
            e.printStackTrace();
            rollback();
        }
    }
}
