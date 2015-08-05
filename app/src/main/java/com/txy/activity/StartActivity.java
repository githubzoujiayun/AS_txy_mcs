package com.txy.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.Window;

import com.txy.constants.Constants;
import com.txy.txy_mcs.R;
import com.txy.util.SPUtils;

public class StartActivity extends Activity {

    private static final long DISPLAY_LENGHT = 3000;// 延时3秒

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
        boolean isFirstTime = (Boolean) SPUtils.get(this, "isFirstTime", false);
        if (isFirstTime){
            SPUtils.put(this, Constants.IP, Constants.IP);
            SPUtils.put(this, "Constants.SENDPORT", Constants.SENDPORT);
        }
        new Handler().postDelayed(new Runnable(){

            @Override
            public void run() {
                Intent intent = new Intent(StartActivity.this,IndexActivity.class);
                startActivity(intent );
                StartActivity.this.finish();
            }

        },DISPLAY_LENGHT);
    }


}
