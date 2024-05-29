package com.example.digitalelections.Repositry;

import android.content.Context;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

public class FireBase {
    // המתודה מבצעת כניסה למערכת ומופנית ל־Repository ולפעולה המתאימה

    public void SignIn(String email, String id, Context context, boolean c, Repository.Completed callback3){
        Repository r = new Repository(context);
        r.singInAuthentication(email, id, c, callback3);
    }

    // המתודה מבצעת הרשמה למערכת ומופנית ל־Repository ולפעולה המתאימה
    public void SignUp(String email, String id, String name, int age, String phone, String city, Context context, boolean check, Repository.Completed completed) {
        Repository r = new Repository(context);
        r.singUpAuthentication(email, id, name, age, phone, city, check, completed);
    }
}

