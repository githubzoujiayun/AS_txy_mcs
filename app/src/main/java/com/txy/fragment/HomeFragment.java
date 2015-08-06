package com.txy.fragment;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.net.SocketException;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.RelativeLayout;

import com.txy.constants.Constants;
import com.txy.txy_mcs.R;
import com.txy.udp.Sender;
import com.txy.util.ParseUtil;
import com.txy.util.SPUtils;

/**
 * A simple {@link android.support.v4.app.Fragment} subclass.
 *
 */
public class HomeFragment extends Fragment implements OnClickListener {

    // 界面上的Button
    private ImageButton mSaveModeButton;
    private ImageButton mMeetModeButton;
    private ImageButton mPlayModeButton;
    private ImageButton mShowModeButton;
    private ImageButton mNightModeButton;
    private ImageButton mDayModeButton;
    private ImageButton mPowerButton;

    private int mSituationMode;// 四种情景模式
    private int mMode;// 白天、夜晚模式
    private ImageButton mOkButton;
    private ImageButton mCancelButton;
    private Dialog mDialog;
    private byte cl;
    private byte data[] = new byte[32];

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View layout = inflater.inflate(R.layout.fragment_home, null);
        initUI(layout);
        initListener();
        initStatus();
        return layout;
    }

    private void initStatus() {
        mMode = (Integer) SPUtils.get(getActivity(), Constants.SP.MODE, 0);
        mSituationMode = (Integer) SPUtils.get(getActivity(), Constants.SP.SITUATION, 0);
        setMode(mMode);
        modeSetBG(mSituationMode);
    }

    /**
     * 为按键注册监听事件
     */
    private void initListener() {
        mSaveModeButton.setOnClickListener(this);
        mMeetModeButton.setOnClickListener(this);
        mPlayModeButton.setOnClickListener(this);
        mShowModeButton.setOnClickListener(this);
        mNightModeButton.setOnClickListener(this);
        mDayModeButton.setOnClickListener(this);
        mPowerButton.setOnClickListener(this);
    }

    /**
     * 找到界面上所有的控件
     *
     * @param layout
     */
    private void initUI(View layout) {
        mSaveModeButton = (ImageButton) layout.findViewById(R.id.btn_savemode);
        mMeetModeButton = (ImageButton) layout.findViewById(R.id.btn_meetmode);
        mPlayModeButton = (ImageButton) layout.findViewById(R.id.btn_playmode);
        mShowModeButton = (ImageButton) layout.findViewById(R.id.btn_showmode);
        mNightModeButton = (ImageButton) layout.findViewById(R.id.btn_yw);
        mDayModeButton = (ImageButton) layout.findViewById(R.id.btn_bt);
        mPowerButton = (ImageButton) layout.findViewById(R.id.btn_powerdown);
    }

    @Override
    public void onClick(View v) {
        situationChange(v);
        modeChange(v);
        saveNowMode();
    }

    /**
     * 记忆当前的模式
     */
    private void saveNowMode() {
        SPUtils.put(getActivity(), Constants.SP.MODE, mMode);
        SPUtils.put(getActivity(), Constants.SP.SITUATION, mSituationMode);
    }

    /**
     * 白天、夜晚两种模式的切换
     *
     * @param v
     */
    private void modeChange(View v) {
        switch (v.getId()) {
            case R.id.btn_yw:
                mMode = Constants.NIGHTMODE;
                break;

            case R.id.btn_bt:
                mMode = Constants.DAYMODE;
                break;

            case R.id.btn_powerdown:
                showDialog();
                break;

            case R.id.btn_powerdownok:
                break;
            case R.id.btn_powerdowncancel:
                mDialog.dismiss();
                break;

            default:
                break;
        }
        setMode(mMode);
    }

    /**
     * 弹出自定义对话框
     */
    private void showDialog() {
        LayoutInflater inflater = LayoutInflater.from(getActivity());
        RelativeLayout layout = (RelativeLayout) inflater.inflate(
                R.layout.dialog_powerdown, null);
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
     * 四种场景的改变
     *
     * @param v
     */
    private void situationChange(View v) {
        byte cl = 0;
        switch (v.getId()) {
            case R.id.btn_savemode:// 节能模式
                mSituationMode = Constants.SAVEMODE;
                send();
                break;
            case R.id.btn_meetmode:// 会议模式
                mSituationMode = Constants.MEETMODE;
                break;
            case R.id.btn_playmode:// 投影模式
                mSituationMode = Constants.PLAYMODE;
                break;
            case R.id.btn_showmode:// 展示模式
                mSituationMode = Constants.SHOWMODE;
                break;
            default:
                break;
        }
        modeSetBG(mSituationMode);
    }

    private void send() {
        StringBuffer sb2 = new StringBuffer();
        sb2.append(String.valueOf("01")); // 00关 01开 10停
        sb2.append(String.valueOf("000"));// 预留
        sb2.append(String.valueOf("001")); // 00 1路 01 2路 10 3路

        try {
            cl = ParseUtil.getByteFromEigthBitsString(sb2.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
        final byte[] data1 = new byte[] { (byte) 0x1e, (byte) 0x1e,
                (byte) 0x48, (byte) 0x4d, (byte) 0x49, (byte) 0x53,
                (byte) 0x00, (byte) 0x08, (byte) 0x00, (byte) 0x00,
                (byte) 0xff, (byte) 0xff, (byte) 0xff, (byte) 0xff,
                (byte) 0x00, (byte) 0x00, (byte) 0x5d, (byte) 0x01,
                (byte) 0xff, (byte) 0xff, (byte) 0xff, (byte) 0xff,
                (byte) 0x08, (byte) 0x00, (byte) 0x00, (byte) 0x00,
                (byte) 0x00, cl, (byte) 0x00, (byte) 0x00 };
        new Thread(new Sender(data1, Constants.IP, Constants.DEFAULT_SENDPORT)).start();
    }

    /**
     * 白天、夜晚模式
     *
     * @param mode
     *
     */
    private void setMode(int mode) {
        switch (mode) {
            case 0:// 白天模式
                mDayModeButton.setBackgroundResource(R.drawable.btn_bt_on);
                mNightModeButton.setBackgroundResource(R.drawable.btn_yw_off);
                break;

            case 1:// 夜晚模式
                mDayModeButton.setBackgroundResource(R.drawable.btn_bt_off);
                mNightModeButton.setBackgroundResource(R.drawable.btn_yw_on);
                break;

            default:
                break;
        }
    }

    /**
     * 四个场景背景的设置
     *
     * @param mode
     */
    private void modeSetBG(int mode) {
        switch (mode) {
            case 0:// 节能模式
                mSaveModeButton.setBackgroundResource(R.drawable.mode_save_on);
                mMeetModeButton.setBackgroundResource(R.drawable.mode_meet_off);
                mPlayModeButton.setBackgroundResource(R.drawable.mode_play_off);
                mShowModeButton.setBackgroundResource(R.drawable.mode_show_off);
                break;
            case 1:// 会议模式
                mSaveModeButton.setBackgroundResource(R.drawable.mode_save_off);
                mMeetModeButton.setBackgroundResource(R.drawable.mode_meet_on);
                mPlayModeButton.setBackgroundResource(R.drawable.mode_play_off);
                mShowModeButton.setBackgroundResource(R.drawable.mode_show_off);
                break;
            case 2:// 投影模式
                mSaveModeButton.setBackgroundResource(R.drawable.mode_save_off);
                mMeetModeButton.setBackgroundResource(R.drawable.mode_meet_off);
                mPlayModeButton.setBackgroundResource(R.drawable.mode_play_on);
                mShowModeButton.setBackgroundResource(R.drawable.mode_show_off);
                break;
            case 3:// 展示模式
                mSaveModeButton.setBackgroundResource(R.drawable.mode_save_off);
                mMeetModeButton.setBackgroundResource(R.drawable.mode_meet_off);
                mPlayModeButton.setBackgroundResource(R.drawable.mode_play_off);
                mShowModeButton.setBackgroundResource(R.drawable.mode_show_on);
                break;

            default:
                break;
        }
    }

}
