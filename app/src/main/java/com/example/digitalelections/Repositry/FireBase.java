package com.example.digitalelections.Repositry;

import static androidx.core.content.ContextCompat.startActivity;

import android.app.Activity;
import android.content.Intent;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.digitalelections.UI.HomePackage.HomePage;
import com.example.digitalelections.UI.SignIn.SingInPage;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class FireBase {
    //the method sign go to repository and to the action
    public boolean SignIn(String email,String id,FirebaseAuth firebaseAuth){
        Repository r = new Repository(firebaseAuth);
        boolean b = r.singInAuthentication(email,id);
        return b;
    }
    public boolean SignUp(User user,FirebaseAuth firebaseAuth){
        Repository r = new Repository(firebaseAuth);
        boolean b = r.singUpAuthentication(user);
        return b;
    }


}
