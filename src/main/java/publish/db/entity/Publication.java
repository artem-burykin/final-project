package publish.db.entity;

import java.io.Serializable;
import java.sql.Date;

public class Publication implements Serializable {
    private int id;
    private int product_id;
    private String name;
    private String content;
    private Date create_date;

    private Publication(){
    }

    private Publication(int product_id, String name, String content){
        this.product_id = product_id;
        this.name  = name;
        this.content = content;
    }

    public static Publication createdPublication (int product_id, String name, String content){
        return new Publication(product_id, name, content);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getProduct_id() {
        return product_id;
    }

    public void setProduct_id(int product_id) {
        this.product_id = product_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getCreate_date() {
        return create_date;
    }

    public void setCreate_date(Date create_date) {
        this.create_date = create_date;
    }

    @Override
    public String toString() {
        return "Publication{" +
                "product_id=" + product_id +
                ", name='" + name + '\'' +
                ", content='" + content + '\'' +
                ", create_date=" + create_date +
                '}';
    }
}
