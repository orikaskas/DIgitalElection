package com.example.digitalelections.UI.SignIn;

import android.content.Context;

import com.example.digitalelections.Repositry.FireBase;
import com.example.digitalelections.Repositry.Repository;
import com.example.digitalelections.Repositry.User;
import com.google.firebase.auth.FirebaseAuth;

public class modelSignIn {
    String email;
    String id;
    boolean c;

    public modelSignIn(String email, String id, boolean c) {
        this.setEmail(email);
        this.setId(id);
        this.setC(c);
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
        fireBase.SignIn(this.email, this.id, context, this.c, new Repository.Completed() {
            @Override
            public void onComplete(boolean flag) {
                callback4.onComplete(flag);
            }
        });
    }
    public void GetInfo(Context context){
        Repository r = new Repository(context);
        r.getInfo();
    }
}
