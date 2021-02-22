package dao;

import entity.Category;
import exception.ConnectionPoolException;
import exception.DAOException;

import java.util.List;

public interface CategoryDao extends BaseDao <Long, Category>{
    List<Category> getByLanguage(long id) throws DAOException, ConnectionPoolException;
}
