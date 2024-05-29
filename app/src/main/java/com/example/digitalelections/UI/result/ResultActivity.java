package com.example.digitalelections.UI.result;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.digitalelections.R;

public class ResultActivity extends AppCompatActivity {

    private Button goback;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        // מציאת ויצירת קישור לכפתור "חזור"
        goback = findViewById(R.id.Backfromresult);

        // הוספת מאזין לכפתור "חזור" כדי לטפל בלחיצה
        goback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // סיום פעילות וחזרה למסך קודם
                finish();
            }
        });
    }
}