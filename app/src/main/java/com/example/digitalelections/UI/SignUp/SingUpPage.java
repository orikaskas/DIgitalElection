package com.example.digitalelections.UI.SignUp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.digitalelections.R;
import com.example.digitalelections.UI.HomePackage.HomePage;
import com.example.digitalelections.UI.SignIn.SingInPage;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class SingUpPage extends AppCompatActivity implements View.OnClickListener {
    EditText etphone, etid, etemail, etname, etage;
    Button buttonSubmit, ButtonMove;
    Spinner spinner;
    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sing_up_page);
        mAuth = FirebaseAuth.getInstance();
        etphone = findViewById(R.id.EtPhone);
        etid = findViewById(R.id.EtId);
        etemail = findViewById(R.id.EtEmail);
        etage = findViewById(R.id.EtAge);
        etname = findViewById(R.id.EtName);
        buttonSubmit = findViewById(R.id.btnSubmitSingup);
        ButtonMove = findViewById(R.id.btnMoveToSingin);
        ButtonMove.setOnClickListener(this);
        buttonSubmit.setOnClickListener(this);
        spinner = (Spinner) findViewById(R.id.SCity);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.citys_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);


    }

    @Override
    public void onClick(View view) {
        String sname = etname.getText().toString().trim();
        String sid = etid.getText().toString().trim();
        String sphone = etphone.getText().toString().trim();
        String sage = etage.getText().toString().trim();
        boolean b = true;
        if (view == buttonSubmit) {

            if (sname.length() != 0) {
                String s1 = nameCheck(sname);
                if (s1 != "1") {
                    etname.setError(s1);
                    b = false;
                }
            } else {
                etname.setError("Enter name");
                b = false;
            }
            if (sage.length() != 0) {
                int num = Integer.parseInt(sage);
                if (num > 120 || num < 18) {
                    etage.setError("Age not valid");
                    b = false;
                }
            } else {
                etage.setError("Enter age");
                b = false;
            }
            if (sid.length() != 9) {
                b = false;
                etid.setError("The length should be 9 digits");
            }
            if (sphone.length() != 0) {
                if (sphone.charAt(0) != '0' || sphone.charAt(1) != '5') {
                    b = false;
                    etphone.setError("The phone start with 05");
                }
            } else {
                etphone.setError("Enter Phone");
                b = false;
            }
            if (etemail.length() == 0) {
                etemail.setError("Enter Phone");
                b = false;
            } else if (!EmailCheck()) {
                etemail.setError("Email not valid");
                b = false;
            }
            if (spinner.getSelectedItem().toString().equals("תבחר עיר")) {
                Toast.makeText(this, "לא בחרת עיר", Toast.LENGTH_SHORT).show();
            }
            if (b) {
                mAuth.createUserWithEmailAndPassword(etemail.getText().toString(), etid.getText().toString())
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    // Sign in success, update UI with the signed-in user's information
                                    Intent intent = new Intent(SingUpPage.this, HomePage.class);
                                    startActivity(intent);
                                } else {
                                    // If sign in fails, display a message to the user.
                                    Toast.makeText(SingUpPage.this, "Already a user.",
                                            Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
            }
        }
        if (view == ButtonMove) {
            Intent intent = new Intent(SingUpPage.this, SingInPage.class);
            startActivity(intent);
        }
    }

    private boolean EmailCheck() {
        String semail = etemail.getText().toString().trim();
        if (semail.charAt(semail.length() - 1) == '.' || semail.charAt(semail.length() - 1) == '@' || semail.charAt(0) == '.' || semail.charAt(0) == '@') {
            return false;
        }
        int counter = 0;
        for (int i = 0; i < semail.length() - 2; i++) {
            if (semail.charAt(i) == '.' && semail.charAt(i + 1) == '@') {
                return false;
            }
//            else if (semail.charAt(i+1)=='.'&&semail.charAt(i)=='@') {
//                return false;
//            }
            if (semail.charAt(i) == '@')
                counter++;
            if (!semail.contains(".com") && !semail.contains(".co.")) {
                return false;
            }
            if (semail.indexOf(".") - semail.indexOf("@") <= 3) {
                return false;
            }
            if (semail.indexOf("@") < 3) {
                return false;
            }
        }
        if (counter != 1) {
            return false;
        }
        return true;
    }

    public String nameCheck(String s) {
        if (s.charAt(0) < 'A' || s.charAt(0) > 'Z') {
            return "The first letter is upper-case letter";
        }
        for (int i = 0; i < s.length(); i++) {
            if (!(s.charAt(i) >= 'A' && s.charAt(i) <= 'Z') && !(s.charAt(i) >= 'a' && s.charAt(i) <= 'z')) {
                return "all letter except the first letter are lower-case letters";
            }
        }
        return "1";
    }
}