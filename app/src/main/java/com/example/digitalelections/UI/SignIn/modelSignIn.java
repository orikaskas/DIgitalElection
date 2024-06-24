package com.example.digitalelections.UI.SignIn;

import android.content.Context;

import com.example.digitalelections.DB.FireBase;
import com.example.digitalelections.Repositry.Repository;

public class modelSignIn {
    private String email; // כתובת האימייל
    private String id; // זיהוי המשתמש
    private boolean c; // הרשאה להתחברות
    private Repository r ;//משתנה מסוג ריפוזיטורי

    public modelSignIn(String email, String id, boolean c,Context context) {
        this.setEmail(email); // הגדרת האימייל
        this.setId(id); // הגדרת הזיהוי
        this.setC(c); // הגדרת ההרשאה
        this.r = new Repository(context);
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public boolean isC() {
        return c;
    }

    public void setC(boolean c) {
        this.c = c;
    }

    public void SingIn(Context context, Repository.Completed callback4){
        FireBase fireBase = new FireBase();
        // קריאה לפונקציה להתחברות ב-Firebase
        fireBase.SignIn(this.email, this.id, context, this.c, callback4);
    }

    public void GetInfo( String email, Repository.Completed completed){
        // קריאה לפונקציה לקבלת מידע מה-Repository
        r.getInfo(email, completed);
    }
}
