package com.example.digitalelections.UI.HomePackage;

import android.content.Context;

import com.example.digitalelections.Repositry.Repository;

public class modelHomePage  {

    public modelHomePage() {
        // יצירת מודל חדש לדף הבית
    }

    public void GetInfo(Context context, String id, Repository.Completed completed){
        // קבלת מידע על המשתמש ממאגר הנתונים על ידי ה-ID שלו
        Repository r = new Repository(context);
        r.getInfo(id, completed);
    }

    public void ResetallVotes(Context context) {
        // איפוס כל ההצבעות בבסיס הנתונים
        Repository r = new Repository(context);
        r.ResetAllVotes() ;
    }

    public void getTime(Context context, Repository.CompletedString ans){
        // קבלת הזמן המוגדר להתחלת ההצבעה מבסיס הנתונים
        Repository r = new Repository(context);
        r.GetTime(ans);
    }

    public void Updatetime(Context context,String time){
        // עדכון הזמן המוגדר להתחלת ההצבעה בבסיס הנתונים
        Repository r = new Repository(context);
        r.UpdateTime(time);
    }
}

