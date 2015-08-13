package com.txy.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

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

    private int mFanSpeed;// 风速
    private int mMode;// 模式
    private int mNowTemperature = 22;// 当前的温度
    private int mStatus;

    private ImageButton mFanLow;
    private ImageButton mFanMid;
    private ImageButton mFanHig;
    private ImageButton mFanSong;
    private ImageButton mHot;
    private ImageButton mCold;
    private ImageView mImageFanSpeedLow;
    private ImageView mImageFanSpeedMid;
    private ImageView mImageFanSpeedHigh;
    private ImageView mImageMode;
    private ImageButton mTemperatureUp;
    private ImageButton mTemperatureDown;
    private TextView mNowTemperatureShow;
    private ImageButton mUseAll;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View layout = inflater.inflate(R.layout.fragment_tab_air_condition, null);
        initParams();
        initUI(layout);
        initListener();
        return layout;
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {

        }
    }

    private void initListener() {
        mFanLow.setOnClickListener(this);
        mFanMid.setOnClickListener(this);
        mFanHig.setOnClickListener(this);

        mFanSong.setOnClickListener(this);
        mHot.setOnClickListener(this);
        mCold.setOnClickListener(this);

        mTemperatureUp.setOnClickListener(this);
        mTemperatureDown.setOnClickListener(this);

        mUseAll.setOnClickListener(this);
    }

    private void initUI(View layout) {

        mFanLow = (ImageButton) layout.findViewById(R.id.btn_kgwf);
        mFanMid = (ImageButton) layout.findViewById(R.id.btn_kgzf);
        mFanHig = (ImageButton) layout.findViewById(R.id.btn_kgdf);

        mFanSong = (ImageButton) layout.findViewById(R.id.imgBtn_sf);
        mHot = (ImageButton) layout.findViewById(R.id.imgBtn_zr);
        mCold = (ImageButton) layout.findViewById(R.id.imgBtn_zl);

        mTemperatureUp = (ImageButton) layout.findViewById(R.id.tempup);
        mTemperatureDown = (ImageButton) layout.findViewById(R.id.tempdown);

        mUseAll = (ImageButton) layout.findViewById(R.id.imgBtn_kgyy);

        mImageFanSpeedLow = (ImageView) layout.findViewById(R.id.view_fs1);
        mImageFanSpeedMid = (ImageView) layout.findViewById(R.id.view_fs2);
        mImageFanSpeedHigh = (ImageView) layout.findViewById(R.id.view_fs3);

        mImageMode = (ImageView) layout.findViewById(R.id.view_sf);

        mNowTemperatureShow = (TextView) layout.findViewById(R.id.txt_temp);

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
                mFanSpeed = 0;
                setFanSpeedBackGround();
                send(mPosition);
                break;
            case R.id.btn_kgzf:
                mFanSpeed = 1;
                setFanSpeedBackGround();
                break;
            case R.id.btn_kgdf:
                mFanSpeed = 2;
                setFanSpeedBackGround();
                break;
            case R.id.imgBtn_sf:
                mMode = 0;
                setModeBackGround();
                break;
            case R.id.imgBtn_zr:
                mMode = 1;
                setModeBackGround();
                break;
            case R.id.imgBtn_zl:
                mMode = 2;
                setModeBackGround();
                break;

            case R.id.tempup:
                if (mNowTemperature == 29) {
                    return;
                }
                mNowTemperature++;
                upDataTemperatureShow();
                break;
            case R.id.tempdown:
                if (mNowTemperature == 22) {
                    return;
                }
                mNowTemperature--;
                upDataTemperatureShow();
                break;

            case R.id.imgBtn_kgyy:
                useToAll();
                break;
        }
    }

    /**
     * 应用到全部
     */
    private void useToAll() {

        for (int i = 0; i < mAirConditionNum; i++) {
            send(i);
        }

    }

    /**
     * 更新温度的显示
     */
    private void upDataTemperatureShow() {
        mNowTemperatureShow.setText(mNowTemperature+"°");
    }


    private void send(int position) {
        AirCondition airCondition = new AirCondition();
        airCondition.position = position;
        airCondition.temperature = mNowTemperature;

        switch (mFanSpeed) {
            case 0:
                airCondition.fanRate_L = Integer.parseInt(UdpSend.AIRCONDITION.FAN_RATE_LOW_L);
                airCondition.fanRate_H = Integer.parseInt(UdpSend.AIRCONDITION.FAN_RATE_LOW_H);
                break;
            case 1:
                airCondition.fanRate_L = Integer.parseInt(UdpSend.AIRCONDITION.FAN_RATE_MID_L);
                airCondition.fanRate_H = Integer.parseInt(UdpSend.AIRCONDITION.FAN_RATE_MID_H);
                break;

            case 2:
                airCondition.fanRate_L = Integer.parseInt(UdpSend.AIRCONDITION.FAN_RATE_HIGH_L);
                airCondition.fanRate_H = Integer.parseInt(UdpSend.AIRCONDITION.FAN_RATE_HIGH_H);
                break;
        }

        switch (mMode) {
            case 0:
                airCondition.fanRate_L = Integer.parseInt(UdpSend.AIRCONDITION.FAN_RATE_HOT_L);
                airCondition.fanRate_H = Integer.parseInt(UdpSend.AIRCONDITION.FAN_RATE_HOT_H);
                break;

            case 1:
                airCondition.mode = Integer.parseInt(UdpSend.AIRCONDITION.HOT);
                break;

            case 2:
                airCondition.mode = Integer.parseInt(UdpSend.AIRCONDITION.COLD);
                break;
        }


        airCondition.status = Integer.parseInt(UdpSend.AIRCONDITION.OPEN);

        String msg = new StringMerge().airConditionControl(UdpSend.AIRCONDITION.AIRCONDITION, "01", airCondition);
        String ip = (String) SPUtils.get(getActivity(), Constants.IP, Constants.DEFAULT_IP);
        int port =(Integer) SPUtils.get(getActivity(), Constants.SENDPORT, Constants.DEFAULT_SENDPORT);
        new Sender(msg, ip,port).send();
    }


    private void setModeBackGround() {
        if (mMode == 0)
        {
            mFanSong.setBackgroundResource(R.drawable.btn_sf_on);
            mHot.setBackgroundResource(R.drawable.btn_zr_off);
            mCold.setBackgroundResource(R.drawable.btn_zl_off);

            mImageMode.setBackgroundResource(R.drawable.sfz);
        }
        else if (mMode == 1)
        {
            mFanSong.setBackgroundResource(R.drawable.btn_sf_off);
            mHot.setBackgroundResource(R.drawable.btn_zr_on);
            mCold.setBackgroundResource(R.drawable.btn_zl_off);

            mImageMode.setBackgroundResource(R.drawable.view_zr);
        }
        else if (mMode == 2)
        {
            mFanSong.setBackgroundResource(R.drawable.btn_sf_off);
            mHot.setBackgroundResource(R.drawable.btn_zr_off);
            mCold.setBackgroundResource(R.drawable.btn_zl_on);

            mImageMode.setBackgroundResource(R.drawable.zlz);
        }
    }

    /**
     * 设置风速的背景
     */
    private void setFanSpeedBackGround() {
        if (mFanSpeed == 0)
        {
            mFanLow.setBackgroundResource(R.drawable.btn_wf_on);
            mFanMid.setBackgroundResource(R.drawable.btn_zf_off);
            mFanHig.setBackgroundResource(R.drawable.btn_df_off);

            mImageFanSpeedMid.setVisibility(View.INVISIBLE);
            mImageFanSpeedHigh.setVisibility(View.INVISIBLE);
        }

        else if (mFanSpeed == 1)
        {
            mFanLow.setBackgroundResource(R.drawable.btn_wf_off);
            mFanMid.setBackgroundResource(R.drawable.btn_zf_on);
            mFanHig.setBackgroundResource(R.drawable.btn_df_off);

            mImageFanSpeedMid.setVisibility(View.VISIBLE);
            mImageFanSpeedHigh.setVisibility(View.INVISIBLE);
        }

        else if (mFanSpeed == 2)
        {
            mFanLow.setBackgroundResource(R.drawable.btn_wf_off);
            mFanMid.setBackgroundResource(R.drawable.btn_zf_off);
            mFanHig.setBackgroundResource(R.drawable.btn_df_on);

            mImageFanSpeedMid.setVisibility(View.VISIBLE);
            mImageFanSpeedHigh.setVisibility(View.VISIBLE);
        }
    }
}
