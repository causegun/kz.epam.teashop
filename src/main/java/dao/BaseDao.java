package dao;


import entity.Entity;
import exception.ConnectionPoolException;
import exception.DAOException;

import java.util.List;

public interface BaseDao<K, T extends Entity> {
    List<T> getAll() throws DAOException, ConnectionPoolException;
    T get(K id) throws DAOException, ConnectionPoolException;
    void insert(T t) throws DAOException, ConnectionPoolException;
    void update(T t) throws DAOException, ConnectionPoolException;
    void delete(T t) throws DAOException, ConnectionPoolException;
}
