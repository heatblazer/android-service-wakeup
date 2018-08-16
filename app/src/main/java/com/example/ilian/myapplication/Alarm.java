package com.example.ilian.myapplication;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;
import android.widget.Toast;

import com.example.ilian.helpers.CustomNotifier;

import java.util.Calendar;

public class Alarm
{

    private AlarmManager alarmManager = null;
    private PendingIntent pendingIntent = null;
    private Context context = null;
    private Activity parent = null;
    //private CustomNotifier customNotifier = null;

    public Alarm(Context ctx, Activity activity)
    {
        context = ctx;
        parent = activity;
        //customNotifier  = new CustomNotifier(ctx);

    }


    public  void SetAlarm()
    {

        alarmManager = (AlarmManager) context.getSystemService(context.ALARM_SERVICE);
        Intent myIntent = new Intent(parent, AlarmReceiver.class);
        myIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        pendingIntent = PendingIntent.getBroadcast(parent, 0, myIntent, 0);

  //      PendingIntent pendingIntent = PendingIntent.getActivity(parent, 1, myIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        /*

         Intent intent = new Intent(ReminderActivity.this, ReminderActivity.class);

            PendingIntent pendingIntent = PendingIntent.getActivity(ReminderActivity.this, 1, intent, PendingIntent.FLAG_UPDATE_CURRENT);
         */


        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, 2);
        calendar.set(Calendar.MINUTE, 50);
//        alarmManager.setExact(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);
        alarmManager.setRepeating(AlarmManager.ELAPSED_REALTIME_WAKEUP,
                SystemClock.elapsedRealtime(),
                10 * 1000,
                pendingIntent);

       // customNotifier.Notify();
    }

    public  void CancelAlarm()
    {
        if (alarmManager != null)
        {
            alarmManager.cancel(pendingIntent);
        }
    }
}


/*

public final void setAlarm(int seconds) {
    // create the pending intent
    Intent intent = new Intent(MainActivity.this, AlarmReceiver.class);
    // intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
    pendingIntent = PendingIntent.getBroadcast(MainActivity.this, 0,
            intent, 0);
    // get the alarm manager, and scedule an alarm that calls the receiver
    ((AlarmManager) getSystemService(ALARM_SERVICE)).set(
            AlarmManager.RTC, System.currentTimeMillis() + seconds
                    * 1000, pendingIntent);
    Toast.makeText(MainActivity.this, "Timer set to " + seconds + " seconds.",
            Toast.LENGTH_SHORT).show();
}

public static class AlarmReceiver extends BroadcastReceiver {
    public void onReceive(Context context, Intent intent) {
        Log.d("-", "Receiver3");
    }
}
 */

/*

 <receiver android:name="com.example.test.MainActivity$AlarmReceiver" >
    </receiver>

 */