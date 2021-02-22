package dao;

import entity.CartItem;
import exception.ConnectionPoolException;
import exception.DAOException;

import java.util.List;

public interface CartItemDao extends BaseDao <Long, CartItem>{
    List<CartItem> getByCartId(long cartId) throws DAOException, ConnectionPoolException;
    void deleteByCartId(long cartId) throws DAOException, ConnectionPoolException;
}
