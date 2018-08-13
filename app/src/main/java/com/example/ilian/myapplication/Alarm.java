package com.example.ilian.myapplication;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import java.util.Calendar;

public class Alarm
{

    private AlarmManager alarmManager = null;
    private PendingIntent pendingIntent = null;
    private Context context = null;
    private Activity parent = null;


    public Alarm(Context ctx, Activity activity)
    {
        context = ctx;
        parent = activity;
    }



    public  void SetAlarm()
    {

        alarmManager = (AlarmManager) context.getSystemService(context.ALARM_SERVICE);
        Intent myIntent = new Intent(parent, AlarmReceiver.class);
        pendingIntent = PendingIntent.getBroadcast(parent, 0, myIntent, 0);


        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, 2);
        calendar.set(Calendar.MINUTE, 50);
        alarmManager.setExact(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);
    }

    public  void CancelAlarm()
    {
        if (alarmManager != null)
        {
            alarmManager.cancel(pendingIntent);
        }
    }
}
