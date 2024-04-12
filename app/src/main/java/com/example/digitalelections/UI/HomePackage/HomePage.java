package com.example.digitalelections.UI.HomePackage;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.app.Service;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.digitalelections.Model.TimerService;
import com.example.digitalelections.R;
import com.example.digitalelections.Repositry.Repository;
import com.example.digitalelections.Repositry.User;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

public class HomePage extends AppCompatActivity {

    private TextView username,timer;
    private ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);
        username = findViewById(R.id.UseridHome);
        imageView = findViewById(R.id.personBtn);
        timer= findViewById(R.id.Timerp);
        modelHomePage m  = new modelHomePage();
        Intent intent = getIntent();
        int s1 = intent.getIntExtra("FirstSI",0);
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
        if(s1 == 10){
            InfoDialog();
        }
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomePage.this, profileActivity.class);
                startActivity(intent);
            }
        });
        timer.setText(TimerService.day+":"+TimerService.hour+":"+TimerService.min+":"+TimerService.sec);
    }

    private void InfoDialog() {
        Dialog dialog = new Dialog(this);
        dialog.setCancelable(true);
        dialog.setContentView(R.layout.infodialog);
        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(dialog.getWindow().getAttributes());
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        dialog.getWindow().setAttributes(lp);
        dialog.show();
    }
}