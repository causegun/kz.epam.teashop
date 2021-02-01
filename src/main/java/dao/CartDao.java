package dao;

import entity.Cart;

public interface CartDao extends BaseDao <Long, Cart>{
    long getId(long userId, String datetime);
}
