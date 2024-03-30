package com.example.digitalelections.Repositry;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.Nullable;

public class MyDataBaseHelper extends SQLiteOpenHelper {

    private Context context;
    private static final String DATABASE_NAME = "User.db";
    private static final int DATABASE_VERSION = 2;

    private static final String TABLE_NAME = "my_user";
    private static final String COLUMN_ID = "_id";
    private static final String COLUMN_USERNAME = "name_title";

    private static final String COLUMN_USERID = "userid_title";

    private static final String COLUMN_EMAIL = "Email_title";
    private static final String COLUMN_PHONE = "phone_title";

    private static final String COLUMN_AGE = "age_title";
    private static final String COLUMN_CITY = "city_title";
    private static final String COLUMN_Vote = "Vote_title";



    public MyDataBaseHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query = "CREATE TABLE " + TABLE_NAME + " (" +
                COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_USERNAME + " TEXT, " +
                COLUMN_USERID + " TEXT , " +
                COLUMN_EMAIL + " TEXT , " +
                COLUMN_PHONE + " TEXT, " +
                COLUMN_AGE + " INTEGER, " +
                COLUMN_CITY + " TEXT, " + COLUMN_Vote + " INTEGER);";
        db.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    public void addUser(String username, String userid,String email,int age,String city, String phonenumber) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(COLUMN_USERNAME, username);
        cv.put(COLUMN_USERID, userid);
        cv.put(COLUMN_EMAIL, email);
        cv.put(COLUMN_PHONE, phonenumber);
        cv.put(COLUMN_AGE, age);
        cv.put(COLUMN_CITY, city);
        cv.put(COLUMN_Vote, 0);


        long result = db.insert(TABLE_NAME, null, cv);

        if (result == -1) {
            Toast.makeText(context, "Failed", Toast.LENGTH_SHORT).show();
        } else {
           // Toast.makeText(context, "Added Successfully!", Toast.LENGTH_SHORT).show();
        }
    }

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
    public Cursor readAllData() {
        String query = "SELECT " + COLUMN_ID + ", " + COLUMN_USERNAME + ", " + COLUMN_USERID  + ", " +
                COLUMN_EMAIL + ", " + COLUMN_PHONE + ", " +COLUMN_AGE+", " +COLUMN_CITY+", " +COLUMN_Vote+ " FROM " + TABLE_NAME;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = null;
        if (db != null) {
            cursor = db.rawQuery(query, null);
        }
        return cursor;
    }
    public void updateData(String row_id, String username, String email,String age, String phonenumber) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(COLUMN_USERNAME, username);
        cv.put(COLUMN_EMAIL, email);
        cv.put(COLUMN_PHONE, phonenumber);
        cv.put(COLUMN_AGE, age);

        long result = db.update(TABLE_NAME, cv, COLUMN_USERID + "=?", new String[]{row_id});

        if (result == -1) {
            Toast.makeText(context, "Failed", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(context, "Updated Successfully!", Toast.LENGTH_SHORT).show();
        }
    }
    public void deleteAllData() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DELETE FROM " + TABLE_NAME);
    }

}
