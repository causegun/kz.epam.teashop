package dao;

import entity.User;
import exception.ConnectionPoolException;
import exception.DAOException;

import java.util.List;

public interface UserDao extends BaseDao <Long, User> {
    User checkLogin(String email, String password) throws DAOException, ConnectionPoolException;
    User checkAdminLogin(String email, String password) throws DAOException, ConnectionPoolException;
    User getByEmail(String email) throws DAOException, ConnectionPoolException;
}
