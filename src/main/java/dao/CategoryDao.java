package dao;

import entity.Category;

public interface CategoryDao extends BaseDao <Long, Category>{
    Category getByName(String categoryName);
}
