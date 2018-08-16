package com.example.ilian.helpers;

import android.app.Activity;
import android.os.Bundle;

import com.example.ilian.myapplication.R;

public class NotificationReciever extends Activity
{
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.notification);
    }

}
