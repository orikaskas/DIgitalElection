package com.example.digitalelections.Model;

import static java.util.Calendar.getInstance;

import android.app.Service;
import android.content.Intent;
import android.os.CountDownTimer;
import android.os.IBinder;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

public class TimerService extends Service {
    public static boolean premission=false;
     public static String day = null,hour = null,min=null,sec=null;

    public TimerService() {
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Calendar calendar = getInstance();
        CountDownTimer countDownTimer;
        long mil=calendar.getTimeInMillis();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/mm/dd HH:mm:ss ", Locale.ENGLISH);
        LocalDateTime localDate = LocalDateTime.parse("2024/04/12 12:00:00", formatter);
        long timeInMilliseconds = localDate.atOffset(ZoneOffset.UTC).toInstant().toEpochMilli();
        long Currentmil=timeInMilliseconds-mil;
        if(Currentmil>0){
             countDownTimer= new CountDownTimer(Currentmil,1000) {
                @Override
                public void onTick(long millisUntilFinished) {
                    String time = String.format(Locale.getDefault(),"%02d:%02d:%02d:%02d", TimeUnit.MILLISECONDS.toDays(millisUntilFinished)
                            ,TimeUnit.MILLISECONDS.toHours(millisUntilFinished)
                            ,TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished)
                            ,TimeUnit.MILLISECONDS.toSeconds(millisUntilFinished));
                    final String[] dayHourminsec=time.split(":");
                    day.equals(dayHourminsec[0]);
                    hour.equals(dayHourminsec[1]);
                    min.equals(dayHourminsec[2]);
                    sec.equals(dayHourminsec[3]);
                    premission = false;
                }
                @Override
                public void onFinish() {
                    premission= true;
                }
            };
        }
        return START_STICKY;
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }
}