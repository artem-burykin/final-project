package publish.service;

import publish.db.dao.DBException;
import publish.db.entity.Category;

import java.util.List;

public interface CategoryService {
    List<Category> findAllCategories() throws DBException;
}
