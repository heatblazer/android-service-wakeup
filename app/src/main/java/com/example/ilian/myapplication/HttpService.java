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
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Timer;
import java.util.TimerTask;


    /*
    'https://thingspeak.com/channels/401474/field/1.json?callback=?&amp;offset=0&amp;days=1'
     'https://thingspeak.com/channels/401474/feed.json'
    'https://thingspeak.com/channels/401474/feed.xml'
    'https://thingspeak.com/channels/401474/feed.csv?results=1'
     */

public class HttpService extends  Service
{


    private  Alarm alarm = null; // alarm instance from service

    private final  String URL_401474_CSV = "https://thingspeak.com/channels/401474/feed.csv";

    private  static int counter = 0;
    private File mFile = null;
    private Timer mTimer = null;
    private TimerTask timerTask = null;


    private String readStream(InputStream input)
    {
        StringBuilder sb = new StringBuilder();
        BufferedReader reader = new BufferedReader(new InputStreamReader(input));
        String line = null;
        try
        {
            while ((line = reader.readLine()) != null)
            {
                sb.append(line);
            }
        }
        catch (Exception ex)
        {
        }
        finally
        {
            try
            {
                reader.close();
            }
            catch (Exception ex) {}
        }
        return  sb.toString();
    }


    private File getTempFile(Context context, String url)
    {
        File file = null;
        try
        {
            String fileName = Uri.parse(url).getLastPathSegment();
            file = File.createTempFile(fileName, null, context.getCacheDir());
        } catch (IOException e)
        {
        }
        return file;
    }


    private void writeToFile(String text)
    {
        if (mFile == null)
        {
            mFile = getTempFile(null, "mytmpfile");
        }
        try
        {
            FileOutputStream fs = new FileOutputStream(mFile);
            try {
                fs.write(text.getBytes());
            } catch (Exception ex) {}
            finally {
                fs.close();
            }
        } catch (Exception ex) {}
    }

    private String getCsvWParams(String param)
    {
        return  URL_401474_CSV + param;
    }

    private  void testHttp()
    {
        URL url = null;
        HttpURLConnection urlConnection = null;
        try
        {
            String text = getCsvWParams("?results=1");
            url = new URL(text);

            urlConnection = (HttpURLConnection) url.openConnection();
            InputStream in = new BufferedInputStream(urlConnection.getInputStream());

            String output = readStream(in);
            if (output != null)
            {
                Log.d("IVZ", output);
   //             writeToFile(output);
            }
            else
            {
                Log.d("IVZ", "No output");
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

    private  Context mCtx = null;

    public HttpService() {}

    public  HttpService(Context ctx)
    {
        super();
        Log.d("[IVZ]", "Simple service started...");
//        mFile = getTempFile(ctx, "myTestFile");
        if (ctx != null)
            mCtx = ctx;
        else
            ctx = this;
    }

    public void startTimer()
    {
        //set a new Timer
        mTimer= new Timer();
        //initialize the TimerTask's job
        initializeTimerTask();

        //schedule the timer, to wake up every 1 second
        mTimer.schedule(timerTask, 1000, 10000); //
    }

    public void initializeTimerTask()
    {
        timerTask = new TimerTask()
        {
            public void run() {
                testHttp();
            }
        };
    }

    public void stoptimertask()
    {
        //stop the timer, if it's not already null
        if (mTimer != null) {
            mTimer.cancel();
            mTimer = null;
        }
    }


    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        super.onStartCommand(intent, flags, startId);
        startTimer();
        if (mCtx == null)
            mCtx = this;

        if (mCtx != null)
        {
            alarm = new Alarm(mCtx);
            alarm.SetAlarm();
            AlarmReceiver.alarm = alarm;
            AlarmReceiver.sContext = mCtx;
        }
        else
        {
            Log.d("[IVZ]: HttpService", "Context is null?");
        }

        return START_STICKY;
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent)
    {
        return null;
    }

    @Override
    public void onDestroy()
    {
        super.onDestroy();
        Log.d("EXIT", "ondestroy!");
        Intent broadcastIntent = new Intent("com.example.ilian.ServiceRestarter");
        sendBroadcast(broadcastIntent);
        stoptimertask();
    }
}
