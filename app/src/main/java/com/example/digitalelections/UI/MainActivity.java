package com.example.digitalelections.UI;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.digitalelections.R;
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