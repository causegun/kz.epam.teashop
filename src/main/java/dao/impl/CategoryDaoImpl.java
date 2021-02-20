package dao.impl;

import connection.ConnectionPool;
import connection.ConnectionPoolException;
import dao.CategoryDao;
import dao.ProductDao;
import dao.factory.DaoFactory;
import entity.Category;
import entity.Product;
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

    private static final String SQL_SELECT_CATEGORY_BY_LANGUAGE_ID = "SELECT * FROM category WHERE languageId = ?";

    private static final String SQL_INSERT_NEW_CATEGORY =
            "INSERT INTO category (languageId, categoryName) values (?, ?)";

    private static final String SQL_DELETE_CATEGORY = "DELETE FROM category WHERE id = ?";

    private static final String SQL_UPDATE_CATEGORY =
            "UPDATE category SET languageId = ?, categoryName = ? WHERE id = ? ";

    ConnectionPool connectionPool = DaoFactory.getConnectionPool();

    @Override
    public List<Category> getByLanguage(long languageId) {
        List<Category> categories = new ArrayList<>();
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        try {
            connection = connectionPool.takeConnection();
            statement = connection.prepareStatement(SQL_SELECT_CATEGORY_BY_LANGUAGE_ID);
            statement.setLong(1, languageId);
            resultSet = statement.executeQuery();

            long id;
            String categoryName;

            while (resultSet.next()) {
                Category category = new Category();
                id = resultSet.getLong("id");
                categoryName = resultSet.getString("categoryName");
                category.setId(id);
                category.setLanguageId(languageId);
                category.setName(categoryName);
                categories.add(category);
            }
        } catch (ConnectionPoolException | SQLException e) {
            logger.error("Error while getting category from category database . Message: " + e.getMessage());
            e.printStackTrace();
        } finally {
            if (connection != null && statement != null && resultSet != null)
                connectionPool.closeConnection(connection, statement, resultSet);
        }
        return categories;
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

            long id;
            long languageId;
            String categoryName;
            ProductDao productDao = DaoFactory.getProductDao();
            List<Product> products;

            while (resultSet.next()) {
                Category category = new Category();
                id = resultSet.getLong("id");
                languageId = resultSet.getLong("languageId");
                categoryName = resultSet.getString("categoryName");

                category.setId(id);
                category.setLanguageId(languageId);
                category.setName(categoryName);

                products = productDao.getByCategory(id);
                category.setProducts(products);

                categories.add(category);
            }
        } catch (ConnectionPoolException | SQLException e) {
            logger.error("Error while getting categories from category database . Message: " + e.getMessage());
            e.printStackTrace();
        } finally {
            if (connection != null && statement != null && resultSet != null)
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
            ProductDao productDao = DaoFactory.getProductDao();
            List<Product> products;

            long languageId;
            String categoryName;

            while (resultSet.next()) {
                languageId = resultSet.getLong("languageId");
                categoryName = resultSet.getString("categoryName");

                category.setId(id);
                category.setLanguageId(languageId);
                category.setName(categoryName);
                products = productDao.getByCategory(id);
                category.setProducts(products);
            }
        } catch (ConnectionPoolException | SQLException e) {
            logger.error("Error while getting category by ID from category database . Message: " + e.getMessage());
            e.printStackTrace();
        } finally {
            if (connection != null && statement != null && resultSet != null)
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

            long languageId = category.getLanguageId();
            String categoryName = category.getName();

            statement.setLong(1, languageId);
            statement.setString(2, categoryName);
            statement.executeUpdate();
        } catch (ConnectionPoolException | SQLException e) {
            logger.error("Error while inserting category to category database . Message: " + e.getMessage());
            e.printStackTrace();
        } finally {
            if (connection != null && statement != null)
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

            long languageId = category.getLanguageId();
            String categoryName = category.getName();
            long id = category.getId();

            statement.setLong(1, languageId);
            statement.setString(2, categoryName);
            statement.setLong(3, id);
            statement.executeUpdate();
        } catch (ConnectionPoolException | SQLException e) {
            logger.error("Error while updating category in category database . Message: " + e.getMessage());
            e.printStackTrace();
        } finally {
            if (connection != null && statement != null)
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

            long id = category.getId();
            statement.setLong(1, id);

            statement.executeUpdate();
        } catch (ConnectionPoolException | SQLException e) {
            logger.error("Error while deleting category from category database . Message: " + e.getMessage());
            e.printStackTrace();
        } finally {
            if (connection != null && statement != null)
                connectionPool.closeConnection(connection, statement);
        }
    }
}
