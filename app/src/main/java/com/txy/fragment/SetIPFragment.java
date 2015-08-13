package com.txy.fragment;

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
import android.widget.EditText;

import com.txy.constants.Constants;
import com.txy.txy_mcs.R;
import com.txy.utils.SPUtils;

/**
 * A simple {@link android.support.v4.app.Fragment} subclass.
 *
 */
public class SetIPFragment extends Fragment {

    private EditText mEtIP;
    private EditText mEtPort;
    private EditText mEtMode1;
    private EditText mEtMode2;
    private EditText mEtMode3;
    private EditText mEtMode4;
    private BroadcastReceiver receive = new MyBrocastReceive();

    public SetIPFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View layout = inflater.inflate(R.layout.fragment_set_i, null);
        initUI(layout);
        initData();
        return layout;
    }

    @Override
    public void onResume() {
        super.onResume();
        IntentFilter intentfilte = new IntentFilter(Constants.BROADCAST.SAVESETTING);
        LocalBroadcastManager.getInstance(getActivity()).registerReceiver(receive , intentfilte);
    }

    @Override
    public void onPause() {
        super.onPause();
        LocalBroadcastManager.getInstance(getActivity()).unregisterReceiver(receive);
    }

    private void initData() {
        String ip = (String) SPUtils.get(getActivity(), Constants.IP, Constants.IP);
        int sendport = (Integer) SPUtils.get(getActivity(), "Constants.SENDPORT", Constants.SENDPORT);
        mEtIP.setText(ip);
        mEtPort.setText(sendport);
    }

    private void initUI(View layout) {
        mEtIP = (EditText) layout.findViewById(R.id.ipaddrVal);
        mEtPort = (EditText) layout.findViewById(R.id.portVal);
        mEtMode1 = (EditText) layout.findViewById(R.id.mname1);
        mEtMode2 = (EditText) layout.findViewById(R.id.mname2);
        mEtMode3 = (EditText) layout.findViewById(R.id.mname3);
        mEtMode4 = (EditText) layout.findViewById(R.id.mname4);
    }

    /**
     * 保存数据的广播接收
     * @author Clearlove
     *
     */
    class MyBrocastReceive extends BroadcastReceiver{

        @Override
        public void onReceive(Context context, Intent intent) {
            SPUtils.put(getActivity(), Constants.IP, mEtIP.getText().toString());
            SPUtils.put(getActivity(), "Constants.SENDPORT", mEtPort.getText().toString());
        }

    }

}
