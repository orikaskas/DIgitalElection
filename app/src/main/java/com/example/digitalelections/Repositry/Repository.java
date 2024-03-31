package com.example.digitalelections.Repositry;

import static android.content.ContentValues.TAG;

import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserInfo;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Executor;

public class Repository {
    private Context context;
    private  FirebaseAuth firebaseAuth  ;
    private static SharedPreferences sharedPreferences;
    private FirebaseFirestore db ;
    private  MyDataBaseHelper myDataBaseHelper;

    public void UpdateUser(String Userid,String email,String username, int age ,String phone) {
        myDataBaseHelper.updateData(Userid,username,email, String.valueOf(age),phone);
        Map<String, Object> map = new HashMap<>();
        map.put("Email",email);
        map.put("Age",age);
        map.put("Name",username);
        map.put("Phone",phone);
        DocumentReference documentReference = db.collection("Users").document("User"+Userid);
        documentReference.update(map);
    }

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
                            ReadData(id);
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
                                RememberMe(id, email);
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
        map.put("Vote",false);
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
    public void getInfo(String id){
        DocumentReference documentReference = this.db.collection("Users").document("User"+id);
        documentReference.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if(task.isSuccessful()){
                    DocumentSnapshot documentSnapshot =task.getResult();
                    if(documentSnapshot.exists()){
                        String email = (String) documentSnapshot.getData().get("Email");
                        String id = (String) documentSnapshot.getData().get("Id");
                        String name = (String) documentSnapshot.getData().get("Name");
                        String phone = (String) documentSnapshot.getData().get("Phone");
                        int age = Integer.parseInt(documentSnapshot.getData().get("Age").toString()) ;
                        String City = (String) documentSnapshot.getData().get("City");
                        User.setInfo(name,id,email,phone,age,City,0);
                        Toast.makeText(context, User.getUsername(), Toast.LENGTH_SHORT).show();
                    }
                    else
                    {
                        Toast.makeText(context, "no such ", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }
    private void ReadData(String id){
        db.collection("Users").document("User"+id)
                .get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        DocumentSnapshot documentSnapshot =task.getResult();
                        if(task.isSuccessful()){
                            if(documentSnapshot.exists()){
                                String email = (String) documentSnapshot.getData().get("Email");
                                String id = (String) documentSnapshot.getData().get("Id");
                                String name = (String) documentSnapshot.getData().get("Name");
                                String phone = (String) documentSnapshot.getData().get("Phone");
                                int age =Integer.parseInt(documentSnapshot.getData().get("Age").toString()) ;
                                String City = (String) documentSnapshot.getData().get("City");
                                myDataBaseHelper.addUser(name,id,email,age,City,phone);
                            }
                        }

                    }
                });
    }
}
