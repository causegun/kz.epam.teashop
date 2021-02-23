package dao.impl;

import connection.ConnectionPool;
import exception.ConnectionPoolException;
import dao.CartItemDao;
import dao.factory.DaoFactory;
import entity.CartItem;
import exception.DAOException;
import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CartItemDaoImpl implements CartItemDao {

    private final static Logger logger = Logger.getLogger(CartItemDaoImpl.class);

    private static final String SQL_SELECT_ALL_CART_ITEMS = "SELECT * FROM cart_item";

    private static final String SQL_SELECT_CART_ITEM = "SELECT * FROM cart_item WHERE id = ?";

    private static final String SQL_INSERT_NEW_CART_ITEM =
            "INSERT INTO cart_item (productId, cartId, createdAt, quantity) values (?, ?, ?, ?)";

    private static final String SQL_DELETE_CART_ITEM = "DELETE FROM cart_item WHERE id = ?";

    private static final String SQL_DELETE_CART_ITEMS_BY_CART_ID = "DELETE FROM cart_item WHERE cartId = ?";

    private static final String SQL_UPDATE_CART_ITEM =
            "UPDATE cart_item SET productId = ?, createdAt = ?, quantity = ? WHERE id = ?";

    private static final String SQL_SELECT_CART_ITEMS_BY_CART_ID = "SELECT * FROM cart_item WHERE cartId = ?";

    ConnectionPool connectionPool = DaoFactory.getConnectionPool();

    private static final String ID = "id";
    private static final String PRODUCT_ID = "productId";
    private static final String CREATED_AT = "createdAt";
    private static final String QUANTITY = "quantity";
    private static final String CART_ID = "cartId";

    @Override
    public List<CartItem> getAll() throws DAOException, ConnectionPoolException {
        List<CartItem> cartItems = new ArrayList<>();
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        try {
            connection = connectionPool.takeConnection();
            statement = connection.prepareStatement(SQL_SELECT_ALL_CART_ITEMS);
            resultSet = statement.executeQuery();
            long id;

            while (resultSet.next()) {
                CartItem cartItem = new CartItem();
                id = resultSet.getLong(ID);
                setDataToCartItems(id, cartItem, resultSet);
                cartItems.add(cartItem);
            }
        } catch (ConnectionPoolException | SQLException e) {
            logger.error("Error while getting cart items from cart_item database . Message: " + e.getMessage());
            throw new DAOException("Error querying cart items from database. ", e);
        } finally {
            if (connection != null && statement != null && resultSet != null)
                connectionPool.closeConnection(connection, statement, resultSet);
        }
        return cartItems;
    }

    @Override
    public CartItem get(Long id) throws DAOException, ConnectionPoolException {
        CartItem cartItem = new CartItem();
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            connection = connectionPool.takeConnection();
            statement = connection.prepareStatement(SQL_SELECT_CART_ITEM);
            statement.setLong(1, id);
            resultSet = statement.executeQuery();

            while (resultSet.next()) {
                setDataToCartItems(id, cartItem, resultSet);
            }
        } catch (ConnectionPoolException | SQLException e) {
            logger.error("Error while getting cart item from cart_item database . Message: " + e.getMessage());
            throw new DAOException("Error querying cart item from database. ", e);
        } finally {
            if (connection != null && statement != null && resultSet != null)
                connectionPool.closeConnection(connection, statement, resultSet);
        }
        return cartItem;

    }

    private void setDataToCartItems(long id, CartItem cartItem, ResultSet resultSet) throws SQLException {
        long productId;
        long cartId;
        String createdAt;
        int quantity;
        productId = resultSet.getLong(PRODUCT_ID);
        cartId = resultSet.getLong(CART_ID);
        createdAt = resultSet.getString(CREATED_AT);
        quantity = resultSet.getInt(QUANTITY);

        cartItem.setId(id);
        cartItem.setProductId(productId);
        cartItem.setCartId(cartId);
        cartItem.setCreatedAt(createdAt);
        cartItem.setQuantity(quantity);
    }

    @Override
    public void insert(CartItem cartItem) throws DAOException, ConnectionPoolException {
        Connection connection = null;
        PreparedStatement statement = null;

        try {
            connection = connectionPool.takeConnection();
            statement = connection.prepareStatement(SQL_INSERT_NEW_CART_ITEM);

            long productId = cartItem.getProductId();
            long cartId = cartItem.getCartId();
            String createdAt = cartItem.getCreatedAt();
            int quantity = cartItem.getQuantity();

            statement.setLong(1, productId);
            statement.setLong(2, cartId);
            statement.setString(3, createdAt);
            statement.setInt(4, quantity);
            statement.executeUpdate();
        } catch (ConnectionPoolException | SQLException e) {
            logger.error("Error while inserting cart item to cart_item database . Message: " + e.getMessage());
            throw new DAOException("Error inserting cart item from database. ", e);
        } finally {
            if (connection != null && statement != null)
                connectionPool.closeConnection(connection, statement);
        }
    }

    @Override
    public void update(CartItem cartItem) throws ConnectionPoolException, DAOException {
        Connection connection = null;
        PreparedStatement statement = null;

        try {
            connection = connectionPool.takeConnection();
            statement = connection.prepareStatement(SQL_UPDATE_CART_ITEM);

            long id = cartItem.getId();
            long productId = cartItem.getProductId();
            String createdAt = cartItem.getCreatedAt();
            int quantity = cartItem.getQuantity();

            statement.setLong(1, productId);
            statement.setString(2, createdAt);
            statement.setInt(3, quantity);
            statement.setLong(4, id);
            statement.executeUpdate();
        } catch (ConnectionPoolException | SQLException e) {
            logger.error("Error while updating cart item in cart_item database . Message: " + e.getMessage());
            throw new DAOException("Error updating cart item from database. ", e);
        } finally {
            if (connection != null && statement != null)
                connectionPool.closeConnection(connection, statement);
        }
    }

    @Override
    public void delete(CartItem cartItem) throws ConnectionPoolException, DAOException {
        Connection connection = null;
        PreparedStatement statement = null;

        try {
            connection = connectionPool.takeConnection();
            statement = connection.prepareStatement(SQL_DELETE_CART_ITEM);

            long id = cartItem.getId();
            statement.setLong(1, id);
            statement.executeUpdate();
        } catch (ConnectionPoolException | SQLException e) {
            logger.error("Error while deleting cart item from cart_item database . Message: " + e.getMessage());
            throw new DAOException("Error deleting cart item from database. ", e);
        } finally {
            if (connection != null && statement != null)
                connectionPool.closeConnection(connection, statement);
        }
    }

    public void deleteByCartId(long cartId) throws ConnectionPoolException, DAOException {
        Connection connection = null;
        PreparedStatement statement = null;

        try {
            connection = connectionPool.takeConnection();
            statement = connection.prepareStatement(SQL_DELETE_CART_ITEMS_BY_CART_ID);
            statement.setLong(1, cartId);
            statement.executeUpdate();
        } catch (ConnectionPoolException | SQLException e) {
            logger.error("Error while deleting cart items from cart_item database . Message: " + e.getMessage());
            throw new DAOException("Error deleting cart item from database. ", e);
        } finally {
            if (connection != null && statement != null)
                connectionPool.closeConnection(connection, statement);
        }
    }

    @Override
    public List<CartItem> getByCartId(long cartId) throws ConnectionPoolException, DAOException {
        List<CartItem> cartItems = new ArrayList<>();
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        try {
            connection = connectionPool.takeConnection();
            statement = connection.prepareStatement(SQL_SELECT_CART_ITEMS_BY_CART_ID);
            statement.setLong(1, cartId);
            long id;
            resultSet = statement.executeQuery();

            while (resultSet.next()) {
                CartItem cartItem = new CartItem();
                id = resultSet.getLong(ID);
                setDataToCartItems(id, cartItem, resultSet);
                cartItems.add(cartItem);
            }
        } catch (ConnectionPoolException | SQLException e) {
            logger.error("Error while getting cart items by ID from cart_item database . Message: " + e.getMessage());
            throw new DAOException("Error querying cart item from database. ", e);
        } finally {
            if (connection != null && statement != null && resultSet != null)
                connectionPool.closeConnection(connection, statement, resultSet);
        }
        return cartItems;
    }
}
