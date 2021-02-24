package connection;

import exception.ConnectionPoolException;
import org.apache.log4j.Logger;

import java.sql.*;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public final class ConnectionPool {
    private final static Logger logger = Logger.getLogger(ConnectionPool.class);
    private static ConnectionPool instance;
    private BlockingQueue<Connection> connectionQueue;
    private String driverName;
    private String url;
    private String user;
    private String password;
    private int poolSize;

    private ConnectionPool() {
        DBResourceManager dbResourceManager = DBResourceManager.getInstance();

        try {
            this.driverName = dbResourceManager.getValue(DBParameter.DB_DRIVER);
            this.url = dbResourceManager.getValue(DBParameter.DB_URL);
            this.user = dbResourceManager.getValue(DBParameter.DB_USER);
            this.password = dbResourceManager.getValue(DBParameter.DB_PASSWORD);
        } catch (NullPointerException e) {
            try {
                throw new ConnectionPoolException("Empty driver setting(s). ", e);
            } catch (ConnectionPoolException connectionPoolException) {
                logger.error("Error while getting driver settings in connection pool. Message: "
                        + connectionPoolException.getMessage() + "(" + e.getMessage() + ")");
            }
        }
        try {
            this.poolSize = Integer.parseInt(dbResourceManager.getValue(DBParameter.DB_POLL_SIZE));
        } catch (NumberFormatException e) {
            logger.error("Error while getting size of connection pool. Message: " + e.getMessage());
            poolSize = 10;
        }
    }

    public static synchronized ConnectionPool getInstance() {
        if (instance == null) {
            instance = new ConnectionPool();
            try {
                instance.initPoolData();
            } catch (ConnectionPoolException e) {
                try {
                    throw new ConnectionPoolException("Can't init connection pool data", e);
                } catch (ConnectionPoolException connectionPoolException) {
                    logger.error("Error while getting instance of connection pool. Message: " + connectionPoolException.getMessage());
                }
            }
        }
        return instance;
    }

    private void initPoolData() throws ConnectionPoolException {
        try {
            Class.forName(driverName);
            connectionQueue = new ArrayBlockingQueue<>(poolSize);
            for (int i = 0; i < poolSize; i++) {
                Connection connection = DriverManager.getConnection(url, user, password);
                connectionQueue.add(connection);
            }
        } catch (ClassNotFoundException e) {
            logger.error("Can't find database driver class. Message: " + e.getMessage());
            throw new ConnectionPoolException("Can't find database driver class. ", e);
        } catch (SQLException e) {
            logger.error("Can't get connection to a database. Message: " + e.getMessage());
            throw new ConnectionPoolException("Can't get connection to a database. ", e);
        }
    }

    public Connection takeConnection() throws ConnectionPoolException {
        Connection connection;

        try {
            connection = connectionQueue.take();
        } catch (InterruptedException e) {
            logger.error("Error connecting to the data source. Message: " + e.getMessage());
            throw new ConnectionPoolException("Can't take connection from queue.", e);
        }
        return connection;
    }

    public void closeConnection(Connection con, Statement st, ResultSet rs) throws ConnectionPoolException {
        connectionQueue.add(con);

        try {
            rs.close();
        } catch (SQLException e) {
            logger.error("ResultSet isn't closed. Message: " + e.getMessage());
            throw new ConnectionPoolException("ResultSet isn't closed. ", e);
        }
        try {
            st.close();
        } catch (SQLException e) {
            logger.error("Statement isn't closed. Message: " + e.getMessage());
            throw new ConnectionPoolException("Statement isn't closed. ", e);
        }
    }

    public void closeConnection(Connection con, Statement st) throws ConnectionPoolException {
        connectionQueue.add(con);

        try {
            st.close();
        } catch (SQLException e) {
            logger.error("Statement isn't closed. Message: " + e.getMessage());
            throw new ConnectionPoolException("Statement isn't closed. ", e);
        }
    }
}

