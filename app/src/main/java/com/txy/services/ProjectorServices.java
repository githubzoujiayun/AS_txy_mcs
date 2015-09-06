package com.txy.services;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;

public class ProjectorServices extends Service {
    public ProjectorServices() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        return new ProjectorBinder();
    }

    public class ProjectorBinder extends Binder {

        public ProjectorServices getProjectorServices() {
            return ProjectorServices.this;
        }
    }



}
