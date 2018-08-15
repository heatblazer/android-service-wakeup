package com.example.ilian.helpers;

import android.app.Activity;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;


public class CustomNotifier implements INotify
{

    class IntentMessage
    {
        public  String action;
    }

    private Context context;


    public CustomNotifier(Context ctx)
    {
        context = ctx;
    }

    @Override
    public void Notify()
    {

    }
}
