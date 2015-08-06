package com.txy.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;

import com.txy.constants.Constants;
import com.txy.txy_mcs.R;
import com.txy.util.SPUtils;
import com.txy.util.ToastUtils;

public class StartActivity extends Activity {

    private EditText edtText_ipset;
    private EditText edtText_portset;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        boolean isFirstTime = (Boolean) SPUtils.get(this, "isFirstTime", true);
        if (isFirstTime){
            show();
        } else {
            new Handler().postDelayed(new Runnable(){
                @Override
                public void run() {
                    go2Indext();
                }

            },Constants.STARTAPP_DELAY);
        }

    }

    private void go2Indext() {
        Intent intent = new Intent(this,IndexActivity.class);
        startActivity(intent );
        this.finish();
    }

    private void show(){
        LayoutInflater inflater = LayoutInflater.from(this);
        LinearLayout ipsetlayout = (LinearLayout) inflater.inflate(R.layout.dialog_ipset, null);

        // 2. 新建对话框对象
        final AlertDialog ipsetdialog = new AlertDialog.Builder(this).create();
        // adddialog.setCancelable(false);
        ipsetdialog.show();
        ipsetdialog.getWindow().setContentView(ipsetlayout);
        ipsetdialog.getWindow().clearFlags( WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM);

        edtText_ipset = (EditText) ipsetlayout.findViewById(R.id.edtText_ipset);
        // 加入端口号的设置
        edtText_portset = (EditText) ipsetlayout.findViewById(R.id.edtText_portset);

        final String ip = (String) SPUtils.get(this, Constants.SERVERIP, Constants.DEFAULT_SERVER_IP);
        if (!ip.equalsIgnoreCase(Constants.DEFAULT_SERVER_IP)) {
            edtText_ipset.setText(ip);
        }
        final String port = (String) SPUtils.get(this, Constants.SERVERPORT, Constants.DEFAULT_SERVER_PORT);
        if (!port.equalsIgnoreCase(Constants.DEFAULT_SERVER_PORT)) {
            edtText_portset.setText(port);
        }

        ImageButton imgBtn_ipsubmit = (ImageButton) ipsetlayout.findViewById(R.id.imgBtn_ipsubmit);
        ImageButton imgBtn_ipcancel = (ImageButton) ipsetlayout.findViewById(R.id.imgBtn_ipcancel);

        imgBtn_ipsubmit.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {

                if (edtText_ipset.getText().equals("")) {
                    ToastUtils.showShort(StartActivity.this, "请输入服务器IP地址!");
                    return;
                }

                if (edtText_portset.getText().equals("")) {
                    ToastUtils.showShort(StartActivity.this, "请输入端口号!");
                    return;
                }
                String ip = edtText_ipset.getText().toString();
                String port = edtText_portset.getText().toString();
                SPUtils.put(StartActivity.this,ip,Constants.DEFAULT_SERVER_IP);
                SPUtils.put(StartActivity.this, port, Constants.DEFAULT_SERVER_PORT);
                ToastUtils.showShort(StartActivity.this, "设置成功!");

                ipsetdialog.dismiss();

                go2Indext();
                SPUtils.put(StartActivity.this,"isFirstTime",false);
            }
        });

        imgBtn_ipcancel.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                ipsetdialog.dismiss();
                StartActivity.this.finish();
            }
        });
    }

}
