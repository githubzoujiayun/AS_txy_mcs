package com.txy.fragment;

import java.util.ArrayList;
import java.util.List;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.txy.adapter.SetCheckBoxAdapter;
import com.txy.constants.Constants;
import com.txy.database.DBManager;
import com.txy.database.RoomList;
import com.txy.txy_mcs.R;

/**
 * A simple {@link android.support.v4.app.Fragment} subclass.
 *
 */
public class SetModeFragment extends Fragment implements OnClickListener {

    private LinearLayout mDaySetModeButton;
    private LinearLayout mNightSetModeButton;
    private LinearLayout mDaySetUI;
    private LinearLayout mNightSetUI;
    private GridView mDayLightGrid;
    private GridView mNightLightGrid;
    private ArrayList<Boolean> DayLightStatus = new ArrayList<Boolean>();
    private ArrayList<Boolean> NightLightStatus = new ArrayList<Boolean>();
    private ArrayList<String> DayLightName;
    private ArrayList<String> NightLightName;
    private SetCheckBoxAdapter mDayLightAdapter;
    private SetCheckBoxAdapter mNightLightAdapter;
    private int mRoomId;// 当前房间的ID
    private RoomList mRoomList;// 房间的信息
    private BroadcastReceiver receive;
    private int mode;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View layout = inflater.inflate(R.layout.fragment_mode_set, null);
        initData();
        initUI(layout);
        initListener();
        initStates();
        initLightGridView(layout);
        return layout;
    }

    @Override
    public void onResume() {
        super.onResume();
        receive = new MyBrocastReceive();
        IntentFilter intentfilter = new IntentFilter(
                Constants.BROADCAST.SAVESETTING);
        LocalBroadcastManager.getInstance(getActivity()).registerReceiver(
                receive, intentfilter);
    }

    @Override
    public void onPause() {
        super.onPause();
        LocalBroadcastManager.getInstance(getActivity()).unregisterReceiver(
                receive);
    }

    private void initData() {
        Bundle bundle = getArguments();
        mRoomId = bundle.getInt("roomid");
        mode = bundle.getInt("mode");
    }

    private void initLightGridView(View layout) {
        mDayLightGrid = (GridView) layout.findViewById(R.id.gd_btlightset);
        mNightLightGrid = (GridView) layout.findViewById(R.id.gd_lightset);
        mDayLightAdapter = new SetCheckBoxAdapter(getActivity(),
                DayLightStatus, DayLightName);
        mNightLightAdapter = new SetCheckBoxAdapter(getActivity(),
                NightLightStatus, NightLightName);
        if (mRoomList != null) {
            mDayLightAdapter.setNum(mRoomList.lightNum);
            mNightLightAdapter.setNum(mRoomList.lightNum);
        }
        mDayLightGrid.setAdapter(mDayLightAdapter);
        mNightLightGrid.setAdapter(mNightLightAdapter);
    }

    /**
     * 初始化界面的状态
     */
    private void initStates() {
        mNightSetUI.setVisibility(View.INVISIBLE);
        mRoomList = DBManager.getRoom("" + mRoomId);
    }

    private void initListener() {
        mDaySetModeButton.setOnClickListener(this);
        mNightSetModeButton.setOnClickListener(this);
    }

    private void initUI(View layout) {
        mDaySetModeButton = (LinearLayout) layout.findViewById(R.id.ll_bttitle);
        mNightSetModeButton = (LinearLayout) layout
                .findViewById(R.id.ll_ywtitle);
        mNightSetUI = (LinearLayout) layout.findViewById(R.id.ll_ywset);
        mDaySetUI = (LinearLayout) layout.findViewById(R.id.ll_btset);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_bttitle:
                mDaySetUI.setVisibility(View.VISIBLE);
                mNightSetUI.setVisibility(View.GONE);
                break;
            case R.id.ll_ywtitle:
                mDaySetUI.setVisibility(View.GONE);
                mNightSetUI.setVisibility(View.VISIBLE);
                break;

            default:
                break;
        }
    }

    /**
     * 保存数据的广播接收
     *
     * @author Clearlove
     *
     */
    class MyBrocastReceive extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            int button = intent.getIntExtra("button", 0);
            switch (button) {
                case 0:// 节能模式设置保存
                    saveLight(button);

                    break;
                case 1:// 会议模式设置保存
                    saveLight(button);

                    break;
                case 2:// 投影模式设置保存
                    saveLight(button);

                    break;
                case 3:// 展示模式设置保存
                    saveLight(button);

                    break;

                default:
                    break;
            }
        }

    }

    /**
     * 白天跟黑夜模式灯状态的保存
     *
     * @param button
     */
    private void saveLight(int button) {
        List<Boolean> getDayStatus = getGdValue(mDayLightGrid);
        List<Boolean> getNightStatus = getGdValue(mNightLightGrid);
    }

    /**
     * 从gridview中遍历所有子item的值
     * @param gd
     * @return
     */
    private List<Boolean> getGdValue(GridView gd) {

        List<Boolean> getDayStatus = new ArrayList<Boolean>();
        for (int i = 0; i < gd.getChildCount(); i++) {
            RelativeLayout layout = (RelativeLayout) gd.getChildAt(i);// 获得子item的layout
            CheckBox cb = (CheckBox) layout.findViewById(R.id.cb_setitem);// 从layout中获得控件,根据其id
            if (cb.isChecked()) {
                getDayStatus.add(true);
            } else {
                getDayStatus.add(false);
            }
        }
        return getDayStatus;

    }
}
