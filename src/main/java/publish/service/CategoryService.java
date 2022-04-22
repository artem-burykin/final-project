package publish.service;

import publish.db.dao.DBException;
import publish.db.entity.Category;

import java.util.List;

/**
 * Service for manage category.
 * @author Burykin
 */
public interface CategoryService {
    List<Category> findAllCategories() throws DBException;
}
