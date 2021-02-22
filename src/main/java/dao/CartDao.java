package dao;

import entity.Cart;
import exception.ConnectionPoolException;
import exception.DAOException;

public interface CartDao extends BaseDao <Long, Cart>{
    long getId(long userId, String datetime) throws DAOException, ConnectionPoolException;
}
