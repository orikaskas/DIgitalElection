package com.example.digitalelections.Repositry;

import android.content.Context;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

public class FireBase {
    //the method sign go to repository and to the action

    public boolean SignIn(String email, String id,Context context,boolean c){
        Repository r = new Repository(context);
        boolean b = r.singInAuthentication(email,id,c);
        return b;
    }
    public boolean SignUp(String email, String id, String name, int age, String phone, String city,Context context,boolean check){
        Repository r = new Repository(context);
        boolean b = r.singUpAuthentication(email,id,name,age,phone,city,check);
        return b;
    }
}
