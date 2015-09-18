package com.txy.application;

import com.activeandroid.ActiveAndroid;
import com.txy.constants.Constants;
import com.txy.utils.SPUtils;

import android.app.Application;

public class MyApplication extends Application{


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

}
