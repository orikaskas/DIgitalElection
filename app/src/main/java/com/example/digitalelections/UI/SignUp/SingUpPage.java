package com.example.digitalelections.UI.SignUp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
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
    private EditText etphone, etid, etemail, etname, etage;
    private Button buttonSubmit, ButtonMove;
    private Spinner spinner;
    private modelSignUp m ;
    private CheckBox check;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sing_up_page);

        etphone = findViewById(R.id.EtPhone);
        etid = findViewById(R.id.EtId);
        etemail = findViewById(R.id.EtEmail);
        etage = findViewById(R.id.EtAge);
        etname = findViewById(R.id.EtName);
        buttonSubmit = findViewById(R.id.btnSubmitSingup);
        ButtonMove = findViewById(R.id.btnMoveToSingin);
        ButtonMove.setOnClickListener(this);
        buttonSubmit.setOnClickListener(this);
        check = findViewById(R.id.CheckSignup);
        check.setOnClickListener(this);
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
             m = new modelSignUp(sname,sid,etemail.getText().toString(),sphone,sage,spinner.getSelectedItem().toString(),check.isChecked());
            String[] s = m.check();
            for (int i = 0; i < s.length; i++) {
                if(!s[i].equals("")){
                    b= false;
                    if(i==0){
                        etname.setError(s[0]);
                    }
                    if(i==1){
                        etage.setError(s[1]);
                    }
                    if(i==2){
                        etid.setError(s[2]);
                    }if(i==3){
                        etphone.setError(s[3]);
                    }
                    if(i==4){
                        etemail.setError(s[4]);
                    }
                    if(i==4){
                        Toast.makeText(this, s[5], Toast.LENGTH_SHORT).show();
                    }
                }
            }
            boolean d =m.CheckId(this,sid);
            if(!d){
                etid.setError("id already exist");
            }
            if (b && d) {
               boolean c = m.SingUp(this);
               if(c){
                    Intent intent = new Intent(SingUpPage.this, HomePage.class);
                    startActivity(intent);
                }
                else{
                   // Toast.makeText(this, "authentication failed", Toast.LENGTH_SHORT).show();
               }
            }
        }
        if (view == ButtonMove) {
            Intent intent = new Intent(SingUpPage.this, SingInPage.class);
            startActivity(intent);
        }
    }
}