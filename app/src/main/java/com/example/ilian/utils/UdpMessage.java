package com.example.ilian.utils;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.Inet4Address;
import java.net.InetAddress;

import android.annotation.SuppressLint;
import android.os.AsyncTask;
import android.os.Build;
import android.util.Log;

public class UdpMessage implements IMessage
{

    private  AsyncTask<Void, Void, Void> asyncClient;
    private   String message;
    private String host;
    private int port;

    public UdpMessage(String host, int port)
    {
        this.host = host; this.port = port;
    }

    public  UdpMessage()
    {

    }

    @Override
    public void Send(final String message)
    {

        asyncClient = new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... voids)
            {
                Inet4Address address = null;
                try
                {
                    address = (Inet4Address) Inet4Address.getByName("10.4.25.57");
                }
                catch (Exception ex) {}

                DatagramSocket ds = null;
                try
                {
                    ds = new DatagramSocket();
                    Log.d("IVZ:", "Sending UDP message" );
                    /**/
                    DatagramPacket dp = new DatagramPacket(message.getBytes(), message.length(), address, 12345);
                    ds.setBroadcast(true);
                    ds.send(dp);
                }
                catch (Exception ex)
                {
                    Log.d("IVZ:", "Excetion at send udp message" );
                    ex.printStackTrace();
                }
                finally
                {
                    if (ds != null)
                    {
                        ds.close();
                    }
                }
                return null;
            }

            protected void onPostExecute(Void result)
            {
                super.onPostExecute(result);
            }
        };

        if (Build.VERSION.SDK_INT >= 11)
            asyncClient.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
        else
            asyncClient.execute();
    }

    @Override
    public String Recieve()
    {
        return  null;
    }

}
