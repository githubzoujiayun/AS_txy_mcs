package com.txy.udp;

import com.txy.util.BytesUtils;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;

public class Sender implements Runnable {

    private String ip;
    private int port;
    private byte[] mSendData;

    public Sender(String msg, String ip, int port) {

        this.mSendData = BytesUtils.hexStringToBytes(msg);
        this.ip = ip;
        this.port = port;
    }

    public void send(){
        new Thread(this).start();
    }

    public synchronized void run() {

        DatagramSocket socket = null;
        try {
            socket = new DatagramSocket();
            DatagramPacket dp = new DatagramPacket(mSendData, mSendData.length,InetAddress.getByName(ip), port);
            socket.send(dp);
        } catch (SocketException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (socket != null) {
                socket.close();
            }
        }

    }

}
