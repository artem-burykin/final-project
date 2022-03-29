package publish.service;

import publish.db.entity.Product;

public interface ProductService {
    Product getProduct(String name, double price, int category_id);
}
