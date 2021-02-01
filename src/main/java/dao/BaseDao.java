package dao;


import connection.ConnectionPool;
import connection.ConnectionPoolException;
import entity.Entity;


import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.List;

public interface BaseDao<K, T extends Entity> {
    List<T> getAll();
    T get(K id);
    void insert(T t);
    void update(T t);
    void delete(T t);
}
