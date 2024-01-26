package com.example.digitalelections.Repositry;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class MyDataBaseHelper extends SQLiteOpenHelper {

    private Context context;
    private static final String DATABASE_NAME = "User.db";
    private static final int DATABASE_VERSION = 1;

    private static final String TABLE_NAME = "my_user";
    private static final String COLUMN_ID = "_id";
    private static final String COLUMN_PRODUCT = "name_title";
    private static final String COLUMN_USERID = "userid_title";

    private static final String COLUMN_EMAIL = "Email_title";
    private static final String COLUMN_PHONE = "phone_title";

    private static final String COLUMN_AGE = "age_title";
    private static final String COLUMN_CITY = "city_title";





    MyDataBaseHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }
    @Override
    public void onCreate(SQLiteDatabase db) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }
    void addUser(String name, int price){

        }
    }
