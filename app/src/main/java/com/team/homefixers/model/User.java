package com.team.homefixers.model;

public class User {

    private String id;
    private String name;
    private String email;
    private String password;
    private String numberPhone;
    private String city;

    public User() {
    }

    public User(String id, String name, String email, String password, String numberPhone, String city) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.password = password;
        this.numberPhone = numberPhone;
        this.city = city;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getNumberPhone() {
        return numberPhone;
    }

    public String getCity() {
        return city;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", numberPhone='" + numberPhone + '\'' +
                ", city='" + city + '\'' +
                '}';
    }
}
