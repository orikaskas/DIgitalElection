package com.example.digitalelections.Repositry;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

public class FireBase {
    //the method sign go to repository and to the action

    public boolean SignIn(String email, String id){
        Repository r = new Repository();
        boolean b = r.singInAuthentication(email,id);
        return b;
    }
    public boolean SignUp(String name, String id, String email, int age, String phone, String city){
        Repository r = new Repository();
        boolean b = r.singUpAuthentication(email,id,name,age,phone,city);
        return b;
    }


}
