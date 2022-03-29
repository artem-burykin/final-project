package publish.service;

import publish.db.dao.CategoryDao;
import publish.db.entity.Category;

public class CategoryServiceImp {
    private final CategoryDao categoryDao;

    public CategoryServiceImp(CategoryDao categoryDao) {
        this.categoryDao = categoryDao;
    }

    public static Category getCategory(String name){
        return Category.createCategory(name);
    }
}
