package com.txy.fragment;


import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
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
    private int mStatus = 0;// 开/关状态

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
    private ImageButton mSwitchButton;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        View layout = inflater.inflate(R.layout.fragment_air_control, null);
        initParams();
        initUI(layout);
//        initListener();
//        initAirConditionStatus();
        return layout;
    }

    /**
     * 判断空调是否开机，若光机则关闭显示
     */
    private void initAirConditionStatus() {

        if (mStatus == 0) {

        } else {

        }
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

        mSwitchButton.setOnClickListener(this);
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

        mUseAll = (ImageButton) layout.findViewById(R.id.btn_kgmode);

        mImageFanSpeedLow = (ImageView) layout.findViewById(R.id.view_fs1);
        mImageFanSpeedMid = (ImageView) layout.findViewById(R.id.view_fs2);
        mImageFanSpeedHigh = (ImageView) layout.findViewById(R.id.view_fs3);

//        mImageMode = (ImageView) layout.findViewById(R.id.view_sf);

        mNowTemperatureShow = (TextView) layout.findViewById(R.id.txt_temp);

        mSwitchButton = (ImageButton) layout.findViewById(R.id.btn_kgpower);

    }

    private void initParams() {
        Bundle bundle = getArguments();
        mPosition = bundle.getInt("position");
        mAirConditionNum = bundle.getInt("airConditionNum");
    }

    @Override
    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.btn_kgpower:
                if (mStatus == 0) {
                    mStatus = 1;
                    openAirCondition();
                } else {
                    mStatus = 0;
                    closeAirCondition();
                }
                break;

            case R.id.btn_kgwf:
                if (mStatus == 0) {
                    return;
                }
                mFanSpeed = 0;
                setFanSpeedBackGround();
                send(mPosition);
                break;
            case R.id.btn_kgzf:
                if (mStatus == 0) {
                    return;
                }
                mFanSpeed = 1;
                setFanSpeedBackGround();
                send(mPosition);
                break;
            case R.id.btn_kgdf:
                if (mStatus == 0) {
                    return;
                }
                mFanSpeed = 2;
                setFanSpeedBackGround();
                send(mPosition);
                break;
            case R.id.imgBtn_sf:
                if (mStatus == 0) {
                    return;
                }
                mMode = 0;
                setModeBackGround();
                send(mPosition);
                break;
            case R.id.imgBtn_zr:
                if (mStatus == 0) {
                    return;
                }
                mMode = 1;
                setModeBackGround();
                send(mPosition);
                break;
            case R.id.imgBtn_zl:
                if (mStatus == 0) {
                    return;
                }
                mMode = 2;
                setModeBackGround();
                send(mPosition);
                break;

            case R.id.tempup:
                if (mStatus == 0) {
                    return;
                }
                if (mNowTemperature == 30) {
                    return;
                }
                mNowTemperature++;
                upDataTemperatureShow();
                send(mPosition);
                break;
            case R.id.tempdown:
                if (mStatus == 0) {
                    return;
                }
                if (mNowTemperature == 18) {
                    return;
                }
                mNowTemperature--;
                upDataTemperatureShow();
                send(mPosition);
                break;

            case R.id.btn_kgmode:
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


    /**
     * 发送控制命令
     * @param position
     */
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

        if (mStatus == 0) {
            airCondition.status = Integer.parseInt(UdpSend.AIRCONDITION.CLOSE);
        } else {
            airCondition.status = Integer.parseInt(UdpSend.AIRCONDITION.OPEN);
        }


        String msg = new StringMerge().airConditionControl(getActivity(), UdpSend.AIRCONDITION.AIRCONDITION, "01", airCondition);
        String ip = (String) SPUtils.get(getActivity(), Constants.IP, Constants.DEFAULT_IP);
        int port =(Integer) SPUtils.get(getActivity(), Constants.SENDPORT, Constants.DEFAULT_SENDPORT);
        new Sender(msg, ip,port).send();
    }

    /**
     * 设置模式按钮的选中图片
     */
    private void setModeBackGround() {
        if (mMode == 0)
        {
            mFanSong.setBackgroundResource(R.drawable.sf2);
            mHot.setBackgroundResource(R.drawable.zr1);
            mCold.setBackgroundResource(R.drawable.zr1);

//            mImageMode.setBackgroundResource(R.drawable.sfz);
        }
        else if (mMode == 1)
        {
            mFanSong.setBackgroundResource(R.drawable.sf1);
            mHot.setBackgroundResource(R.drawable.zr2);
            mCold.setBackgroundResource(R.drawable.zl1);

//            mImageMode.setBackgroundResource(R.drawable.view_zr);
        }
        else if (mMode == 2)
        {
            mFanSong.setBackgroundResource(R.drawable.sf1);
            mHot.setBackgroundResource(R.drawable.zr1);
            mCold.setBackgroundResource(R.drawable.zl2);

//            mImageMode.setBackgroundResource(R.drawable.zlz);
        }
    }

    /**
     * 关闭空调
     */
    private void closeAirCondition() {
//        mImageMode.setBackground(null);
        mImageFanSpeedLow.setVisibility(View.INVISIBLE);
        mImageFanSpeedMid.setVisibility(View.INVISIBLE);
        mImageFanSpeedHigh.setVisibility(View.INVISIBLE);
        mNowTemperatureShow.setText("");

        mFanLow.setBackgroundResource(R.drawable.btn_wf_off);
        mFanMid.setBackgroundResource(R.drawable.btn_zf_off);
        mFanHig.setBackgroundResource(R.drawable.btn_df_off);

        mFanSong.setBackgroundResource(R.drawable.sf1);
        mHot.setBackgroundResource(R.drawable.zr1);
        mCold.setBackgroundResource(R.drawable.zl1);
    }

    private void openAirCondition() {
        setModeBackGround();
        upDataTemperatureShow();
        setFanSpeedBackGround();
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

            mImageFanSpeedLow.setVisibility(View.VISIBLE);
            mImageFanSpeedMid.setVisibility(View.INVISIBLE);
            mImageFanSpeedHigh.setVisibility(View.INVISIBLE);
        }

        else if (mFanSpeed == 1)
        {
            mFanLow.setBackgroundResource(R.drawable.btn_wf_off);
            mFanMid.setBackgroundResource(R.drawable.btn_zf_on);
            mFanHig.setBackgroundResource(R.drawable.btn_df_off);

            mImageFanSpeedLow.setVisibility(View.VISIBLE);
            mImageFanSpeedMid.setVisibility(View.VISIBLE);
            mImageFanSpeedHigh.setVisibility(View.INVISIBLE);
        }

        else if (mFanSpeed == 2)
        {
            mFanLow.setBackgroundResource(R.drawable.btn_wf_off);
            mFanMid.setBackgroundResource(R.drawable.btn_zf_off);
            mFanHig.setBackgroundResource(R.drawable.btn_df_on);

            mImageFanSpeedLow.setVisibility(View.VISIBLE);
            mImageFanSpeedMid.setVisibility(View.VISIBLE);
            mImageFanSpeedHigh.setVisibility(View.VISIBLE);
        }
    }

    class UpdateAirConditionStatus extends BroadcastReceiver{

        @Override
        public void onReceive(Context context, Intent intent) {

        }
    }
}
