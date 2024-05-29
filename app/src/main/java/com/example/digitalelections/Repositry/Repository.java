package com.example.digitalelections.Repositry;

import static android.content.ContentValues.TAG;

import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.digitalelections.User.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class Repository {
    private Context context;
    private  FirebaseAuth firebaseAuth  ;
    private FirebaseDatabase database;
    private static SharedPreferences sharedPreferences;
    private FirebaseFirestore db ;
    private  MyDataBaseHelper myDataBaseHelper;
    // פעולה בונה
    public Repository(Context context){
        this.firebaseAuth = FirebaseAuth.getInstance();
        this.db = FirebaseFirestore.getInstance();
        this.context = context;
        this.database = FirebaseDatabase.getInstance();
        sharedPreferences = context.getSharedPreferences("Share",Context.MODE_PRIVATE );
        myDataBaseHelper = new MyDataBaseHelper(context);
    }

    // מתודה לעדכון פרטי משתמש
    public void UpdateUser(String Userid,String email,String username, int age ,String phone) {
        myDataBaseHelper.updateData(Userid,username,email, String.valueOf(age),phone, User.getVote(),User.getVoteCity());
        Map<String, Object> map = new HashMap<>();
        map.put("Email",email);
        map.put("Age",age);
        map.put("Name",username);
        map.put("Phone",phone);
        DocumentReference documentReference = db.collection("Users").document("User"+Userid);
        documentReference.update(map);
    }

    // מתודה לקבלת תוצאת הצבעת עיר מסוימת
    public void getVoteCountryResult(String an,CompletedString string){
        DatabaseReference databaseReference = database.getReference("country");
        databaseReference.get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if(task.isSuccessful()){
                    if(task.getResult().exists()){
                        String a= task.getResult().child(an).getValue()+"";
                        string.onCompleteString(a);
                    }
                }
                else {
                    string.onCompleteString("");
                }
            }
        });
    }

    // מתודה לקבלת מצב הצבעת קונקרטית למדינה
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

    // מתודה לקבלת מצב הצבעת קונקרטית לעיר
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
        // עדכון הנתונים במסד הנתונים המקומי
        myDataBaseHelper.updateData(User.getId(),User.getUsername(),User.getEmail(), String.valueOf(User.getAge()),User.getPhone(),Vote,VoteCity);
        // עדכון הנתונים במופע המשתמש
        User.setVote(Vote);
        User.setVoteCity(VoteCity);
        // יצירת מפת נתונים לעדכון במסד הנתונים המרוחק
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
        // עדכון הנתונים במסד הנתונים המרוחק
        DocumentReference documentReference = db.collection("Users").document("User"+id );
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
    public void ResetAllVotes(){
        // עדכון כל ההצבעות במסד הנתונים המקומי ל־0
        myDataBaseHelper.updateData(User.getId(),User.getUsername(),User.getEmail(), String.valueOf(User.getAge()),User.getPhone(),0,0);
        // איפוס כל ההצבעות במופע המשתמש ל־0
        User.setVote(0);
        User.setVoteCity(0);
        // קבלת כל המשתמשים ממסד הנתונים המרוחק ועדכון ההצבעות שלהם ל־false
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

    // פעולה שמעדכנת את מספר ההצבעות למדינה מסוימת
    public void UpdateNormal(String answer) {
        DatabaseReference databaseReference = database.getReference("country");
        Map<String,Object> map = new HashMap<>();
        databaseReference.get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if(task.isSuccessful()){
                    if(task.getResult().exists()){
                        // קבלת הערך הנוכחי והוספת 1
                        int a= Integer.parseInt(String.valueOf(task.getResult().child(answer).getValue()))+1 ;
                        map.put(answer,a);
                        // עדכון הנתונים
                        databaseReference.updateChildren(map);
                    }
                }
                else {
                    // טיפול במקרה של כישלון
                }
            }
        });

    }

    // פעולה שמעדכנת את מספר ההצבעות לעיר מסוימת
    public void UpdateNormalCity(String string) {
        DatabaseReference databaseReference = database.getReference("city");
        Map<String,Object> map = new HashMap<>();
        databaseReference.get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if(task.isSuccessful()){
                    if(task.getResult().exists()){
                        // קבלת הערך הנוכחי והוספת 1
                        map.put(string,Integer.parseInt(String.valueOf(task.getResult().child(User.getCity()).child(string).getValue()))+1);
                        // עדכון הנתונים
                        databaseReference.child(User.getCity()).updateChildren(map);
                    }
                }
                else {
                    // טיפול במקרה של כישלון
                }
            }
        });
    }

    public void GetTime(CompletedString ans) {
        // מקבל את ההפנייה לקולקציה "time" מבסיס הנתונים
        DatabaseReference databaseReference = database.getReference("time");
        // מבצע פעולת קריאה לבסיס הנתונים
        databaseReference.get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if(task.isSuccessful())
                {
                    if(task.getResult().exists())
                    {
                        // אם המידע קיים, מפעיל את הפונקציה onCompleteString ומעביר אליה את הזמן
                        ans.onCompleteString(String.valueOf(task.getResult().child("time1").getValue()));
                    }
                    else {
                        // אם המידע אינו קיים, מחזיר "wrong"
                        ans.onCompleteString("wrong");
                    }
                } else {
                    // אם הבקשה נכשלה, מחזיר "wrong"
                    ans.onCompleteString("wrong");
                }
            }
        });

    }

    public void UpdateTime(String time) {
        // מקבל את ההפנייה לקולקציה "time" מבסיס הנתונים
        DatabaseReference databaseReference = database.getReference("time");
        // ממפה את הזמן למפתח "time1" בבסיס הנתונים
        Map<String,Object> map = new HashMap<>();
        map.put("time1",time);
        databaseReference.updateChildren(map);
    }

    public void getVoteCityResult(String an,String city, CompletedString completedString) {
        // מקבל את ההפנייה לקולקציה "city" מבסיס הנתונים
        DatabaseReference databaseReference = database.getReference("city");
        // מבצע פעולת קריאה לבסיס הנתונים
        databaseReference.get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if(task.isSuccessful()){
                    if(task.getResult().exists()){
                        // אם המידע קיים, מפעיל את הפונקציה onCompleteString ומעביר אליה את הערך המתאים לעיר והכיוון
                        String a= task.getResult().child(city).child(an).getValue()+"";
                        completedString.onCompleteString(a);
                    }
                }
                else {
                    // אם הבקשה נכשלה, מחזיר מחרוזת ריקה
                    completedString.onCompleteString("");
                }
            }
        });
    }

    // פונקציות קול עם ערכי חזרה
    public interface Completed
    {
        void onComplete(boolean flag);
    }

    // פונקציות קול עם ערכי חזרה מספריים
    public interface Completed1234
    {
        void onComplete(int flag);
    }

    // פונקציות קול עם ערכי חזרה מחרוזות
    public interface CompletedString
    {
        void onCompleteString(String flag);
    }
    public void singInAuthentication(String email, String id, boolean c, Completed callback) {
        // בודק אם המשתמש כבר מחובר
        if (!checkSignIn(email, id)) {
            if (c) {
                // אם ה-user ביקש לזכור אותו, קורא לפונקציה RememberMe
                RememberMe(id, email);
            }
            // משדר לממשק callback שהפעולה הצליחה
            callback.onComplete(true);
        } else {
            // אם המשתמש לא מחובר, מבצע התחברות ל-Firebase
            signInFireBase(email, id, c, new Completed() {
                @Override
                public void onComplete(boolean flag) {
                    callback.onComplete(flag);
                }
            });
        }
    }

    public void signInFireBase(String email, String id, boolean c, Completed callback2) {
        // מבצע התחברות ל-Firebase עם אימייל וסיסמה
        this.firebaseAuth.signInWithEmailAndPassword(email, id)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // אם ההתחברות הצליחה
                            if (c) {
                                // אם ה-user ביקש לזכור אותו, קורא לפונקציה RememberMe
                                RememberMe(id, email);
                            }
                            // קורא לפונקציה לקריאת נתונים
                            ReadData(id);
                            callback2.onComplete(true);
                        } else {
                            // אם ההתחברות נכשלה
                            callback2.onComplete(false);
                        }
                    }
                });
    }

    private void SignUPFirebase(String email, String id, String name, int age, String phone, String city, boolean check, Completed callback) {
        // מבצע הרשמה ל-Firebase עם אימייל וסיסמה
        this.firebaseAuth.createUserWithEmailAndPassword(email, id)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // אם ההרשמה הצליחה, מוסיף את המשתמש למאגר הנתונים
                            addUser(email, id, name, age, phone, city);
                            if (check) {
                                // אם ה-user ביקש לזכור אותו, קורא לפונקציה RememberMe
                                RememberMe(id, email);
                            }
                            callback.onComplete(true);
                        } else {
                            // אם ההרשמה נכשלה
                            callback.onComplete(false);
                        }
                    }
                });
    }

    public void singUpAuthentication(String email, String id, String name, int age, String phone, String city, boolean check, Completed callback) {
        // בודק אם ה-ID כבר קיים במערכת
        checkId(id, new Completed1234() {
            @Override
            public void onComplete(int flag) {
                int f = flag;
                if (f != 0) {
                    // אם ה-ID כבר קיים, מציג הודעה מתאימה
                    Toast.makeText(context, "id already exist", Toast.LENGTH_SHORT).show();
                } else {
                    // אם ה-ID לא קיים, מבצע הרשמה ל-Firebase
                    SignUPFirebase(email, id, name, age, phone, city, check, new Completed() {
                        @Override
                        public void onComplete(boolean flag) {
                            callback.onComplete(flag);
                        }
                    });
                }
            }
        });
    }

    private void addUser(String email, String id, String name, int age, String phone, String city) {
        // מגדיר מפת נתונים למשתמש החדש
        Map<String, Object> map = new HashMap<>();
        String s = myDataBaseHelper.EmailCaps(email);
        map.put("Email", s);
        map.put("Id", id);
        map.put("Name", name);
        map.put("Age", age);
        map.put("Phone", phone);
        map.put("City", city);
        map.put("Vote", false);
        map.put("VoteCity", false);

        // מוסיף את המשתמש החדש ל-Firebase Firestore
        this.db.collection("Users").document("User" + id).set(map).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                // אם ההוספה הצליחה, מוסיף את המשתמש גם לבסיס הנתונים המקומי
                myDataBaseHelper.addUser(name, id, email, age, city, phone);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                // אם ההוספה נכשלה, מציג הודעת שגיאה
                Toast.makeText(context, "Authentication failed", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void checkId(String s, Completed1234 completed1234) {
        // מבצע קריאה ל-Firebase Firestore לבדיקה אם ה-ID כבר קיים
        db.collection("Users").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    int found = 0;
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        if (s.equals(document.getData().get("Id").toString())) {
                            found = 1;
                        }
                    }
                    // קורא לפונקציה onComplete של הממשק ומעביר את התוצאה
                    completed1234.onComplete(found);
                } else {
                    Log.w(TAG, "Error getting documents.", task.getException());
                    completed1234.onComplete(0);  // במידה ויש שגיאה, מחזיר 0
                }
            }
        });
    }

    private boolean checkSignIn(String email, String id) {
        // קורא את כל הנתונים מהבסיס נתונים המקומי
        Cursor cursor = this.myDataBaseHelper.readAllData();
        String s = myDataBaseHelper.EmailCaps(email);
        cursor.moveToFirst();
        int c = cursor.getCount();
        for (int i = 0; i < c; i++) {
            // בודק אם המשתמש כבר קיים
            if (s.equals(cursor.getString(3)) && id.equals(cursor.getString(2))) {
                return false;
            }
            cursor.moveToNext();
        }
        return true;
    }

    private void RememberMe(String id, String email) {
        // שומר את פרטי המשתמש ב-SharedPreferences
        SharedPreferences.Editor editor = this.sharedPreferences.edit();
        editor.clear().apply();
        editor.putString("Email", email);
        editor.putString("Id", id);
        editor.apply();
    }

    public void SignOut() {
        // מבצע התנתקות מהחשבון ומוחק את הנתונים מ-SharedPreferences
        SharedPreferences.Editor editor = this.sharedPreferences.edit();
        editor.clear().apply();
        firebaseAuth.signOut();
    }

    public void getInfo(String id, Completed callback) {
        // קורא את פרטי המשתמש מ-Firebase Firestore
        DocumentReference documentReference = this.db.collection("Users").document("User" + id);
        documentReference.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot documentSnapshot = task.getResult();
                    if (documentSnapshot.exists()) {
                        // מקבל את הנתונים ומעדכן את המידע של ה-User
                        String email = (String) documentSnapshot.getData().get("Email");
                        String id = (String) documentSnapshot.getData().get("Id");
                        String name = (String) documentSnapshot.getData().get("Name");
                        String phone = (String) documentSnapshot.getData().get("Phone");
                        int age = Integer.parseInt(documentSnapshot.getData().get("Age").toString());
                        String City = (String) documentSnapshot.getData().get("City");
                        boolean vote = (boolean) documentSnapshot.getData().get("Vote");
                        boolean voteCity = (boolean) documentSnapshot.getData().get("VoteCity");
                        int vote1 = 0;
                        int votecity = 0;
                        if (vote) {
                            vote1 = 1;
                        }
                        if (voteCity) {
                            votecity = 1;
                        }
                        User.setInfo(name, id, email, phone, age, City, vote1, votecity);
                        callback.onComplete(true);
                    } else {
                        callback.onComplete(false);
                    }
                }
            }
        });
    }

    private void ReadData(String id) {
        // קורא את פרטי המשתמש מ-Firebase Firestore ומוסיף אותם לבסיס הנתונים המקומי
        db.collection("Users").document("User" + id)
                .get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        DocumentSnapshot documentSnapshot = task.getResult();
                        if (task.isSuccessful()) {
                            if (documentSnapshot.exists()) {
                                String email = (String) documentSnapshot.getData().get("Email");
                                String id = (String) documentSnapshot.getData().get("Id");
                                String name = (String) documentSnapshot.getData().get("Name");
                                String phone = (String) documentSnapshot.getData().get("Phone");
                                int age = Integer.parseInt(documentSnapshot.getData().get("Age").toString());
                                String City = (String) documentSnapshot.getData().get("City");
                                myDataBaseHelper.addUser(name, id, email, age, City, phone);
                            }
                        }
                    }
                });
    }
}
