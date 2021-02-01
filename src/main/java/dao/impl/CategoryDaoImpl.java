package dao.impl;

import connection.ConnectionPool;
import connection.ConnectionPoolException;
import dao.CategoryDao;
import dao.factory.DaoFactory;
import entity.Category;
import org.apache.log4j.Logger;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CategoryDaoImpl implements CategoryDao {

    private final static Logger logger = Logger.getLogger(CategoryDaoImpl.class);

    private static final String SQL_SELECT_ALL_CATEGORIES = "SELECT * FROM category";

    private static final String SQL_SELECT_CATEGORY_BY_ID = "SELECT * FROM category WHERE id = ?";

    private static final String SQL_INSERT_NEW_CATEGORY =
            "INSERT INTO category (languageId, categoryName) values (?, ?)";

    private static final String SQL_DELETE_CATEGORY = "DELETE FROM category WHERE id = ?";

    private static final String SQL_UPDATE_CATEGORY =
            "UPDATE category SET languageId = ?, categoryName = ? WHERE id = ? ";

    private static final String SQL_SELECT_BY_CATEGORY_NAME =
            "SELECT * FROM category WHERE categoryName = ?";


    ConnectionPool connectionPool = DaoFactory.getConnectionPool();


    @Override
    public Category getByName(String categoryName) {
        Category category = new Category();
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            connection = connectionPool.takeConnection();
            statement = connection.prepareStatement(SQL_SELECT_BY_CATEGORY_NAME);
            statement.setString(1, categoryName);
            resultSet = statement.executeQuery();
            if (resultSet.next()) {
                category.setId(resultSet.getLong("id"));
                category.setLanguageId(resultSet.getLong("languageId"));
                category.setName(categoryName);
                category.setProducts(DaoFactory.getProductDao().getByCategory(category.getId()));
            }
        } catch (ConnectionPoolException | SQLException e) {
            logger.error("Error while getting category from category database . Message: " + e.getMessage());
            e.printStackTrace();
        } finally {
            if (connection != null)
                connectionPool.closeConnection(connection, statement, resultSet);
        }
        return category;
    }

    @Override
    public List<Category> getAll() {
        List<Category> categories = new ArrayList<>();
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        try {
            connection = connectionPool.takeConnection();
            statement = connection.prepareStatement(SQL_SELECT_ALL_CATEGORIES);
            resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Category category = new Category();
                category.setId(resultSet.getLong("id"));
                category.setLanguageId(resultSet.getLong("languageId"));
                category.setName(resultSet.getString("categoryName"));
                category.setProducts(DaoFactory.getProductDao().getByCategory(category.getId()));
                categories.add(category);
            }
        } catch (ConnectionPoolException | SQLException e) {
            logger.error("Error while getting categories from category database . Message: " + e.getMessage());
            e.printStackTrace();
        } finally {
            if (connection != null)
                connectionPool.closeConnection(connection, statement, resultSet);
        }
        return categories;
    }

    @Override
    public Category get(Long id) {
        Category category = new Category();
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        try {
            connection = connectionPool.takeConnection();
            statement = connection.prepareStatement(SQL_SELECT_CATEGORY_BY_ID);
            statement.setLong(1, id);
            resultSet = statement.executeQuery();
            while (resultSet.next()) {
                category.setId(id);
                category.setLanguageId(resultSet.getLong("languageId"));
                category.setName(resultSet.getString("categoryName"));
                category.setProducts(DaoFactory.getProductDao().getByCategory(category.getId()));
            }
        } catch (ConnectionPoolException | SQLException e) {
            logger.error("Error while getting category by ID from category database . Message: " + e.getMessage());
            e.printStackTrace();
        } finally {
            if (connection != null)
                connectionPool.closeConnection(connection, statement, resultSet);
        }
        return category;
    }

    @Override
    public void insert(Category category) {
        Connection connection = null;
        PreparedStatement statement = null;
        try {
            connection = connectionPool.takeConnection();
            statement = connection.prepareStatement(SQL_INSERT_NEW_CATEGORY);
            statement.setLong(1, category.getLanguageId());
            statement.setString(2, category.getName());
            statement.executeUpdate();
        } catch (ConnectionPoolException | SQLException e) {
            logger.error("Error while inserting category to category database . Message: " + e.getMessage());
            e.printStackTrace();
        } finally {
            if (connection != null)
                connectionPool.closeConnection(connection, statement);

        }
    }

    @Override
    public void update(Category category) {
        Connection connection = null;
        PreparedStatement statement = null;
        try {
            connection = connectionPool.takeConnection();
            statement = connection.prepareStatement(SQL_UPDATE_CATEGORY);
            statement.setLong(1, category.getLanguageId());
            statement.setString(2, category.getName());
            statement.executeUpdate();
        } catch (ConnectionPoolException | SQLException e) {
            logger.error("Error while updating category in category database . Message: " + e.getMessage());
            e.printStackTrace();
        } finally {
            if (connection != null)
                connectionPool.closeConnection(connection, statement);
        }
    }

    @Override
    public void delete(Category category) {
        Connection connection = null;
        PreparedStatement statement = null;
        try {
            connection = connectionPool.takeConnection();
            statement = connection.prepareStatement(SQL_DELETE_CATEGORY);
            statement.setLong(1, category.getId());

            statement.executeUpdate();
        } catch (ConnectionPoolException | SQLException e) {
            logger.error("Error while deleting category from category database . Message: " + e.getMessage());
            e.printStackTrace();
        } finally {
            if (connection != null)
                connectionPool.closeConnection(connection, statement);
        }
    }
}
