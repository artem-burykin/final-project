package publish.db.entity;

import java.util.Date;

public class Account {
    private int id;
    private String login;
    private String password;
    private int role_id;
    private Date create_date;
    private Date last_update;

    public Account() {
    }

    private Account(int id, String login, String password, int role_id) {
        this.id = id;
        this.login = login;
        this.password = password;
        this.role_id = role_id;
    }

    public static Account createAccount(String login, String password, int role_id) {
        return new Account(0, login, password, role_id);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getRole_id() {
        return role_id;
    }

    public void setRole_id(int role_id) {
        this.role_id = role_id;
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
        return "Account{" +
                "login = '" + login + '\'' +
                ", password = '" + "<PROTECTED>" + '\'' +
                ", role_id = " + role_id +
                ", create_date = " + create_date +
                ", last_update = " + last_update +
                '}';
    }
}
