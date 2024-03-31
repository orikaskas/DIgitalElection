package com.example.digitalelections.UI.HomePackage;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.digitalelections.R;
import com.example.digitalelections.Repositry.Repository;
import com.example.digitalelections.Repositry.User;
import com.example.digitalelections.UI.MainActivity;
import com.example.digitalelections.UI.SignUp.modelSignUp;

import org.checkerframework.common.subtyping.qual.Bottom;

public class profileActivity extends AppCompatActivity {
    TextView username,email,phone,city,age,id;
    Button Update,buttonSignout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        username = findViewById(R.id.UserName);
        email = findViewById(R.id.UserEmail);
        phone = findViewById(R.id.UserPhone);
        city = findViewById(R.id.UserCity);
        age = findViewById(R.id.UserAge);
        id = findViewById(R.id.UserIdInside);
        username.setText("Username: "+ User.getUsername());
        email.setText("email: "+ User.getEmail());
        id.setText("Id: "+ User.getId());
        age.setText("Age: "+ User.getAge());
        phone.setText("phone: "+ User.getPhone());
        city.setText("City: "+ User.getCity());
        Update = findViewById(R.id.btnUpdate);
        profilemodle m = new profilemodle();
        buttonSignout = findViewById(R.id.btnSingout);
        buttonSignout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                m.Signout(profileActivity.this);
                Intent intent = new Intent(profileActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
        Update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateDataDialog();
            }
        });


    }
    public void updateDataDialog() {
        Dialog dialog = new Dialog(this);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.updatedialog);

        EditText etuserNameUpdate = dialog.findViewById(R.id.userNameUpdate);
        EditText etAgeUpdate = dialog.findViewById(R.id.UpdateAge);
        EditText etphoneUpdate = dialog.findViewById(R.id.phoneUpdate);
        Button btnUpdate = dialog.findViewById(R.id.btnUpdate1);
        Button closeUpdate = dialog.findViewById(R.id.btncancel);

        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(dialog.getWindow().getAttributes());
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        dialog.getWindow().setAttributes(lp);
        closeUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                dialog.dismiss();
            }
        });
        profilemodle profilemodle = new profilemodle();
        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final boolean[] b = {false};
                if(etuserNameUpdate.length() != 0){
                    if(!profilemodle.checkName(etuserNameUpdate.getText().toString()).equals("")){
                        etuserNameUpdate.setError(profilemodle.checkName(etuserNameUpdate.getText().toString()));
                        b[0]=true;
                    }
                    else {
                        User.setUsername(etuserNameUpdate.getText().toString());
                    }

                }
                if(etphoneUpdate.length() != 0){
                    String phone = etphoneUpdate.getText().toString();
                    String answer = profilemodle.checkphone(phone);
                    if(!answer.equals("good") && !answer.equals("")){
                        b[0] = true;
                        etphoneUpdate.setError(answer);
                    }
                    else
                    {
                        User.setPhone(phone);
                    }
                }
                if(etAgeUpdate.length() != 0){
                    if(Integer.parseInt(etAgeUpdate.getText().toString())>120 ||Integer.parseInt(etAgeUpdate.getText().toString())<18){
                        b[0] = true;
                        etAgeUpdate.setError("age not valid");
                    }
                    else
                        User.setAge(Integer.parseInt(etAgeUpdate.getText().toString()));
                }
                if(!b[0]){
                    dialog.dismiss();
                    Update();
                    Intent intent = new Intent(profileActivity.this,HomePage.class);
                    startActivity(intent);
                }

            }
        });
        etuserNameUpdate.setHint(User.getUsername());
        etAgeUpdate.setHint(User.getAge()+"");
        etphoneUpdate.setHint(User.getPhone());
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.show();
    }
    public void Update(){
        Repository r = new Repository(this);
         r.UpdateUser(User.getId(),User.getEmail(),User.getUsername(),User.getAge(),User.getPhone());
    }



}