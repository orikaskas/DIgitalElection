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
                 modelSignIn m = new modelSignIn(editemail.getText().toString().trim(),editid.getText().toString().trim(),checkBox.isChecked());
                 m.SingIn(SingInPage.this, new Repository.Completed() {
                     @Override
                     public void onComplete(boolean flag) {
                         if (flag){
                             m.GetInfo(SingInPage.this, editid.getText().toString(), new Repository.Completed() {
                                 @Override
                                 public void onComplete(boolean flag) {
                                     Intent intent = new Intent(SingInPage.this,HomePage.class);
                                     startActivity(intent);
                                 }
                             });

                         }
                         else
                             Toast.makeText(SingInPage.this, "bbb", Toast.LENGTH_SHORT).show();
                     }
                 });

            }
        });
    }
}