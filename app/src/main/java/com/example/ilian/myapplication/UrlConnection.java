package com.example.ilian.myapplication;

import android.util.Log;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.SocketException;
import java.net.URL;

public class UrlConnection
{
    private Thread mThread = null;

    private boolean isRunning = false;


    // get and respond
    private String getResponseTest(String uri) throws Exception
    {
        URL url = new URL(uri);
        HttpURLConnection connection = (HttpURLConnection)url.openConnection();
        connection.setRequestMethod("GET");
        connection.setDoOutput(true);
        connection.setConnectTimeout(5000);
        connection.setReadTimeout(5000);
        connection.connect();
        BufferedReader rd = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        String content = "", line;

        while ((line = rd.readLine()) != null)
        {
            content += line + "\n";
        }
        return content;

    }



    public UrlConnection()
    {

    }

    public void Create()
    {
        if (isRunning)
        {
            return;
        }
        else
        {
            isRunning = true;
            mThread = new Thread(new Runnable()
            {
                @Override
                public void run()
                {
                    while (isRunning)
                    {
                        try
                        {
                            String resp = getResponseTest("https://thingspeak.com/130194/feed.json");
                            Log.d("[IVZ]", resp);
                        } catch (Exception ex)
                        {
                            Log.d("[IVZ]", "Exception in getResponseTest() (" + ex.getMessage() + ")");
                        }

                        try
                        {
                            Thread.currentThread().sleep(5000);
                        } catch (Exception ex) { }

                    }
                }
            });
            mThread.start();
        }
    }
}

//130194 - user ID