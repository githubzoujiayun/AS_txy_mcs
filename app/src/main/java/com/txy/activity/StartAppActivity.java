package com.txy.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;

import com.android.volley.VolleyError;
import com.txy.constants.Constants;
import com.txy.database.BoardRoomDB;
import com.txy.database.DBManager;
import com.txy.database.MyMusic;
import com.txy.database.httpdata.BoardRoom;
import com.txy.database.httpdata.BoardRoomMachineCode;
import com.txy.database.httpdata.MachineCode;
import com.txy.gson.GsonUtils;
import com.txy.txy_mcs.R;
import com.txy.utils.SPUtils;
import com.txy.utils.ToastUtils;
import com.txy.volley.HttpUtils;
import com.txy.volley.VolleyListener;

import java.util.ArrayList;
import java.util.List;

public class StartAppActivity extends Activity implements View.OnClickListener {

    private EditText edtText_ipSet;
    private EditText edtText_portSet;
    private AlertDialog mIpSetDialog;

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
            getData();
            getMachineCode();
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
        overridePendingTransition(R.anim.right_to_left_in, R.anim.right_to_left_out);
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
        StartAppActivity.this.finish();
    }

    private void submitButton() {
        if (edtText_ipSet.getText().toString().equals("") ) {
            ToastUtils.showShort(StartAppActivity.this, "请输入服务器IP地址!");
            return;
        }

        if (edtText_portSet.getText().toString().equals("")) {
            ToastUtils.showShort(StartAppActivity.this, "请输入端口号!");
            return;
        }

        String ip = edtText_ipSet.getText().toString();
        String port = edtText_portSet.getText().toString();
        SPUtils.put(StartAppActivity.this, Constants.SERVERIP, ip);
        SPUtils.put(StartAppActivity.this, Constants.SERVERPORT, port);
        ToastUtils.showShort(StartAppActivity.this, "设置成功!");

        mIpSetDialog.dismiss();

        getData();
        getMachineCode();
        SPUtils.put(StartAppActivity.this, "isFirstTime", false);
    }

    public void getData(){
        String ip = (String) SPUtils.get(this, Constants.SERVERIP, Constants.DEFAULT_SERVER_IP);
        String port = (String) SPUtils.get(this, Constants.SERVERPORT, Constants.DEFAULT_SERVER_PORT);
        String url = "http://" + ip + ":" + port + Constants.URL.INIT_DATA;
        HttpUtils.get(this, url, new VolleyListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Log.e("onErrorResponse", "------读取会议列表出错----------");
            }

            @Override
            public void onResponse(String response) {
                BoardRoom boardRoom = GsonUtils.parseJSON(response, BoardRoom.class);
                int id = boardRoom.getVersion();
                int httpVersion = (int) SPUtils.get(StartAppActivity.this, "HttpVersion", -1);
                SPUtils.put(StartAppActivity.this, "HttpVersion", id);
                BoardRoomDB.deleteBoardRoomList();
                BoardRoomDB.deleteLight();
                BoardRoomDB.deleteModel();
                BoardRoomDB.deleteCurtain();
                BoardRoomDB.deleteAir();
                BoardRoomDB.deleteProjector();
                BoardRoomDB.deleteTv();
                BoardRoomDB.saveBoardRoom(boardRoom.getBoardRoom());
                if (httpVersion != id) {

                }
            }

        });
    }

    private void delayGoToIndextActivity() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                go2indext();
            }

        }, Constants.STARTAPP_DELAY);
    }

    private void getMachineCode() {
        String ip = (String) SPUtils.get(StartAppActivity.this, Constants.SERVERIP, Constants.DEFAULT_SERVER_IP);
        String port = (String) SPUtils.get(StartAppActivity.this, Constants.SERVERPORT, Constants.DEFAULT_SERVER_PORT);
        String url = "http://" + ip + ":" + port + Constants.URL.INIT_CODE;
        HttpUtils.get(StartAppActivity.this, url, new VolleyListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                delayGoToIndextActivity();
                Log.e("onErrorResponse", "---------读取机器代码错误-----------");

            }

            @Override
            public void onResponse(String response) {

                BoardRoomMachineCode boardRoomMachineCode = GsonUtils.parseJSON(response, BoardRoomMachineCode.class);
                List<MachineCode> machineCode = boardRoomMachineCode.getMachineCode();
                BoardRoomDB.deleteMachineCode();
                BoardRoomDB.saveMachineCode(machineCode);
                delayGoToIndextActivity();
            }
        });
    }
}
