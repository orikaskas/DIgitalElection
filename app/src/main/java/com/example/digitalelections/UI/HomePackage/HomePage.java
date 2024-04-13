package com.example.digitalelections.UI.HomePackage;

import static java.util.Calendar.getInstance;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.digitalelections.R;
import com.example.digitalelections.Repositry.Repository;
import com.example.digitalelections.Repositry.User;
import com.example.digitalelections.UI.Vote.VoteActivity;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

public class HomePage extends AppCompatActivity {

    private TextView username, timer;
    private ImageView imageView;
    public static boolean premission = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);
        username = findViewById(R.id.UseridHome);
        imageView = findViewById(R.id.personBtn);
        timer = findViewById(R.id.Timerp);
        starttime();
        modelHomePage m = new modelHomePage();
        Intent intent = getIntent();
        int s1 = intent.getIntExtra("FirstSI", 0);
        SharedPreferences s = this.getSharedPreferences("Share", this.MODE_PRIVATE);
        if (!(s.getString("Email", "").isEmpty() && s.getString("Id", "").isEmpty())) {
            m.GetInfo(this, s.getString("Id", "").toString(), new Repository.Completed() {
                @Override
                public void onComplete(boolean flag) {
                    if (flag) {
                        username.setText(User.getUsername() + " היי ");
                    }
                }
            });
        } else {
            username.setText(User.getUsername() + " היי ");
        }
        if (s1 == 10) {
            InfoDialog();
        }
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomePage.this, profileActivity.class);
                startActivity(intent);
            }
        });
        //VoteDialog();
    }

    private void InfoDialog() {
        Dialog dialog = new Dialog(this);
        dialog.setCancelable(true);
        TextView textView = findViewById(R.id.TVdia);

        dialog.setContentView(R.layout.infodialog);
        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(dialog.getWindow().getAttributes());
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        dialog.getWindow().setAttributes(lp);
        dialog.show();
    }
    private void VoteDialog(){
        Dialog dialog = new Dialog(this);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.truevote);
        Button vote = findViewById(R.id.BtnVote);
        Button back=findViewById(R.id.Btnback);
        vote.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(HomePage.this, VoteActivity.class);
                    startActivity(intent);
                }
        });
        back.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                }
        });
        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(dialog.getWindow().getAttributes());
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        dialog.getWindow().setAttributes(lp);
        dialog.show();
    }

    public void starttime()
    {
        Calendar calendar = getInstance();
        long mil = calendar.getTimeInMillis();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd  HH:mm:ss");
        // Use Israel time zone
        ZoneId israelTimeZone = ZoneId.of("Israel");
        LocalDateTime localDate = LocalDateTime.parse("2024/04/13  19:52:00", formatter);
        ZonedDateTime zonedDateTime = ZonedDateTime.of(localDate, israelTimeZone);
        long timeInMilliseconds = zonedDateTime.toInstant().toEpochMilli();
        long Currentmil = timeInMilliseconds - mil;
        if (Currentmil > 0)
        {
            new CountDownTimer(Currentmil, 1000)
            {
                @Override
                public void onTick(long millisUntilFinished)
                {
                    Long daytomil = TimeUnit.DAYS.toMillis(TimeUnit.MILLISECONDS.toDays(millisUntilFinished));
                    long hourtomil = TimeUnit.HOURS.toMillis(TimeUnit.MILLISECONDS.toHours(millisUntilFinished));
                    long mintomil = TimeUnit.MINUTES.toMillis(TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished));
                    String time = String.format(Locale.getDefault(), "%02d:%02d:%02d:%02d", TimeUnit.MILLISECONDS.toDays(millisUntilFinished)
                            , TimeUnit.MILLISECONDS.toHours(millisUntilFinished - daytomil)
                            , TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished - hourtomil)
                            , TimeUnit.MILLISECONDS.toSeconds(millisUntilFinished - mintomil));
                    timer.setText("הבחירות מתחילות בעוד "+time);
                    premission = false;
                }
                @Override
                public void onFinish()
                {
                    premission = true;
                    timer.setText("הבחירות התחילו");

                }
            }.start();
        }
        else{

        }
    }
}