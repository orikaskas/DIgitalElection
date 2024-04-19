package com.example.digitalelections.UI.HomePackage;

import static android.app.PendingIntent.getActivity;
import static java.util.Calendar.getInstance;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.digitalelections.R;
import com.example.digitalelections.Repositry.Repository;
import com.example.digitalelections.Repositry.User;
import com.example.digitalelections.UI.Vote.VoteActivity;

import java.text.DateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

public class HomePage extends AppCompatActivity implements DatePickerDialog.OnDateSetListener, TimePickerDialog.OnTimeSetListener {

    private TextView username, timer;
    private ImageView imageper,imagevote;
    private CountDownTimer countDownTimer;
    public static boolean premission = false;
    private static final String AdminEmail = "orikaskas@gmail.com";
    private String time1="";
    private RelativeLayout R1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);
        username = findViewById(R.id.UseridHome);
        R1= findViewById(R.id.HomepageId);
        imageper = findViewById(R.id.personBtn);
        imagevote=findViewById(R.id.VoteGoBtn);
        timer = findViewById(R.id.Timerp);
        modelHomePage m = new modelHomePage();
        m.getTime(this, new Repository.CompletedString() {
            @Override
            public void onCompleteString(String flag) {

                if(flag.equals("wrong")){
                    Toast.makeText(HomePage.this, "Cant start timer", Toast.LENGTH_SHORT).show();
                }
                else{
                    time1 = flag;
                    starttime(time1);
                }
            }
        });
        Intent intent = getIntent();
        int s1 = intent.getIntExtra("FirstSI", 0);
        SharedPreferences s = this.getSharedPreferences("Share", this.MODE_PRIVATE);
        if (!(s.getString("Email", "").isEmpty() && s.getString("Id", "").isEmpty())) {
            m.GetInfo(this, s.getString("Id", "").toString(), new Repository.Completed() {
                @Override
                public void onComplete(boolean flag) {
                    if (flag) {
                        if(AdminEmail.equals(User.getEmail())){
                            username.setText(User.getUsername() + " היי Admin ");
                        }
                        else
                        {
                            username.setText(User.getUsername() + " היי ");
                        }
                    }
                }
            });
        } else {
            if(AdminEmail.equals(User.getEmail())){username.setText("Admin - "+User.getUsername() + " היי ");}
            else {
                username.setText(User.getUsername() + " היי ");
            }
        }
        if (s1 == 10){
            InfoDialog();
        }
        if(AdminEmail.equals(User.getEmail())){
            ChangeTime();
        }

        imageper.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomePage.this, profileActivity.class);
                startActivity(intent);
            }
        });
        imagevote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(premission){
                    Intent intent1 = new Intent(HomePage.this,VoteActivity.class);
                    startActivity(intent1);
                }
                else
                    Toast.makeText(HomePage.this, "אתה לא יכול להצביע בעת", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void ChangeTime() {
        Button Changehour = new Button(this);
        Button ChangeDate = new Button(this);
        ChangeDate.setText("שנה תאריך");
        Changehour.setText("שנה שעה");
        RelativeLayout relativeLayout = new RelativeLayout(this);
        relativeLayout.addView(ChangeDate);
        relativeLayout.addView(Changehour);
        RelativeLayout.LayoutParams layoutParams1 = new RelativeLayout.LayoutParams(250,100);
        layoutParams1.setMargins(250,0,100,0);
        Changehour.setLayoutParams(layoutParams1);
        //ChangeDate.setLayoutParams(layoutParams1);
        Changehour.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Changehour1();
            }
        });
        ChangeDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ChangeDate1();
            }
        });
        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(-1,100);
        layoutParams.setMargins(40,300,40,0);
        relativeLayout.setLayoutParams(layoutParams);
        R1.addView(relativeLayout);
    }

    private void Changehour1() {
        final Calendar c = Calendar.getInstance();
        int hour = c.get(Calendar.HOUR);
        int minute = c.get(Calendar.MINUTE);
        int sec = c.get(Calendar.SECOND);
        new TimePickerDialog(this,this,hour,minute,true).show();
    }

    private void ChangeDate1() {
        final Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);
        new DatePickerDialog(HomePage.this,this, year,month,day).show();
    }


    private void InfoDialog() {
        Dialog dialog = new Dialog(this);
        dialog.setCancelable(true);
        dialog.setContentView(R.layout.infodialog);
        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(dialog.getWindow().getAttributes());
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        dialog.getWindow().setAttributes(lp);
        dialog.show();
    }
    private void VoteDialogtrue(){
        Dialog dialog = new Dialog(this);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.truevote);
        Button vote = dialog.findViewById(R.id.BtnVote);
        Button back=dialog.findViewById(R.id.Btnback);
        vote.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(HomePage.this, VoteActivity.class);
                    startActivity(intent);
                    dialog.dismiss();
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
    private void VoteDialogFalse(){
        Dialog dialog = new Dialog(this);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.falsevote);
        Button back =dialog.findViewById(R.id.Btnback1);
        TextView textView = dialog.findViewById(R.id.Votetv1);
        if(timer.getText().toString().equals("הבחירות הסתיימו")){
            textView.setText("הבחירות הסתיימו");
        }
        else
            textView.setText("הבחירות עוד לא התחילו");
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
    public void starttime(String time)
    {
        Calendar calendar = getInstance();
        long mil = calendar.getTimeInMillis();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
        // Use Israel time zone
        ZoneId israelTimeZone = ZoneId.of("Israel");
        LocalDateTime localDate = LocalDateTime.parse(time, formatter);
        ZonedDateTime zonedDateTime = ZonedDateTime.of(localDate, israelTimeZone);
        long timeInMilliseconds = zonedDateTime.toInstant().toEpochMilli();
        long Currentmil = timeInMilliseconds - mil;
        if (!premission&&Currentmil>0)
        {
            countDownTimer = new CountDownTimer(Currentmil, 1000)
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
            VoteDialogFalse();
        }
        else{
            if(mil >= timeInMilliseconds+TimeUnit.HOURS.toMillis(12))
            {
                premission = false;
                timer.setText("הבחירות הסתיימו");
                VoteDialogFalse();
                ResetallVotes();
            } else if (mil<timeInMilliseconds+TimeUnit.HOURS.toMillis(12))
            {
                VoteDialogtrue();
                long mil1=timeInMilliseconds+TimeUnit.HOURS.toMillis(12)-mil;
                new CountDownTimer(mil1, 1000)
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
                        timer.setText("נשאר לבחירות עוד "+time);
                        premission = true;
                    }

                    @Override
                    public void onFinish()
                    {
                        ResetallVotes();
                    }
                }.start();
            }
        }
    }

    public void ResetallVotes() {
        modelHomePage modelHomePage = new modelHomePage();
        modelHomePage.ResetallVotes(HomePage.this);
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        int year1 = year;
        int mount1=(month+1);
        int day=dayOfMonth;
        String ms,ds;
        if(mount1 <10){
             ms="/0"+mount1;
        }
        else
             ms="/"+mount1;
        if(dayOfMonth<10){
            ds = "/0"+day;
        }
        else
            ds="/"+day;
        String n=year1+ms+ds;
        ChangeString(n,0);
    }

    private void ChangeString(String s, int i) {
        String[] timesplit= time1.split(" ");
        if(i==0){
            time1 = s+" "+timesplit[1];
        }
        if(i==1){
            time1 = timesplit[0]+" "+s;
        }
        modelHomePage m = new modelHomePage();
        m.Updatetime(this,time1);
        if(!timer.getText().toString().equals("הבחירות הסתיימו")){
            countDownTimer.cancel();
        }
        starttime(time1);
    }

    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        String ho,min1;
        if(hourOfDay<10){
            ho = "0" + hourOfDay;
        }else
            ho =""+ hourOfDay;
        if(minute<10){
            min1 = "0" + minute;
        }
        else
            min1="" + minute;
        String n=ho+":"+min1+":00";
        ChangeString(n,1);
    }
}