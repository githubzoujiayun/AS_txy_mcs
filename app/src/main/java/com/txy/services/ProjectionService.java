package com.txy.services;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;

public class ProjectionService extends Service {

    private int mPowerOnTime; // 开机时间
    private int mPowerOffTime;// 关机时间
    private boolean isPowerOnRunning = false;// 开机是否在计时
    private boolean isPowerOffRunning = false;// 关机是否计时

    @Override
    public IBinder onBind(Intent intent) {
        return new ProjectionBinder();
    }

    class ProjectionBinder extends Binder {

        public ProjectionService getProjectionService(){
            return ProjectionService.this;
        }
    }

    public int getPowerOnTime(){
        return mPowerOnTime;
    }

    public int getPowerOffTime(){
        return mPowerOffTime;
    }

    public void cleanPowerOnTime(){
        mPowerOnTime = 0;
    }

    public void cleanPowerOffTime() {
        mPowerOffTime = 0;
    }

    /**
     * 开始计时
     */
    public void powerOnStart(){
        if (!isPowerOnRunning) {
            isPowerOnRunning = true;
            Thread thread = new Thread(new Runnable() {

                @Override
                public void run() {
                    while(isPowerOnRunning){
                        try {
                            Thread.sleep(1000);
                            mPowerOnTime ++;
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
            });
            thread.start();
        }
    }

    public void powerOnStop(){
        isPowerOnRunning = false;
    }

    public void powerOffStart() {
        if (!isPowerOffRunning) {
            isPowerOffRunning = true;
            new Thread(new Runnable() {

                @Override
                public void run() {
                    while(isPowerOffRunning) {
                        try {
                            Thread.sleep(1000);
                            mPowerOffTime ++;
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }).start();
        }
    }

    public void powerOffStop() {
        isPowerOffRunning = false;
    }


}
