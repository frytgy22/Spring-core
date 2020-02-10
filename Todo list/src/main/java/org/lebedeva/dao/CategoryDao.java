package org.lebedeva.dao;

import lombok.Cleanup;
import org.lebedeva.model.Category;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CategoryDao extends GenericDao<Category> {
    private static final String INSERT = "INSERT INTO `Categories` (`name`) VALUES(?)";
    private static final String GET_ALL = "SELECT `categoryID`, `name` FROM `Categories`";
    private static final String UPDATE = "UPDATE  `Categories` SET `name` = ? WHERE `categoryID` = ?";
    private static final String DELETE = "DELETE FROM `Categories` WHERE `categoryID` = ? ";

    public CategoryDao(String url, String user, String password) throws SQLException {
        super(url, user, password);
    }

    @Override
    public List<Category> getAll() throws SQLException {
        List<Category> categories = new ArrayList<>();

        try {
            startTransaction();
            @Cleanup PreparedStatement preparedStatement = connection.prepareStatement(GET_ALL);
            @Cleanup ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                categories.add(Category.builder()
                        .id(resultSet.getLong("categoryID"))
                        .name(resultSet.getString("name")).build());
            }
            commit();
        } catch (SQLException e) {
            e.printStackTrace();
            rollback();
        }
        return categories;
    }

    @Override
    public void save(Category data) throws SQLException {
        try {
            startTransaction();
            @Cleanup PreparedStatement preparedStatement = connection.prepareStatement(INSERT);

            preparedStatement.setString(1, data.getName());
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
