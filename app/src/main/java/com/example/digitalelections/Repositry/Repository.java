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
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
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
    private FirebaseDatabase database;
    private static SharedPreferences sharedPreferences;
    private FirebaseFirestore db ;
    private  MyDataBaseHelper myDataBaseHelper;

    public void UpdateUser(String Userid,String email,String username, int age ,String phone) {
        myDataBaseHelper.updateData(Userid,username,email, String.valueOf(age),phone,User.getVote(),User.getVoteCity());
        Map<String, Object> map = new HashMap<>();
        map.put("Email",email);
        map.put("Age",age);
        map.put("Name",username);
        map.put("Phone",phone);
        DocumentReference documentReference = db.collection("Users").document("User"+Userid);
        documentReference.update(map);
    }

    public void GetVoteCountry(String id,Completed completed) {
        DocumentReference documentReference = this.db.collection("Users").document("User"+id);
        documentReference.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                DocumentSnapshot documentSnapshot =task.getResult();
                    if(documentSnapshot.exists()){
                        Boolean b = (Boolean)documentSnapshot.getData().get("Vote");
                        completed.onComplete(b);
                    }
                    else {
                        completed.onComplete(false);
                        Toast.makeText(context, "aaaaaa", Toast.LENGTH_SHORT).show();
                    }
            }
        });
    }
    public void GetVoteCity(String id,Completed completed) {
        DocumentReference documentReference = this.db.collection("Users").document("User"+id);
        documentReference.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                DocumentSnapshot documentSnapshot =task.getResult();
                if(documentSnapshot.exists()){
                    Boolean b = (Boolean)documentSnapshot.getData().get("VoteCity");
                    completed.onComplete(b);
                }
                else {
                    completed.onComplete(false);
                }
            }
        });
    }

    public void UpdateVote(int Vote,int VoteCity,String id,Completed completed) {
        myDataBaseHelper.updateData(User.getId(),User.getUsername(),User.getEmail(), String.valueOf(User.getAge()),User.getPhone(),Vote,VoteCity);
        User.setVote(Vote);
        User.setVoteCity(VoteCity);
        Map<String, Object> map = new HashMap<>();
        if(Vote==1){
            map.put("Vote",true);
        }
        else
            map.put("Vote",false);
        if(VoteCity==1)
        {
            map.put("VoteCity",true);
        }
        else
            map.put("VoteCity",false);
        DocumentReference documentReference = db.collection("Users").document("User"+id);
        documentReference.update(map).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    completed.onComplete(true);
                }
                else
                {
                    completed.onComplete(false);
                }
            }
        });
    }
    public void Resetallvotes(){
        myDataBaseHelper.updateData(User.getId(),User.getUsername(),User.getEmail(), String.valueOf(User.getAge()),User.getPhone(),0,0);
        User.setVote(0);
        User.setVoteCity(0);
        db.collection("Users").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if(task.isSuccessful()){
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        Map<String,Object> map = new HashMap<>();
                        map.put("Vote",false);
                        map.put("VoteCity",false);
                        db.collection("Users").document(document.getId()).update(map);
                    }
                }
            }
        });
    }
    public void UpdateNormal(String answer) {
        DatabaseReference databaseReference = database.getReference("country");
        Map<String,Object> map = new HashMap<>();
        databaseReference.get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if(task.isSuccessful()){
                    if(task.getResult().exists()){
                        int a= Integer.parseInt(String.valueOf(task.getResult().child(answer).getValue()))+1 ;
                        map.put(answer,a);
                        databaseReference.updateChildren(map);
                    }
                }
                else {

                }
            }
        });

    }

    public void UpdateNormalCity(String string) {
        DatabaseReference databaseReference = database.getReference("city");
        Map<String,Object> map = new HashMap<>();
        databaseReference.get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if(task.isSuccessful()){
                    if(task.getResult().exists()){
                        map.put(string,Integer.parseInt(String.valueOf(task.getResult().child(User.getCity()).child(string).getValue()))+1);
                        databaseReference.child(User.getCity()).updateChildren(map);
                    }
                }
                else {
                }
            }
        });
    }

    public void GetTime(CompletedString ans) {
        DatabaseReference databaseReference = database.getReference("time");
        databaseReference.get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if(task.isSuccessful())
                {
                    if(task.getResult().exists())
                    {
                        ans.onCompleteString(String.valueOf(task.getResult().child("time1").getValue()));
                    }
                    else {
                        ans.onCompleteString("wrong");
                    }
                }else
                    ans.onCompleteString("wrong");

            }
        });

    }

    public void UpdateTime(String time) {
        DatabaseReference databaseReference = database.getReference("time");
        Map<String,Object> map = new HashMap<>();
        map.put("time1",time);
        databaseReference.updateChildren(map);
    }

    public interface Completed
    {
        void onComplete(boolean flag);
    }
    public interface CompletedString
    {
        void onCompleteString(String flag);
    }
    public Repository(Context context){
           this.firebaseAuth = FirebaseAuth.getInstance();
           this.db = FirebaseFirestore.getInstance();
           this.context = context;
           this.database = FirebaseDatabase.getInstance();
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
        String s = myDataBaseHelper.EmailCaps(email);
        map.put("Email", s);
        map.put("Id", id);
        map.put("Name", name);
        map.put("Age", age);
        map.put("Phone", phone);
        map.put("City", city);
        map.put("Vote",false);
        map.put("VoteCity",false);

        this.db.collection("Users").document("User"+id).set(map).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                myDataBaseHelper.addUser(name,id,email,age,city,phone);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(context, "Authentication failed", Toast.LENGTH_SHORT).show();
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
        String s=myDataBaseHelper.EmailCaps(email);
        cursor.moveToFirst();
        int c = cursor.getCount();
        for (int i = 0; i < c; i++) {
            if(s.equals(cursor.getString(3)) && id.equals(cursor.getString(2))){
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
        firebaseAuth.signOut();
    }
    public void getInfo(String id, Completed callback){
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
                        boolean vote = (boolean) documentSnapshot.getData().get("Vote");
                        boolean voteCity = (boolean) documentSnapshot.getData().get("VoteCity");
                        int vote1=0;
                        int votecity=0;
                        if(vote)
                        {
                            vote1 = 1;
                        }
                        if(voteCity)
                        {
                            votecity=1;
                        }
                        User.setInfo(name,id,email,phone,age,City,vote1,votecity);
                        callback.onComplete(true);
                    }
                    else
                    {
                        callback.onComplete(false);
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
