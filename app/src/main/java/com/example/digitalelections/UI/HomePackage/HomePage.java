package com.example.digitalelections.UI.HomePackage;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.digitalelections.R;
import com.example.digitalelections.Repositry.Repository;
import com.example.digitalelections.Repositry.User;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

public class HomePage extends AppCompatActivity {

    private TextView username;
    private ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);
        username = findViewById(R.id.UseridHome);
        imageView = findViewById(R.id.personBtn);
        modelHomePage m  = new modelHomePage();
        SharedPreferences s=this.getSharedPreferences("Share",this.MODE_PRIVATE);
        if(!(s.getString("Email", "").isEmpty()&&s.getString("Id", "").isEmpty()))
        {
            m.GetInfo(this, s.getString("Id", "").toString(), new Repository.Completed() {
                @Override
                public void onComplete(boolean flag) {
                    if (flag)
                    {
                        username.setText(User.getUsername()+" היי ");
                    }
                }
            });
        }
        else{
            username.setText(User.getUsername()+" היי ");
        }
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomePage.this, profileActivity.class);
                startActivity(intent);
            }
        });


    }
}