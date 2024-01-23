package com.example.digitalelections.Repositry;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class Repository {
    FirebaseAuth fireBase;
    public Repository(FirebaseAuth f){
        this.fireBase=f;
    }
    public boolean singInAuthentication(String email,String id){
        boolean[] b = {false};
        this.fireBase.signInWithEmailAndPassword(email,id)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            b[0] = true;
                        } else {
                            // If sign in fails, display a message to the user.
                           b[0] = false;
                        }
                    }
                });
        return b[0];
    }

    public boolean singUpAuthentication(User user) {
        boolean[] b = {false};
        this.fireBase.createUserWithEmailAndPassword(user.getEmail(), user.getId())
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            b[0] = true;
                        } else {
                            // If sign in fails, display a message to the user.
                            b[0] = false;
                        }
                    }
                });
        return b[0];
    }


}
