package publish.db.entity;

public class Role {
    private int id;
    private String name;
    private String description;

    public Role() {
    }

    private Role(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public static Role createRole(String name) {
        return new Role(0, name);
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

    @Override
    public String toString() {
        return "Role{" +
                "name = '" + name + '\'' +
                ", description = '" + description + '\'' +
                '}';
    }
}
