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
import com.txy.database.BoardRoomDB;
import com.txy.database.DBManager;
import com.txy.database.RoomList;
import com.txy.database.httpdata.BoardRoomEntity;
import com.txy.fragment.HomeSetFragment;
import com.txy.fragment.SetModeFragment;
import com.txy.fragment.SetEquipmentFragment;
import com.txy.fragment.SetIPFragment;
import com.txy.fragment.SetNameFragment;
import com.txy.fragment.SetProjectionTimeFragment;
import com.txy.fragment.SettingFragment;
import com.txy.txy_mcs.R;
import com.txy.utils.SPUtils;
import com.txy.utils.ToastUtils;

public class SetActivity extends FragmentActivity implements OnClickListener, OnItemClickListener, OnLongClickListener {

    private ListView mListView;
    private RoomListAdapter mRoomListAdapter;
    private int mPosition;// 当前选中的房间
    private List<BoardRoomEntity> boardRoomList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set);
        initUI();
        initListener();

        getRoomList();
        initListView();
        initOperate();// 初始化操作的界面
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }


    /**
     * 初始化操作的界面
     */
    private void initOperate() {
        if (boardRoomList == null || boardRoomList.size() == 0) {
            return;
        }
        replaceFragment(new SettingFragment());
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
        boardRoomList = BoardRoomDB.getBoardRoomList();
    }

    /**
     * ListView的初始化
     * 先判断数据库中是否有数据
     */
    private void initListView() {
        mListView = (ListView) findViewById(R.id.listview_roomlist);
        mRoomListAdapter = new RoomListAdapter(this, boardRoomList, 0);
        mListView.setAdapter(mRoomListAdapter);

        mListView.setOnItemClickListener(this);
        mListView.setOnLongClickListener(this);
    }

    private void initListener() {
        findViewById(R.id.btn_back).setOnClickListener(this);
        findViewById(R.id.btn_saveset).setOnClickListener(this);
    }

    private void initUI() {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_back:// 返回按键
                finish();
                overridePendingTransition(R.anim.left_to_right_in, R.anim.left_to_right_out);
                break;

            case R.id.btn_saveset:// 保存
                // 如果可以返回，说明正在进行设置操作,就可以保存
                sendSaveOrder();
                ToastUtils.showLong(this, "保存成功");
                break;

            default:
                break;
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position,
                            long id) {
        mPosition = position;
        mRoomListAdapter.setPosition(position);
        mRoomListAdapter.notifyDataSetChanged();
        SettingFragment settingFragment = new SettingFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("position", position);
        settingFragment.setArguments(bundle);
        replaceFragment(settingFragment);

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            finish();
            overridePendingTransition(R.anim.left_to_right_in, R.anim.left_to_right_out);
            return true;
        }

        return super.onKeyDown(keyCode, event);
    }

    @Override
    public boolean onLongClick(View v) {
        return false;
    }


    /**
     * 发送广播跟Fragment通信
     */
    private void sendSaveOrder() {
        Intent intent = new Intent(Constants.BROADCAST.SAVESETTING);
        LocalBroadcastManager.getInstance(this).sendBroadcast(intent);
    }

}
