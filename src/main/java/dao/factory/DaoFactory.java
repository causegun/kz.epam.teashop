package dao.factory;

import connection.ConnectionPool;
import dao.*;
import dao.impl.*;

public class DaoFactory {
    private static DaoFactory instance;
    private static final ConnectionPool connectionPool = ConnectionPool.getInstance();
    private static final UserDao userDao = new UserDaoImpl();
    private static final ProductDao productDao = new ProductDaoImpl();
    private static final LanguageDao languageDao = new LanguageDaoImpl();
    private static final CategoryDao categoryDao = new CategoryDaoImpl();
    private static final CartItemDao cartItemDao = new CartItemDaoImpl();
    private static final CartDao cartDao = new CartDaoImpl();

    public DaoFactory() {
    }

    public static synchronized DaoFactory getInstance() {
        if (instance == null)
            instance = new DaoFactory();
        return instance;
    }

    public ConnectionPool getConnectionPool() {
        return connectionPool;
    }

    public UserDao getUserDao() {
        return userDao;
    }

    public ProductDao getProductDao() {
        return productDao;
    }

    public LanguageDao getLanguageDao() {
        return languageDao;
    }

    public CategoryDao getCategoryDao() {
        return categoryDao;
    }

    public CartItemDao getCartItemDao() {
        return cartItemDao;
    }

    public CartDao getCartDao() {
        return cartDao;
    }
}