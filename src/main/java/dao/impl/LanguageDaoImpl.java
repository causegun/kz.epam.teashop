package dao.impl;

import connection.ConnectionPool;
import connection.ConnectionPoolException;
import dao.LanguageDao;
import dao.factory.DaoFactory;
import entity.Language;
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

    ConnectionPool connectionPool = DaoFactory.getConnectionPool();

    @Override
    public List<Language> getAll() {
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
                id = resultSet.getLong("id");
                languageName = resultSet.getString("languageName");

                language.setId(id);
                language.setName(languageName);
                languages.add(language);
            }
        } catch (ConnectionPoolException | SQLException e) {
            logger.error("Error while getting languages from language_info database . Message: " + e.getMessage());
            e.printStackTrace();
        } finally {
            if (connection != null && statement != null && resultSet != null)
                connectionPool.closeConnection(connection, statement, resultSet);
        }
        return languages;
    }

    @Override
    public Language get(Long id) {
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
            e.printStackTrace();
        } finally {
            if (connection != null && statement != null && resultSet != null)
                connectionPool.closeConnection(connection, statement, resultSet);
        }
        return language;
    }

    @Override
    public void insert(Language language) {
        Connection connection = null;
        PreparedStatement statement = null;
        try {
            connection = connectionPool.takeConnection();
            statement = connection.prepareStatement(SQL_INSERT_NEW_LANGUAGE);
            statement.setString(1, language.getName());
            statement.executeUpdate();
        } catch (ConnectionPoolException | SQLException e) {
            logger.error("Error while inserting language to language_info database . Message: " + e.getMessage());
            e.printStackTrace();
        } finally {
            if (connection != null && statement != null)
                connectionPool.closeConnection(connection, statement);

        }
    }

    @Override
    public void update(Language language) {
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
            e.printStackTrace();
        } finally {
            if (connection != null && statement != null)
                connectionPool.closeConnection(connection, statement);
        }
    }

    @Override
    public void delete(Language language) {
    }

}
