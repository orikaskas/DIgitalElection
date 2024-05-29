package com.example.digitalelections.UI.result;

import android.content.Context;

import com.example.digitalelections.Repositry.Repository;

public class Resultmodle {
    // פונקציה לקבלת תוצאות הקולות לפי מדינה
    public void result(Context context, String country, Repository.CompletedString callback){
        Repository repository = new Repository(context);
        // קריאה לפונקציה ב־Repository לקבלת תוצאות הקולות לפי מדינה
        repository.getVoteCountryResult(country, new Repository.CompletedString() {
            @Override
            public void onCompleteString(String result) {
                // החזרת התוצאה ל־Callback
                callback.onCompleteString(result);
            }
        });
    }

    // פונקציה לקבלת תוצאות הקולות לפי עיר
    public void resultCity(Context context, String city, String country, Repository.CompletedString callback){
        Repository repository = new Repository(context);
        // קריאה לפונקציה ב־Repository לקבלת תוצאות הקולות לפי עיר
        repository.getVoteCityResult(city, country, new Repository.CompletedString() {
            @Override
            public void onCompleteString(String result) {
                // החזרת התוצאה ל־Callback
                callback.onCompleteString(result);
            }
        });
    }
}