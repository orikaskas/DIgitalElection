package com.example.digitalelections.Repositry;

public class User {
    private String username;
    private String id;
    private String email;
    private String phone;
    private int age;
    private String city;
    public User(String username, String id, String email, String phone, int age, String city ){
        this.setUsername(username);
        this.setId(id);
        this.setEmail(email);
        this.setPhone(phone);
        this.setAge(age);
        this.setCity(city);
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

}
