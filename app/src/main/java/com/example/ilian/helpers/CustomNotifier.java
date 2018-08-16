package com.example.ilian.helpers;

import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.example.ilian.myapplication.R;

import static android.content.Context.NOTIFICATION_SERVICE;
import static android.support.v4.content.ContextCompat.getSystemService;


public class CustomNotifier implements INotify
{

    class IntentMessage
    {
        public  String action;
    }

    private Context context;

    public CustomNotifier() {/*for the manifest*/}


    public CustomNotifier(Context ctx)
    {
        context = ctx;
    }

    @Override
    public void Notify()
    {
        try {
            // Prepare intent which is triggered if the
            // notification is selected
            Intent intent = new Intent(context, NotificationReciever.class);
            PendingIntent pIntent = PendingIntent.getActivity(context, (int) System.currentTimeMillis(), intent, 0);

            // Build notification
            // Actions are just fake
            Notification noti = new Notification.Builder(context)
                    .setContentTitle("New mail from " + "test@gmail.com")
                    .setContentText("Subject")
                    .setContentIntent(pIntent)
                    .build();
            NotificationManager notificationManager = (NotificationManager) context.getSystemService(NOTIFICATION_SERVICE);
            // hide the notification after its selected
            noti.flags |= Notification.FLAG_AUTO_CANCEL;

            notificationManager.notify(0, noti);
        }
        catch (Exception ex)
        {
            Log.d("[IVZ]:", ex.getMessage());
        }
    }
}
