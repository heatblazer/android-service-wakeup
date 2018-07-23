package com.example.ilian.myapplication;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

import java.io.BufferedInputStream;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Timer;
import java.util.TimerTask;



/**
 * Created by fabio on 30/01/2016.
 */
public class SensorService extends Service {
    public int counter = 0;

    UrlConnection mUrl = null;

    Alarm mAlarm = null;

    File mFile = null;

    public SensorService(Context applicationContext) {
        super();
        Log.d("[IVZ]", "Simple service started...");
        mFile = getTempFile(applicationContext, "myTestFile");

        int a = 0;
    }

    public SensorService()
    {
    }

    private File getTempFile(Context context, String url) {
        File file = null;
        try
        {
            String fileName = Uri.parse(url).getLastPathSegment();
            file = File.createTempFile(fileName, null, context.getCacheDir());
        } catch (IOException e)
        {// Error while creating file
        }
        return file;
    }

    /*
    wget -c 'https://thingspeak.com/channels/401474/field/1.json?callback=?&amp;offset=0&amp;days=1'
    wget -c 'https://thingspeak.com/channels/401474/feed.json'
    wget -c 'https://thingspeak.com/channels/401474/feed.xml'
    wget -c 'https://thingspeak.com/channels/401474/feed.csv'
     */

    private  void testHttp()
    {
        URL url = null;
        HttpURLConnection urlConnection = null;
        try
        {
//            url = new URL("https://thingspeak.com/channels/401474/feed.json");
            url = new URL("http://www.nucleusys.com");

            urlConnection = (HttpURLConnection) url.openConnection();
            InputStream in = new BufferedInputStream(urlConnection.getInputStream());
            int size = in.available();
            byte[] data = new byte[size];

            if (in.read(data, 0, size) <= 0)
            {
                Log.d("[IVZ]:", "No data from URL");
            }
            else
            {
                String s = new String(data);
                Log.d("[IVZ]:" , s);
            }

        }
        catch (Exception ex)
        {
            Log.d("[IVZ]:", ex.getMessage());
        }
        finally
        {
            urlConnection.disconnect();
        }

    }


    private void writeTestFile()
    {
        try
        {
            testHttp();
            String s = new String("TESTTIMER +++ " + (counter++));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        super.onStartCommand(intent, flags, startId);
        startTimer();
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
        timer.schedule(timerTask, 1000, 10000); //
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

