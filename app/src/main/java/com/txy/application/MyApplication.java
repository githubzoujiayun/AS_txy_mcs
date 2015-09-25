package com.txy.application;

import com.activeandroid.ActiveAndroid;
import com.txy.constants.Constants;
import com.txy.utils.SPUtils;

import android.app.Application;

public class MyApplication extends Application{

    private int mNowRoomId;

    @Override
    public void onCreate() {
        super.onCreate();
        ActiveAndroid.initialize(this);// Android ORM框架ActiveAndroid的初始化
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
        ActiveAndroid.dispose();
    }

    public int getNowRoomId() {
        return mNowRoomId;
    }

    public void setNowRoomId(int mNowRoomId) {
        this.mNowRoomId = mNowRoomId;
    }

}
