package dao;

import entity.Product;
import exception.ConnectionPoolException;
import exception.DAOException;

import java.util.List;

public interface ProductDao extends BaseDao <Long, Product>{
    List<Product> getByCategory(long categoryId) throws DAOException, ConnectionPoolException;
}
