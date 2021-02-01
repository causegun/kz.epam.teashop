package dao.factory;

import connection.ConnectionPool;
import dao.*;
import dao.impl.*;

public class DaoFactory {

    private static DaoFactory instance;

    private static ConnectionPool connectionPool = ConnectionPool.getInstance();

    private static UserDao userDao = new UserDaoImpl();

    private static ProductDao productDao = new ProductDaoImpl();

    private static LanguageDao languageDao = new LanguageDaoImpl();

    private static CategoryDao categoryDao = new CategoryDaoImpl();

    private static CartItemDao cartItemDao = new CartItemDaoImpl();

    private static CartDao cartDao = new CartDaoImpl();

    public DaoFactory() {
    }

    public static DaoFactory getInstance() {
        if (instance == null) {
            synchronized (DaoFactory.class) {
                if (instance == null)
                    instance = new DaoFactory();
            }
        }
        return instance;
    }

    public static ConnectionPool getConnectionPool() {
        return getInstance().connectionPool;
    }

    public static UserDao getUserDao() {
        return getInstance().userDao;
    }

    public static ProductDao getProductDao() {
        return getInstance().productDao;
    }

    public static LanguageDao getLanguageDao() {
        return getInstance().languageDao;
    }

    public static CategoryDao getCategoryDao() {
        return getInstance().categoryDao;
    }

    public static CartItemDao getCartItemDao() {
        return getInstance().cartItemDao;
    }

    public static CartDao getCartDao() {
        return getInstance().cartDao;
    }
}