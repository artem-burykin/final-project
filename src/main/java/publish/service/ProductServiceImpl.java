package publish.service;

import publish.db.dao.ProductDao;
import publish.db.entity.Product;

public class ProductServiceImpl {
    private final ProductDao productDao;

    public ProductServiceImpl(ProductDao productDao) {
        this.productDao = productDao;
    }

    public static Product getProduct(String name, double price, int category_id){
        return Product.createProduct(name, price, category_id);
    }
}
