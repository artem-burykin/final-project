package publish.db.entity;

import java.io.Serializable;
import java.util.Date;

/**
 * Product entity.
 * @author Burykin
 */
public class Product implements Serializable {
    private int id;
    private String name;
    private String description;
    private double price;
    private int category_id;
    private String logo;
    private Date create_date;
    private Date last_update;

    private Product(){}

    private Product(String name, double price, int category_id) {
        this.name = name;
        this.price = price;
        this.category_id = category_id;
    }

    public static Product createProduct(String name, double price, int category_id){
        return new Product(name, price, category_id);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getCategory_id() {
        return category_id;
    }

    public void setCategory_id(int category_id) {
        this.category_id = category_id;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public Date getCreate_date() {
        return create_date;
    }

    public void setCreate_date(Date create_date) {
        this.create_date = create_date;
    }

    public Date getLast_update() {
        return last_update;
    }

    public void setLast_update(Date last_update) {
        this.last_update = last_update;
    }

    @Override
    public String toString() {
        return "Product{" + name + ", " + description + ", " + price +
                ", " + category_id +
                ", " + logo + '\'' +
                ", " + create_date +
                ", " + last_update +
                '}';
    }
}