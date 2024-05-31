package org.sc;

import org.sc.data.Flat;
import org.sc.data.House;

import java.io.Serial;
import java.io.Serializable;

public class Request implements Serializable {

    @Serial
    private static final long serialVersionUID = 5760575944040770153L;

    private Long id = null;
    private String message = null;
    private Flat flat = null;
    private House house = null;
    private String password = null;
    private String login = null;

    public Request(String message, Flat flat, String password, String login) {
        this.message = message;
        this.flat = flat;
        this.password = password;
        this.login = login;
    }

    public Request(String message) {
        this.message = message;
    }

    public Request(String message, Long id) {
        this.message = message;
        this.id = id;
    }

    public Request(String message, Long id, Flat flat) {
        this.flat = flat;
        this.message = message;
        this.id = id;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Flat getFlat() {
        return flat;
    }

    public void setFlat(Flat flat) {
        this.flat = flat;
    }

    public House getHouse() {
        return house;
    }

    public void setHouse(House house) {
        this.house = house;
    }
    public String getPassword() {
        return password;
    }
    public String getLogin() {
        return login;
    }

    public long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}