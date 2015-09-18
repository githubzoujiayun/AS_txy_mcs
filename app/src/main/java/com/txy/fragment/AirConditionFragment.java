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
import android.widget.RadioButton;
import android.widget.RadioGroup;
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
public class AirConditionFragment extends Fragment implements View.OnClickListener, RadioGroup.OnCheckedChangeListener {

    private int mPosition;// 第几台空调
    private int mAirConditionNum;// 空调的总数

    private int mFanSpeed;// 风速
    private int mMode;// 模式
    private int mNowTemperature = 16;// 当前的温度
    private int mStatus = 0;// 开/关状态

    private RadioButton mFanLow;
    private RadioButton mFanMid;
    private RadioButton mFanHig;
    private RadioButton mFanSong;
    private RadioButton mHot;
    private RadioButton mCold;
    private ImageView mImageFanSpeedLow;
    private ImageView mImageFanSpeedMid;
    private ImageView mImageFanSpeedHigh;
    private ImageButton mTemperatureUp;
    private ImageButton mTemperatureDown;
    private TextView mNowTemperatureShow;
    private ImageButton mUseAll;
    private ImageButton mSwitchButton;
    private RadioGroup mModeRadioGroup;
    private RadioGroup mRadioGroup;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        View layout = inflater.inflate(R.layout.fragment_air_control, null);
        initParams();
        initUI(layout);
        initListener();
        initAirConditionStatus();
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

        mTemperatureUp.setOnClickListener(this);
        mTemperatureDown.setOnClickListener(this);
        mUseAll.setOnClickListener(this);
        mSwitchButton.setOnClickListener(this);

        mModeRadioGroup.setOnCheckedChangeListener(this);

    }

    private void initUI(View layout) {

        mFanLow = (RadioButton) layout.findViewById(R.id.btn_kgwf);
        mFanMid = (RadioButton) layout.findViewById(R.id.btn_kgzf);
        mFanHig = (RadioButton) layout.findViewById(R.id.btn_kgdf);

        mFanSong = (RadioButton) layout.findViewById(R.id.imgBtn_sf);
        mHot = (RadioButton) layout.findViewById(R.id.imgBtn_zr);
        mCold = (RadioButton) layout.findViewById(R.id.imgBtn_zl);

        mTemperatureUp = (ImageButton) layout.findViewById(R.id.tempup);
        mTemperatureDown = (ImageButton) layout.findViewById(R.id.tempdown);

        mUseAll = (ImageButton) layout.findViewById(R.id.btn_kgmode);

        mImageFanSpeedLow = (ImageView) layout.findViewById(R.id.view_fs1);
        mImageFanSpeedMid = (ImageView) layout.findViewById(R.id.view_fs2);
        mImageFanSpeedHigh = (ImageView) layout.findViewById(R.id.view_fs3);

        mNowTemperatureShow = (TextView) layout.findViewById(R.id.tv_show_tempeture);

        mSwitchButton = (ImageButton) layout.findViewById(R.id.btn_kgpower);

        mModeRadioGroup = (RadioGroup) layout.findViewById(R.id.mode_radioGroup);
        mRadioGroup = (RadioGroup) layout.findViewById(R.id.radioGroup);

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

    private void setFanSpeedBackGround() {
        if (mFanSpeed == 0)
        {
            mImageFanSpeedLow.setVisibility(View.VISIBLE);
            mImageFanSpeedMid.setVisibility(View.INVISIBLE);
            mImageFanSpeedHigh.setVisibility(View.INVISIBLE);
        }

        else if (mFanSpeed == 1)
        {
            mImageFanSpeedLow.setVisibility(View.VISIBLE);
            mImageFanSpeedMid.setVisibility(View.VISIBLE);
            mImageFanSpeedHigh.setVisibility(View.INVISIBLE);
        }

        else if (mFanSpeed == 2)
        {

            mImageFanSpeedLow.setVisibility(View.VISIBLE);
            mImageFanSpeedMid.setVisibility(View.VISIBLE);
            mImageFanSpeedHigh.setVisibility(View.VISIBLE);
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
        mNowTemperatureShow.setText(mNowTemperature + "°");
    }


    /**
     * 发送控制命令
     * @param position
     */
    private void send(int position) {
        AirCondition airCondition = new AirCondition();
        airCondition.position = position;
        switch (mNowTemperature) {
            case 16:
                airCondition.temperature = Integer.parseInt(UdpSend.AIRCONDITION.TEMPERATURE16);
                break;
            case 17:
                airCondition.temperature = Integer.parseInt(UdpSend.AIRCONDITION.TEMPERATURE17);
                break;
            case 18:
                airCondition.temperature = Integer.parseInt(UdpSend.AIRCONDITION.TEMPERATURE18);
                break;
            case 19:
                airCondition.temperature = Integer.parseInt(UdpSend.AIRCONDITION.TEMPERATURE19);
                break;
            case 20:
                airCondition.temperature = Integer.parseInt(UdpSend.AIRCONDITION.TEMPERATURE20);
                break;
            case 21:
                airCondition.temperature = Integer.parseInt(UdpSend.AIRCONDITION.TEMPERATURE21);
                break;
            case 22:
                airCondition.temperature = Integer.parseInt(UdpSend.AIRCONDITION.TEMPERATURE22);
                break;
            case 23:
                airCondition.temperature = Integer.parseInt(UdpSend.AIRCONDITION.TEMPERATURE23);
                break;
            case 24:
                airCondition.temperature = Integer.parseInt(UdpSend.AIRCONDITION.TEMPERATURE24);
                break;
            case 25:
                airCondition.temperature = Integer.parseInt(UdpSend.AIRCONDITION.TEMPERATURE25);
                break;
            case 26:
                airCondition.temperature = Integer.parseInt(UdpSend.AIRCONDITION.TEMPERATURE26);
                break;
            case 27:
                airCondition.temperature = Integer.parseInt(UdpSend.AIRCONDITION.TEMPERATURE27);
                break;
            case 28:
                airCondition.temperature = Integer.parseInt(UdpSend.AIRCONDITION.TEMPERATURE28);
                break;
            case 29:
                airCondition.temperature = Integer.parseInt(UdpSend.AIRCONDITION.TEMPERATURE29);
                break;
            case 30:
                airCondition.temperature = Integer.parseInt(UdpSend.AIRCONDITION.TEMPERATURE30);
                break;
        }

        switch (mFanSpeed) {
            case 0:
                airCondition.fanRate = Integer.parseInt(UdpSend.AIRCONDITION.FAN_RATE_LOW);
                break;
            case 1:
                airCondition.fanRate = Integer.parseInt(UdpSend.AIRCONDITION.FAN_RATE_MID);
                break;

            case 2:
                airCondition.fanRate = Integer.parseInt(UdpSend.AIRCONDITION.FAN_RATE_HIGH);
                break;
        }

        switch (mMode) {
            case 0:
                airCondition.fanRate = Integer.parseInt(UdpSend.AIRCONDITION.FAN_RATE_HOT);
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
     * 关闭空调
     */
    private void closeAirCondition() {

        mRadioGroup.setOnCheckedChangeListener(null);
        mModeRadioGroup.setOnCheckedChangeListener(null);

        mImageFanSpeedLow.setVisibility(View.INVISIBLE);
        mImageFanSpeedMid.setVisibility(View.INVISIBLE);
        mImageFanSpeedHigh.setVisibility(View.INVISIBLE);
        mNowTemperatureShow.setText("");

        mRadioGroup.clearCheck();
        mModeRadioGroup.clearCheck();
    }

    private void openAirCondition() {
        setCheck();
        upDataTemperatureShow();
        setFanSpeedBackGround();

    }

    private void setCheck() {
        mModeRadioGroup.check(mModeRadioGroup.getChildAt(mMode).getId());
        mRadioGroup.check(mRadioGroup.getChildAt(mFanSpeed).getId());

        mRadioGroup.setOnCheckedChangeListener(this);
        mModeRadioGroup.setOnCheckedChangeListener(this);
    }


    @Override
    public void onCheckedChanged(RadioGroup radioGroup, int position) {

        if (mStatus == 0) {
            return;
        }

        int id = radioGroup.getId();
        if (id == R.id.radioGroup) {
            int checkedRadioButtonId = radioGroup.getCheckedRadioButtonId();
            if (checkedRadioButtonId == R.id.btn_kgwf) {
                mFanSpeed = 0;
            } else if (checkedRadioButtonId == R.id.btn_kgzf) {
                mFanSpeed = 1;
            } else if (checkedRadioButtonId == R.id.btn_kgdf) {
                mFanSpeed = 2;
            }
            setFanSpeedBackGround();
        } else if (id == R.id.mode_radioGroup) {
            int checkedRadioButtonId = radioGroup.getCheckedRadioButtonId();
            if (checkedRadioButtonId == R.id.imgBtn_zl) {
                mMode = 2;
            } else if (checkedRadioButtonId == R.id.imgBtn_zr) {
                mMode = 1;
            } else if (checkedRadioButtonId == R.id.imgBtn_sf) {
                mMode = 0;
            }
        }
        send(mPosition);
    }


    class UpdateAirConditionStatus extends BroadcastReceiver{

        @Override
        public void onReceive(Context context, Intent intent) {

        }
    }
}
