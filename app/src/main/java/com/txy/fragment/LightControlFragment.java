package com.txy.fragment;

import java.util.ArrayList;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.LocalBroadcastManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

import com.txy.adapter.LightGridAdapter;
import com.txy.txy_mcs.R;
import com.txy.udp.InitData.ByteMerge;
import com.txy.utils.SPUtils;

/**
 * A simple {@link android.support.v4.app.Fragment} subclass.
 *
 */
public class LightControlFragment extends Fragment {

    private GridView mGridView;
    private ArrayList<Boolean> mLightStatus = new ArrayList<Boolean>();// 灯的状态
    private ArrayList<String> mLightName;// 灯的名字
    private LightGridAdapter mLightGridAdapter;
    private BroadcastReceiver receive;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View layout = inflater.inflate(R.layout.fragment_light_control, null);

        initGridView(layout);
        initParameter();
        return layout;
    }

    /**
     * 初始化参数
     */
    private void initParameter() {

        getEquipStatus();
        updateLightStatus();

    }

    @Override
    public void onStart() {
        super.onStart();
        receive = new UpdateLightStatusReceive();
        IntentFilter filter = new IntentFilter("txPark.updateEquipStatus");
        LocalBroadcastManager.getInstance(getActivity()).registerReceiver(receive,filter);
    }

    @Override
    public void onPause() {
        super.onPause();
        LocalBroadcastManager.getInstance(getActivity()).unregisterReceiver(receive);
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);

        if (isVisibleToUser) {
            getEquipStatus();
            updateLightStatus();
        }

    }


    /**
     * 初始化GridView
     * @param layout
     */
    private void initGridView(View layout) {

        mGridView = (GridView) layout.findViewById(R.id.gridView);
        mLightGridAdapter = new LightGridAdapter(getActivity(), mLightStatus, mLightName);
        mGridView.setAdapter(mLightGridAdapter);

    }


    public void getEquipStatus() {

        mLightStatus.clear();
        String equipStatus = (String) SPUtils.get(getActivity(), "equipStatus", null);
        if (equipStatus == null) {
            return;
        }

        byte[] bytes = equipStatus.getBytes();
        for (int i = 0; i < 5; i++) {
            mLightStatus.addAll(ByteMerge.parseByteToBit(bytes[i]));
        }

    }

    private void updateLightStatus() {

        mLightGridAdapter.setLightStatus(mLightStatus);
        mLightGridAdapter.notifyDataSetChanged();

    }

    class UpdateLightStatusReceive extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {

            mLightStatus.clear();
            String equipStatus = intent.getStringExtra("equipStatus");
            byte[] bytes = equipStatus.substring(48, 52).getBytes();
            for (int i = 0; i < 5; i++) {
                mLightStatus.addAll(ByteMerge.parseByteToBit(bytes[i]));
            }

            updateLightStatus();
        }
    }
}
