package dao.impl;

import connection.ConnectionPool;
import exception.ConnectionPoolException;
import dao.UserDao;
import dao.factory.DaoFactory;
import entity.User;
import exception.DAOException;
import org.apache.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDaoImpl implements UserDao {

    private final static Logger logger = Logger.getLogger(UserDaoImpl.class);

    private static final String SQL_SELECT_ALL_USERS =
            "SELECT * FROM user_info";

    private static final String SQL_SELECT_USER_BY_ID =
            "SELECT * FROM user_info WHERE id = ?";

    private static final String SQL_INSERT_NEW_USER =
            "INSERT INTO user_info (isAdmin, userName, email, userPassword, phoneNumber) values (?, ?, ?, ?, ?)";

    private static final String SQL_CHECK_USER =
            "SELECT * FROM user_info WHERE email = ? and userPassword = ?";

    private static final String SQL_CHECK_ADMIN =
            "SELECT * FROM user_info WHERE isAdmin = true and email = ? and userPassword = ?";

    private static final String SQL_DELETE_USER =
            "DELETE FROM user_info WHERE id = ?";

    private static final String SQL_UPDATE_USER =
            "UPDATE user_info SET isAdmin = ?, userName = ?, email = ?, phoneNumber = ? WHERE id = ? ";

    private static final String SQL_SELECT_BY_EMAIL = "SELECT id, userName, email FROM user_info WHERE email = ?";

    private static final String ID = "id";
    private static final String USER_NAME = "userName";
    private static final String EMAIL = "email";
    private static final String PHONE_NUMBER = "phoneNumber";
    private static final String IS_ADMIN = "isAdmin";

    private final DaoFactory daoFactory = DaoFactory.getInstance();
    private final ConnectionPool connectionPool = daoFactory.getConnectionPool();

    public UserDaoImpl() {
    }

    @Override
    public List<User> getAll() throws DAOException, ConnectionPoolException {
        List<User> users = new ArrayList<>();
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        try {
            connection = connectionPool.takeConnection();
            statement = connection.prepareStatement(SQL_SELECT_ALL_USERS);
            resultSet = statement.executeQuery();
            long id;

            while (resultSet.next()) {
                User user = new User();

                id = resultSet.getLong(ID);
                setDataToUser(resultSet, id, user);
                users.add(user);
            }
        } catch (ConnectionPoolException | SQLException e) {
            logger.error("Error while getting Users from user_info database . Message: " + e.getMessage());
            throw new DAOException("Error querying users from database. ", e);
        } finally {
            if (connection != null && statement != null && resultSet != null)
                connectionPool.closeConnection(connection, statement, resultSet);
        }
        return users;
    }

    private void setDataToUser(ResultSet resultSet, long id, User user) throws SQLException {
        String userName;
        boolean isAdmin;
        String email;
        String phoneNumber;
        userName = resultSet.getString(USER_NAME);
        isAdmin = resultSet.getBoolean(IS_ADMIN);
        email = resultSet.getString(EMAIL);
        phoneNumber = resultSet.getString(PHONE_NUMBER);

        user.setId(id);
        user.setName(userName);
        user.setAdmin(isAdmin);
        user.setEmail(email);
        user.setPhoneNumber(phoneNumber);
    }

    @Override
    public User get(Long id) throws DAOException, ConnectionPoolException {
        User user = new User();
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        try {
            connection = connectionPool.takeConnection();
            statement = connection.prepareStatement(SQL_SELECT_USER_BY_ID);
            statement.setLong(1, id);
            resultSet = statement.executeQuery();

            if (resultSet.next()) {
                setDataToUser(resultSet, id, user);
            }
        } catch (ConnectionPoolException | SQLException e) {
            logger.error("Error while getting User from user_info database . Message: " + e.getMessage());
            throw new DAOException("Error querying user from database. ", e);
        } finally {
            if (connection != null && statement != null && resultSet != null)
                connectionPool.closeConnection(connection, statement, resultSet);
        }
        return user;
    }

    @Override
    public void insert(User user) throws DAOException, ConnectionPoolException {
        Connection connection = null;
        PreparedStatement statement = null;

        try {
            connection = connectionPool.takeConnection();
            statement = connection.prepareStatement(SQL_INSERT_NEW_USER);

            boolean isAdmin = user.isAdmin();
            String name = user.getName();
            String email = user.getEmail();
            String password = user.getPassword();
            String phoneNumber = user.getPhoneNumber();

            statement.setBoolean(1, isAdmin);
            statement.setString(2, name);
            statement.setString(3, email);
            statement.setString(4, password);
            statement.setString(5, phoneNumber);

            statement.executeUpdate();
        } catch (ConnectionPoolException | SQLException e) {
            logger.error("Error while inserting User to user_info database . Message: " + e.getMessage());
            throw new DAOException("Error inserting user to database. ", e);
        } finally {
            if (connection != null && statement != null)
                connectionPool.closeConnection(connection, statement);

        }
    }


    @Override
    public void update(User user) throws DAOException, ConnectionPoolException {
        Connection connection = null;
        PreparedStatement statement = null;

        try {
            connection = connectionPool.takeConnection();
            statement = connection.prepareStatement(SQL_UPDATE_USER);

            long id = user.getId();
            boolean isAdmin = user.isAdmin();
            String name = user.getName();
            String email = user.getEmail();
            String phoneNumber = user.getPhoneNumber();

            statement.setBoolean(1, isAdmin);
            statement.setString(2, name);
            statement.setString(3, email);
            statement.setString(4, phoneNumber);
            statement.setLong(5, id);
            statement.executeUpdate();
        } catch (ConnectionPoolException | SQLException e) {
            logger.error("Error while updating User in user_info database . Message: " + e.getMessage());
            throw new DAOException("Error updating user in database. ", e);
        } finally {
            if (connection != null && statement != null)
                connectionPool.closeConnection(connection, statement);
        }
    }

    @Override
    public void delete(User user) throws DAOException, ConnectionPoolException {
        Connection connection = null;
        PreparedStatement statement = null;
        try {
            connection = connectionPool.takeConnection();
            statement = connection.prepareStatement(SQL_DELETE_USER);

            statement.setLong(1, user.getId());

            statement.executeUpdate();
        } catch (ConnectionPoolException | SQLException e) {
            logger.error("Error while deleting User from user_info database . Message: " + e.getMessage());
            throw new DAOException("Error deleting user from database. ", e);
        } finally {
            if (connection != null && statement != null)
                connectionPool.closeConnection(connection, statement);
        }
    }

    @Override
    public User checkLogin(String email, String password) throws DAOException, ConnectionPoolException {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        User user = null;
        try {
            connection = connectionPool.takeConnection();
            statement = connection.prepareStatement(SQL_CHECK_USER);
            statement.setString(1, email);
            statement.setString(2, password);
            resultSet = statement.executeQuery();

            String userName;
            String emailFromDB;

            if (resultSet.next()) {
                user = new User();

                userName = resultSet.getString(USER_NAME);
                emailFromDB = resultSet.getString(EMAIL);

                user.setName(userName);
                user.setEmail(emailFromDB);
            }
        } catch (SQLException | ConnectionPoolException e) {
            logger.error("Error while checking login of User in user_info database . Message: " + e.getMessage());
            throw new DAOException("Error checking user in database. ", e);
        } finally {
            if (connection != null && statement != null && resultSet != null)
                connectionPool.closeConnection(connection, statement, resultSet);
        }
        return user;
    }

    @Override
    public User checkAdminLogin(String email, String password) throws DAOException, ConnectionPoolException {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        User user = null;
        try {
            connection = connectionPool.takeConnection();
            statement = connection.prepareStatement(SQL_CHECK_ADMIN);
            statement.setString(1, email);
            statement.setString(2, password);
            resultSet = statement.executeQuery();

            String userName;
            String emailFromDB;

            if (resultSet.next()) {
                user = new User();

                userName = resultSet.getString(USER_NAME);
                emailFromDB = resultSet.getString(EMAIL);

                user.setName(userName);
                user.setEmail(emailFromDB);
            }
        } catch (SQLException | ConnectionPoolException e) {
            logger.error("Error while checking login of Admin in user_info database . Message: " + e.getMessage());
            throw new DAOException("Error checking admin in database. ", e);
        } finally {
            if (connection != null && statement != null && resultSet != null)
                connectionPool.closeConnection(connection, statement, resultSet);
        }
        return user;
    }

    @Override
    public User getByEmail(String email) throws DAOException, ConnectionPoolException {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        User user = null;
        try {
            connection = connectionPool.takeConnection();
            statement = connection.prepareStatement(SQL_SELECT_BY_EMAIL);
            statement.setString(1, email);
            resultSet = statement.executeQuery();

            long id;
            String userName;

            if (resultSet.next()) {
                user = new User();

                id = resultSet.getLong(ID);
                userName = resultSet.getString(USER_NAME);

                user.setId(id);
                user.setName(userName);
                user.setEmail(email);
            }
        } catch (SQLException | ConnectionPoolException e) {
            logger.error("Error while getting User by email from user_info database . Message: " + e.getMessage());
            throw new DAOException("Error querying user from database. ", e);
        } finally {
            if (connection != null && statement != null && resultSet != null)
                connectionPool.closeConnection(connection, statement, resultSet);
        }
        return user;
    }
}
