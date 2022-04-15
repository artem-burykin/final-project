package publish.db.entity;

import java.io.Serializable;
import java.util.Date;

/**
 * Account entity
 * @author Burykin
 */
public class Account implements Serializable {
    private int id;
    private String login;
    private String password;
    private String email;
    private String first_name;
    private String last_name;
    private double score;
    private int role_id;
    private int isBlocked;
    private Date create_date;
    private Date last_update;

    private Account() {
    }

    private Account(int id, String login, String password, String email, String first_name, String last_name, double score, int role_id) {
        this.id = id;
        this.login = login;
        this.password = password;
        this.email = email;
        this.first_name = first_name;
        this.last_name = last_name;
        this.score = score;
        this.role_id = role_id;
    }

    public static Account createAccount(String login, String password, String email, String first_name, String last_name, double score, int role_id) {
        return new Account(0, login, password, email, first_name, last_name, score, role_id);
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public double getScore() {
        return score;
    }

    public void setScore(double score) {
        this.score = score;
    }

    public int getIsBlocked() {
        return isBlocked;
    }

    public void setIsBlocked(int isBlocked) {
        this.isBlocked = isBlocked;
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
