package com.example.ilian.myapplication;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Environment;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.util.Timer;
import java.util.TimerTask;



/**
 * Created by fabio on 30/01/2016.
 */
public class SensorService extends Service {
    public int counter = 0;

    UrlConnection mUrl = null;

    Alarm mAlarm = null;

    public SensorService(Context applicationContext) {
        super();
        Log.d("[IVZ]", "Simple service started...");
        Init();
    }

    public SensorService() {
    }

    File fp  = Environment.getDataDirectory();
    String  myfile = new String(fp.getAbsolutePath() + "/Test.txt");
    BufferedWriter mWriter ;

    public void Init()
    {
        try {
            fp = Environment.getDataDirectory();
            myfile = new String(fp.getAbsolutePath() + File.separator + "Test.txt");

            mWriter = new BufferedWriter(new FileWriter(new
                    File(fp.getAbsolutePath()+File.separator+"MyFile.txt")));

        } catch (Exception ex)  {
            Log.d("[IVZ]", "Exception in fopen (" + ex.getMessage() + ")");
        }
    }

    private void writeTestFile()
    {
        try
        {
            String s = new String("TESTTIMER +++ " + (counter++));
            mWriter.write(s.toCharArray());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        super.onStartCommand(intent, flags, startId);
//        startTimer();
//        mAlarm = new Alarm();
//        mAlarm.setAlarm(this);
//        mUrl = new UrlConnection();
//        mUrl.Create();
        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d("EXIT", "ondestroy!");
        Intent broadcastIntent = new Intent("com.example.ilian.ServiceRestarter");
        sendBroadcast(broadcastIntent);
        stoptimertask();
    }

    private Timer timer;
    private TimerTask timerTask;
    long oldTime = 0;

    public void startTimer() {
        //set a new Timer
        timer = new Timer();

        //initialize the TimerTask's job
        initializeTimerTask();

        //schedule the timer, to wake up every 1 second
        timer.schedule(timerTask, 1000, 1000); //
    }

    /**
     * it sets the timer to print the counter every x seconds
     */
    public void initializeTimerTask()
    {
        timerTask = new TimerTask()
        {
            public void run() {
                writeTestFile();
            }
        };
    }


    public void stoptimertask() {
        //stop the timer, if it's not already null
        if (timer != null) {
            timer.cancel();
            timer = null;
        }
    }


    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}

