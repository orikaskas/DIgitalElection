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
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class Repository {
    private Context context;
    private  FirebaseAuth firebaseAuth  ;
    private static SharedPreferences sharedPreferences;
    private FirebaseFirestore db ;
    private  MyDataBaseHelper myDataBaseHelper;
    public Repository(Context context){
           this.firebaseAuth = FirebaseAuth.getInstance();
           this.db = FirebaseFirestore.getInstance();
           this.context = context;
           sharedPreferences = context.getSharedPreferences("Share",Context.MODE_PRIVATE );
           myDataBaseHelper = new MyDataBaseHelper(context);
    }
    public boolean singInAuthentication(String email,String id){
        boolean[] b = {false};
        if(!checkSignIn(email,id)){
            return true;
        }
        else
        {
            this.firebaseAuth.signInWithEmailAndPassword(email,id)
                    .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                // Sign in success, update UI with the signed-in user's information
                                b[0] =true;
                            } else {
                                // If sign in fails, display a message to the user.
                                b[0] =false;
                            }
                        }
                    });
        }
        return b[0];
    }

    public boolean singUpAuthentication(String email, String id, String name, int age, String phone, String city) {
        boolean[] b = {false};
        if(!checkId(id)){
            Toast.makeText(context, "id already exist", Toast.LENGTH_SHORT).show();
        }
        else{
            this.firebaseAuth.createUserWithEmailAndPassword(email, id)
                    .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                // Sign in success, update UI with the signed-in user's information
                                addUser(email, id, name, age, phone, city);
                                b[0] = true;
                                Toast.makeText(context, "Authentication sucsseed", Toast.LENGTH_SHORT).show();
                            } else {
                                // If sign in fails, display a message to the user.
                                b[0] = false;
                                Toast.makeText(context, "Authentication Failed", Toast.LENGTH_SHORT).show();

                            }
                        }
                    });
        }
        return b[0];
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
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("Email", email);
                editor.putString("Id", id);
                editor.putString("Name", name);
                editor.putInt("Age", age);
                editor.putString("Phone", phone);
                editor.putString("City", city);
                editor.apply();
                myDataBaseHelper.addUser(name,id,email,age,city,phone);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(context, "aaa", Toast.LENGTH_SHORT).show();
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
}
