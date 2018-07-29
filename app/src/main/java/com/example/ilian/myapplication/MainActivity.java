package com.example.ilian.myapplication;
import android.Manifest;
import android.app.ActivityManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.ServiceConnection;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.IBinder;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.os.Bundle;
import android.app.Activity;
import android.util.Log;
import android.view.View;

import android.app.AlarmManager;

public class MainActivity extends AppCompatActivity {

    String msg = "TestActivity";
    boolean mBound = false;

    Intent mServiceIntent = null;
    private SensorService mSensorService = null;
    Context ctx;

    private void setupUserPerms()
    {
        if (Build.VERSION.SDK_INT >= 23)
        {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.INTERNET)
                    != PackageManager.PERMISSION_GRANTED)
            {
                Log.d("[IVZ]",  "Over 23 API - no permission on internet");
            }
            else
            {
                Log.d("[IVZ]", "Over 23 API - we have internet perms");
            }
            /*
            ActivityCompat.requestPermissions(MainActivity.this,
                    new String[]
                            {
                                    Manifest.permission.INTERNET,
                                    Manifest.permission.WRITE_EXTERNAL_STORAGE
                            }, 1);
            */
        }
    }

    public Context getCtx()
    {
        return  ctx;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        ctx = this;

        setContentView(R.layout.activity_main);
        setupUserPerms(); // ivz - test

        mSensorService = new SensorService(getCtx());
        mServiceIntent = new Intent(getCtx(), mSensorService.getClass());
        if (!isMyServiceRunning(mSensorService.getClass()))
        {
            startService(mServiceIntent);
        }

        Log.d(msg, "The onCreate() event");
    }

    private boolean isMyServiceRunning(Class<?> serviceClass) {
        ActivityManager manager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
            if (serviceClass.getName().equals(service.service.getClassName())) {
                Log.d ("isMyServiceRunning?", true+"");
                return true;
            }
        }
        Log.d ("isMyServiceRunning?", false+"");
        return false;
    }



    @Override
    protected void onStart()
    {
        super.onStart();
        // start the service
//        Intent intent = new Intent(this, AlarmService.class);
//        bindService(intent, mConnection, Context.BIND_AUTO_CREATE);
    }


    /*
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }
    */


    /*
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    */
    private  Thread mThread = null;

    private void startMessager()
    {
        /*
        mThread = new Thread(new Runnable() {
            @Override
            public void run()
            {
                while(true)
                {
                    Log.d("Debug Message", mService.getServiceMessage());

                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        });

        mThread.start();
        */
    }

    // service connection
    private ServiceConnection mConnection = new ServiceConnection() {

        @Override
        public void onServiceConnected(ComponentName className,
                                       IBinder service) {
            /*
            // We've bound to LocalService, cast the IBinder and get LocalService instance
            AlarmService.LocalBinder binder = (AlarmService.LocalBinder) service;
            mService = binder.getService();
            mBound = true;

            if (mService != null)
            {
                startMessager();
            }
            */
        }

        @Override
        public void onServiceDisconnected(ComponentName arg0)
        {
            mBound = false;
        }
    };


    @Override
    protected void onDestroy()
    {
        stopService(mServiceIntent);
        Log.i("[IVZ]", "MainActivity.onDestroy()!");
        super.onDestroy();

    }

}
