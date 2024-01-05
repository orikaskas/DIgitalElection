package com.example.digitalelections;

import static android.hardware.biometrics.BiometricManager.Authenticators.BIOMETRIC_STRONG;
import static android.hardware.biometrics.BiometricManager.Authenticators.DEVICE_CREDENTIAL;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.biometric.BiometricPrompt;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.hardware.biometrics.BiometricManager;
import android.hardware.fingerprint.FingerprintManager;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.concurrent.Executor;

public class SingUpPage extends AppCompatActivity implements View.OnClickListener {
EditText etphone,etid,etemail,etname,etage;
Button buttonSubmit,ButtonMove;
Spinner spinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sing_up_page);
        etphone = findViewById(R.id.EtPhone);
        etid = findViewById(R.id.EtId);
        etemail = findViewById(R.id.EtEmail);
        etage= findViewById(R.id.EtAge);
        etname = findViewById(R.id.EtName);
        buttonSubmit = findViewById(R.id.btnSubmitSingup);
        ButtonMove = findViewById(R.id.btnMoveToSingin);
        ButtonMove.setOnClickListener(this);
        buttonSubmit.setOnClickListener(this);
        spinner = (Spinner) findViewById(R.id.SCity);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.citys_array,android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
    }
    @Override
    public void onClick(View view) {
       String sname= etname.getText().toString().trim();
        String sid= etid.getText().toString().trim();
        String sphone= etphone.getText().toString().trim();
        String sage=  etage.getText().toString().trim();
        boolean b = true;
        if(view == buttonSubmit) {
            if (sname.length() == 0 || !nameCheck(sname)) {
                etname.setError("name not valid");
                b = false;
            }
            if (sage.length() != 0) {
                int num = Integer.parseInt(sage);
                if (num > 99 || num < 18) {
                    etage.setError("Age not valid");
                    b = false;
                }
            } else {
                etage.setError("Age not valid");
                b = false;
            }
            if (sid.length() != 9) {
                b = false;
                etid.setError("Id not valid");
            }
            if (sphone.length() == 0 || sphone.charAt(0) != '0' || sphone.charAt(1) != '1') {
                etphone.setError("phone not valid");
                b = false;
            }
            if (!EmailCheck()) {
                etemail.setError("Email not valid");
                b = false;
            }
            if (spinner.getSelectedItem().toString().equals("תבחר עיר") ){
                 Toast.makeText(this, "לא בחרת עיר", Toast.LENGTH_SHORT).show();
            }
           if(b){
               Intent intent = new Intent(SingUpPage.this,HomePage.class);
               startActivity(intent);
           }
        }
        if(view == ButtonMove){
            Intent intent = new Intent(SingUpPage.this,SingInPage.class);
            startActivity(intent);
        }
    }

    private boolean EmailCheck() {
        String semail= etemail.getText().toString().trim();
        if(semail.length()==0)
            return false;
        else if (semail.charAt(semail.length()-1)=='.'||semail.charAt(semail.length()-1)=='@'||semail.charAt(0)=='.'||semail.charAt(0)=='@') {
            return false;
        }
        int counter=0;
        for (int i = 0; i < semail.length()-2; i++) {
            if(semail.charAt(i)=='.'&&semail.charAt(i+1)=='@')
            {
                return false;
            }
            else if (semail.charAt(i+1)=='.'&&semail.charAt(i)=='@') {
                return false;
            }
            if(semail.charAt(i)=='@')
                counter++;
        }
        if(counter !=1)
        {
            return false;
        }
        return true;
    }

    public boolean nameCheck(String s) {
        for (int i = 0; i < s.length(); i++) {
            if (i == 0) {
                if (s.charAt(i) < 'A' || s.charAt(i) > 'Z') {
                    return false;
                }
            } else {
                if (!(s.charAt(i) >= 'A' && s.charAt(i) <= 'Z') && !(s.charAt(i) >= 'a' && s.charAt(i) <= 'z')) {
                    return false;
                }
            }
        }
        return true;
    }
}