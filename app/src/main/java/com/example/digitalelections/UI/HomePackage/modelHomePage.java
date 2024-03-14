package com.example.digitalelections.UI.HomePackage;

import android.content.Context;

import com.example.digitalelections.Repositry.Repository;
import com.example.digitalelections.Repositry.User;

public class modelHomePage  {

    public modelHomePage() {

    }






    public User a(Context context){
        Repository r = new Repository(context);
        User user1 = r.getInfo();
        return user1;
    }
}
