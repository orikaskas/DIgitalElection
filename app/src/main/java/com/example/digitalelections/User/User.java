package com.example.digitalelections.User;

public class User {
    private static String username = null; // שם המשתמש הנוכחי
    private static String id = null; // זיהוי המשתמש הנוכחי
    private static String email = null; // כתובת האימייל של המשתמש הנוכחי
    private static String phone = null; // מספר הטלפון של המשתמש הנוכחי
    private static int age = -1; // גיל המשתמש הנוכחי
    private static String city = null; // העיר של המשתמש הנוכחי
    private static int vote = 0; // ההצבעה של המשתמש הנוכחי
    private static int voteCity = 0; // ההצבעה בעיר של המשתמש הנוכחי

    public User() {
    }
    // פעולה לעדכון פרטי המשתמש
    public static void setInfo(String username1, String id1, String email1, String phone1, int age1, String city1, int v, int Vcity) {
        User.username = username1;
        User.id = id1;
        User.email = email1;
        User.phone = phone1;
        User.age = age1;
        User.city = city1;
        User.vote = v;
        voteCity = Vcity;
    }

    // פעולות להחזרת ערכים של המשתמש
    public static int getVoteCity() {
        return voteCity;
    }

    public static void setVoteCity(int voteCity) {
        User.voteCity = voteCity;
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
