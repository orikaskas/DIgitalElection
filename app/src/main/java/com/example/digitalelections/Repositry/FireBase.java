package com.example.digitalelections.Repositry;

import android.content.Context;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

public class FireBase {
    //the method sign go to repository and to the action

    public void SignIn(String email, String id, Context context, boolean c, Repository.Completed callback3){
        Repository r = new Repository(context);
        r.singInAuthentication(email, id, c, new Repository.Completed() {
            @Override
            public void onComplete(boolean flag) {
                callback3.onComplete(flag);
            }
        });

    }
    public void SignUp(String email, String id, String name, int age, String phone, String city, Context context, boolean check, Repository.Completed completed) {
        Repository r = new Repository(context);
        r.singUpAuthentication(email, id, name, age, phone, city, check, new Repository.Completed() {
            @Override
            public void onComplete(boolean flag) {
                completed.onComplete(flag);
            }
        });

    }
}
