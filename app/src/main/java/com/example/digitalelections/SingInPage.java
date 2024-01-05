package com.example.digitalelections;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class SingInPage extends AppCompatActivity {
Button buttonMove;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sing_in_page);
        buttonMove = findViewById(R.id.btnMoveToSingUp);
        buttonMove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SingInPage.this,SingUpPage.class);
                startActivity(intent);
            }
        });
    }
}