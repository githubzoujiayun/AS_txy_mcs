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

import com.android.volley.VolleyError;
import com.txy.constants.Constants;
import com.txy.database.DBManager;
import com.txy.database.MyMusic;
import com.txy.database.RoomList;
import com.txy.gson.GsonUtils;
import com.txy.txy_mcs.R;
import com.txy.udp.InitData.StringMerge;
import com.txy.udp.Sender;
import com.txy.utils.SPUtils;
import com.txy.utils.ToastUtils;
import com.txy.volley.HttpUtils;
import com.txy.volley.VolleyListener;

import java.util.ArrayList;
import java.util.List;

public class StartActivity extends Activity implements View.OnClickListener {

    private EditText edtText_ipSet;
    private EditText edtText_portSet;
    private AlertDialog mIpSetDialog;
    private List<RoomList> mRoomList = new ArrayList<RoomList>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);


        boolean isFirstTime = (Boolean) SPUtils.get(this, "isFirstTime", true);
        if (isFirstTime){
            show();
            createMusicDB();
        } else {
            new Handler().postDelayed(new Runnable(){
                @Override
                public void run() {
                    go2indext();
                }

            },Constants.STARTAPP_DELAY);
        }

    }

    /**
     * 建一个数据库
     */
    private void createMusicDB() {
        MyMusic myMusic = new MyMusic("txyPark", "txyPark", "txyPark", "txyPark", 0);
        myMusic.setMode(100);
        List<MyMusic> musicList = new ArrayList<MyMusic>();
        musicList.add(myMusic);
        DBManager.saveMusicList(musicList);
    }

    private void go2indext() {
        Intent intent = new Intent(this, IndexActivity.class);
        startActivity(intent );
        this.finish();
    }

    /**
     * IP、Port的设置Dialog
     */
    private void show(){

        LayoutInflater inflater = LayoutInflater.from(this);
        LinearLayout ipSetLayout = (LinearLayout) inflater.inflate(R.layout.dialog_ipset, null);

        // 2. 新建对话框对象
        mIpSetDialog = new AlertDialog.Builder(this).create();
        mIpSetDialog.show();
        mIpSetDialog.getWindow().setContentView(ipSetLayout);
        mIpSetDialog.getWindow().clearFlags( WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM);

        edtText_ipSet = (EditText) ipSetLayout.findViewById(R.id.edtText_ipset);
        edtText_portSet = (EditText) ipSetLayout.findViewById(R.id.edtText_portset);

        ImageButton imgBtn_ipSubmit = (ImageButton) ipSetLayout.findViewById(R.id.imgBtn_ipsubmit);
        ImageButton imgBtn_ipCancel = (ImageButton) ipSetLayout.findViewById(R.id.imgBtn_ipcancel);

        imgBtn_ipSubmit.setOnClickListener(this);
        imgBtn_ipCancel.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.imgBtn_ipsubmit:
                submitButton();
                break;
            case R.id.imgBtn_ipcancel:
                cancelButton();
                break;
        }
    }

    private void cancelButton() {
        mIpSetDialog.dismiss();
        StartActivity.this.finish();
    }

    private void submitButton() {
        if (edtText_ipSet.getText().equals("") || edtText_ipSet.getText() == null) {
            ToastUtils.showShort(StartActivity.this, "请输入服务器IP地址!");
            return;
        }

        if (edtText_portSet.getText().equals("") || edtText_portSet.getText() == null) {
            ToastUtils.showShort(StartActivity.this, "请输入端口号!");
            return;
        }

        String ip = edtText_ipSet.getText().toString();
        String port = edtText_portSet.getText().toString();
        SPUtils.put(StartActivity.this, ip, Constants.DEFAULT_SERVER_IP);
        SPUtils.put(StartActivity.this, port, Constants.DEFAULT_SERVER_PORT);
        ToastUtils.showShort(StartActivity.this, "设置成功!");

        mIpSetDialog.dismiss();

        getData();
        SPUtils.put(StartActivity.this,"isFirstTime",false);
    }

    public void getData(){
        HttpUtils.get(this, Constants.URL.INIT_DATA, new VolleyListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                go2indext();
            }

            @Override
            public void onResponse(String response) {
                mRoomList.clear();
                mRoomList = (List<RoomList>) GsonUtils.parseJSON(response,RoomList.class);
                if (mRoomList != null) {
                    DBManager.deleteAllRoonList();// 删除所有的房间信息
                    int size = mRoomList.size();
                    for (int i = 0;i < size;i++) {
                        DBManager.saveRoomList(mRoomList.get(i));
                    }
                }
                go2indext();
            }
        });
    }
}
