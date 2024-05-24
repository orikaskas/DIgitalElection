package com.example.digitalelections.User;

public class UpdateUser {
    private String username=null;
    private String id=null;
    private String email=null;
    private String phone=null;
    private int age=-1;
    private String city=null;
    private int vote=0;
    private int voteCity=0;

    public UpdateUser() {
    }

    public UpdateUser(String username, String id, String email, String phone, int age, String city, int vote, int voteCity) {
        this.username = username;
        this.id = id;
        this.email = email;
        this.phone = phone;
        this.age = age;
        this.city = city;
        this.vote = vote;
        this.voteCity = voteCity;
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

    public int getVote() {
        return vote;
    }

    public void setVote(int vote) {
        this.vote = vote;
    }

    public int getVoteCity() {
        return voteCity;
    }

    public void setVoteCity(int voteCity) {
        this.voteCity = voteCity;
    }
}
