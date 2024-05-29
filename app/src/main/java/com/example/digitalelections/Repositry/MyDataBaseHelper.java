package com.example.digitalelections.Repositry;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.Nullable;

// מחלקת עזר לבניית בסיס הנתונים
public class MyDataBaseHelper extends SQLiteOpenHelper {

    // קביעת תכונות עבור בסיס הנתונים
    private Context context;
    private static final String DATABASE_NAME = "User.db";
    private static final int DATABASE_VERSION = 3;

    private static final String TABLE_NAME = "my_user";
    private static final String COLUMN_ID = "_id";
    private static final String COLUMN_USERNAME = "name_title";
    private static final String COLUMN_USERID = "userid_title";
    private static final String COLUMN_EMAIL = "Email_title";
    private static final String COLUMN_PHONE = "phone_title";
    private static final String COLUMN_AGE = "age_title";
    private static final String COLUMN_CITY = "city_title";
    private static final String COLUMN_Vote = "Vote_title";
    private static final String COLUMN_VoteCity = "VoteCity_title";

    // בנאי
    public MyDataBaseHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }

    // מתודה זו נקראת בעת יצירת בסיס הנתונים
    @Override
    public void onCreate(SQLiteDatabase db) {
        String query = "CREATE TABLE " + TABLE_NAME + " (" +
                COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_USERNAME + " TEXT, " +
                COLUMN_USERID + " TEXT , " +
                COLUMN_EMAIL + " TEXT , " +
                COLUMN_PHONE + " TEXT, " +
                COLUMN_AGE + " INTEGER, " +
                COLUMN_CITY + " TEXT, " + COLUMN_Vote + " INTEGER, " +COLUMN_VoteCity + " INTEGER);";
        db.execSQL(query);
    }

    // מתודה זו נקראת כאשר נדרשת עדכון לגרסת בסיס הנתונים
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    // מתודה להוספת משתמש חדש לבסיס הנתונים
    public void addUser(String username, String userid,String email,int age,String city, String phonenumber) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        String email1=EmailCaps(email);
        cv.put(COLUMN_USERNAME, username);
        cv.put(COLUMN_USERID, userid);
        cv.put(COLUMN_EMAIL, email1);
        cv.put(COLUMN_PHONE, phonenumber);
        cv.put(COLUMN_AGE, age);
        cv.put(COLUMN_CITY, city);
        cv.put(COLUMN_Vote, 0);
        cv.put(COLUMN_VoteCity, 0);

        long result = db.insert(TABLE_NAME, null, cv);

        if (result == -1) {
            Toast.makeText(context, "Failed", Toast.LENGTH_SHORT).show();
        } else {
            // Toast.makeText(context, "Added Successfully!", Toast.LENGTH_SHORT).show();
        }
    }

    // מתודה לחיפוש משתמש על פי כתובת האימייל
    public Cursor FindUserByEmail(String email)
    {
        String query = "SELECT * FROM " + TABLE_NAME + " WHERE " + COLUMN_EMAIL + " = '" + email + "'";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query,null);
        if (cursor != null && cursor.moveToFirst()) {
            return cursor;
        } else {
            // Handle the case where no results were found
            Log.d("TAG", "No results found for email: " + email);
        }

        return cursor;
    }
    // מתודה לקריאת כל הנתונים מבסיס הנתונים
    public Cursor readAllData() {
        String query = "SELECT " + COLUMN_ID + ", " + COLUMN_USERNAME + ", " + COLUMN_USERID  + ", " +
                COLUMN_EMAIL + ", " + COLUMN_PHONE + ", " +COLUMN_AGE+", " +COLUMN_CITY+", " +COLUMN_Vote+", " +COLUMN_VoteCity+ " FROM " + TABLE_NAME;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = null;
        if (db != null) {
            cursor = db.rawQuery(query, null);
        }
        return cursor;
    }

    // מתודה לעדכון נתונים של משתמש קיים
    public void updateData(String row_id, String username, String email,String age, String phonenumber,int vote,int VoteCity) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(COLUMN_USERNAME, username);
        cv.put(COLUMN_EMAIL, email);
        cv.put(COLUMN_PHONE, phonenumber);
        cv.put(COLUMN_AGE, age);
        cv.put(COLUMN_VoteCity, VoteCity);
        cv.put(COLUMN_Vote, vote);

        long result = db.update(TABLE_NAME, cv, COLUMN_USERID + "=?", new String[]{row_id});

        if (result == -1) {
            Toast.makeText(context, "Failed", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(context, "Updated Successfully!", Toast.LENGTH_SHORT).show();
        }
    }

    // מתודה למחיקת כל הנתונים מבסיס הנתונים
    public void deleteAllData() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DELETE FROM " + TABLE_NAME);
    }

    // מתודה למחיקת שורה ספציפית בבסיס הנתונים
    public void deleteOneRow(String row_id){
        SQLiteDatabase db = this.getWritableDatabase();
        long result = db.delete(TABLE_NAME, COLUMN_USERID + "=?", new String[]{row_id});
        if(result == -1){
            Toast.makeText(context, "Failed to Delete.", Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(context, "Successfully Deleted.", Toast.LENGTH_SHORT).show();
        }
    }

    // מתודה להמרת אימייל לאותיות קטנות
    public String EmailCaps(String email){
        return email.toLowerCase();
    }

}
