package com.txy.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import com.txy.constants.Constants;
import com.txy.database.AirCondition;
import com.txy.txy_mcs.R;
import com.txy.udp.InitData.StringMerge;
import com.txy.udp.InitData.UdpSend;
import com.txy.udp.Sender;
import com.txy.utils.SPUtils;

/**
 * A simple {@link Fragment} subclass.
 */
public class AirConditionFragment extends Fragment implements View.OnClickListener {

    private int mPosition;// 第几台空调
    private int mAirConditionNum;// 空调的总数
    private ImageButton mFanLow;
    private ImageButton mFanMid;
    private ImageButton mFanHig;
    private ImageButton mFanSong;
    private ImageButton mHot;
    private ImageButton mCold;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View layout = inflater.inflate(R.layout.fragment_tab_air_condition, null);
        initParams();
        initUI(layout);
        initListener();
        return layout;
    }

    private void initListener() {
        mFanLow.setOnClickListener(this);
        mFanMid.setOnClickListener(this);
        mFanHig.setOnClickListener(this);

        mFanSong.setOnClickListener(this);
        mHot.setOnClickListener(this);
        mCold.setOnClickListener(this);
    }

    private void initUI(View layout) {

        mFanLow = (ImageButton) layout.findViewById(R.id.btn_kgwf);
        mFanMid = (ImageButton) layout.findViewById(R.id.btn_kgzf);
        mFanHig = (ImageButton) layout.findViewById(R.id.btn_kgdf);

        mFanSong = (ImageButton) layout.findViewById(R.id.imgBtn_sf);
        mHot = (ImageButton) layout.findViewById(R.id.imgBtn_zr);
        mCold = (ImageButton) layout.findViewById(R.id.imgBtn_zl);

    }

    private void initParams() {
        Bundle bundle = getArguments();
        mPosition = bundle.getInt("position");
        mAirConditionNum = bundle.getInt("airConditionNum");
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_kgwf:
                AirCondition airCondition = new AirCondition();
                airCondition.position = mPosition;
                airCondition.temperature = Integer.parseInt(UdpSend.AIRCONDITION.TEMPERATURE22);
                airCondition.fanRate_L = Integer.parseInt(UdpSend.AIRCONDITION.FAN_RATE_MID_L);
                airCondition.fanRate_H = Integer.parseInt(UdpSend.AIRCONDITION.FAN_RATE_MID_H);
                airCondition.mode = Integer.parseInt(UdpSend.AIRCONDITION.COLD);
                airCondition.status = Integer.parseInt(UdpSend.AIRCONDITION.OPEN);

                String msg = new StringMerge().airConditionControl(UdpSend.AIRCONDITION.AIRCONDITION, "01", airCondition);
                String ip = (String) SPUtils.get(getActivity(), Constants.IP, Constants.DEFAULT_IP);
                int port =(Integer) SPUtils.get(getActivity(), Constants.SENDPORT, Constants.DEFAULT_SENDPORT);
                new Sender(msg, ip,port).send();
                break;
            case R.id.btn_kgzf:
                break;
            case R.id.btn_kgdf:
                break;
            case R.id.imgBtn_sf:
                break;
            case R.id.imgBtn_zr:
                break;
            case R.id.imgBtn_zl:
                break;
        }
    }
}
