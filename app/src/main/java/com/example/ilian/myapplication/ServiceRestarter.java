package com.example.ilian.myapplication;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class ServiceRestarter extends BroadcastReceiver
{
    @Override
    public void onReceive(Context context, Intent intent)
    {
        Log.d(ServiceRestarter.class.getSimpleName(), "[IVZ]: Service stopped. Needs restart...");
            context.startService(new Intent(context, HttpService.class));
    }
}

