package com.example.digitalelections.Repositry;

import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class Repository {
    private Context context;
    private  FirebaseAuth firebaseAuth  ;
    private static SharedPreferences sharedPreferences;
    private FirebaseFirestore db ;
    private  MyDataBaseHelper myDataBaseHelper;

    public interface Completed
    {
        void onComplete(boolean flag);
    }
    public Repository(Context context){
           this.firebaseAuth = FirebaseAuth.getInstance();
           this.db = FirebaseFirestore.getInstance();
           this.context = context;
           sharedPreferences = context.getSharedPreferences("Share",Context.MODE_PRIVATE );
           myDataBaseHelper = new MyDataBaseHelper(context);
    }
    public void singInAuthentication(String email,String id,boolean c, Completed callback){
        boolean[] b = {false};
        if(!checkSignIn(email,id)){
            if (c){
                RememberMe(id,email);
            }
            callback.onComplete(true);
        }
        else
        {
            signInFireBase(email, id, c, new Completed() {
                @Override
                public void onComplete(boolean flag) {
                    callback.onComplete(flag);
                }
            });
        }
    }
    public void signInFireBase(String email, String id, boolean c ,Completed callback2)
    {
        this.firebaseAuth.signInWithEmailAndPassword(email,id)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            if (c){
                                RememberMe(id,email);
                            }
                            callback2.onComplete(true);
                        } else {
                            // If sign in fails, display a message to the user.
                            callback2.onComplete(false);
                        }
                    }
                });
    }
    private void SignUPFirebase(String email, String id, String name, int age, String phone, String city,boolean check,Completed callback){
        this.firebaseAuth.createUserWithEmailAndPassword(email, id)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            addUser(email, id, name, age, phone, city);
                            if(check){
                                RememberMe( id, email);
                            }
                            callback.onComplete(true);
                        } else {
                            // If sign in fails, display a message to the user.
                            callback.onComplete(false);


                        }
                    }
                });
    }

    public void singUpAuthentication(String email, String id, String name, int age, String phone, String city,boolean check,Completed callback) {
        boolean[] b = {false};
        if(!checkId(id)){
            Toast.makeText(context, "id already exist", Toast.LENGTH_SHORT).show();
        }
        else{
            SignUPFirebase(email, id, name, age, phone, city, check, new Completed() {
                @Override
                public void onComplete(boolean flag) {
                    callback.onComplete(flag);
                }
            });
        }

    }
    private void addUser(String email, String id, String name, int age, String phone, String city) {
        Map<String, Object> map = new HashMap<>();
        map.put("Email", email);
        map.put("Id", id);
        map.put("Name", name);
        map.put("Age", age);
        map.put("Phone", phone);
        map.put("City", city);
        this.db.collection("Users").document("User"+id).set(map).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                myDataBaseHelper.addUser(name,id,email,age,city,phone);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
            }
        });
    }
    private boolean checkId(String s){
        Cursor cursor = this.myDataBaseHelper.readAllData();
        cursor.moveToFirst();
        int c = cursor.getCount();
        for (int i = 0; i < c; i++) {
            if(s.equals( cursor.getString(2))){
                return false;
            }
            cursor.moveToNext();
        }
        return true;
    }
    private boolean checkSignIn(String email,String id){
        Cursor cursor = this.myDataBaseHelper.readAllData();
        cursor.moveToFirst();
        int c = cursor.getCount();
        for (int i = 0; i < c; i++) {
            if(email.equals(cursor.getString(3)) && id.equals(cursor.getString(2))){
                return false;
            }
            cursor.moveToNext();
        }
        return true;
    }
    private void RememberMe(String id,String email){
         SharedPreferences.Editor editor = this.sharedPreferences.edit();
         editor.clear().apply();
         editor.putString("Email",email);
         editor.putString("Id",id);
         editor.apply();
    }
    public void SignOut(){
        SharedPreferences.Editor editor = this.sharedPreferences.edit();
        editor.clear().apply();
    }
    public void getInfo(){
        SharedPreferences s=context.getSharedPreferences("Share",Context.MODE_PRIVATE);
        String Email ="";
        if(s.getAll().isEmpty()){
            FirebaseUser user = this.firebaseAuth.getCurrentUser();
            Email = user.getEmail();
        }
        else {
            Email = s.getString("Email","");
        }
        Cursor cursor = this.myDataBaseHelper.readAllData();
        cursor.moveToFirst();
        int k = cursor.getCount();
        for (int i = 0; i < k; i++) {
            if(Email.equals(cursor.getString(3))){
                User.setInfo(cursor.getString(1),cursor.getString(2),cursor.getString(3),cursor.getString(4),cursor.getInt(5),cursor.getString(6));
            }
        }

    }
}
