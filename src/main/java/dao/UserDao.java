package dao;

import entity.User;

import java.util.List;

public interface UserDao extends BaseDao <Long, User> {
    User checkLogin(String email, String password);
    User checkAdminLogin(String email, String password);
    User getByEmail(String email);
}
