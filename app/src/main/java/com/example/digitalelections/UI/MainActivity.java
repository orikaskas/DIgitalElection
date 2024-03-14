package com.example.digitalelections.UI;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.digitalelections.R;
import com.example.digitalelections.UI.HomePackage.HomePage;
import com.example.digitalelections.UI.SignIn.SingInPage;
import com.example.digitalelections.UI.SignUp.SingUpPage;

public class MainActivity extends AppCompatActivity {

    Button singup, singin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        singin = findViewById(R.id.singInButton);
        singup = findViewById(R.id.singUpButton);
        SharedPreferences s=this.getSharedPreferences("Share",this.MODE_PRIVATE);
        SharedPreferences.Editor editor=s.edit();
        if(editor != null){
            Intent intent = new Intent(MainActivity.this, HomePage.class);
            startActivity(intent);
        }
        singup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, SingUpPage.class);
                startActivity(intent);
            }
        });
        singin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, SingInPage.class);
                startActivity(intent);
            }
        });
    }
}