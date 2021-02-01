package dao.impl;

import connection.ConnectionPool;
import connection.ConnectionPoolException;
import dao.CartDao;
import dao.factory.DaoFactory;
import entity.Cart;
import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CartDaoImpl implements CartDao {

    private final static Logger logger = Logger.getLogger(CartDaoImpl.class);

    private static final String SQL_SELECT_ALL_CARTS = "SELECT * FROM cart";

    private static final String SQL_SELECT_CART = "SELECT * FROM cart WHERE id = ?";

    private static final String SQL_INSERT_NEW_CART = "INSERT INTO cart (userId, createdAt, totalPrice) values (?, ?, ?)";

    private static final String SQL_UPDATE_CART = "UPDATE cart SET createdAt = ?, totalPrice = ? WHERE id = ?";

    private static final String SQL_GET_CART_ID = "SELECT id FROM cart WHERE userId = ? AND createdAt = ?";

    ConnectionPool connectionPool = DaoFactory.getConnectionPool();

    @Override
    public List<Cart> getAll() {
        List<Cart> carts = new ArrayList<>();
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            connection = connectionPool.takeConnection();
            statement = connection.prepareStatement(SQL_SELECT_ALL_CARTS);
            resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Cart cart = new Cart();
                cart.setId(resultSet.getLong("id"));
                cart.setUserId(resultSet.getLong("userId"));
                cart.setCreatedAt(resultSet.getString("createdAt"));
                cart.setTotalPrice(resultSet.getBigDecimal("totalPrice"));
                carts.add(cart);
            }
        } catch (ConnectionPoolException | SQLException e) {
            logger.error("Error while getting Carts from database. Message: " + e.getMessage());
            e.printStackTrace();
        } finally {
            if (connection != null)
                connectionPool.closeConnection(connection, statement, resultSet);
        }
        return carts;
    }

    @Override
    public Cart get(Long id) {
        Cart cart = new Cart();
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        try {
            connection = connectionPool.takeConnection();
            statement = connection.prepareStatement(SQL_SELECT_CART);
            statement.setLong(1, id);
            resultSet = statement.executeQuery();
            if (resultSet.next()) {
                cart.setId(resultSet.getLong("id"));
                cart.setUserId(resultSet.getLong("userId"));
                cart.setCreatedAt(resultSet.getString("createdAt"));
                cart.setTotalPrice(resultSet.getBigDecimal("totalPrice"));
            }
        } catch (ConnectionPoolException | SQLException e) {
            logger.error("Error while getting Cart from database. Message: " + e.getMessage());
            e.printStackTrace();
        } finally {
            if (connection != null)
                connectionPool.closeConnection(connection, statement, resultSet);
        }
        return cart;
    }

    @Override
    public void insert(Cart cart) {
        Connection connection = null;
        PreparedStatement statement = null;
        try {
            connection = connectionPool.takeConnection();
            statement = connection.prepareStatement(SQL_INSERT_NEW_CART);
            statement.setLong(1, cart.getUserId());
            statement.setString(2, cart.getCreatedAt());
            statement.setBigDecimal(3, cart.getTotalPrice());
            statement.executeUpdate();
        } catch (ConnectionPoolException | SQLException e) {
            logger.error("Error while inserting Cart to database. Message: " + e.getMessage());
            e.printStackTrace();
        } finally {
            if (connection != null)
                connectionPool.closeConnection(connection, statement);
        }
    }

    @Override
    public void update(Cart cart) {
        Connection connection = null;
        PreparedStatement statement = null;
        try {
            connection = connectionPool.takeConnection();
            statement = connection.prepareStatement(SQL_UPDATE_CART);
            statement.setString(1, cart.getCreatedAt());
            statement.setBigDecimal(2, cart.getTotalPrice());
            statement.setLong(3, cart.getId());
            statement.executeUpdate();
        } catch (ConnectionPoolException | SQLException e) {
            logger.error("Error while updating Cart in database. Message: " + e.getMessage());
            e.printStackTrace();
        } finally {
            if (connection != null)
                connectionPool.closeConnection(connection, statement);
        }
    }

    @Override
    public void delete(Cart cart) {

    }

    @Override
    public long getId(long userId, String datetime) {
        long cartId = 0;
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        try {
            connection = connectionPool.takeConnection();
            statement = connection.prepareStatement(SQL_GET_CART_ID);
            statement.setLong(1, userId);
            statement.setString(2, datetime);
            resultSet = statement.executeQuery();
            if (resultSet.next()) {
                cartId = resultSet.getLong("id");
            }
        } catch (ConnectionPoolException | SQLException e) {
            logger.error("Error while getting ID from cart database. Message: " + e.getMessage());
            e.printStackTrace();
        } finally {
            if (connection != null)
                connectionPool.closeConnection(connection, statement, resultSet);
        }
        return cartId;
    }
}
