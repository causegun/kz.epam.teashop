package dao;

import entity.CartItem;

import java.util.List;

public interface CartItemDao extends BaseDao <Long, CartItem>{
    List<CartItem> getByCartId(long cartId);
}
