package com.example.digitalelections.Repositry;

import android.content.SharedPreferences;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import org.checkerframework.common.returnsreceiver.qual.This;

import java.util.HashMap;
import java.util.Map;

public class Repository {
    FirebaseAuth firebaseAuth  ;
    SharedPreferences sharedPreferences;
    FirebaseFirestore db ;
    MyDataBaseHelper myDataBaseHelper;
    public Repository(){
           this.firebaseAuth = FirebaseAuth.getInstance();
           db = FirebaseFirestore.getInstance();
    }
    public boolean singInAuthentication(String email,String id){
        boolean[] b = {false};
        this.firebaseAuth.signInWithEmailAndPassword(email,id)
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

    public boolean singUpAuthentication(String email, String id, String name, int age, String phone, String city) {
        boolean[] b = {false};
        this.firebaseAuth.createUserWithEmailAndPassword(email, id)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                                if(addUser(email, id, name, age, phone, city)) {
                                    b[0] = true;
                                }
                        } else {
                            // If sign in fails, display a message to the user.
                             b[0] = false;
                        }
                    }
                });
        return b[0];
    }
    private Boolean addUser(String email, String id, String name, int age, String phone, String city) {
        Boolean[] s = new Boolean[1];
        Map<String, Object> map = new HashMap<>();
        map.put("Email", email);
        map.put("Id", id);
        map.put("Name", name);
        map.put("Age", age);
        map.put("Phone", phone);
        map.put("City", city);
        this.db.document("Users/user" + id).set(map).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                s[0]=true;
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                s[0]=false;
            }
        });
        return s[0];
    }

}
