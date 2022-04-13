package publish.db.dao;

import publish.db.entity.Category;

import java.util.List;

/**
 * Interface with method, which realization in MysqlCategoryDao.
 * @author Byrukin
 */
public interface CategoryDao {
    List<Category> findAllCategories() throws DBException;
}
