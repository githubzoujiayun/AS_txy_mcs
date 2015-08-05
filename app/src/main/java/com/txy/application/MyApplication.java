package com.txy.application;

import com.activeandroid.ActiveAndroid;
import com.txy.constants.Constants;
import com.txy.util.SPUtils;

import android.app.Application;

public class MyApplication extends Application{

    private String ip;// IP号
    private int port;// 端口号

    @Override
    public void onCreate() {
        super.onCreate();
        ip = (String) SPUtils.get(getApplicationContext(), Constants.IP, Constants.IP);
        port = (Integer) SPUtils.get(getApplicationContext(), "Constants.SENDPORT", Constants.SENDPORT);
        ActiveAndroid.initialize(this);// Android ORM框架ActiceAndroid的初始化
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
        ActiveAndroid.dispose();
    }

    public String getIp() {
        return ip;
    }
    public void setIp(String ip) {
        this.ip = ip;
    }
    public int getPort() {
        return port;
    }
    public void setPort(int port) {
        this.port = port;
    }



}
