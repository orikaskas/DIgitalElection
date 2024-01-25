package com.example.digitalelections.Repositry;

import com.google.firebase.auth.FirebaseAuth;

public class FireBase {
    //the method sign go to repository and to the action
    public boolean SignIn(String email,String id,FirebaseAuth firebaseAuth){
        Repository r = new Repository(firebaseAuth);
        boolean b = r.singInAuthentication(email,id);
        return b;
    }
    public boolean SignUp(String name, String id, String email, int age, String phone, String city, FirebaseAuth firebaseAuth){
        Repository r = new Repository(firebaseAuth);
        boolean b = r.singUpAuthentication(email,id);
        return b;
    }


}
