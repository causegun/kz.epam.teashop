package dao.impl;

import connection.ConnectionPool;
import connection.ConnectionPoolException;
import dao.CartItemDao;
import dao.factory.DaoFactory;
import entity.CartItem;
import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CartItemDaoImpl implements CartItemDao {

    private final static Logger logger = Logger.getLogger(CategoryDaoImpl.class);

    private static final String SQL_SELECT_ALL_CART_ITEMS = "SELECT * FROM cart_item";

    private static final String SQL_SELECT_CART_ITEM = "SELECT * FROM cart_item WHERE id = ?";

    private static final String SQL_INSERT_NEW_CART_ITEM =
            "INSERT INTO cart_item (productId, cartId, createdAt, quantity) values (?, ?, ?, ?)";

    private static final String SQL_DELETE_CART_ITEM = "DELETE FROM cart_item WHERE id = ?";

    private static final String SQL_UPDATE_CART_ITEM =
            "UPDATE cart_item SET productId = ?, createdAt = ?, quantity = ? WHERE id = ?";

    private static final String SQL_SELECT_CART_ITEMS_BY_CART_ID = "SELECT * FROM cart_item WHERE cartId = ?";

    ConnectionPool connectionPool = DaoFactory.getConnectionPool();

    @Override
    public List<CartItem> getAll() {
        List<CartItem> cartItems = new ArrayList<>();
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            connection = connectionPool.takeConnection();
            statement = connection.prepareStatement(SQL_SELECT_ALL_CART_ITEMS);
            resultSet = statement.executeQuery();
            while (resultSet.next()) {
                CartItem cartItem = new CartItem();
                cartItem.setId(resultSet.getLong("id"));
                cartItem.setProductId(resultSet.getLong("productId"));
                cartItem.setCartId(resultSet.getLong("cartId"));
                cartItem.setCreatedAt(resultSet.getString("createdAt"));
                cartItem.setQuantity(resultSet.getInt("quantity"));
                cartItems.add(cartItem);
            }
        } catch (ConnectionPoolException | SQLException e) {
            logger.error("Error while getting cart items from cart_item database . Message: " + e.getMessage());
            e.printStackTrace();
        } finally {
            if (connection != null)
                connectionPool.closeConnection(connection, statement, resultSet);
        }
        return cartItems;
    }

    @Override
    public CartItem get(Long id) {
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
                cartItem.setId(resultSet.getLong("id"));
                cartItem.setProductId(resultSet.getLong("productId"));
                cartItem.setCartId(resultSet.getLong("cartId"));
                cartItem.setCreatedAt(resultSet.getString("createdAt"));
                cartItem.setQuantity(resultSet.getInt("quantity"));
            }
        } catch (ConnectionPoolException | SQLException e) {
            logger.error("Error while getting cart item from cart_item database . Message: " + e.getMessage());
            e.printStackTrace();
        } finally {
            if (connection != null)
                connectionPool.closeConnection(connection, statement, resultSet);
        }
        return cartItem;

    }

    @Override
    public void insert(CartItem cartItem) {
        Connection connection = null;
        PreparedStatement statement = null;
        try {
            connection = connectionPool.takeConnection();
            statement = connection.prepareStatement(SQL_INSERT_NEW_CART_ITEM);
            statement.setLong(1, cartItem.getProductId());
            statement.setLong(2, cartItem.getCartId());
            statement.setString(3, cartItem.getCreatedAt());
            statement.setInt(4, cartItem.getQuantity());
            statement.executeUpdate();
        } catch (ConnectionPoolException | SQLException e) {
            logger.error("Error while inserting cart item to cart_item database . Message: " + e.getMessage());
            e.printStackTrace();
        } finally {
            if (connection != null)
                connectionPool.closeConnection(connection, statement);
        }
    }

    @Override
    public void update(CartItem cartItem) {
        Connection connection = null;
        PreparedStatement statement = null;
        try {
            connection = connectionPool.takeConnection();
            statement = connection.prepareStatement(SQL_UPDATE_CART_ITEM);
            statement.setLong(1, cartItem.getProductId());
            statement.setString(2, cartItem.getCreatedAt());
            statement.setInt(3, cartItem.getQuantity());
            statement.setLong(4, cartItem.getId());
            statement.executeUpdate();
        } catch (ConnectionPoolException | SQLException e) {
            logger.error("Error while updating cart item in cart_item database . Message: " + e.getMessage());
            e.printStackTrace();
        } finally {
            if (connection != null)
                connectionPool.closeConnection(connection, statement);
        }
    }

    @Override
    public void delete(CartItem cartItem) {
        Connection connection = null;
        PreparedStatement statement = null;
        try {
            connection = connectionPool.takeConnection();
            statement = connection.prepareStatement(SQL_DELETE_CART_ITEM);
            statement.setLong(1, cartItem.getId());
            statement.executeUpdate();
        } catch (ConnectionPoolException | SQLException e) {
            logger.error("Error while deleting cart items from cart_item database . Message: " + e.getMessage());
            e.printStackTrace();
        } finally {
            if (connection != null)
                connectionPool.closeConnection(connection, statement);
        }
    }

    @Override
    public List<CartItem> getByCartId(long cartId) {
        List<CartItem> cartItems = new ArrayList<>();
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            connection = connectionPool.takeConnection();
            statement = connection.prepareStatement(SQL_SELECT_CART_ITEMS_BY_CART_ID);
            statement.setLong(1, cartId);
            resultSet = statement.executeQuery();
            while (resultSet.next()) {
                CartItem cartItem = new CartItem();
                cartItem.setId(resultSet.getLong("id"));
                cartItem.setProductId(resultSet.getLong("productId"));
                cartItem.setCartId(cartId);
                cartItem.setCreatedAt(resultSet.getString("createdAt"));
                cartItem.setQuantity(resultSet.getInt("quantity"));
                cartItems.add(cartItem);
            }
        } catch (ConnectionPoolException | SQLException e) {
            logger.error("Error while getting cart items by ID from cart_item database . Message: " + e.getMessage());
            e.printStackTrace();
        } finally {
            if (connection != null)
                connectionPool.closeConnection(connection, statement, resultSet);
        }
        return cartItems;
    }
}
