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
    private int status_id;
    private String description;
    private Date date_start;
    private Date date_end;
    private Date create_date;
    private Date last_update;

    private Order(){}

    private Order(double total, int account_id, int status_id, Date date_start, Date date_end){
        this.total = total;
        this.account_id = account_id;
        this.status_id = status_id;
        this.date_start = date_start;
        this.date_end = date_end;
    }

    public static Order createOrder (double total, int account_id, int status_id, Date date_start, Date date_end){
        return new Order(total, account_id, status_id, date_start, date_end);
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

    public int getStatus_id() {
        return status_id;
    }

    public void setStatus_id(int status_id) {
        this.status_id = status_id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getDate_start() {
        return date_start;
    }

    public void setDate_start(Date date_start) {
        this.date_start = date_start;
    }

    public Date getDate_end() {
        return date_end;
    }

    public void setDate_end(Date date_end) {
        this.date_end = date_end;
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
                ", status_id=" + status_id +
                ", description='" + description + '\'' +
                ", date_start=" + date_start +
                ", date_end=" + date_end +
                ", create_date=" + create_date +
                ", last_update=" + last_update +
                '}';
    }
}
