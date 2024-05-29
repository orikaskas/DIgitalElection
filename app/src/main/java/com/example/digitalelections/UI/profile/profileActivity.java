package com.example.digitalelections.UI.profile;

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
import android.widget.TextView;

import com.example.digitalelections.R;
import com.example.digitalelections.Repositry.Repository;
import com.example.digitalelections.User.User;
import com.example.digitalelections.UI.MainActivity;

public class profileActivity extends AppCompatActivity {
    TextView username, email, phone, city, age, id;
    Button Update, buttonSignout, goback;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        // קישור לאלמנטים ב-XML
        username = findViewById(R.id.UserName);
        email = findViewById(R.id.UserEmail);
        phone = findViewById(R.id.UserPhone);
        city = findViewById(R.id.UserCity);
        age = findViewById(R.id.UserAge);
        id = findViewById(R.id.UserIdInside);
        goback = findViewById(R.id.BackfromProfile);
        Update = findViewById(R.id.btnUpdate);
        buttonSignout = findViewById(R.id.btnSingout);

        // הגדרת טקסט לתיבות הטקסט על פי המידע הנוכחי של המשתמש
        username.setText("שם משתמש: " + User.getUsername());
        email.setText("אימייל: " + User.getEmail());
        id.setText("תעודת זהות: " + User.getId());
        age.setText("גיל: " + User.getAge());
        phone.setText("טלפון: " + User.getPhone());
        city.setText("עיר: " + User.getCity());

        // יצירת מודל לפרופיל
        profilemodle m = new profilemodle();

        // כפתור ההתנתקות
        buttonSignout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // התנתקות מהחשבון ומעבר למסך הראשי
                m.Signout(profileActivity.this);
                Intent intent = new Intent(profileActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });

        // כפתור עדכון פרטי המשתמש
        Update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // הצגת דיאלוג לעדכון המידע
                updateDataDialog();
            }
        });

        // כפתור חזרה למסך הקודם
        goback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    // פתיחת דיאלוג לעדכון המידע
    public void updateDataDialog() {
        Dialog dialog = new Dialog(this);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.updatedialog);

        // קישור לאלמנטים ב-XML
        EditText etuserNameUpdate = dialog.findViewById(R.id.userNameUpdate);
        EditText etAgeUpdate = dialog.findViewById(R.id.UpdateAge);
        EditText etphoneUpdate = dialog.findViewById(R.id.phoneUpdate);
        Button btnUpdate = dialog.findViewById(R.id.btnUpdate1);
        Button closeUpdate = dialog.findViewById(R.id.btncancel);

        // הצגת הדיאלוג
        dialog.show();

        // סגירת הדיאלוג בלחיצה על כפתור הביטול
        closeUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

        profilemodle profilemodle = new profilemodle();

        // עדכון המידע לפי הערכים שהוזנו
        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final boolean[] b = {false};
                if (etuserNameUpdate.length() != 0) {
                    // בדיקת תקינות השם החדש שהוזן
                    if (!profilemodle.checkName(etuserNameUpdate.getText().toString()).equals("")) {
                        etuserNameUpdate.setError(profilemodle.checkName(etuserNameUpdate.getText().toString().trim()));
                        b[0] = true;
                    } else {
                        User.setUsername(etuserNameUpdate.getText().toString());
                    }
                }
                if (etphoneUpdate.length() != 0) {
                    // בדיקת תקינות הטלפון החדש שהוזן
                    String phone = etphoneUpdate.getText().toString().trim();
                    String answer = profilemodle.checkphone(phone).trim();
                    if (!answer.equals("good") && !answer.equals("")) {
                        b[0] = true;
                        etphoneUpdate.setError(answer);
                    } else {
                        User.setPhone(phone);
                    }
                }
                if (etAgeUpdate.length() != 0) {
                    // בדיקת תקינות הגיל החדש שהוזן
                    if (Integer.parseInt(etAgeUpdate.getText().toString().trim()) > 120 || Integer.parseInt(etAgeUpdate.getText().toString().trim()) < 18) {
                        b[0] = true;
                        etAgeUpdate.setError("גיל לא תקין");
                    } else
                        User.setAge(Integer.parseInt(etAgeUpdate.getText().toString().trim()));
                }
                if (!b[0]) {
                    // אם הקלט תקין, סגירת הדיאלוג ועדכון המידע
                    dialog.dismiss();
                    Update();
                    finish();
                }
            }
        });

        // הצגת הנתונים הנוכחיים בשדות הקלט
        etuserNameUpdate.setHint(User.getUsername());
        etAgeUpdate.setHint(User.getAge() + "");
        etphoneUpdate.setHint(User.getPhone());
    }

    // עדכון המידע בבסיס הנתונים
    public void Update() {
        Repository r = new Repository(this);
        r.UpdateUser(User.getId(), User.getEmail(), User.getUsername(), User.getAge(), User.getPhone());
    }
}
