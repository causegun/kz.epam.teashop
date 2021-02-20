package connection;

import org.apache.log4j.Logger;

import java.sql.*;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public final class ConnectionPool {
    private final static Logger logger = Logger.getLogger(ConnectionPool.class);
    private static ConnectionPool obj = null;
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
            logger.error("Error while setting driver settings in connection pool. Message: " + e.getMessage());
            e.printStackTrace();
        }
        try {
            this.poolSize = Integer.parseInt(dbResourceManager.getValue(DBParameter.DB_POLL_SIZE));
        } catch (NumberFormatException e) {
            logger.error("Error while getting size of connection pool. Message: " + e.getMessage());
            e.printStackTrace();
            poolSize = 10;
        }
    }

    public static ConnectionPool getInstance() {
        if (obj == null) {
            obj = new ConnectionPool();
            try {
                obj.initPoolData();
            } catch (ConnectionPoolException e) {
                logger.error("Error while getting instance of connection pool. Message: " + e.getMessage());
                e.printStackTrace();
            }
        }
        return obj;
    }

    public void initPoolData() throws ConnectionPoolException {
        try {
            Class.forName(driverName);
            connectionQueue = new ArrayBlockingQueue<>(poolSize);
            for (int i = 0; i < poolSize; i++) {
                Connection connection = DriverManager.getConnection(url, user, password);
                connectionQueue.add(connection);
            }
        } catch (SQLException e) {
            logger.error("SQLException in ConnectionPool. Message: " + e.getMessage());
            throw new ConnectionPoolException("SQLException in ConnectionPool. Message: ", e);
        } catch (ClassNotFoundException e) {
            logger.error("Can't find database driver class" + e.getMessage());
            throw new ConnectionPoolException("Can't find database driver class. Message: ", e);
        }
    }

    public Connection takeConnection() throws ConnectionPoolException {
        Connection connection;

        try {
            connection = connectionQueue.take();
        } catch (InterruptedException e) {
            logger.error("Error connecting to the data source. Message: " + e.getMessage());
            throw new ConnectionPoolException("Error connecting to the data source.", e);
        }
        return connection;
    }

    public void closeConnection(Connection con, Statement st, ResultSet rs) {
        connectionQueue.add(con);

        try {
            rs.close();
        } catch (SQLException e) {
            logger.error("ResultSet isn't closed. Message: " + e.getMessage());
        }
        try {
            st.close();
        } catch (SQLException e) {
            logger.error("Statement isn't closed. Message: " + e.getMessage());
        }
    }

    public void closeConnection(Connection con, Statement st) {
        connectionQueue.add(con);

        try {
            st.close();
        } catch (SQLException e) {
            logger.error("Statement isn't closed. Message: " + e.getMessage());
        }
    }
}

