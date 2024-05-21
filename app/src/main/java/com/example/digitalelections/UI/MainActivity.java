package com.example.digitalelections.UI;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.digitalelections.R;
import com.example.digitalelections.Repositry.MyDataBaseHelper;
import com.example.digitalelections.UI.HomePackage.HomePage;
import com.example.digitalelections.UI.SignIn.SingInPage;
import com.example.digitalelections.UI.SignUp.SingUpPage;

public class MainActivity extends AppCompatActivity {

    Button signup, signin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        signin = findViewById(R.id.singInButton);
        signup = findViewById(R.id.singUpButton);
        //MyDataBaseHelper myDataBaseHelper = new MyDataBaseHelper(this);
       // myDataBaseHelper.deleteAllData();
        checkShare();
        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, SingUpPage.class);
                startActivity(intent);
            }
        });
        signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, SingInPage.class);
                startActivity(intent);
            }
        });
    }
    //בודק עם המשתמש היה מחובר לפני ולחץ על הremember me
    public void checkShare()
    {
        SharedPreferences s=this.getSharedPreferences("Share",this.MODE_PRIVATE);
        // s.edit().clear().apply();
        if(!(s.getString("Email", "").isEmpty()&&s.getString("Id", "").isEmpty())) {
            Intent intent = new Intent(MainActivity.this, HomePage.class);
            startActivity(intent);
        }
        else{

        }
    }
}