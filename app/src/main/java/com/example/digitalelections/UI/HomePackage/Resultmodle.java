package com.example.digitalelections.UI.HomePackage;

import android.content.Context;

import com.example.digitalelections.Repositry.Repository;

public class Resultmodle {
    public void result(Context context, String an, Repository.CompletedString a){
        Repository r = new Repository(context);
        r.getVoteCountryresult(an,new Repository.CompletedString() {
            @Override
            public void onCompleteString(String flag) {
                a.onCompleteString(flag);
            }
        });
    }
    public void resultCity(Context context, String an, Repository.CompletedString a){
        Repository r = new Repository(context);
        r.getVoteCityresult(an,new Repository.CompletedString() {
            @Override
            public void onCompleteString(String flag) {
                a.onCompleteString(flag);
            }
        });
    }
}
