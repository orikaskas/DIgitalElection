package com.example.digitalelections.UI.SignIn;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.digitalelections.R;
import com.example.digitalelections.UI.HomePackage.HomePage;
import com.example.digitalelections.UI.SignUp.SingUpPage;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class SingInPage extends AppCompatActivity {
    Button buttonMove, btnsubmit;
    EditText editemail, editid;
    FirebaseAuth MAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sing_in_page);
        buttonMove = findViewById(R.id.btnMoveToSingUp);
        btnsubmit = findViewById(R.id.btnSubmitSingIn);
        editemail = findViewById(R.id.EtEmail2);
        editid = findViewById(R.id.EtId2);
        MAuth = FirebaseAuth.getInstance();
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
                MAuth.signInWithEmailAndPassword(editemail.getText().toString(), editid.getText().toString())
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    // Sign in success, update UI with the signed-in user's information
                                    Intent intent = new Intent(SingInPage.this, HomePage.class);
                                    startActivity(intent);
                                } else {
                                    // If sign in fails, display a message to the user.
                                    Toast.makeText(SingInPage.this, "Authentication failed.",
                                            Toast.LENGTH_SHORT).show();

                                }
                            }
                        });
            }
        });
    }
}