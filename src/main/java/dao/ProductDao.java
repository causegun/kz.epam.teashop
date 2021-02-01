package dao;

import entity.Product;

import java.util.List;

public interface ProductDao extends BaseDao <Long, Product>{
    List<Product> getByCategory(long categoryId);
}
