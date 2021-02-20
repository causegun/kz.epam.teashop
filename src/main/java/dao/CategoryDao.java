package dao;

import entity.Category;

import java.util.List;

public interface CategoryDao extends BaseDao <Long, Category>{
    List<Category> getByLanguage(long id);
}
