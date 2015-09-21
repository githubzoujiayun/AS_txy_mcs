package com.txy.fragment;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

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

import com.txy.SPdata;
import com.txy.adapter.LightGridAdapter;
import com.txy.constants.Constants;
import com.txy.database.BoardRoomDB;
import com.txy.database.httpdata.BoardRoomEntity;
import com.txy.database.httpdata.LightEntity;
import com.txy.database.httpdata.MachineCode;
import com.txy.txy_mcs.R;
import com.txy.udp.InitData.ByteMerge;
import com.txy.udp.InitData.StringMerge;
import com.txy.udp.Sender;
import com.txy.utils.BytesUtils;
import com.txy.utils.ParseUtil;
import com.txy.utils.SPUtils;

/**
 * A simple {@link android.support.v4.app.Fragment} subclass.
 *
 */
public class LightControlFragment extends Fragment {

    private GridView mGridView;
    private ArrayList<Boolean> mLightStatus = new ArrayList<Boolean>();// 灯的状态
    private int mLightNum;// 灯的數量
    private LightGridAdapter mLightGridAdapter;
    private BroadcastReceiver receive;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View layout = inflater.inflate(R.layout.fragment_light_control, null);

        initGridView(layout);
        initLightNum();
        return layout;
    }

    private void initLightNum() {
        List<MachineCode> machineCodeList = BoardRoomDB.getMachineCodeList();
        int selectBoardRoomPosition = SPdata.readSelectBoardRoomPosition(getActivity());
        List<LightEntity> light = BoardRoomDB.getLight(machineCodeList.get(selectBoardRoomPosition).getTypeId());
        mLightGridAdapter.setLightList(light);
        mLightGridAdapter.notifyDataSetChanged();
    }

    @Override
    public void onStart() {
        super.onStart();
        receive = new UpdateLightStatusReceive();
        IntentFilter filter = new IntentFilter("txPark.updateEquipStatus");
        LocalBroadcastManager.getInstance(getActivity()).registerReceiver(receive, filter);
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
            getAllEquipStatus();
        }

    }


    /**
     * 初始化GridView
     * @param layout
     */
    private void initGridView(View layout) {

        mGridView = (GridView) layout.findViewById(R.id.gridView);
        mLightGridAdapter = new LightGridAdapter(getActivity(), mLightStatus);
        mGridView.setAdapter(mLightGridAdapter);

    }

    /**
     * 发送命令去获取所有设备的状态
     */
    private void getAllEquipStatus() {
        String allEquipStatus = StringMerge.getAllEquipMentStatus(getActivity());
        String ip = (String) SPUtils.get(getActivity(), Constants.IP, Constants.DEFAULT_IP);
        int port =(Integer) SPUtils.get(getActivity(), Constants.SENDPORT, Constants.DEFAULT_SENDPORT);
        new Sender(allEquipStatus,ip,port).send();
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
            String substring = equipStatus.substring(75, 89);
            String s = substring.replaceAll(" ", "");
//            String s = "b000000000";
            String array[] = new String[5];
            for (int j = 0; j < 5; j++) {
                array[j] = s.substring(2 * j, 2 * j + 2);
                for (int i = 0; i < array[j].length(); i++) {
                    byte[] bytes = array[j].getBytes();
                    mLightStatus.addAll(ByteMerge.parseByteToBit(bytes[i]));
                }
            }
            updateLightStatus();
        }
    }
}
