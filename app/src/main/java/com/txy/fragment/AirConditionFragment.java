package com.txy.fragment;


import android.app.AlertDialog;
import android.app.Application;
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
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.txy.application.MyApplication;
import com.txy.constants.Constants;
import com.txy.database.AirCondition;
import com.txy.database.BoardRoomDB;
import com.txy.database.httpdata.MachineCode;
import com.txy.database.httpdata.SetAirEntity;
import com.txy.txy_mcs.R;
import com.txy.udp.InitData.StringMerge;
import com.txy.udp.InitData.UdpSend;
import com.txy.udp.Sender;
import com.txy.utils.ParseUtil;
import com.txy.utils.SPUtils;

import java.util.List;

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
    private boolean isVisible = false;

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
    private UpdateAirConditionStatus receive;
    private ImageButton mOkButton;
    private ImageButton mCancelButton;
    private AlertDialog mDialog;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        View layout = inflater.inflate(R.layout.fragment_air_control, null);
        initParams();
        initUI(layout);
        initListener();
//        initAirConditionStatus();
        getAllEquipStatus();
        return layout;
    }

    /**
     * 判断空调是否开机，若光机则关闭显示
     */
    private void initAirConditionStatus() {
        MyApplication application = (MyApplication) getActivity().getApplication();
        AirCondition airConditionStatus = BoardRoomDB.getAirConditionStatus(application.getNowRoomId(), mPosition);
        if (airConditionStatus == null) {
            return;
        }
        if (airConditionStatus.status == 0) {
            return;
        } else {
            if (UdpSend.AIRCONDITION.COLD.equals(""+airConditionStatus.mode)) {
                mMode = 2;
            } else if (UdpSend.AIRCONDITION.HOT.equals(""+airConditionStatus.mode)){
                mMode = 1;
            } else if (UdpSend.AIRCONDITION.FAN_RATE_HOT.equals(""+airConditionStatus.mode)){
                mMode = 0;
            }

            if (UdpSend.AIRCONDITION.FAN_RATE_LOW.equals(""+airConditionStatus.fanRate)) {
                mFanSpeed = 0;
            } else if (UdpSend.AIRCONDITION.FAN_RATE_MID.equals(""+airConditionStatus.fanRate)){
                mFanSpeed = 1;
            } else if (UdpSend.AIRCONDITION.FAN_RATE_HIGH.equals(""+airConditionStatus.fanRate)){
                mFanSpeed = 2;
            }

            mNowTemperature = airConditionStatus.temperature + 16;
            mStatus = 1;
            openAirCondition();
        }
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {
            isVisible = true;
            getAllEquipStatus();
        } else {
            isVisible = false;
        }
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

//        MyApplication application = (MyApplication) getActivity().getApplication();
//        List<MachineCode> machineCodeList = BoardRoomDB.getMachineCodeList();
//        MachineCode machineCode = machineCodeList.get(application.getPosition());
//        List<SetAirEntity> setAir = BoardRoomDB.getSetAir(machineCode.getTypeId());
//        if (setAir == null) {
//            return;
//        }
//        SetAirEntity setAirEntity = setAir.get(mPosition);
//        String mode = setAirEntity.getMode();
//        if (mode.equalsIgnoreCase("0")) {
//            mMode = 0;
//        } else if (mode.equalsIgnoreCase("1")) {
//            mMode = 1;
//        } else {
//            mMode = 2;
//        }
//        if (mMode == 0) {
//            mNowTemperature = 16;
//        } else {
//            String temp = setAirEntity.getTemp();
//            mNowTemperature = Integer.parseInt(temp);
//        }
//
//        String speed = setAirEntity.getSpeed();
//        if (speed.equalsIgnoreCase("00")) {
//            mFanSpeed = 0;
//        } else if (speed.equalsIgnoreCase("01")) {
//            mFanSpeed = 1;
//        } else if (speed.equalsIgnoreCase("10")) {
//            mFanSpeed = 2;
//        }

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
                if (mNowTemperature >= 30) {
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
                if (mNowTemperature <= 16) {
                    return;
                }
                mNowTemperature--;
                upDataTemperatureShow();
                send(mPosition);
                break;

            case R.id.btn_kgmode:
                showDialog();
                break;

            case R.id.btn_powerdownok:
                useToAll();
                mDialog.dismiss();
                break;
            case R.id.btn_powerdowncancel:
                mDialog.dismiss();
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
     * 弹出自定义对话框
     */
    private void showDialog() {
        LayoutInflater inflater = LayoutInflater.from(getActivity());
        RelativeLayout layout = (RelativeLayout) inflater.inflate(
                R.layout.dialog_powerdown, null);
        layout.findViewById(R.id.pauseWindow).setBackground(getActivity().getResources().getDrawable(R.mipmap.user_all_air_dialog));
        mOkButton = (ImageButton) layout.findViewById(R.id.btn_powerdownok);
        mCancelButton = (ImageButton) layout
                .findViewById(R.id.btn_powerdowncancel);

        mDialog = new AlertDialog.Builder(getActivity()).create();
        mDialog.setCancelable(false);
        mDialog.show();
        mDialog.getWindow().setContentView(layout);
        mOkButton.setOnClickListener(this);
        mCancelButton.setOnClickListener(this);
    }

    /**
     * 更新温度的显示
     */
    private void upDataTemperatureShow() {
        mNowTemperatureShow.setVisibility(View.VISIBLE);
        mNowTemperatureShow.setText(mNowTemperature + "°");
    }


    /**
     * 发送控制命令
     * @param position
     */
    private void send(int position) {

        MyApplication application = (MyApplication) getActivity().getApplication();
        int nowRoomId = application.getNowRoomId();

        AirCondition airCondition = new AirCondition();

        airCondition.roomId = nowRoomId;
        airCondition.position = position;
        position += 1;
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
            case 2:
                airCondition.temperature = Integer.parseInt(UdpSend.AIRCONDITION.FAN_RATE_HOT);
                break;

            case 1:
                airCondition.mode = Integer.parseInt(UdpSend.AIRCONDITION.HOT);
                break;

            case 0:
                airCondition.mode = Integer.parseInt(UdpSend.AIRCONDITION.COLD);
                break;
        }

        if (mStatus == 0) {
            airCondition.status = Integer.parseInt(UdpSend.AIRCONDITION.CLOSE);
        } else {
            airCondition.status = Integer.parseInt(UdpSend.AIRCONDITION.OPEN);
        }

        BoardRoomDB.saveAirConditionStatus(airCondition);
        String msg = new StringMerge().airConditionControl(getActivity(), UdpSend.AIRCONDITION.AIRCONDITION, "0"+position, airCondition);
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
        View childAt = mRadioGroup.getChildAt(mFanSpeed);
        int id = childAt.getId();
        mRadioGroup.check(id);

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
                mMode = 0;
            } else if (checkedRadioButtonId == R.id.imgBtn_zr) {
                mMode = 1;
            } else if (checkedRadioButtonId == R.id.imgBtn_sf) {
                mMode = 2;
            }
        }
        send(mPosition);
    }

    @Override
    public void onStart() {
        super.onStart();
        receive = new UpdateAirConditionStatus();
        IntentFilter filter = new IntentFilter("txPark.updateEquipStatus");
        LocalBroadcastManager.getInstance(getActivity()).registerReceiver(receive, filter);
    }

    @Override
    public void onPause() {
        super.onPause();
        LocalBroadcastManager.getInstance(getActivity()).unregisterReceiver(receive);
    }

    class UpdateAirConditionStatus extends BroadcastReceiver{

        @Override
        public void onReceive(Context context, Intent intent) {
//            if (!isVisible) {
//                return;
//            }
            String equipStatus = intent.getStringExtra("equipStatus");
            String substring = "";
            if (mPosition == 0) {
                substring = equipStatus.substring(90, 107).replaceAll(" ","");
            } else if (mPosition == 1) {
                substring = equipStatus.substring(108, 125).replaceAll(" ", "");
            } else if (mPosition == 2) {
                substring = equipStatus.substring(126, 143).replaceAll(" ", "");
            } else if (mPosition == 3) {
                substring = equipStatus.substring(144, 162).replaceAll(" ", "");
            }
            String switchStatus = substring.substring(0, 2);
            if (switchStatus.equals("00")) {
                mStatus = 0;
                closeAirCondition();
                return;
            } else {
                switchStatus = substring.substring(4, 6);
                if (switchStatus.equals("00")) {
                    mMode = 0;
                } else if (switchStatus.equals("01")){
                    mMode = 1;
                } else if (switchStatus.equals("02")){
                    mMode = 2;
                }

                switchStatus = substring.substring(6, 8);
                if (switchStatus.equals("00")) {
                    mFanSpeed = 0;
                } else if (switchStatus.equals("01")){
                    mFanSpeed = 0;
                } else if (switchStatus.equals("02")){
                    mFanSpeed = 1;
                } else if (switchStatus.equals("03")){
                    mFanSpeed = 2;
                }
                int stringToInt = ParseUtil.getStringToInt(substring.substring(10, 11));
                stringToInt = stringToInt * 16;
                int stringToInt1 = ParseUtil.getStringToInt(substring.substring(11, 12));
                mNowTemperature = stringToInt + stringToInt1;

                mStatus = 1;


                if (mModeRadioGroup == null || mRadioGroup == null) {
                    return;
                }
                openAirCondition();
            }

        }
    }
}
