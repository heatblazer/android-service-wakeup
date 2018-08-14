package com.example.ilian.myapplication;
import android.content.Context;
import android.content.Intent;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v4.content.WakefulBroadcastReceiver;
import android.util.Log;
import java.lang.Thread;


public class AlarmReceiver extends WakefulBroadcastReceiver
{


    static ThingpseakUrl thingspeakUrl = new ThingpseakUrl();

    @Override
    public void onReceive(final Context context, Intent intent) {

        Thread t = null;
        try {
             t = new Thread(new Runnable() {
                @Override
                public void run() {
                    thingspeakUrl.testHttp();
                }
            });
            t.start();
        }
        catch (Exception ex)
        {
        }

        //MainActivity.getTextView2().setText("Enough Rest. Do Work Now!");
        Log.d("[IVZ]:", "Alarm recieved... ringing");
        Uri uri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM);
        Ringtone ringtone = RingtoneManager.getRingtone(context, uri);
        ringtone.play();
    }
}