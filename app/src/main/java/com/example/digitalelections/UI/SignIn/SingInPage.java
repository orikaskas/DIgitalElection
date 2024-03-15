package com.example.digitalelections.UI.SignIn;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.example.digitalelections.R;
import com.example.digitalelections.Repositry.Repository;
import com.example.digitalelections.Repositry.User;
import com.example.digitalelections.UI.HomePackage.HomePage;
import com.example.digitalelections.UI.SignUp.SingUpPage;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class SingInPage extends AppCompatActivity {
    private Button buttonMove, btnsubmit;
    private EditText editemail, editid;
    private CheckBox checkBox;
    User user;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sing_in_page);
        buttonMove = findViewById(R.id.btnMoveToSingUp);
        btnsubmit = findViewById(R.id.btnSubmitSingIn);
        editemail = findViewById(R.id.EtEmail2);
        editid = findViewById(R.id.EtId2);
        checkBox = findViewById(R.id.CheckSignIn);
        buttonMove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SingInPage.this, SingUpPage.class);
                startActivity(intent);
            }
        });
        btnsubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                 modelSignIn m = new modelSignIn(editemail.getText().toString(),editid.getText().toString(),checkBox.isChecked());
                 m.SingIn(SingInPage.this, new Repository.Completed() {
                     @Override
                     public void onComplete(boolean flag) {
                         if (flag){
                             m.GetInfo(SingInPage.this);
                             Intent intent = new Intent(SingInPage.this,HomePage.class);
                             startActivity(intent);
                         }
                         else
                             Toast.makeText(SingInPage.this, "bbb", Toast.LENGTH_SHORT).show();
                     }
                 });

            }
        });
    }
}