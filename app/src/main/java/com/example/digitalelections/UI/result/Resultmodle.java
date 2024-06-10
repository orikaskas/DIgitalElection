package com.example.digitalelections.UI.result;

import android.content.Context;

import com.example.digitalelections.Repositry.Repository;

public class Resultmodle {
    private Repository repository;
    Resultmodle(Context context){repository = new Repository(context);}

    // פונקציה לקבלת תוצאות הקולות לפי מדינה
    public void result( String country, Repository.CompletedString callback) {
        ;
        // קריאה לפונקציה ב־Repository לקבלת תוצאות הקולות לפי מדינה
        repository.getVoteCountryResult(country, callback);
    }

    // פונקציה לקבלת תוצאות הקולות לפי עיר
    public void resultCity( String city, String country, Repository.CompletedString callback){
        
        // קריאה לפונקציה ב־Repository לקבלת תוצאות הקולות לפי עיר
        repository.getVoteCityResult(city, country, callback);
    }
}