package com.example.ilian.myapplication;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Timer;
import java.util.TimerTask;


import com.example.ilian.utils.UdpMessage;

public class ThingpseakUrl {

    private final String URL_401474_CSV = "https://thingspeak.com/channels/401474/feed.csv";
    UdpMessage myMessage ;

    private String readStream(InputStream input) {
        StringBuilder sb = new StringBuilder();
        BufferedReader reader = new BufferedReader(new InputStreamReader(input));
        String line = null;
        try {
            while ((line = reader.readLine()) != null) {
                sb.append(line);
            }
        } catch (Exception ex) {
        } finally {
            try {
                reader.close();
            } catch (Exception ex) {
            }
        }
        return sb.toString();
    }


    private String getCsvWParams(String param) {
        return URL_401474_CSV + param;
    }

    public void testHttp() {
        URL url = null;
        HttpURLConnection urlConnection = null;
        try {
            String text = getCsvWParams("?results=1");
            url = new URL(text);

            urlConnection = (HttpURLConnection) url.openConnection();
            InputStream in = new BufferedInputStream(urlConnection.getInputStream());

            String output = readStream(in);
            if (output != null) {
                Log.d("IVZ", output);
                //             writeToFile(output);
                myMessage.Send(output);
            } else {
                Log.d("IVZ", "No output");
            }
        } catch (Exception ex) {
            Log.d("[IVZ]:", ex.getMessage());
        } finally {
            urlConnection.disconnect();
        }
    }


    public ThingpseakUrl()
    {
        Log.d("[IVZ]", "ThinkSpeakUrl()");
//        mFile = getTempFile(ctx, "myTestFile");
        myMessage = new UdpMessage();
    }




}
