package publish.db.entity;

import java.io.Serializable;

public class Category implements Serializable {
    private int id;
    private String name;
    private int parent_id;
    private String description;

    private Category(){
    }

    private Category(String name){
        this.name = name;
    }

    public static Category createCategory(String name){
        return new Category(name);
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

    public int getParent_id() {
        return parent_id;
    }

    public void setParent_id(int parent_id) {
        this.parent_id = parent_id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "Category{" +
                "name='" + name + '\'' +
                ", parent_id=" + parent_id +
                ", description='" + description + '\'' +
                '}';
    }
}
