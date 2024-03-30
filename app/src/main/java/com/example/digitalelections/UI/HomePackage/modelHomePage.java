package com.example.digitalelections.UI.HomePackage;

import android.content.Context;

import com.example.digitalelections.Repositry.Repository;
import com.example.digitalelections.Repositry.User;

public class modelHomePage  {

    public modelHomePage() {

    }
    public void GetInfo(Context context,String email){
        Repository r = new Repository(context);
        r.getInfo(email);
    }

}
