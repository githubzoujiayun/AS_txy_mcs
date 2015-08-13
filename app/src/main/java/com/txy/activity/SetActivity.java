package com.txy.activity;

import java.util.List;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.LocalBroadcastManager;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.txy.adapter.RoomListAdapter;
import com.txy.constants.Constants;
import com.txy.database.DBManager;
import com.txy.database.RoomList;
import com.txy.fragment.HomeSetFragment;
import com.txy.fragment.SetModeFragment;
import com.txy.fragment.SetEquipmentFragment;
import com.txy.fragment.SetIPFragment;
import com.txy.fragment.SetNameFragment;
import com.txy.fragment.SetProjectionTimeFragment;
import com.txy.txy_mcs.R;
import com.txy.utils.SPUtils;
import com.txy.utils.ToastUtils;

public class SetActivity extends FragmentActivity implements OnClickListener, OnItemClickListener, OnLongClickListener {

    private EditText mEtRoomName;
    private EditText mEtLightNum;
    private EditText mEtWindowNum;
    private EditText mEtAirNum;
    private EditText mEtProjectionNum;
    private EditText mEtTvNum;
    private EditText mEtSoundNum;
    private AlertDialog mAddDialog;
    private ListView mListView;
    private List<RoomList> mRoomListData;
    private RoomListAdapter mRoomListAdapter;
    private int mPosition;// 当前选中的房间
    private int mNowSetButton;// 当前设置的按钮
    private boolean canRetrue = false;
    private MyBrocast receiver = new MyBrocast();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set);
        initUI();
        initListener();

        getRoomList();
        initListView();
        initOprate();// 初始化操作的界面
    }

    @Override
    protected void onResume() {
        super.onResume();
        IntentFilter intentFilter = new IntentFilter(Constants.BROADCAST.SETFRAGMENT2SET);
        LocalBroadcastManager.getInstance(this).registerReceiver(receiver, intentFilter);
    }

    @Override
    protected void onPause() {
        super.onPause();
        LocalBroadcastManager.getInstance(this).unregisterReceiver(receiver);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event){
        if (keyCode == KeyEvent.KEYCODE_BACK ){
            canReturn();
        }
        return false;
    }

    /**
     * 判断是否可以返回
     */
    private boolean canReturn() {
        if (canRetrue){
            replaceFragment(new HomeSetFragment());
            canRetrue = false;
            return true;
        }
        return false;
    }

    /**
     * 初始化操作的界面
     */
    private void initOprate() {
        if(mRoomListData != null && mRoomListData.size() > 0 ){
            HomeSetFragment setFragment = new HomeSetFragment();
            replaceFragment(setFragment);
        }
    }

    /**
     * 替换片段
     *
     * @param fragment
     */
    @SuppressLint("Recycle")
    private void replaceFragment(Fragment fragment) {
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.replace(R.id.framelayout, fragment);
        ft.commit();
    }

    /**
     * 获取房间列表
     */
    private void getRoomList() {
        mRoomListData = DBManager.getAllRoomList();
    }

    /**
     * ListView的初始化
     * 先判断数据库中是否有数据
     */
    private void initListView() {
        mListView = (ListView) findViewById(R.id.listview_roomlist);
        mRoomListAdapter = new RoomListAdapter(this, mRoomListData, 0);
        mListView.setAdapter(mRoomListAdapter);

        mListView.setOnItemClickListener(this);
        mListView.setOnLongClickListener(this);
    }

    private void initListener() {
        findViewById(R.id.btn_back).setOnClickListener(this);
        findViewById(R.id.btn_addroom).setOnClickListener(this);
        findViewById(R.id.btn_saveset).setOnClickListener(this);
    }

    private void initUI() {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_back:// 返回按键
                if (!canReturn()){
                    finish();
                }
                break;

            case R.id.btn_saveset:// 保存
                // 如果可以返回，说明正在进行设置操作,就可以保存
                if (canRetrue){
                    sendSaveOrder();
                    ToastUtils.showLong(this, "保存成功");
                }
                break;

            case R.id.btn_addroom:// 添加房间按键
                showAddDialog();
                break;
            case R.id.btn_sure:// Dialog确定按钮
                if (judge()){
                    save2DB();
                }
                break;
            case R.id.btn_cancel:// Dialog取消
                mAddDialog.dismiss();
                break;

            default:
                break;
        }
    }

    /**
     * 保存到数据库
     */
    private void save2DB() {
        String roomName = mEtRoomName.getText().toString();
        String lightNum = mEtLightNum.getText().toString();
        String windowNum = mEtWindowNum.getText().toString();
        String airNum = mEtAirNum.getText().toString();
        String projectionNum = mEtProjectionNum.getText().toString();
        String tvNum = mEtTvNum.getText().toString();
        String soundNum = mEtSoundNum.getText().toString();

        int lightnumber = Integer.parseInt(lightNum);

        // 创建一个房间
        int roomId = (Integer) SPUtils.get(this, "roomid", 0);
        roomId += 1;
        RoomList roomList = new RoomList();
        DBManager.saveRoomList(roomList);
        mRoomListAdapter.addOneRoomList(roomList);
        mRoomListAdapter.notifyDataSetChanged();
        SPUtils.put(this, "roomid", roomId);

        //创建Light模式表
        saveLight(lightnumber, roomId);

        mAddDialog.dismiss();
        replaceFragment(new HomeSetFragment());
    }

    private void saveLight(int lightnumber, int roomId) {
    }

    /**
     * 判断输入的参数是否合法
     * @return
     */
    private boolean judge() {
        if ((mEtRoomName.getText().toString() == null)
                || (mEtRoomName.getText().toString().equalsIgnoreCase(""))) {
            ToastUtils.showShort(this, "请输入区域名称！");
            return false;
        } else if ((mEtLightNum.getText().toString() == null)
                || (mEtLightNum.getText().toString().equalsIgnoreCase(""))) {
            ToastUtils.showShort(this, "请输入灯光数量！");
            return false;
        } else if (Integer.parseInt(mEtLightNum.getText().toString()) > 20) {
            ToastUtils.showShort(this, "灯光数量不能超过20盏！");
            return false;
        } else if ((mEtWindowNum.getText().toString() == null)
                || (mEtWindowNum.getText().toString().equalsIgnoreCase(""))) {
            ToastUtils.showShort(this, "请输入窗帘数量！");
            return false;
        } else if (Integer.parseInt(mEtWindowNum.getText().toString()) > 8) {
            ToastUtils.showShort(this, "窗帘数量不能超过8！");
            return false;
        } else if ((mEtAirNum.getText().toString() == null)
                || (mEtAirNum.getText().toString().equalsIgnoreCase(""))) {
            ToastUtils.showShort(this, "请输入空调数量！");
            return false;
        } else if (Integer.parseInt(mEtAirNum.getText().toString()) > 8) {
            ToastUtils.showShort(this, "空调数量不能超过6！");
            return false;
        } else if ((mEtProjectionNum.getText().toString() == null)
                || (mEtProjectionNum.getText().toString().equalsIgnoreCase(""))) {
            ToastUtils.showShort(this, "请输入投影数量！");
            return false;
        } else if (Integer.parseInt(mEtProjectionNum.getText().toString()) > 6) {
            ToastUtils.showShort(this, "投影数量不能超过6！");
            return false;
        } else if ((mEtTvNum.getText().toString() == null)
                || (mEtTvNum.getText().toString().equalsIgnoreCase(""))) {
            ToastUtils.showShort(this, "请输入电视机数量！");
            return false;
        } else if (Integer.parseInt(mEtTvNum.getText().toString()) > 6) {
            ToastUtils.showShort(this, "投影电视机不能超过6！");
            return false;
        } else if ((mEtSoundNum.getText().toString() == null)
                || (mEtSoundNum.getText().toString().equalsIgnoreCase(""))) {
            ToastUtils.showShort(this, "请输入音响数量！");
            return false;
        } else if (Integer.parseInt(mEtSoundNum.getText().toString()) > 1) {
            ToastUtils.showShort(this, "音响不能超过1！");
            return false;
        }
        return true;
    }

    /**
     * 显示添加房间的对话框
     */
    private void showAddDialog() {

        LayoutInflater inflater = LayoutInflater.from(this);
        LinearLayout addroomlayout = (LinearLayout) inflater.inflate(R.layout.dialog_addroom, null);

        mAddDialog = new AlertDialog.Builder(this).create();
        mAddDialog.show();
        mAddDialog.getWindow().setContentView(addroomlayout);
        mAddDialog.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM);

        mEtRoomName = (EditText) addroomlayout.findViewById(R.id.et_roomname);
        mEtLightNum = (EditText) addroomlayout.findViewById(R.id.et_lightnum);
        mEtWindowNum = (EditText) addroomlayout.findViewById(R.id.et_windowsnum);
        mEtAirNum = (EditText) addroomlayout.findViewById(R.id.et_kgnum);
        mEtProjectionNum = (EditText) addroomlayout.findViewById(R.id.et_tynum);
        mEtTvNum = (EditText) addroomlayout.findViewById(R.id.et_tvnum);
        mEtSoundNum = (EditText) addroomlayout.findViewById(R.id.et_soundnum);

        addroomlayout.findViewById(R.id.btn_sure).setOnClickListener(this);
        addroomlayout.findViewById(R.id.btn_cancel).setOnClickListener(this);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position,
                            long id) {
        canRetrue = false;
        mPosition = position;
//		int roomid = mRoomListData.get(position).roomid;
        mRoomListAdapter.setPosition(position);
        mRoomListAdapter.notifyDataSetChanged();
        replaceFragment(new HomeSetFragment());

    }

    @Override
    public boolean onLongClick(View v) {
        // TODO Auto-generated method stub
        return false;
    }

    class MyBrocast extends BroadcastReceiver{

        @Override
        public void onReceive(Context context, Intent intent) {
            canRetrue = true;
            mNowSetButton = intent.getIntExtra("position", 0);
            switch (mNowSetButton) {
                case 0:
                case 1:
                case 2:
                case 3:
                    go2SetMode();
                    break;
                case 4:
                    replaceFragment(new SetIPFragment());
                    break;
                case 5:
                    replaceFragment(new SetEquipmentFragment());
                    break;
                case 6:
                    replaceFragment(new SetNameFragment());
                    break;
                case 7:
                    replaceFragment(new SetProjectionTimeFragment());
                    break;
                default:
                    break;
            }

        }


    }

    private void go2SetMode() {
        SetModeFragment modeSetFragment = new SetModeFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("roomid", mRoomListData.get(mPosition).roomid);
        bundle.putInt("mode", mNowSetButton);
        modeSetFragment.setArguments(bundle);
        replaceFragment(modeSetFragment);
    }

    /**
     * 发送广播跟Fragment通信
     */
    private void sendSaveOrder() {
        Intent intent = new Intent(Constants.BROADCAST.SAVESETTING);
        intent.putExtra("button", mNowSetButton);
        LocalBroadcastManager.getInstance(this).sendBroadcast(intent);
    }

}
