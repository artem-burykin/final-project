package publish.db.entity;

import java.io.Serializable;
import java.sql.Date;

/**
 * Order entity
 * @author Burykin
 */
public class Order implements Serializable {
    private int id;
    private double total;
    private int account_id;
    private int product_id;
    private String description;
    private Date create_date;
    private Date last_update;

    private Order(){}

    private Order(double total, int account_id, int product_id){
        this.total = total;
        this.account_id = account_id;
        this.product_id = product_id;
    }

    public static Order createOrder (double total, int account_id, int product_id){
        return new Order(total, account_id, product_id);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public int getAccount_id() {
        return account_id;
    }

    public void setAccount_id(int account_id) {
        this.account_id = account_id;
    }

    public int getProduct_id() {
        return product_id;
    }

    public void setProduct_id(int product_id) {
        this.product_id = product_id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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

    public String toString() {
        return "Order{" +
                "total=" + total +
                ", account_id=" + account_id +
                ", product_id=" + product_id +
                ", description='" + description +
                ", create_date=" + create_date +
                ", last_update=" + last_update +
                '}';
    }
}
