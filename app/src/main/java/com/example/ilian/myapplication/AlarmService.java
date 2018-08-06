package com.example.ilian.myapplication;
import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;



public class AlarmService extends Service
{
    private  Thread mWorker = null;

    private final IBinder mBinder = new LocalBinder();

    @Nullable
    @Override
    public IBinder onBind(Intent intent)
    {
        Log.d("Debug", "Bound...");
        mWorker = new Thread(new Runnable() {
            @Override
            public void run() {
                while (true)
                {
                    Log.d("[IVZ]ServiceThread", "Serviice thread is running....");
                    try
                    {
                        Thread.sleep(1000);
                    } catch (InterruptedException e)
                    {
                        e.printStackTrace();
                    }
                }
            }
        });
        mWorker.start();

        return mBinder;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId)
    {
        // Let it continue running until it is stopped.
        Log.d("Debug", "on start command");
        return START_STICKY;
    }

    @Override
    public void onDestroy()
    {
        super.onDestroy();
        Log.d("Debug", "On destroy...");
    }

    public  class LocalBinder extends Binder
    {
        AlarmService getService()
        {
            return  AlarmService.this;
        }
    }

    public String getServiceMessage()
    {
        return  new String("Message from server ... OK");
    }
}
