package com.example.digitalelections.UI.SignIn;

import android.content.Context;

import com.example.digitalelections.Repositry.FireBase;
import com.example.digitalelections.Repositry.User;
import com.google.firebase.auth.FirebaseAuth;

public class modelSignIn {
    String email;
    String id;
    modelSignIn(String email,String id){
        this.email=email;
        this.id=id;
    }


    public boolean SingIn(Context context){
        FireBase fireBase = new FireBase();
        boolean b =  fireBase.SignIn(this.email,this.id,context);
        return b;
    }
}
