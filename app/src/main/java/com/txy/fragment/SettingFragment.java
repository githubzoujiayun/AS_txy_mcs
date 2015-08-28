package com.txy.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.txy.constants.Constants;
import com.txy.txy_mcs.R;
import com.txy.utils.SPUtils;

/**
 * A simple {@link Fragment} subclass.
 */
public class SettingFragment extends Fragment {


    private EditText mRoomIpEdt;
    private EditText mRoomPortEdt;
    private EditText mServerIpEdt;
    private EditText mServerPortEdt;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View layout = inflater.inflate(R.layout.fragment_setting, container, false);
        initUI(layout);
        initEdt();
        return layout;
    }

    private void initEdt() {
        String ip = (String) SPUtils.get(getActivity(), Constants.IP, Constants.DEFAULT_IP);
        int port = (int) SPUtils.get(getActivity(), Constants.SENDPORT, Constants.DEFAULT_SENDPORT);

        String serverIp = (String) SPUtils.get(getActivity(), Constants.SERVERIP, Constants.DEFAULT_SERVER_IP);
        String serverPort = (String) SPUtils.get(getActivity(), Constants.SERVERPORT, Constants.DEFAULT_SERVER_PORT);

        mRoomIpEdt.setText(ip);
        mRoomPortEdt.setText(""+port);
        mServerIpEdt.setText(serverIp);
        mServerPortEdt.setText(""+serverPort);

    }

    private void initUI(View layout) {

        mRoomIpEdt = (EditText) layout.findViewById(R.id.roomIPEdt);
        mRoomPortEdt = (EditText) layout.findViewById(R.id.roomPortEdt);
        mServerIpEdt = (EditText) layout.findViewById(R.id.serveIPEdt);
        mServerPortEdt = (EditText) layout.findViewById(R.id.servePortEdt);
    }

    private void sava() {
        String ip = mRoomIpEdt.getText().toString();
        String port = mRoomPortEdt.getText().toString();

        String serverIp = mServerIpEdt.getText().toString();
        String serverPort = mServerPortEdt.getText().toString();

        SPUtils.put(getActivity(), Constants.IP, ip);
        SPUtils.put(getActivity(), Constants.SENDPORT, port);
        SPUtils.put(getActivity(), Constants.SERVERIP, serverIp);
        SPUtils.put(getActivity(), Constants.SERVERPORT, serverPort);
    }


}
