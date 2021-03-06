package dao.impl;

import connection.ConnectionPool;
import exception.ConnectionPoolException;
import dao.LanguageDao;
import dao.factory.DaoFactory;
import entity.Language;
import exception.DAOException;
import org.apache.log4j.Logger;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class LanguageDaoImpl implements LanguageDao {

    private final static Logger logger = Logger.getLogger(LanguageDaoImpl.class);

    private static final String SQL_SELECT_ALL_LANGUAGES = "SELECT * FROM language_info";

    private static final String SQL_SELECT_LANGUAGE = "SELECT * FROM language_info WHERE id = ?";

    private static final String SQL_INSERT_NEW_LANGUAGE = "INSERT INTO language_info (languageName) values (?)";

    private static final String SQL_UPDATE_LANGUAGE = "UPDATE language_info SET languageName = ? WHERE id = ?";

    private final DaoFactory daoFactory = DaoFactory.getInstance();
    private final ConnectionPool connectionPool = daoFactory.getConnectionPool();

    private static final String ID = "id";
    private static final String LANGUAGE_NAME = "languageName";

    @Override
    public List<Language> getAll() throws ConnectionPoolException, DAOException {
        List<Language> languages = new ArrayList<>();
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        try {
            connection = connectionPool.takeConnection();
            statement = connection.prepareStatement(SQL_SELECT_ALL_LANGUAGES);
            resultSet = statement.executeQuery();

            long id;
            String languageName;

            while (resultSet.next()) {
                Language language = new Language();
                id = resultSet.getLong(ID);
                languageName = resultSet.getString(LANGUAGE_NAME);

                language.setId(id);
                language.setName(languageName);
                languages.add(language);
            }
        } catch (ConnectionPoolException | SQLException e) {
            logger.error("Error while getting languages from language_info database . Message: " + e.getMessage());
            throw new DAOException("Error querying languages from database. ", e);
        } finally {
            if (connection != null && statement != null && resultSet != null)
                connectionPool.closeConnection(connection, statement, resultSet);
        }
        return languages;
    }

    @Override
    public Language get(Long id) throws DAOException, ConnectionPoolException {
        Language language = new Language();
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        try {
            connection = connectionPool.takeConnection();
            statement = connection.prepareStatement(SQL_SELECT_LANGUAGE);
            statement.setLong(1, id);
            resultSet = statement.executeQuery();
            String languageName;
            if (resultSet.next()) {
                language.setId(id);
                languageName = resultSet.getString("languageName");
                language.setName(languageName);
            }
        } catch (ConnectionPoolException | SQLException e) {
            logger.error("Error while getting language from language_info database . Message: " + e.getMessage());
            throw new DAOException("Error querying language from database. ", e);
        } finally {
            if (connection != null && statement != null && resultSet != null)
                connectionPool.closeConnection(connection, statement, resultSet);
        }
        return language;
    }

    @Override
    public void insert(Language language) throws DAOException, ConnectionPoolException {
        Connection connection = null;
        PreparedStatement statement = null;
        try {
            connection = connectionPool.takeConnection();
            statement = connection.prepareStatement(SQL_INSERT_NEW_LANGUAGE);
            statement.setString(1, language.getName());
            statement.executeUpdate();
        } catch (ConnectionPoolException | SQLException e) {
            logger.error("Error while inserting language to language_info database . Message: " + e.getMessage());
            throw new DAOException("Error inserting languages to database. ", e);
        } finally {
            if (connection != null && statement != null)
                connectionPool.closeConnection(connection, statement);

        }
    }

    @Override
    public void update(Language language) throws ConnectionPoolException, DAOException {
        Connection connection = null;
        PreparedStatement statement = null;

        try {
            connection = connectionPool.takeConnection();
            statement = connection.prepareStatement(SQL_UPDATE_LANGUAGE);

            long id = language.getId();
            String languageName = language.getName();

            statement.setString(1, languageName);
            statement.setLong(2, id);
            statement.executeUpdate();
        } catch (ConnectionPoolException | SQLException e) {
            logger.error("Error while updating language in language_info database . Message: " + e.getMessage());
            throw new DAOException("Error updating language in database. ", e);
        } finally {
            if (connection != null && statement != null)
                connectionPool.closeConnection(connection, statement);
        }
    }

    @Override
    public void delete(Language language) {
    }

}
