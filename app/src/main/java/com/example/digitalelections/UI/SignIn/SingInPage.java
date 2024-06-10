package com.example.digitalelections.UI.SignIn;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.example.digitalelections.R;
import com.example.digitalelections.Repositry.Repository;
import com.example.digitalelections.UI.HomePackage.HomePage;
import com.example.digitalelections.UI.SignUp.SingUpPage;

public class SingInPage extends AppCompatActivity {
    private Button buttonMove, btnsubmit;
    private EditText editemail, editid;
    private CheckBox checkBox;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sing_in_page);

        // איתחול כפתור המעבר לדף ההרשמה
        buttonMove = findViewById(R.id.btnMoveToSingUp);
        // איתחול כפתור ההגשה
        btnsubmit = findViewById(R.id.btnSubmitSingIn);
        // איתחול שדות הטקסט והתיבת סימון
        editemail = findViewById(R.id.EtEmail2);
        editid = findViewById(R.id.EtId2);
        checkBox = findViewById(R.id.CheckSignIn);

        // לחיצה על כפתור המעבר לדף ההרשמה
        buttonMove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SingInPage.this, SingUpPage.class);
                startActivity(intent);
            }
        });

        // לחיצה על כפתור ההגשה
        btnsubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // בדיקה אם כל השדות מלאים
                if (editemail.getText().toString().trim().isEmpty() || editid.getText().toString().trim().isEmpty()) {
                    Toast.makeText(SingInPage.this, "הזן מידע", Toast.LENGTH_SHORT).show();
                } else {
                    // יצירת אובייקט מסוג modelSignIn וביצוע התחברות
                    modelSignIn m = new modelSignIn(editemail.getText().toString().trim(), editid.getText().toString().trim(), checkBox.isChecked(),SingInPage.this);
                    m.SingIn(SingInPage.this, new Repository.Completed() {
                        @Override
                        public void onComplete(boolean flag) {
                            // במידה וההתחברות הצליחה
                            if (flag) {
                                // קריאה לפונקציה לקבלת מידע מה-Repository
                                btnsubmit.setClickable(false);
                                m.GetInfo( editid.getText().toString(), new Repository.Completed() {
                                    @Override
                                    public void onComplete(boolean flag) {
                                        // פתיחת מסך הבית
                                        Intent intent = new Intent(SingInPage.this, HomePage.class);
                                        startActivity(intent);
                                    }
                                });
                            } else {
                                // התראה אם ההתחברות נכשלה
                                Toast.makeText(SingInPage.this, "ההזדהות נכשלה", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
            }
        });
    }
}