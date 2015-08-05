package com.txy.services;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.net.wifi.WifiManager;
import android.os.AsyncTask;
import android.os.Binder;
import android.os.IBinder;

import com.txy.constants.Constants;

public class ReceiverService extends Service {

    private byte[] data = new byte[32];
    private OnReceiveSuccessListener mOnReceiveSuccessListener;
    private ReceiveTask mReceiveTask;// 接收的线程
    private WifiManager.MulticastLock lock;

    @Override
    public IBinder onBind(Intent intent) {
        return new MyBinder();
    }

    public class MyBinder extends Binder{

        public ReceiverService getReceiveService(){
            WifiManager manager = (WifiManager) ReceiverService.this.getSystemService(Context.WIFI_SERVICE);
            if (lock == null) {
                lock = manager.createMulticastLock("mywifi");
            }
            return ReceiverService.this;
        }
    }

    public void startTask(){
        if (mReceiveTask == null) {
            mReceiveTask = new ReceiveTask();
            mReceiveTask.execute("");
        }
    }

    public interface OnReceiveSuccessListener{
        void onSuccessData(byte[] data);
    }

    public void setOnReceiveSuccessListener(OnReceiveSuccessListener listener){
        mOnReceiveSuccessListener = listener;
    }

    class ReceiveTask extends AsyncTask<String, Byte, Void>{

        @Override
        protected Void doInBackground(String... params) {
            while(true){
                lock.acquire();
                DatagramSocket socket = null;
                try {
                    socket = new DatagramSocket(Constants.RECEIVEPORT);
                    DatagramPacket dp = new DatagramPacket(data , data.length);
                    socket.receive(dp);
                    mOnReceiveSuccessListener.onSuccessData(data);
                } catch (SocketException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    lock.release();
                    if (socket != null) {
                        socket.close();
                    }
                }
            }
        }

    }

}
