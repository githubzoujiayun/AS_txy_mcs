package com.txy.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.LocalBroadcastManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;

import com.txy.constants.Constants;
import com.txy.txy_mcs.R;

/**
 * A simple {@link android.support.v4.app.Fragment} subclass.
 *
 */
public class HomeSetFragment extends Fragment implements OnClickListener {

    private Button mSaveSetButton;
    private Button mMeetSetButton;
    private Button mProjectionSetButton;
    private Button mBaseSetButton;
    private Button mNameSetButton;
    private Button mTimeSetButton;
    private Button mOFFSetButton;
    private Button mChannelSetButton;
    private Button mShowSetButton;
    private int mPosition;

    public HomeSetFragment(){}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View layout = inflater.inflate(R.layout.fragment_set, container, false);
        initUI(layout);
        initListener();
        return layout;
    }

    private void initListener() {
        mSaveSetButton.setOnClickListener(this);
        mMeetSetButton.setOnClickListener(this);
        mProjectionSetButton.setOnClickListener(this);
        mShowSetButton.setOnClickListener(this);
        mBaseSetButton.setOnClickListener(this);
        mNameSetButton.setOnClickListener(this);
        mTimeSetButton.setOnClickListener(this);
        mOFFSetButton.setOnClickListener(this);
        mChannelSetButton.setOnClickListener(this);
    }

    private void initUI(View layout) {
        mSaveSetButton = (Button) layout.findViewById(R.id.btn_mode1set);
        mMeetSetButton = (Button) layout.findViewById(R.id.btn_mode2set);
        mProjectionSetButton = (Button) layout.findViewById(R.id.btn_mode3set);
        mShowSetButton = (Button) layout.findViewById(R.id.btn_mode4set);
        mBaseSetButton = (Button) layout.findViewById(R.id.btn_baseset);
        mNameSetButton = (Button) layout.findViewById(R.id.btn_powerset);
        mTimeSetButton = (Button) layout.findViewById(R.id.btn_tyset);
        mOFFSetButton = (Button) layout.findViewById(R.id.btn_offset);
        mChannelSetButton = (Button) layout.findViewById(R.id.btn_pdset);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_mode1set:
                mPosition = 0;
                break;
            case R.id.btn_mode2set:
                mPosition = 1;
                break;
            case R.id.btn_mode3set:
                mPosition = 2;
                break;
            case R.id.btn_mode4set:
                mPosition = 3;
                break;
            case R.id.btn_baseset:
                mPosition = 4;
                break;
            case R.id.btn_powerset:
                mPosition = 5;
                break;
            case R.id.btn_tyset:
                mPosition = 6;
                break;
            case R.id.btn_offset:
                mPosition = 7;
                break;
            case R.id.btn_pdset:
                mPosition = 8;
                break;

            default:
                break;
        }
        sendBro();
    }

    /**
     * 发送广播跟Activity通信
     */
    private void sendBro() {
        Intent intent = new Intent(Constants.BROADCAST.SETFRAGMENT2SET);
        intent.putExtra("position", mPosition);
        LocalBroadcastManager.getInstance(getActivity()).sendBroadcast(intent);
    }



}
