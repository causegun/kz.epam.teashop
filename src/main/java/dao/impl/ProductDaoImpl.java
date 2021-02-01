package dao.impl;

import connection.ConnectionPool;
import connection.ConnectionPoolException;
import dao.ProductDao;
import dao.factory.DaoFactory;
import entity.Product;
import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ProductDaoImpl implements ProductDao {

    private final static Logger logger = Logger.getLogger(ProductDaoImpl.class);

    private static final String SQL_SELECT_ALL_PRODUCTS = "SELECT * FROM product";

    private static final String SQL_SELECT_PRODUCT_BY_ID = "SELECT * FROM product WHERE id = ?";

    private static final String SQL_INSERT_NEW_PRODUCT =
            "INSERT INTO product (languageId, categoryId, productName, productDescription, price, pathToPicture) values (?, ?, ?, ?, ?, ?)";

    private static final String SQL_DELETE_PRODUCT = "DELETE FROM product WHERE id = ?";

    private static final String SQL_UPDATE_PRODUCT =
            "UPDATE product SET languageId = ?, categoryId = ?, productName = ?, productDescription = ?, price = ?, pathToPicture = ? " +
                    "WHERE id = ? ";

    private static final String SQL_SELECT_PRODUCTS_BY_CATEGORY =
            "SELECT id, languageId, categoryId, productName, productDescription, price, pathToPicture FROM product WHERE categoryId = ?";

    ConnectionPool connectionPool = DaoFactory.getConnectionPool();

    public ProductDaoImpl() {
    }

    @Override
    public List<Product> getByCategory(long categoryId) {
        List<Product> products = new ArrayList<>();
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            connection = connectionPool.takeConnection();
            statement = connection.prepareStatement(SQL_SELECT_PRODUCTS_BY_CATEGORY);
            statement.setLong(1, categoryId);
            resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Product product = new Product();
                product.setId(resultSet.getLong("id"));
                product.setLanguageId(resultSet.getLong("languageId"));
                product.setCategoryId(categoryId);
                product.setName(resultSet.getString("productName"));
                product.setDescription(resultSet.getString("productDescription"));
                product.setPrice(resultSet.getBigDecimal("price"));
                product.setPathToPicture(resultSet.getString("pathToPicture"));
                products.add(product);
            }
        } catch (ConnectionPoolException | SQLException e) {
            logger.error("Error while getting product by category from product database . Message: " + e.getMessage());
            e.printStackTrace();
        } finally {
            if (connection != null)
                connectionPool.closeConnection(connection, statement, resultSet);
        }
        return products;
    }

    @Override
    public List<Product> getAll() {
        List<Product> products = new ArrayList<>();
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            connection = connectionPool.takeConnection();
            statement = connection.prepareStatement(SQL_SELECT_ALL_PRODUCTS);
            resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Product product = new Product();
                product.setId(resultSet.getLong("id"));
                product.setLanguageId(resultSet.getLong("languageId"));
                product.setCategoryId(resultSet.getLong("categoryId"));
                product.setName(resultSet.getString("productName"));
                product.setDescription(resultSet.getString("productDescription"));
                product.setPrice(resultSet.getBigDecimal("price"));
                product.setPathToPicture(resultSet.getString("pathToPicture"));
                products.add(product);
            }
        } catch (ConnectionPoolException | SQLException e) {
            logger.error("Error while getting products from product database . Message: " + e.getMessage());
            e.printStackTrace();
        } finally {
            if (connection != null)
                connectionPool.closeConnection(connection, statement, resultSet);
        }
        return products;
    }

    @Override
    public Product get(Long id) {
        Product product = new Product();
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        try {
            connection = connectionPool.takeConnection();
            statement = connection.prepareStatement(SQL_SELECT_PRODUCT_BY_ID);
            statement.setLong(1, id);
            resultSet = statement.executeQuery();
            while (resultSet.next()) {
                product.setId(id);
                product.setLanguageId(resultSet.getLong("languageId"));
                product.setCategoryId(resultSet.getLong("categoryId"));
                product.setName(resultSet.getString("productName"));
                product.setDescription(resultSet.getString("productDescription"));
                product.setPrice(resultSet.getBigDecimal("price"));
                product.setPathToPicture(resultSet.getString("pathToPicture"));
            }
        } catch (ConnectionPoolException | SQLException e) {
            logger.error("Error while getting product from product database . Message: " + e.getMessage());
            e.printStackTrace();
        } finally {
            if (connection != null)
                connectionPool.closeConnection(connection, statement, resultSet);
        }
        return product;
    }

    @Override
    public void insert(Product product) {
        Connection connection = null;
        PreparedStatement statement = null;

        try {
            connection = connectionPool.takeConnection();
            statement = connection.prepareStatement(SQL_INSERT_NEW_PRODUCT);
            statement.setLong(1, product.getLanguageId());
            statement.setLong(2, product.getCategoryId());
            statement.setString(3, product.getName());
            statement.setString(4, product.getDescription());
            statement.setBigDecimal(5, product.getPrice());
            statement.setString(6, product.getPathToPicture());

            statement.executeUpdate();
        } catch (ConnectionPoolException | SQLException e) {
            logger.error("Error while inserting product to product database . Message: " + e.getMessage());
            e.printStackTrace();
        } finally {
            if (connection != null)
                connectionPool.closeConnection(connection, statement);

        }
    }

    @Override
    public void update(Product product) {
        Connection connection = null;
        PreparedStatement statement = null;

        try {
            connection = connectionPool.takeConnection();
            statement = connection.prepareStatement(SQL_UPDATE_PRODUCT);
            statement.setLong(1, product.getLanguageId());
            statement.setLong(2, product.getCategoryId());
            statement.setString(3, product.getName());
            statement.setString(4, product.getDescription());
            statement.setBigDecimal(5, product.getPrice());
            statement.setString(6, product.getPathToPicture());
            statement.setLong(7, product.getId());

            statement.executeUpdate();
        } catch (ConnectionPoolException | SQLException e) {
            logger.error("Error while updating product in product database . Message: " + e.getMessage());
            e.printStackTrace();
        } finally {
            if (connection != null)
                connectionPool.closeConnection(connection, statement);

        }
    }

    @Override
    public void delete(Product product) {
        Connection connection = null;
        PreparedStatement statement = null;
        try {
            connection = connectionPool.takeConnection();
            statement = connection.prepareStatement(SQL_DELETE_PRODUCT);

            statement.setLong(1, product.getId());

            statement.executeUpdate();
        } catch (ConnectionPoolException | SQLException e) {
            logger.error("Error while deleting product from product database . Message: " + e.getMessage());
            e.printStackTrace();
        } finally {
            if (connection != null)
                connectionPool.closeConnection(connection, statement);
        }
    }
}
