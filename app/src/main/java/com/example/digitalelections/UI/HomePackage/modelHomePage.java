package com.example.digitalelections.UI.HomePackage;

import android.content.Context;

import com.example.digitalelections.Repositry.Repository;
import com.example.digitalelections.Repositry.User;

public class modelHomePage  {

    public modelHomePage() {

    }
    public void GetInfo(Context context, String id, Repository.Completed completed){
        Repository r = new Repository(context);
        r.getInfo(id, new Repository.Completed() {
            @Override
            public void onComplete(boolean flag) {
                completed.onComplete(flag);
            }
        });
    }

    public void ResetallVotes(Context context) {
        Repository r = new Repository(context);
        r.Resetallvotes() ;

    }
}
