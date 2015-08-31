package com.txy.fragment;


import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.txy.constants.Constants;
import com.txy.database.BoardRoomDB;
import com.txy.database.httpdata.MachineCode;
import com.txy.txy_mcs.R;
import com.txy.utils.SPUtils;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class SettingFragment extends Fragment {


    private EditText mRoomIpEdt;
    private EditText mRoomPortEdt;
    private EditText mServerIpEdt;
    private EditText mServerPortEdt;
    private int position;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View layout = inflater.inflate(R.layout.fragment_setting, container, false);
        initUI(layout);
        getData();
        return layout;
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    private void getData() {
        Bundle arguments = getArguments();
        if (arguments != null) {
            position = arguments.getInt("position");
        } else {
            position = 0;
        }

    }


    private void initUI(View layout) {

        mRoomIpEdt = (EditText) layout.findViewById(R.id.roomIPEdt);
        mRoomPortEdt = (EditText) layout.findViewById(R.id.roomPortEdt);
        mServerIpEdt = (EditText) layout.findViewById(R.id.serveIPEdt);
        mServerPortEdt = (EditText) layout.findViewById(R.id.servePortEdt);
    }

    private void save(List<MachineCode> machineCodeList) {

        String ip = mRoomIpEdt.getText().toString();
        String port = mRoomPortEdt.getText().toString();

        String serverIp = mServerIpEdt.getText().toString();
        String serverPort = mServerPortEdt.getText().toString();

        MachineCode machineCode = machineCodeList.get(position);
        machineCode.setIp(ip);
        BoardRoomDB.saveOneMachineIp(machineCode);

        SPUtils.put(getActivity(), Constants.IP, ip);
        SPUtils.put(getActivity(), Constants.SENDPORT, port);
        SPUtils.put(getActivity(), Constants.SERVERIP, serverIp);
        SPUtils.put(getActivity(), Constants.SERVERPORT, serverPort);
    }

    class MyBroadCastReceive extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            List<MachineCode> machineCodeList = BoardRoomDB.getMachineCodeList();
            if (machineCodeList == null || machineCodeList.size() == 0) {
                return;
            }
            save(machineCodeList);

        }
    }

}
