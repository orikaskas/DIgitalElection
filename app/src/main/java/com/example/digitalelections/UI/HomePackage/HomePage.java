package com.example.digitalelections.UI.HomePackage;

import static android.app.PendingIntent.getActivity;
import static java.util.Calendar.getInstance;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
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
import com.example.digitalelections.User.User;
import com.example.digitalelections.UI.Vote.VoteActivity;
import com.example.digitalelections.UI.profile.profileActivity;
import com.example.digitalelections.UI.result.ResultActivity;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

public class   HomePage extends AppCompatActivity implements DatePickerDialog.OnDateSetListener, TimePickerDialog.OnTimeSetListener {

    private TextView username, timer; // טקסטווים שמציגים את שם המשתמש ואת הטיימר
    private ImageView imageper, imagevote; // תמונות הכפתורים לפרופיל ולהצבעה
    private CountDownTimer countDownTimer; // טיימר לספירת זמן לפעולת מעבר
    public static boolean premission = false; // הרשאה להצביע
    private Button result; // כפתור להצגת התוצאות
    private static String AdminEmail = ""; // כתובת האימייל של המנהל
    private String time1 = ""; // זמן ההתחלה של הטיימר
    private boolean admin; // האם המשתמש הוא מנהל או לא
    private RelativeLayout R1; // מיבוא של ה-Layout
    private modelHomePage m; // מודל הדף הראשי

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);
        username = findViewById(R.id.UseridHome);
        R1= findViewById(R.id.HomepageId);
        AdminEmail = getString(R.string.AdminEmail);
        result=findViewById(R.id.btnResult);
        imageper = findViewById(R.id.personBtn);
        imagevote=findViewById(R.id.VoteGoBtn);
        timer = findViewById(R.id.Timerp);
        m = new modelHomePage();
        ProgressDialog progressDialog = new ProgressDialog(HomePage.this);
        progressDialog.setTitle("Progress Dialog");
        progressDialog.setMessage("Loading...");
        progressDialog.setCancelable(false);
        progressDialog.show();
        m.getTime(this, new Repository.CompletedString() {
            @Override
            public void onCompleteString(String flag) {
                if (flag.equals("wrong")) {
                    Toast.makeText(HomePage.this, "לא ניתן להתחיל את הטיימר", Toast.LENGTH_SHORT).show();
                } else {
                    time1 = flag;
                    starttime(time1);
                    progressDialog.dismiss();
                }
            }
        });
        // קבלת המשתנה s1 מה-Intent
        Intent intent = getIntent();
        int s1 = intent.getIntExtra("FirstSI", 0);

        // בדיקה האם המשתמש הוא מנהל או לא
        admin = CheckAdmin();

        // פתיחת חלון הגדרות משתמש אם זה מנהל
        if (s1 == 10) {
            InfoDialog();
        }

        // שינוי הזמן על ידי המנהל
        if (admin) {
            ChangeTime();
        } else {
            // כיבוי הכפתור והטקסט אם המשתמש אינו מנהל
            result.setText("");
            result.setClickable(false);
        }

        // טיפול בלחיצות על הכפתורים
        result.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // פתיחת דף התוצאות בלחיצה על הכפתור
                Intent intent1 = new Intent(HomePage.this, ResultActivity.class);
                startActivity(intent1);
            }
        });

        imageper.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // פתיחת דף הפרופיל בלחיצה על התמונה
                Intent intent = new Intent(HomePage.this, profileActivity.class);
                startActivity(intent);
            }
        });

        imagevote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // פתיחת דף ההצבעה בלחיצה על התמונה, אם יש הרשאה
                if (premission) {
                    Intent intent1 = new Intent(HomePage.this, VoteActivity.class);
                    startActivity(intent1);
                }
                else
                    Toast.makeText(HomePage.this, "אתה לא יכול להצביע בעת", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void ChangeTime() {
        // יצירת כפתורים לשינוי שעה ותאריך
        Button Changehour = new Button(this);
        Button ChangeDate = new Button(this);

        // הגדרת טקסט לכפתורי שינוי השעה והתאריך
        ChangeDate.setText("שנה תאריך");
        Changehour.setText("שנה שעה");

        // יצירת אובייקט RelativeLayout
        RelativeLayout relativeLayout = new RelativeLayout(this);

        // הגדרת פרמטרים לפריסת הכפתורים
        RelativeLayout.LayoutParams Hourparam = new RelativeLayout.LayoutParams(300, 150);
        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(-1, 300);
        RelativeLayout.LayoutParams Dateparam = new RelativeLayout.LayoutParams(300, 150);

        // הגדרת מרווחים ומיקום לכפתורי שינוי השעה והתאריך
        Hourparam.setMargins(0, 0, 190, 0);
        Dateparam.setMargins(190, 0, 0, 0);

        // הגדרת רקע וגודל לכפתורי שינוי השעה והתאריך
        Changehour.setBackground(getDrawable(R.drawable.admincorners));
        ChangeDate.setBackground(getDrawable(R.drawable.admincorners));
        Changehour.setTextSize(17);
        ChangeDate.setTextSize(17);

        // הגדרת כללי התצוגה של כפתורי שינוי השעה והתאריך
        Hourparam.addRule(RelativeLayout.ALIGN_PARENT_END);
        Dateparam.addRule(RelativeLayout.ALIGN_PARENT_START);
        layoutParams.addRule(RelativeLayout.BELOW,timer.getId());
        relativeLayout.setLayoutParams(layoutParams);
        Changehour.setLayoutParams(Hourparam);
        ChangeDate.setLayoutParams(Dateparam);

        // הוספת הכפתורים ל-RelativeLayout
        relativeLayout.addView(ChangeDate);
        relativeLayout.addView(Changehour);

        // הוספת ה-RelativeLayout לתוך המסך הראשי
        R1.addView(relativeLayout);

        // הוספת מאזין ללחיצה על כפתור שינוי השעה
        Changehour.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Changehour1();
            }
        });

        // הוספת מאזין ללחיצה על כפתור שינוי התאריך
        ChangeDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ChangeDate1();
            }
        });
    }

    // פונקציה לקביעת שעה חדשה
    private void Changehour1() {
        final Calendar c = Calendar.getInstance();
        int hour = c.get(Calendar.HOUR);
        int minute = c.get(Calendar.MINUTE);

        // הצגת דיאלוג שינוי שעה
        new TimePickerDialog(this, this, hour, minute, true).show();
    }

    // פונקציה לקביעת תאריך חדש
    private void ChangeDate1() {
        final Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);

        // הצגת דיאלוג שינוי תאריך
        new DatePickerDialog(HomePage.this, this, year, month, day).show();
    }


    private void InfoDialog() {
        // יצירת דיאלוג
        Dialog dialog = new Dialog(this);
        dialog.setCancelable(true);
        dialog.setContentView(R.layout.infodialog);

        // הגדרת פרמטרים לגודל והצגת הדיאלוג
        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(dialog.getWindow().getAttributes());
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        dialog.getWindow().setAttributes(lp);
        dialog.show();
    }

    // פונקציה זו מציגה דיאלוג עם אפשרות להצביע
    private void VoteDialogtrue(){
        // יצירת דיאלוג
        Dialog dialog = new Dialog(this);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.truevote);

        // קישור לכפתורי הדיאלוג
        Button vote = dialog.findViewById(R.id.BtnVote);
        Button back=dialog.findViewById(R.id.Btnback);

        // הוספת מאזין לכפתור "להצביע"
        vote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomePage.this, VoteActivity.class);
                startActivity(intent);
                dialog.dismiss();
            }
        });

        // הוספת מאזין לכפתור "חזור"
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        // הגדרת פרמטרים לגודל והצגת הדיאלוג
        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(dialog.getWindow().getAttributes());
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        dialog.getWindow().setAttributes(lp);
        dialog.show();
    }

    // פונקציה זו מציגה דיאלוג עם הודעת שגיאה בקשר להצבעה
    private void VoteDialogFalse(){
        // יצירת דיאלוג
        Dialog dialog = new Dialog(this);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.falsevote);

        // קישור לכפתור "חזור" ולתיבת ההודעה
        Button back =dialog.findViewById(R.id.Btnback1);
        TextView textView = dialog.findViewById(R.id.Votetv1);

        // בדיקת תנאי והצגת ההודעה המתאימה
        if(timer.getText().toString().equals("הבחירות הסתיימו")){
            textView.setText("הבחירות הסתיימו");
        }
        else
            textView.setText("הבחירות עוד לא התחילו");

        // הוספת מאזין לכפתור "חזור"
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        // הגדרת פרמטרים לגודל והצגת הדיאלוג
        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(dialog.getWindow().getAttributes());
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        dialog.getWindow().setAttributes(lp);
        dialog.show();
    }
    public void starttime(String time)
    {
        // איפוס ההרשאה לצביעה
        premission=false;

        // קבלת הזמן הנוכחי במילישנס
        Calendar calendar = getInstance();
        long mil = calendar.getTimeInMillis();

        // פירוט הפורמט של התאריך והשעה
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
        // שימוש באזור זמן של ישראל
        ZoneId israelTimeZone = ZoneId.of("Israel");
        LocalDateTime localDate = LocalDateTime.parse(time, formatter);
        ZonedDateTime zonedDateTime = ZonedDateTime.of(localDate, israelTimeZone);
        // המרת הזמן למילישנס
        long timeInMilliseconds = zonedDateTime.toInstant().toEpochMilli();

        // חישוב הזמן הנותר עד להצבעה
        long Currentmil = timeInMilliseconds - mil;

        // בדיקת תנאים בהתאם לזמן הנותר
        if (!premission && Currentmil > 0)
        {
            // הפעלת טיימר לפי הזמן הנותר
            countDownTimer = new CountDownTimer(Currentmil, 1000)
            {
                @Override
                public void onTick(long millisUntilFinished)
                {
                    // חישוב הזמן הנותר בטיימר
                    Long daytomil = TimeUnit.DAYS.toMillis(TimeUnit.MILLISECONDS.toDays(millisUntilFinished));
                    long hourtomil = TimeUnit.HOURS.toMillis(TimeUnit.MILLISECONDS.toHours(millisUntilFinished));
                    long mintomil = TimeUnit.MINUTES.toMillis(TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished));
                    // הצגת הזמן הנותר בפורמט מתאים
                    String time = String.format(Locale.getDefault(), "%02d:%02d:%02d:%02d", TimeUnit.MILLISECONDS.toDays(millisUntilFinished)
                            , TimeUnit.MILLISECONDS.toHours(millisUntilFinished - daytomil)
                            , TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished - hourtomil)
                            , TimeUnit.MILLISECONDS.toSeconds(millisUntilFinished - mintomil));
                    timer.setText("הבחירות מתחילות בעוד \n"+time+"         ");
                    premission = false;
                }
                @Override
                public void onFinish()
                {
                    // סיום הטיימר והרשאה להצבעה
                    premission = true;
                    timer.setText("הבחירות התחילו");
                }
            }.start();

            // הצגת ההודעה המתאימה למשתמש
            VoteDialogFalse();

            // במידה והמשתמש אינו מנהל, הטקסט והכפתור של דף התוצאות ישונו
            if(!admin){
                result.setText("");
                result.setClickable(false);
            }
        }
        else
        {
            if(mil >= timeInMilliseconds + TimeUnit.HOURS.toMillis(12))
            {
                // במידה והזמן עבר את זמן ההצבעה, הטקסט והכפתור של דף התוצאות ישונו
                premission = false;
                timer.setText("הבחירות הסתיימו");
                VoteDialogFalse();
                ResetallVotes();
                result.setText("דף תוצאות");
                result.setClickable(true);
            }
            else if (mil < timeInMilliseconds + TimeUnit.HOURS.toMillis(12))
            {
                // אם עוד לא הגיע הזמן להצביע, יוצג דיאלוג עם אפשרות להצביע
                VoteDialogtrue();

                // במידה והמשתמש אינו מנהל, הטקסט והכפתור של דף התוצאות ישונו
                if(!admin){
                    result.setText("");
                    result.setClickable(false);
                }

                // חישוב הזמן הנותר עד לסיום זמן ההצבעה והפעלת טיימר
                long mil1 = timeInMilliseconds + TimeUnit.HOURS.toMillis(12) - mil;
                countDownTimer= new CountDownTimer(mil1, 1000)
                {
                    @Override
                    public void onTick(long millisUntilFinished)
                    {
                        Long daytomil = TimeUnit.DAYS.toMillis(TimeUnit.MILLISECONDS.toDays(millisUntilFinished));
                        long hourtomil = TimeUnit.HOURS.toMillis(TimeUnit.MILLISECONDS.toHours(millisUntilFinished));
                        long mintomil = TimeUnit.MINUTES.toMillis(TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished));
                        // הצגת הזמן הנותר לסיום ההצבעה
                        String time = String.format(Locale.getDefault(), "%02d:%02d:%02d:%02d", TimeUnit.MILLISECONDS.toDays(millisUntilFinished)
                                , TimeUnit.MILLISECONDS.toHours(millisUntilFinished - daytomil)
                                , TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished - hourtomil)
                                , TimeUnit.MILLISECONDS.toSeconds(millisUntilFinished - mintomil));
                        timer.setText("נשאר לבחירות עוד \n"+time+"     ");
                        if(timer.getText().toString().equals("נשאר לבחירות עוד \n"+"00:00:00:00"+"     ")){
                            timer.setText("הבחירות הסתיימו");
                        }
                        premission = true;
                    }

                    @Override
                    public void onFinish()
                    {
                        // איפוס הטיימר והצבעות
                        ResetallVotes();
                    }
                }.start();
            }
        }
    }

    public void ResetallVotes() {
        // איפוס כל ההצבעות במודל "modelHomePage"
        modelHomePage modelHomePage = new modelHomePage();
        modelHomePage.ResetallVotes(HomePage.this);
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        // פונקציה המקבלת תאריך חדש ומשנה את התאריך הנוכחי לתאריך החדש
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
        // פונקציה המשנה את התאריך או השעה בהתאם לערך שהתקבל
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
        // פונקציה המקבלת שעה חדשה ומשנה את השעה הנוכחית לשעה החדשה
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

    public boolean CheckAdmin(){
        // בדיקה האם המשתמש הוא אדמין או לא
        final boolean[] a = {false};
        SharedPreferences s = this.getSharedPreferences("Share", this.MODE_PRIVATE);
        if (!(s.getString("Email", "").isEmpty() && s.getString("Id", "").isEmpty())) {
            if(AdminEmail.equals(s.getString("Email", "").trim())){
                a[0] = true;
            }
            m.GetInfo(this, s.getString("Id", "").toString(), new Repository.Completed() {
                @Override
                public void onComplete(boolean flag) {
                    if (flag) {
                        if(AdminEmail.equals(User.getEmail())){
                            username.setText(User.getUsername() + " Admin ");
                        }
                        else
                        {
                            username.setText(User.getUsername() + "");
                        }
                    }
                }
            });
        } else
        {
            if(AdminEmail.equals(User.getEmail()))
            {
                username.setText(User.getUsername()+" - Admin");
                a[0] = true;
            }
            else {
                username.setText(User.getUsername() + " היי ");
            }
        }
        return a[0];
    }
}