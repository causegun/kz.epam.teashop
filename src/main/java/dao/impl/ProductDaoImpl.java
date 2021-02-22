package dao.impl;

import connection.ConnectionPool;
import exception.ConnectionPoolException;
import dao.ProductDao;
import dao.factory.DaoFactory;
import entity.Product;
import exception.DAOException;
import org.apache.log4j.Logger;

import java.math.BigDecimal;
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
    public List<Product> getByCategory(long categoryId) throws ConnectionPoolException, DAOException {
        List<Product> products = new ArrayList<>();
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        try {
            connection = connectionPool.takeConnection();
            statement = connection.prepareStatement(SQL_SELECT_PRODUCTS_BY_CATEGORY);
            statement.setLong(1, categoryId);
            resultSet = statement.executeQuery();
            long id;

            while (resultSet.next()) {
                Product product = new Product();

                id = resultSet.getLong("id");
                setDataToProduct(id, product, resultSet);

                products.add(product);
            }
        } catch (ConnectionPoolException | SQLException e) {
            logger.error("Error while getting product by category from product database . Message: " + e.getMessage());
            throw new DAOException("Error querying product from database. ", e);
        } finally {
            if (connection != null && statement != null && resultSet != null)
                connectionPool.closeConnection(connection, statement, resultSet);
        }
        return products;
    }

    @Override
    public List<Product> getAll() throws DAOException, ConnectionPoolException {
        List<Product> products = new ArrayList<>();
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        try {
            connection = connectionPool.takeConnection();
            statement = connection.prepareStatement(SQL_SELECT_ALL_PRODUCTS);
            resultSet = statement.executeQuery();
            long id;

            while (resultSet.next()) {
                Product product = new Product();

                id = resultSet.getLong("id");
                setDataToProduct(id, product, resultSet);

                products.add(product);
            }
        } catch (ConnectionPoolException | SQLException e) {
            logger.error("Error while getting products from product database . Message: " + e.getMessage());
            throw new DAOException("Error querying products from database. ", e);
        } finally {
            if (connection != null && statement != null && resultSet != null)
                connectionPool.closeConnection(connection, statement, resultSet);
        }
        return products;
    }

    @Override
    public Product get(Long id) throws DAOException, ConnectionPoolException {
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
                setDataToProduct(id, product, resultSet);
            }
        } catch (ConnectionPoolException | SQLException e) {
            logger.error("Error while getting product from product database . Message: " + e.getMessage());
            throw new DAOException("Error querying product from database. ", e);
        } finally {
            if (connection != null && statement != null && resultSet != null)
                connectionPool.closeConnection(connection, statement, resultSet);
        }
        return product;
    }

    private void setDataToProduct(Long id, Product product, ResultSet resultSet) throws SQLException {
        long languageId;
        long categoryId;
        String productName;
        String productDescription;
        BigDecimal price;
        String pathToPicture;

        languageId = resultSet.getLong("languageId");
        categoryId = resultSet.getLong("categoryId");
        productName = resultSet.getString("productName");
        productDescription = resultSet.getString("productDescription");
        price = resultSet.getBigDecimal("price");
        pathToPicture = resultSet.getString("pathToPicture");

        product.setId(id);
        product.setLanguageId(languageId);
        product.setCategoryId(categoryId);
        product.setName(productName);
        product.setDescription(productDescription);
        product.setPrice(price);
        product.setPathToPicture(pathToPicture);
    }

    @Override
    public void insert(Product product) throws DAOException, ConnectionPoolException {
        Connection connection = null;
        PreparedStatement statement = null;

        try {
            connection = connectionPool.takeConnection();
            statement = connection.prepareStatement(SQL_INSERT_NEW_PRODUCT);

            getDataFromProduct(product, statement);

            statement.executeUpdate();
        } catch (ConnectionPoolException | SQLException e) {
            logger.error("Error while inserting product to product database . Message: " + e.getMessage());
            throw new DAOException("Error inserting product to database. ", e);
        } finally {
            if (connection != null && statement != null)
                connectionPool.closeConnection(connection, statement);

        }
    }

    private void getDataFromProduct(Product product, PreparedStatement statement) throws SQLException {
        long languageId;
        long categoryId;
        String productName;
        String productDescription;
        BigDecimal price;
        String pathToPicture;

        languageId = product.getLanguageId();
        categoryId = product.getCategoryId();
        productName = product.getName();
        productDescription = product.getDescription();
        price = product.getPrice();
        pathToPicture = product.getPathToPicture();

        statement.setLong(1, languageId);
        statement.setLong(2, categoryId);
        statement.setString(3, productName);
        statement.setString(4, productDescription);
        statement.setBigDecimal(5, price);
        statement.setString(6, pathToPicture);
    }

    @Override
    public void update(Product product) throws DAOException, ConnectionPoolException {
        Connection connection = null;
        PreparedStatement statement = null;

        long id;

        try {
            connection = connectionPool.takeConnection();
            statement = connection.prepareStatement(SQL_UPDATE_PRODUCT);

            id = product.getId();
            statement.setLong(7, id);
            getDataFromProduct(product, statement);


            statement.executeUpdate();
        } catch (ConnectionPoolException | SQLException e) {
            logger.error("Error while updating product in product database . Message: " + e.getMessage());
            throw new DAOException("Error updating product in database. ", e);
        } finally {
            if (connection != null && statement != null)
                connectionPool.closeConnection(connection, statement);

        }
    }

    @Override
    public void delete(Product product) throws DAOException, ConnectionPoolException {
        Connection connection = null;
        PreparedStatement statement = null;

        try {
            connection = connectionPool.takeConnection();
            statement = connection.prepareStatement(SQL_DELETE_PRODUCT);

            statement.setLong(1, product.getId());

            statement.executeUpdate();
        } catch (ConnectionPoolException | SQLException e) {
            logger.error("Error while deleting product from product database . Message: " + e.getMessage());
            throw new DAOException("Error deleting product from database. ", e);
        } finally {
            if (connection != null && statement != null)
                connectionPool.closeConnection(connection, statement);
        }
    }
}
