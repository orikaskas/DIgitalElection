package com.example.digitalelections.Repositry;

public class User {
    private static String username=null;
    private static String id=null;
    private static String email=null;
    private static String phone=null;
    private static int age=-1;
    private static String city=null;
    private static int vote=0;

    public User() {
    }
    public static void setInfo(String username1,String id1,String email1,String phone1,int age1,String city1,int v){
        username =username1;
        id=id1;
        email=email1;
        phone=phone1;
        age=age1;
        city=city1;
        vote = v;
    }

    public static int getVote() {
        return vote;
    }

    public static void setVote(int vote) {
        User.vote = vote;
    }

    public static String getUsername() {
        return username;
    }

    public static void setUsername(String username) {
        User.username = username;
    }

    public static String getId() {
        return id;
    }

    public static void setId(String id) {
        User.id = id;
    }

    public static String getEmail() {
        return email;
    }

    public static void setEmail(String email) {
        User.email = email;
    }

    public static String getPhone() {
        return phone;
    }

    public static void setPhone(String phone) {
        User.phone = phone;
    }

    public static int getAge() {
        return age;
    }

    public static void setAge(int age) {
        User.age = age;
    }

    public static String getCity() {
        return city;
    }

    public static void setCity(String city) {
        User.city = city;
    }
}
